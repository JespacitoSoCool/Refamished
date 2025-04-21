package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.model.BlockModel;
import btw.block.model.OvenModel;
import btw.block.tileentity.OvenTileEntity;
import btw.world.util.BlockPos;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.cokeovenTile;
import net.fabricmc.refamished.models.block.CokeOvenModel;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.minecraft.src.*;

import java.util.Arrays;
import java.util.Random;

public class CokeOven extends BlockFurnace {

    protected final BlockModel modelBlockInterior = new CokeOvenModel();
    @Environment(value=EnvType.CLIENT)
    protected boolean isRenderingInterior;
    @Environment(value=EnvType.CLIENT)
    private int interiorBrightness;

    public CokeOven(int i, boolean isLit) {
        super(i, isLit);
        setUnlocalizedName("coke_oven");
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        setStepSound(BTWBlocks.clayBrickStepSound);
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new cokeovenTile();
    }

    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick) {
        int iItemDamage;
        Item item;
        int iMetadata = world.getBlockMetadata(i, j, k);
        int iBlockFacing = iMetadata & 7;
        if (iBlockFacing != iFacing) {
            return false;
        }
        ItemStack heldStack = player.getCurrentEquippedItem();
        cokeovenTile tileEntity = (cokeovenTile)world.getBlockTileEntity(i, j, k);
        ItemStack cookStack = tileEntity.getCookStack();
        if (cookStack != null) {
            tileEntity.givePlayerCookStack(player, iFacing);
            return true;
        }
        if (heldStack != null && this.isValidCookItem(heldStack)) {
            if (!world.isRemote) {
                tileEntity.addCookStack(new ItemStack(heldStack.itemID, 1, heldStack.getItemDamage()));
            }
            --heldStack.stackSize;
            return true;
        }
        return false;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 4 + rand.nextInt(6);
    }

    @Override
    public int idDropped(int iMetaData, Random random, int iFortuneModifier) {
        return Item.brick.itemID;
    }

    @Override
    protected boolean canSilkHarvest(int iMetadata) {
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7) {
        if (!par1World.isRemote) {
            if (par1World.getDifficulty().shouldOvensDropThemselves()) {
                this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(BTWBlocks.idleOven));
            } else {
                int var8 = this.quantityDroppedWithBonus(par7, par1World.rand);
                for (int var9 = 0; var9 < var8; ++var9) {
                    int var10;
                    if (!(par1World.rand.nextFloat() <= par6) || (var10 = this.idDropped(par5, par1World.rand, par7)) <= 0) continue;
                    this.dropBlockAsItem_do(par1World, par2, par3, par4, new ItemStack(var10, 1, this.damageDropped(par5)));
                }
            }
        }
    }

    @Override
    public void onBlockDestroyedWithImproperTool(World world, EntityPlayer player, int i, int j, int k, int iMetadata) {
        this.dropBlockAsItem(world, i, j, k, iMetadata, 0);
    }

    @Override
    public boolean canPlaceBlockAt(World world, int i, int j, int k) {
        if (!WorldUtils.doesBlockHaveSolidTopSurface(world, i, j - 1, k)) {
            return false;
        }
        return super.canPlaceBlockAt(world, i, j, k);
    }

    @Override
    public boolean hasLargeCenterHardPointToFacing(IBlockAccess blockAccess, int i, int j, int k, int iFacing, boolean bIgnoreTransparency) {
        int iBlockFacing = blockAccess.getBlockMetadata(i, j, k) & 7;
        return iBlockFacing != iFacing;
    }

    @Override
    public boolean renderAsNormalBlock() {
        return false;
    }

    @Override
    protected int getIDDroppedOnSilkTouch() {
        return BTWBlocks.idleOven.blockID;
    }

    @Override
    public boolean getIsBlockWarm(IBlockAccess blockAccess, int i, int j, int k) {
        return this.isActive;
    }

    @Override
    public boolean doesBlockHopperInsert(World world, int i, int j, int k) {
        return true;
    }

    @Override
    public int getComparatorInputOverride(World world, int x, int y, int z, int side) {
        return 0;
    }

    public boolean isValidCookItem(ItemStack stack) {
        return CokeOvenSmeltingRecipes.getInstance().canBeSmelted(stack);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:soft_bricks");
        this.furnaceIconFront = register.registerIcon("refamished:coke_oven_core");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int idPicked(World world, int i, int j, int k) {
        return BTWBlocks.idleOven.blockID;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        int iFacing = iMetadata & 7;
        if (iFacing < 2 || iFacing > 5) {
            iFacing = 3;
        }
        int iOpposite = iFacing ^ 1;
        if (iSide == iFacing || iSide == iOpposite) {
            return this.furnaceIconFront;
        }
        return this.blockIcon;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        if (this.isRenderingInterior) {
            BlockPos myPos = new BlockPos(iNeighborI, iNeighborJ, iNeighborK, Block.getOppositeFacing(iSide));
            int iFacing = blockAccess.getBlockMetadata(myPos.x, myPos.y, myPos.z) & 7;
            return iSide != Block.getOppositeFacing(iFacing);
        }
        return super.shouldSideBeRendered(blockAccess, iNeighborI, iNeighborJ, iNeighborK, iSide);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        renderer.setRenderBounds(this.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));
        renderer.renderStandardBlock(this, i, j, k);
        int iFacing = renderer.blockAccess.getBlockMetadata(i, j, k) & 7;
        BlockModel transformedModel = this.modelBlockInterior.makeTemporaryCopy();
        transformedModel.rotateAroundYToFacing(iFacing);
        BlockPos interiorFacesPos = new BlockPos(i, j, k, iFacing);
        this.interiorBrightness = this.getMixedBrightnessForBlock(renderer.blockAccess, interiorFacesPos.x, interiorFacesPos.y, interiorFacesPos.z);
        //renderer.setOverrideBlockTexture(this.blockIcon);
        this.isRenderingInterior = true;
        boolean bReturnValue = transformedModel.renderAsBlockWithColorMultiplier(renderer, this, i, j, k);
        this.isRenderingInterior = false;
        renderer.clearOverrideBlockTexture();
        return bReturnValue;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int getMixedBrightnessForBlock(IBlockAccess par1IBlockAccess, int par2, int par3, int par4) {
        if (this.isRenderingInterior) {
            return this.interiorBrightness;
        }
        return super.getMixedBrightnessForBlock(par1IBlockAccess, par2, par3, par4);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlockWithTexture(RenderBlocks renderer, int i, int j, int k, Icon texture) {
        renderer.setRenderBounds(this.getBlockBoundsFromPoolBasedOnState(renderer.blockAccess, i, j, k));
        renderer.setOverrideBlockTexture(texture);
        boolean bReturnValue = renderer.renderStandardBlock(this, i, j, k);
        renderer.clearOverrideBlockTexture();
        return bReturnValue;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockAsItem(RenderBlocks renderBlocks, int iItemDamage, float fBrightness) {
        renderBlocks.renderBlockAsItemVanilla(this, iItemDamage, fBrightness);
        BlockModel transformedModel = this.modelBlockInterior.makeTemporaryCopy();
        transformedModel.rotateAroundYToFacing(3);
        transformedModel.renderAsItemBlock(renderBlocks, this, iItemDamage);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void randomDisplayTick(World world, int x, int y, int z, Random rand) {
        if (this.isActive) {
            ItemStack cookStack;
            cokeovenTile tileEntity = (cokeovenTile)world.getBlockTileEntity(x, y, z);
            if ((cookStack = tileEntity.getCookStack()) != null && this.isValidCookItem(cookStack)) {
                for (int iTempCount = 0; iTempCount < 1; ++iTempCount) {
                    float fX = (float)x + 0.375f + rand.nextFloat() * 0.25f;
                    float fY = (float)y + 0.45f + rand.nextFloat() * 0.1f;
                    float fZ = (float)z + 0.375f + rand.nextFloat() * 0.25f;
                    world.spawnParticle("fcwhitecloud", fX, fY, fZ, 0.0, 0.0, 0.0);
                }
            }
        }
        super.randomDisplayTick(world, x, y, z, rand);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockMovedByPiston(RenderBlocks renderBlocks, int i, int j, int k) {
        this.renderBlock(renderBlocks, i, j, k);
    }

    static int[][] directions = {
            {  0, -1 },
            {  0,  1 },
            {  -1,  0 },
            {  1,  0 }
    };

    public static boolean isValidStructure(World world, int coreX, int coreY, int coreZ) {
        int iBlockFacing = world.getBlockMetadata(coreX,coreY,coreZ);
        int facing = iBlockFacing-2;
        int fx = directions[facing][0];
        int fz = directions[facing][1];
        //System.out.println(Arrays.toString(directions[facing])+facing);
        int lx = -fz;
        int lz = fx;

        int startX = coreX - fx;
        int startY = coreY;
        int startZ = coreZ - fz;

        for (int dy = -1; dy < 2; dy++) {
            for (int dx = -1; dx < 2; dx++) {
                for (int dz = -1; dz < 2; dz++) {
                    int checkX = startX + dx;
                    int checkY = startY + dy;
                    int checkZ = startZ + dz;

                    if (checkX == coreX && checkY == coreY && checkZ == coreZ) continue;
                    if (dy == 0 && dx == 0 && dz == 0) {
                        if (!world.isAirBlock(checkX, checkY, checkZ)) return false;
                        continue;
                    }
                    if (dy == -1 && dx == 0 && dz == 0) {
                        if (world.getBlockId(checkX, checkY, checkZ) != RefamishedBlocks.retort_grate.blockID) return false;
                        continue;
                    }
                    if (checkX == coreX && checkY == coreY-1 && checkZ == coreZ) {
                        if (world.getBlockId(checkX, checkY, checkZ) == RefamishedBlocks.tar_tank.blockID) continue;
                    }

                    int id = world.getBlockId(checkX, checkY, checkZ);
                    if (id != RefamishedBlocks.softBrickMortar.blockID) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    public static int getFirePower(World world, int coreX, int coreY, int coreZ) {
        int iBlockFacing = world.getBlockMetadata(coreX,coreY,coreZ);
        int facing = iBlockFacing-2;
        int fx = directions[facing][0];
        int fz = directions[facing][1];
        //System.out.println(Arrays.toString(directions[facing])+facing);
        int lx = -fz;
        int lz = fx;
        int getHeatLevel = 0;

        int startX = coreX - fx;
        int startY = coreY-2;
        int startZ = coreZ - fz;

        for (int dx = -1; dx < 2; dx++) {
            for (int dz = -1; dz < 2; dz++) {
                int checkX = startX + dx;
                int checkY = startY;
                int checkZ = startZ + dz;

                int id = world.getBlockId(checkX, checkY, checkZ);
                if (id != Block.fire.blockID) {
                    getHeatLevel += 3;
                }
                else if (id != BTWBlocks.stokedFire.blockID) {
                    getHeatLevel += 3;
                }
                else if (id != BTWBlocks.smallCampfire.blockID) {
                    getHeatLevel += 1;
                }
                else if (id != BTWBlocks.mediumCampfire.blockID) {
                    getHeatLevel += 2;
                }
                else if (id != BTWBlocks.largeCampfire.blockID) {
                    getHeatLevel += 3;
                }
            }
        }
        return getHeatLevel;
    }
}
