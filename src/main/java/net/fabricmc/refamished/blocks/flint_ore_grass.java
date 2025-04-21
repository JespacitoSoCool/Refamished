package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.Random;

public class flint_ore_grass extends Block {

    @Environment(value=EnvType.CLIENT)
    private Icon iconGrassTop;
    @Environment(value=EnvType.CLIENT)
    private Icon iconGrassTopSparse;
    @Environment(value=EnvType.CLIENT)
    private Icon iconGrassTopSparseDirt;
    @Environment(value=EnvType.CLIENT)
    private Icon iconSnowSide;
    @Environment(value=EnvType.CLIENT)
    private Icon iconGrassSideOverlay;
    @Environment(value=EnvType.CLIENT)
    private boolean hasSnowOnTop;
    @Environment(value=EnvType.CLIENT)
    public static boolean secondPass;

    public flint_ore_grass(int par1) {
        super(par1,Material.ground);
        setUnlocalizedName("flint_ore");
        setHardness(1F);
        setShovelsEffectiveOn();
        this.setStepSound(BTWBlocks.grassStepSound);
        setTextureName("refamished:flint_ore");
    }

    @Override
    public int idDropped( int iMetaData, Random rand, int iFortuneModifier )
    {
        return Item.flint.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 1 + rand.nextInt( 1 );
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier )
    {
        super.dropBlockAsItemWithChance(world, i, j, k, iMetaData, fChance, iFortuneModifier );

        if ( !world.isRemote )
        {
            this.dropItemsIndividually( world, i, j, k, BTWItems.dirtPile.itemID, 6, 0, fChance );
        }
    }

    @Override
    public boolean dropComponentItemsOnBadBreak( World world, int i, int j, int k, int iMetadata, float fChance )
    {

        dropItemsIndividually( world, i, j, k, Item.flint.itemID, 1, 0, fChance );

        dropItemsIndividually( world, i, j, k, BTWItems.dirtPile.itemID, 4, 0, fChance );

        return true;
    }

    @Override
    protected boolean canSilkHarvest()
    {
        // can't silk harvest due to conflicting drops and conversion recipes old clay blocks

        return false;
    }

    @Override
    public boolean canTransmitRotationVerticallyOnTurntable( IBlockAccess blockAccess, int i, int j, int k )
    {
        return false;
    }

    @Override
    public boolean canBePistonShoveled( World world, int i, int j, int k )
    {
        return true;
    }

    //------------- Class Specific Methods ------------//

    //----------- Client Side Functionality -----------//

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon(this.getTextureName() + "_side");
        this.iconGrassTop = register.registerIcon(this.getTextureName() + "_top");
        this.iconSnowSide = register.registerIcon(this.getTextureName() + "_side_snowed");
        this.iconGrassSideOverlay = register.registerIcon(this.getTextureName() + "_side_overlay");
        this.iconGrassTopSparse = register.registerIcon("btw:sparse_grass");
        this.iconGrassTopSparseDirt = register.registerIcon("btw:sparse_grass_dirt");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        if (this.hasSnowOnTop || !secondPass) {
            return 0xFFFFFF;
        }
        if (ColorizeBlock.colorizeBlock(this, blockAccess, x, y, z)) {
            return ColorizeBlock.blockColor;
        }
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (int var8 = -1; var8 <= 1; ++var8) {
            for (int var9 = -1; var9 <= 1; ++var9) {
                int var10 = blockAccess.getBiomeGenForCoords(x + var9, z + var8).getBiomeGrassColor();
                var5 += (var10 & 0xFF0000) >> 16;
                var6 += (var10 & 0xFF00) >> 8;
                var7 += var10 & 0xFF;
            }
        }
        return (var5 / 9 & 0xFF) << 16 | (var6 / 9 & 0xFF) << 8 | var7 / 9 & 0xFF;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int neighborX, int neighborY, int neighborZ, int side) {
        if (secondPass && (side == 0 || this.hasSnowOnTop)) {
            return false;
        }
        return super.shouldSideBeRendered(blockAccess, neighborX, neighborY, neighborZ, side);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (!secondPass) {
            if (side == 1) {
                return this.iconGrassTopSparseDirt;
            }
            if (side > 1) {
                Icon betterGrassIcon;
                Icon sideIcon = this.blockIcon;
                if (this.hasSnowOnTop) {
                    sideIcon = this.iconSnowSide;
                }
                if (RenderBlocksUtils.enableBetterGrass && (betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, this.iconGrassTopSparseDirt)) != null) {
                    sideIcon = betterGrassIcon;
                }
                return sideIcon;
            }
            return Block.dirt.blockIcon;
        }
        return this.getBlockTextureSecondPass(blockAccess, x, y, z, side);
    }

    @Environment(value=EnvType.CLIENT)
    public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Icon betterGrassIcon;
        Icon topIcon = this.iconGrassTop;
        if (side == 1) {
            return topIcon;
        }
        Icon sideIcon = this.iconGrassSideOverlay;
        if (RenderBlocksUtils.enableBetterGrass && (betterGrassIcon = RenderBlocksUtils.getGrassTexture(this, blockAccess, x, y, z, side, topIcon)) != null) {
            sideIcon = betterGrassIcon;
        }
        return sideIcon;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks render, int x, int y, int z) {
        this.hasSnowOnTop = this.isSnowCoveringTopSurface(render.blockAccess, x, y, z);
        render.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        return render.renderStandardBlock(this, x, y, z);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void renderBlockSecondPass(RenderBlocks render, int x, int y, int z, boolean firstPassResult) {
        secondPass = true;
        render.setRenderBounds(0.0, 0.0, 0.0, 1.0, 1.0, 1.0);
        render.renderStandardBlock(this, x, y, z);
        secondPass = false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int par1, int par2) {
        return par1 == 1 ? this.iconGrassTop : (par1 == 0 ? Block.dirt.getBlockTextureFromSide(par1) : this.blockIcon);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int getBlockColor() {
        if (ColorizeBlock.colorizeBlock(this)) {
            return ColorizeBlock.blockColor;
        }
        double var1 = 0.5;
        double var3 = 1.0;
        return ColorizerGrass.getGrassColor(var1, var3);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int getRenderColor(int par1) {
        return ColorizeBlock.colorizeBlock(this, par1) ? ColorizeBlock.blockColor : this.getBlockColor();
    }
}
