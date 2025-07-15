package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.client.render.util.RenderUtils;
import btw.item.BTWItems;
import btw.util.MiscUtils;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.tanning;
import net.fabricmc.refamished.entities.tiles.tanningHorse;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import java.util.Random;

public class horsehideTanning extends BlockContainer {
    public static final float m_fBrickHeight = ( 2.01F / 16F );
    public static final float m_fBrickWidth = ( 10F / 16F );
    public static final float m_fBrickHalfWidth = ( m_fBrickWidth / 2F );
    public static final float m_fBrickLength = ( 12F / 16F );
    public static final float m_fBrickHalfLength = ( m_fBrickLength / 2F );
    private static final String[] hideName = new String[]{"white","creamy","chestnut","brown","black","gray","darkbrown","mule"};
    @Environment(value= EnvType.CLIENT)
    private Icon[][] cookIcons;

    public horsehideTanning(int iBlockID) {
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
        return new tanningHorse();
    }

    @Override
    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setIAligned(iMetadata, this.isFacingIAligned(iFacing));
    }

    @Override
    public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
        int variant = stack.getItemDamage() & 7; // lower 3 bits = hide type

        // Get player rotation (0 = South/Z, 1 = West/X, 2 = North/Z, 3 = East/X)
        int facing = MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

        // Align based on player's look direction (Z = 0, 2; X = 1, 3)
        boolean isIAligned = (facing == 1 || facing == 3); // X-axis = i-aligned

        int meta = variant;
        if (isIAligned) {
            meta |= 8; // set bit 3 for X-alignment
        }

        world.setBlockMetadataWithNotify(x, y, z, meta, 2);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedItems.horsehide.itemID;
    }

    @Override
    public int damageDropped(int meta) {
        return getVariant(meta);
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
        if ((iMetadata & 8) != 0) {
            return 4; // X-aligned (East-West)
        }
        return 2; // Z-aligned (North-South)
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
        return (iMetadata & 0b1000) != 0;
    }

    public boolean isFacingIAligned(int iFacing) {
        return iFacing >= 4;
    }

    private int getCookLevel(IBlockAccess access, int x, int y, int z) {
        TileEntity tile = access.getBlockTileEntity(x, y, z);
        if (tile instanceof tanningHorse) {
            return ((tanningHorse) tile).getCookLevel();
        }
        return 0;
    }

    int getVariant(int metadata) {
        return metadata & 0b0111;
    }

    boolean isIAligned(int metadata) {
        return (metadata & 0b1000) != 0;
    }

    int makeMetadata(int variant, boolean iAligned) {
        return (variant & 0b0111) | (iAligned ? 0b1000 : 0);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int idPicked(World world, int x, int y, int z) {
        return RefamishedItems.horsehide.itemID;
    }

    @Environment(EnvType.CLIENT)
    private Icon[] variantIcons;

    @Override
    @Environment(EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        variantIcons = new Icon[hideName.length];
        cookIcons = new Icon[hideName.length][8];

        for (int i = 0; i < hideName.length; i++) {
            String variant = hideName[i];

            variantIcons[i] = register.registerIcon("refamished:variant/horsehide_" + variant);

            for (int co = 0; co < 8; co++) {
                cookIcons[i][co] = register.registerIcon("refamished:variant/tanning_" + variant + "_" + co);
            }
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int side, int metadata) {
        int variant = metadata & 0b0111;
        return variantIcons[variant];
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
    @Environment(value = EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks renderBlocks, int x, int y, int z, boolean firstPassResult) {
        if (firstPassResult) {
            int cookLevel = getCookLevel(renderBlocks.blockAccess, x, y, z);
            int var = getVariant(renderBlocks.blockAccess.getBlockMetadata(x, y, z));
            if (cookLevel > 0 && cookLevel <= 8 && var >= 0 && var < hideName.length) {
                renderBlockWithTexture(renderBlocks, x, y, z, this.cookIcons[var][cookLevel - 1]);
            }
        }
    }

}
