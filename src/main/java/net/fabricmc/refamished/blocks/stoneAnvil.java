package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.model.BlockModel;
import btw.item.BTWItems;
import btw.util.MiscUtils;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.entities.tiles.stoneAnvilContainer;
import net.fabricmc.refamished.entities.tiles.stoneAnvilTile;
import net.fabricmc.refamished.itemsbase.hammer;
import net.fabricmc.refamished.misc.AchievementsThing.RefAchievements;
import net.fabricmc.refamished.misc.RefContains;
import net.fabricmc.refamished.models.block.StoneAnvilModel;
import net.minecraft.src.*;

import java.util.Random;

public class stoneAnvil extends BlockContainer {

    public stoneAnvil(int par1) {
        super(par1,Material.ground);
        setUnlocalizedName("stone_anvil");
        setHardness(2F);
        setPicksEffectiveOn();
        this.setStepSound(BTWBlocks.stoneStepSound);
        setTextureName("refamished:dense_stone");
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int onBlockPlaced(World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ, int iMetadata) {
        return this.setFacing(iMetadata, iFacing);
    }

    @Override
    public int preBlockPlacedBy(World world, int i, int j, int k, int iMetadata, EntityLivingBase entityBy) {
        int iFacing = MiscUtils.convertOrientationToFlatBlockFacing(entityBy);
        return this.setFacing(iMetadata, iFacing);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int iBlockID) {
        if (!WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j - 1, k, 1, true)) {
            int iMetadata = world.getBlockMetadata(i, j, k);
            world.playAuxSFX(2272, i, j, k, this.blockID + (iMetadata << 12));
            this.dropBlockAsItem(world, i, j, k, iMetadata, 0);
            world.setBlockToAir(i, j, k);
        }
    }


    @Override
    public int getFacing(int iMetadata) {
        int iOrientation = iMetadata & 3;
        if ((iOrientation & 1) == 0) {
            if (iOrientation == 0) {
                return 3;
            }
            return 2;
        }
        if (iOrientation == 1) {
            return 4;
        }
        return 5;
    }

    @Override
    public int setFacing(int iMetadata, int iFacing) {
        int iOrientation = iFacing == 2 ? 2 : (iFacing == 3 ? 0 : (iFacing == 4 ? 1 : 3));
        return (iMetadata &= 0xFFFFFFFC) | iOrientation;
    }

    @Override
    public boolean canRotateOnTurntable(IBlockAccess blockAccess, int i, int j, int k) {
        return true;
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return BTWItems.stone.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 3 + rand.nextInt( 1 );
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier )
    {
        super.dropBlockAsItemWithChance(world, i, j, k, iMetaData, fChance, iFortuneModifier );

        if ( !world.isRemote )
        {
            this.dropItemsIndividually( world, i, j, k, BTWItems.stone.itemID, 5, 0, fChance );
        }
    }

    @Override
    public boolean dropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChance )
    {
        this.dropItemsIndividually( world, i, j, k, BTWItems.stone.itemID, 5, 0, fChance );

        return true;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        return false;
    }

    @Override
    public boolean canTransmitRotationVerticallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
    {
        return false;
    }

    public boolean canToolStickInBlockSpecialCase(World world, int x, int y, int z, Item toolOrSword) {
        return toolOrSword instanceof hammer;
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
        if (!world.isRemote && !WorldUtils.doesBlockHaveLargeCenterHardpointToFacing(world, i, j + 1, k, 0) && player instanceof EntityPlayerMP) {

            stoneAnvilContainer container = new stoneAnvilContainer(player.inventory, (stoneAnvilTile) world.getBlockTileEntity(i,j,k));
            RefamishedMod.serverOpenCustomInterface((EntityPlayerMP)player, container, RefContains.stoneAnvil);
            RefamishedMod.triggerAchievement(RefAchievements.STONE_ANVIL, player);
        }
        return true;
    }

    private final Random anvilRand = new Random();

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6) {
        stoneAnvilTile var7;
        if ((var7 = (stoneAnvilTile)par1World.getBlockTileEntity(par2, par3, par4)) != null) {
            for (int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
                ItemStack var9 = var7.getStackInSlot(var8);
                if (var9 == null) continue;
                float var10 = this.anvilRand.nextFloat() * 0.8f + 0.1f;
                float var11 = this.anvilRand.nextFloat() * 0.8f + 0.1f;
                float var12 = this.anvilRand.nextFloat() * 0.8f + 0.1f;
                while (var9.stackSize > 0) {
                    int var13 = this.anvilRand.nextInt(21) + 10;
                    if (var13 > var9.stackSize) {
                        var13 = var9.stackSize;
                    }
                    var9.stackSize -= var13;
                    EntityItem var14 = new EntityItem(par1World, (float)par2 + var10, (float)par3 + var11, (float)par4 + var12, new ItemStack(var9.itemID, var13, var9.getItemDamage()));
                    if (var9.hasTagCompound()) {
                        var14.getEntityItem().setTagCompound((NBTTagCompound)var9.getTagCompound().copy());
                    }
                    float var15 = 0.05f;
                    var14.motionX = (float)this.anvilRand.nextGaussian() * var15;
                    var14.motionY = (float)this.anvilRand.nextGaussian() * var15 + 0.2f;
                    var14.motionZ = (float)this.anvilRand.nextGaussian() * var15;
                    par1World.spawnEntityInWorld(var14);
                }
            }
            par1World.func_96440_m(par2, par3, par4, par5);
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

    //----------- Client Side Functionality -----------//

    StoneAnvilModel model = new StoneAnvilModel();

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        int iMetadata = renderer.blockAccess.getBlockMetadata(i, j, k);
        int iFacing = this.getFacing(iMetadata);
        BlockModel transformedModel = this.model.makeTemporaryCopy();
        transformedModel.rotateAroundYToFacing(iFacing);
        renderer.setUVRotateTop(this.convertFacingToTopTextureRotation(iFacing));
        renderer.setUVRotateBottom(this.convertFacingToBottomTextureRotation(iFacing));
        boolean bReturnValue = transformedModel.renderAsBlock(renderer, this, i, j, k);
        renderer.clearUVRotation();
        return bReturnValue;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return this.currentBlockRenderer.shouldSideBeRenderedBasedOnCurrentBounds(iNeighborI, iNeighborJ, iNeighborK, iSide);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderer, int iItemDamage, float fBrightness) {
        this.model.renderAsItemBlock(renderer, this, iItemDamage);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k) {
        AxisAlignedBB transformedBox = this.model.boxSelection.makeTemporaryCopy();
        transformedBox.rotateAroundYToFacing(this.getFacing(world, i, j, k));
        transformedBox.offset(i, j, k);
        return transformedBox;
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new stoneAnvilTile();
    }
}
