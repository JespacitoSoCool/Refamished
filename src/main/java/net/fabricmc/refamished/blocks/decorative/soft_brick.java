package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.FiredBrickBlock;
import btw.block.tileentity.UnfiredBrickTileEntity;
import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import btw.util.MiscUtils;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.softBrickTile;
import net.fabricmc.refamished.misc.Utils.UtilInventory;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class soft_brick extends BlockContainer {
    public static final double BRICK_HEIGHT = 0.25;
    public static final double BRICK_WIDTH = 0.375;
    public static final double BRICK_HALF_WIDTH = 0.1875;
    public static final double BRICK_LENGTH = 0.75;
    public static final double BRICK_HALF_LENGTH = 0.375;
    private Icon[] cookIcons;

    public soft_brick(int iBlockID) {
        super(iBlockID,Material.circuits);
        this.setHardness(0.0f);
        this.setPicksEffectiveOn(true);
        this.setStepSound(BTWBlocks.smallObjectStepSound);
        this.setTextureName("refamished:soft_brick");
        this.setUnlocalizedName("soft_brick_ground");
        this.hideFromEMI();
    }

    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack brick = new ItemStack(RefamishedItems.soft_brick);

        if (UtilInventory.AddItemStackToInventoryInSlotRange( player.inventory, brick, 0, player.inventory.getSizeInventory() - 5 ))
        {
//            world.playAuxSFX(FCBetterThanWolves.m_iItemCollectionPopSoundAuxFXID, i, j, k, 0);
            player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F,
                    ((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            world.setBlockToAir(i, j, k);
            return true;
        }
        else
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockToAir(i, j, k);
            return true;
        }
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedItems.soft_brick.itemID;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new softBrickTile();
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
        if (this.getIsIAligned(blockAccess, i, j, k)) {
            return AxisAlignedBB.getAABBPool().getAABB(0.125, 0.0, 0.3125, 0.875, 0.25, 0.6875);
        }
        return AxisAlignedBB.getAABBPool().getAABB(0.3125, 0.0, 0.125, 0.6875, 0.25, 0.875);
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

    public void onFinishedCooking(World world, int i, int j, int k) {
        int iMetadata = world.getBlockMetadata(i, j, k) & 1;
        world.setBlockAndMetadataWithNotify(i, j, k, BTWBlocks.placedBrick.blockID, iMetadata);
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

    public void setCookLevel(World world, int i, int j, int k, int iCookLevel) {
        int iMetadata = this.setCookLevel(world.getBlockMetadata(i, j, k), iCookLevel);
        world.setBlockMetadataWithNotify(i, j, k, iMetadata);
    }

    public int setCookLevel(int iMetadata, int iCookLevel) {
        iMetadata &= 1;
        return iMetadata |= iCookLevel << 1;
    }

    public int getCookLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return this.getCookLevel(blockAccess.getBlockMetadata(i, j, k));
    }

    public int getCookLevel(int iMetadata) {
        return iMetadata >> 1;
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
        return RefamishedItems.soft_brick.itemID;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        if (iSide == 0) {
            return RenderUtils.shouldRenderNeighborFullFaceSide(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
        }
        return true;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.cookIcons = new Icon[3];
        for (int iTempIndex = 0; iTempIndex < 3; ++iTempIndex) {
            this.cookIcons[iTempIndex] = register.registerIcon("refamished:cooked_brick_" + (iTempIndex + 1));
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        if (bFirstPassResult) {
            int iKilnCookLevel;
            IBlockAccess blockAccess = renderBlocks.blockAccess;
            int iCookLevel = this.getCookLevel(blockAccess, i, j, k);
            int iBlockBelowID = blockAccess.getBlockId(i, j - 1, k);
            if (iBlockBelowID == BTWBlocks.kiln.blockID && (iKilnCookLevel = BTWBlocks.kiln.getCookCounter(blockAccess, i, j - 1, k) / 2) > iCookLevel) {
                iCookLevel = iKilnCookLevel;
            }
            if (iCookLevel > 0 && iCookLevel <= 3) {
                this.renderBlockWithTexture(renderBlocks, i, j, k, this.cookIcons[iCookLevel - 1]);
            }
        }
    }
}
