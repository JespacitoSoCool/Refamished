package net.fabricmc.refamished.itemsbase;
import btw.block.BTWBlocks;
import btw.block.tileentity.PlacedToolTileEntity;
import btw.community.refamished.RefamishedAddon;
import btw.entity.attribute.AttributeOperation;
import btw.item.BTWItems;
import btw.item.PlaceableAsItem;
import btw.util.MiscUtils;
import btw.world.util.BlockPos;
import btw.world.util.WorldUtils;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class hammer extends Item implements PlaceableAsItem {
    public float m_iWeaponDamage;
    public EnumToolMaterial m_eMaterial;

    public hammer(int iItemID, float weaponDamage, EnumToolMaterial material)
    {
        super(iItemID);
        setFull3D();
        m_iWeaponDamage = weaponDamage;
        m_eMaterial = material;
        this.setMaxStackSize(1);
        this.setMaxDamage((int) (material.getMaxUses()*1.3f));
        setCreativeTab(CreativeTabs.tabTools);
    }

    public int missShatter()
    {
        return 4;
    }
    public int shatter()
    {
        return 12;
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
        var1.put(ResharedMonsterAttributes.toolRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedAddon.toolRangeUUID, "Tool Range", -1, AttributeOperation.ADDITIVE.value));
        var1.put(ResharedMonsterAttributes.attackRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedAddon.toolRangeUUID, "Attack Range", -1, AttributeOperation.ADDITIVE.value));
        return var1;
    }

    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k )
    {
        ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
        float modifiedSpeed = ToolQualityHelper.getDigSpeedMultiplier(quality);
        float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );

        if ( block.blockID == RefamishedBlocks.denseStone.blockID)
        {
            return fStrength*m_eMaterial.getEfficiencyOnProperMaterial()*1.5F*modifiedSpeed;
        }

        return fStrength;
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        par1ItemStack.damageItem(1, par3EntityLivingBase);
        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {
        if (player.isUsingSpecialKey() && player != null && player.canPlayerEdit(i, j, k, iFacing, stack) && this.getCanBePlacedAsBlock() && iFacing == 1) {
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
        return true;
    }

    protected void playPlacementSound(ItemStack stack, Block blockStuckIn, World world, int i, int j, int k) {
        world.playSoundEffect((float)i + 0.5f, (float)j + 0.5f, (float)k + 0.5f, Block.planks.stepSound.getPlaceSound(), (blockStuckIn.getStepSound(world, i, j, k).getPlaceVolume() + 1.0f) / 2.0f, blockStuckIn.getStepSound(world, i, j, k).getPlacePitch() * 0.8f);
    }

    protected boolean getCanBePlacedAsBlock() {
        return true;
    }

    @Override
    public float getVisualVerticalOffsetAsBlock() {
        return 0.44f;
    }

    @Override
    public float getVisualHorizontalOffsetAsBlock() {
        return 0.18f;
    }

    @Override
    public float getVisualRollOffsetAsBlock() {
        return 62.5f;
    }

    @Override
    public float getBlockBoundingBoxHeight() {
        return 0.8f;
    }

    @Override
    public float getBlockBoundingBoxWidth() {
        return 0.8f;
    }
}