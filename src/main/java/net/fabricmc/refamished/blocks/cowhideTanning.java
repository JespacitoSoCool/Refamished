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
import net.fabricmc.refamished.entities.tiles.tanning;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class cowhideTanning extends BlockContainer {
    public static final float m_fBrickHeight = ( 2.01F / 16F );
    public static final float m_fBrickWidth = ( 10F / 16F );
    public static final float m_fBrickHalfWidth = ( m_fBrickWidth / 2F );
    public static final float m_fBrickLength = ( 12F / 16F );
    public static final float m_fBrickHalfLength = ( m_fBrickLength / 2F );
    @Environment(value= EnvType.CLIENT)
    private Icon[] cookIcons;

    public cowhideTanning(int iBlockID) {
        super(iBlockID, Material.circuits);
        this.setHardness(0.0f);
        this.setShovelsEffectiveOn(true);
        this.setStepSound(BTWBlocks.clayStepSound);
        this.setUnlocalizedName("cowhideTanning");
        this.setTextureName("refamished:cowhideTanning");
        this.hideFromEMI();
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new tanning();
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
        return RefamishedItems.cowhide.itemID;
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
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(IBlockAccess blockAccess, int i, int j, int k )
    {
        if ( getIsIAligned( blockAccess, i, j, k ) )
        {
            return AxisAlignedBB.getAABBPool().getAABB(
                    ( 0.5F - m_fBrickHalfLength ), 0D, ( 0.5F - m_fBrickHalfWidth ),
                    ( 0.5F + m_fBrickHalfLength ), m_fBrickHeight, ( 0.5F + m_fBrickHalfWidth ) );
        }

        return AxisAlignedBB.getAABBPool().getAABB(
                ( 0.5F - m_fBrickHalfWidth ), 0D, ( 0.5F - m_fBrickHalfLength ),
                ( 0.5F + m_fBrickHalfWidth ), m_fBrickHeight, ( 0.5F + m_fBrickHalfLength ) );
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
        world.setBlockAndMetadataWithNotify(i, j, k, RefamishedBlocks.cowhideReady.blockID, iMetadata);
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
        return BTWItems.unfiredCrudeBrick.itemID;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.cookIcons = new Icon[7];
        for (int iTempIndex = 0; iTempIndex < 7; ++iTempIndex) {
            this.cookIcons[iTempIndex] = register.registerIcon("refamished:OverlayTanning_" + (iTempIndex + 1));
        }
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
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int i, int j, int k, boolean bFirstPassResult) {
        if (bFirstPassResult) {
            int iKilnCookLevel;
            IBlockAccess blockAccess = renderBlocks.blockAccess;
            int iCookLevel = this.getCookLevel(blockAccess, i, j, k);
            int iBlockBelowID = blockAccess.getBlockId(i, j - 1, k);
            if (iBlockBelowID == BTWBlocks.kiln.blockID && (iKilnCookLevel = BTWBlocks.kiln.getCookCounter(blockAccess, i, j - 1, k) / 2) > iCookLevel) {
                iCookLevel = iKilnCookLevel;
            }
            if (iCookLevel > 0 && iCookLevel <= 7) {
                this.renderBlockWithTexture(renderBlocks, i, j, k, this.cookIcons[iCookLevel - 1]);
            }
        }
    }
}
