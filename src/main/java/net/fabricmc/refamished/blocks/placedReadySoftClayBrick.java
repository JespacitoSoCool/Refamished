package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.tileentity.UnfiredBrickTileEntity;
import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import btw.util.MiscUtils;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class placedReadySoftClayBrick extends Block {
    @Environment(value= EnvType.CLIENT)
    private Icon[] cookIcons;

    public placedReadySoftClayBrick(int iBlockID) {
        super(iBlockID, Material.circuits);
        this.setHardness(0.0f);
        this.setShovelsEffectiveOn(true);
        this.setStepSound(BTWBlocks.clayStepSound);
        this.setUnlocalizedName("soft_brick_clay");
        this.setTextureName("refamished:soft_brick_clay");
        this.hideFromEMI();
    }

    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack brick = new ItemStack(RefamishedItems.soft_brick);

        ItemStack heldItem = player.getHeldItem();

        if (heldItem != null && (heldItem.itemID == RefamishedItems.stone_trowel.itemID))
        {
            heldItem.damageItem(1, player);

            world.playSoundEffect( (double)i + 0.5D, (double)j + 0.5D, (double)k + 0.5D,
                    stepSound.getBreakSound(), ( stepSound.getVolume() + 1.0F ) / 2.0F, stepSound.getPitch() * 0.8F );

            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(RefamishedItems.clay_mud, 1, 0));

            world.setBlockAndMetadataWithNotify( i, j, k, RefamishedBlocks.clayMudBrickGround.blockID, world.getBlockMetadata(i, j, k));
            return true;
        }
        return false;
    }

    @Override
    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setIAligned(iMetadata, this.isFacingIAligned(iFacing));
    }

    @Override
    public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityLiving, ItemStack stack) {
        int iFacing = MiscUtils.convertOrientationToFlatBlockFacingReversed(entityLiving);
        this.setIAligned(world, i, j, k, this.isFacingIAligned(iFacing));
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedItems.clay_mud.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 2;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k) {
        return null;
    }

    @Override
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k) {
        return AxisAlignedBB.getAABBPool().getAABB(1D /16D, 0.0, 1D /16D, 15D /16D, 5D /16D, 15D /16D);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        return WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j - 1, k, 1, true) && world.getBlockId(i, j - 1, k) != Block.leaves.blockID;
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
        if (!WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j - 1, k, 1, true)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public boolean canGroundCoverRestOnBlock(World world, int i, int j, int k) {
        return world.doesBlockHaveSolidTopSurface(i, j - 1, k);
    }

    @Override
    public float groundCoverRestingOnVisualOffset(IBlockAccess blockAccess, int i, int j, int k) {
        return -1.0f;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity) {
        List collisionList;
        if (!world.isRemote && !entity.isDead && entity instanceof EntityLivingBase && !(entity instanceof EntityAmbientCreature) && world.getDifficulty().canBricksBeTrampled() && (collisionList = world.getEntitiesWithinAABB(EntityLivingBase.class, this.getVisualBB(world, i, j, k))) != null && collisionList.size() > 0) {
            world.playSoundEffect((double)i + 0.5, (double)j + 0.5, (double)k + 0.5, this.stepSound.getStepSound(), (this.stepSound.getStepVolume() + 1.0f) / 2.0f, this.stepSound.getStepPitch() * 0.8f);
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockWithNotify(i, j, k, 0);
        }
    }

    @Override
    public int getFacing(int iMetadata) {
        if (this.getIsIAligned(iMetadata)) {
            return 4;
        }
        return 2;
    }

    @Override
    public int setFacing(int iMetadata, int iFacing) {
        return this.setIAligned(iMetadata, this.isFacingIAligned(iFacing));
    }

    @Override
    public boolean canRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k) {
        return true;
    }

    @Override
    public int rotateMetadataAroundYAxis(int iMetadata, boolean bReverse) {
        return this.setIAligned(iMetadata, !this.getIsIAligned(iMetadata));
    }

    public void setIAligned(World world, int i, int j, int k, boolean bIAligned) {
        int iMetadata = this.setIAligned(world.getBlockMetadata(i, j, k), bIAligned);
        world.setBlockMetadataWithNotify(i, j, k, iMetadata);
    }

    public int setIAligned(int iMetadata, boolean bIAligned) {
        iMetadata = bIAligned ? (iMetadata |= 1) : (iMetadata &= 0xFFFFFFFE);
        return iMetadata;
    }

    public boolean getIsIAligned(IBlockAccess blockAccess, int i, int j, int k) {
        return this.getIsIAligned(blockAccess.getBlockMetadata(i, j, k));
    }

    public boolean getIsIAligned(int iMetadata) {
        return (iMetadata & 1) != 0;
    }

    public boolean isFacingIAligned(int iFacing) {
        return iFacing >= 4;
    }

    public AxisAlignedBB getVisualBB(IBlockAccess blockAccess, int i, int j, int k) {
        if (this.getIsIAligned(blockAccess, i, j, k)) {
            return AxisAlignedBB.getAABBPool().getAABB((float)i + 0.125f, j, (float)k + 0.3125f, (float)i + 0.875f, (float)j + 0.25f, (float)k + 0.6875f);
        }
        return AxisAlignedBB.getAABBPool().getAABB((float)i + 0.3125f, j, (float)k + 0.125f, (float)i + 0.6875f, (float)j + 0.25f, (float)k + 0.875f);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int idPicked(World world, int x, int y, int z) {
        return BTWItems.unfiredCrudeBrick.itemID;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        if (iSide == 0) {
            return RenderUtils.shouldRenderNeighborFullFaceSide(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
        }
        return true;
    }
}
