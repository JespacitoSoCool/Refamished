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
import net.fabricmc.refamished.misc.Utils.UtilInventory;
import net.minecraft.src.*;

import java.util.Random;

public class cowhideTanningFinished extends Block {
    public static final float m_fBrickHeight = ( 2.01F / 16F );
    public static final float m_fBrickWidth = ( 10F / 16F );
    public static final float m_fBrickHalfWidth = ( m_fBrickWidth / 2F );
    public static final float m_fBrickLength = ( 12F / 16F );
    public static final float m_fBrickHalfLength = ( m_fBrickLength / 2F );
    @Environment(value= EnvType.CLIENT)
    private Icon[] cookIcons;

    public cowhideTanningFinished(int iBlockID) {
        super(iBlockID, Material.circuits);
        this.setHardness(0.0f);
        this.setShovelsEffectiveOn(true);
        this.setStepSound(BTWBlocks.clayStepSound);
        this.setUnlocalizedName("cowhideReady");
        this.setTextureName("refamished:cowhideReady");
        this.hideFromEMI();
    }

    @Override
    public boolean onBlockActivated( World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack brick = new ItemStack(Item.leather);

        if (UtilInventory.AddItemStackToInventoryInSlotRange( player.inventory, brick, 0, player.inventory.getSizeInventory() - 5 ))
        {
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
    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setIAligned(iMetadata, this.isFacingIAligned(iFacing));
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return Item.leather.itemID;
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
    public AxisAlignedBB getBlockBoundsFromPoolBasedOnState(
            IBlockAccess blockAccess, int i, int j, int k )
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

    @Override
    @Environment(value=EnvType.CLIENT)
    public int idPicked(World world, int x, int y, int z) {
        return Item.leather.itemID;
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
