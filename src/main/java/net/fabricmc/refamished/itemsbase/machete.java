package net.fabricmc.refamished.itemsbase;

import btw.block.BTWBlocks;
import btw.block.tileentity.PlacedToolTileEntity;
import btw.entity.attribute.AttributeOperation;
import btw.item.PlaceableAsItem;
import btw.util.MiscUtils;
import btw.world.util.BlockPos;
import btw.world.util.WorldUtils;
import com.google.common.collect.Multimap;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public class machete extends Item implements PlaceableAsItem
{
    public float m_iWeaponDamage;
    public float m_iReach;
    public EnumToolMaterial m_eMaterial;

    public machete(int iItemID, float weaponDamage, float reachValue, EnumToolMaterial material)
    {
        super(iItemID);
        setFull3D();
        m_iWeaponDamage = weaponDamage;
        m_iReach = reachValue;
        m_eMaterial = material;
        this.setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabCombat);
        setMaxDamage(material.getMaxUses());
    }

    public float func_82803_g() {
        return m_iWeaponDamage;
    }

    @Override
    public boolean isDamagedInCrafting() {
        return true;
    }

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onCreated(par1ItemStack,par2World,par3EntityPlayer);
        int maxDurability = par1ItemStack.getMaxDamage();
        int experience = (int) (maxDurability/7);

        SkillManager.addExperience(par3EntityPlayer,"Artisanry", experience);
        if (!par2World.isRemote)
        {
            if (!par1ItemStack.hasTagCompound()) {
                par1ItemStack.setTagCompound(new NBTTagCompound());
            }
            ToolQuality quality = ToolQuality.getRandomQuality();
            par1ItemStack.getTagCompound().setString("ToolQuality", quality.getName());
        }
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.m_iWeaponDamage, 0));
        var1.put(ResharedMonsterAttributes.toolRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedMod.toolRangeUUID, "Tool Range", this.m_iReach, AttributeOperation.ADDITIVE.value));
        var1.put(ResharedMonsterAttributes.attackRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedMod.toolRangeUUID, "Attack Range", this.m_iReach, AttributeOperation.ADDITIVE.value));
        return var1;
    }

    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k )
    {
        ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
        // Apply quality-based modifiers
        float modifiedSpeed = ToolQualityHelper.getDigSpeedMultiplier(quality);
        float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );

        if ( block.blockID == Block.web.blockID
                || block.blockID == BTWBlocks.web.blockID)
        {
            return m_eMaterial.getEfficiencyOnProperMaterial()*15F*modifiedSpeed;
        }

        Material material = block.blockMaterial;

        if ( material == Material.wood || material == Material.plants || material == Material.vine || material == Material.coral || material == Material.leaves || material == Material.pumpkin)
        {
            return m_eMaterial.getEfficiencyOnProperMaterial()*2.5F*modifiedSpeed;
        }

        return fStrength;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0) {
            par1ItemStack.damageItem(1, par7EntityLivingBase);
        }
        return true;
    }

    @Override
    public float getExhaustionOnUsedToHarvestBlock(int iBlockID, World world, int i, int j, int k, int iBlockMetadata) {
        Block block= Block.blocksList[iBlockID];
        if (block != null)
        {
            Material material = block.blockMaterial;
            if (material == Material.plants || material == Material.vine|| block.blockMaterial.getAxesTreatAsVegetation()) {
            return 0.0f;
        }
        }
        return super.getExhaustionOnUsedToHarvestBlock(iBlockID, world, i, j, k, iBlockMetadata);
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        par1ItemStack.damageItem(1, par3EntityLivingBase);
        return true;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.isUsingSpecialKey()) {
            par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        }
        return par1ItemStack;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.block;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {
        if (player.isUsingSpecialKey() && player != null && player.canPlayerEdit(i, j, k, iFacing, stack) && this.getCanBePlacedAsBlock()) {
            Block blockStuckIn;
            BlockPos placementPos = new BlockPos(i, j, k);
            BlockPos stuckInPos = new BlockPos(i, j, k);
            if (!WorldUtils.isReplaceableBlock(world, i, j, k)) {
                placementPos.addFacingAsOffset(iFacing);
            } else {
                iFacing = 1;
                stuckInPos.addFacingAsOffset(0);
            }
            if (WorldUtils.doesBlockHaveCenterHardpointToFacing(world, stuckInPos.x, stuckInPos.y, stuckInPos.z, iFacing, true) && BTWBlocks.placedTool.canPlaceBlockAt(world, placementPos.x, placementPos.y, placementPos.z) && (blockStuckIn = Block.blocksList[world.getBlockId(stuckInPos.x, stuckInPos.y, stuckInPos.z)]) != null && blockStuckIn.canToolsStickInBlock(world, stuckInPos.x, stuckInPos.y, stuckInPos.z) && this.canToolStickInBlock(stack, blockStuckIn, world, stuckInPos.x, stuckInPos.y, stuckInPos.z)) {
                int iTargetFacingLevel;
                int iTargetFacing;
                if (iFacing >= 2) {
                    iTargetFacing = Block.getOppositeFacing(iFacing);
                    iTargetFacingLevel = 2;
                } else {
                    iTargetFacing = MiscUtils.convertOrientationToFlatBlockFacing(player);
                    iTargetFacingLevel = Block.getOppositeFacing(iFacing);
                }
                int iMetadata = BTWBlocks.placedTool.setFacing(0, iTargetFacing);
                iMetadata = BTWBlocks.placedTool.setVerticalOrientation(iMetadata, iTargetFacingLevel);
                world.setBlockAndMetadataWithNotify(placementPos.x, placementPos.y, placementPos.z, BTWBlocks.placedTool.blockID, iMetadata);
                TileEntity targetTileEntity = world.getBlockTileEntity(placementPos.x, placementPos.y, placementPos.z);
                if (targetTileEntity != null && targetTileEntity instanceof PlacedToolTileEntity) {
                    ((PlacedToolTileEntity)targetTileEntity).setToolStack(stack);
                    if (!world.isRemote) {
                        this.playPlacementSound(stack, blockStuckIn, world, placementPos.x, placementPos.y, placementPos.z);
                    }
                    --stack.stackSize;
                    return true;
                }
            }
        }
        return false;
    }

    protected boolean canToolStickInBlock(ItemStack stack, Block block, World world, int i, int j, int k) {
        return block.areShovelsEffectiveOn() || block.canToolStickInBlockSpecialCase(world, i, j, k, this);
    }

    protected void playPlacementSound(ItemStack stack, Block blockStuckIn, World world, int i, int j, int k) {
        world.playSoundEffect((float)i + 0.5f, (float)j + 0.5f, (float)k + 0.5f, blockStuckIn.getStepSound(world, i, j, k).getPlaceSound(), (blockStuckIn.getStepSound(world, i, j, k).getPlaceVolume() + 1.0f) / 2.0f, blockStuckIn.getStepSound(world, i, j, k).getPlacePitch() * 0.8f);
    }

    protected boolean getCanBePlacedAsBlock() {
        return true;
    }

    @Override
    public float getVisualVerticalOffsetAsBlock() {
        return 0.4f;
    }

    @Override
    public float getVisualHorizontalOffsetAsBlock() {
        return 0.85f;
    }

    @Override
    public float getVisualRollOffsetAsBlock() {
        return -45.0f;
    }

    @Override
    public float getBlockBoundingBoxHeight() {
        return 0.75f;
    }

    @Override
    public float getBlockBoundingBoxWidth() {
        return 0.75f;
    }
}
