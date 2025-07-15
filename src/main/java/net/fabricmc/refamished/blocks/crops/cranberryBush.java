package net.fabricmc.refamished.blocks.crops;

import btw.block.BTWBlocks;
import btw.block.blocks.DailyGrowthCropsBlock;
import btw.client.render.util.RenderUtils;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import com.prupe.mcpatcher.mal.block.RenderBlocksUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;
import org.lwjgl.Sys;

public class cranberryBush extends DailyGrowthCropsBlock {
    public cranberryBush(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("cranberryBush");
        setTextureName("refamished:bush_stage_5");
    }

    Block grass = Block.grass;

    @Override
    public void dropSeeds(World world, int i, int j, int k, int iMetadata) {
        int iSeedItemID = this.getSeedItemID();
        if (iSeedItemID > 0) {
            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(getSeedItemID(), 1, damageDropped(iMetadata)));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return 2;
    }

    @Override
    protected int getCropItemID() {
        return RefamishedItems.berries.itemID;
    }

    @Override
    protected int getSeedItemID() {
        return RefamishedItems.berrySeeds.itemID;
    }

    @Environment(value= EnvType.CLIENT)
    public Icon[] iconArray;
    @Environment(value= EnvType.CLIENT)
    public Icon[] secondRender;

    @Override
    protected boolean requiresNaturalLight() {
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        int iTempIndex;
        this.iconArray = new Icon[8];
        this.secondRender = new Icon[2];
        for (iTempIndex = 0; iTempIndex < this.iconArray.length; ++iTempIndex) {
            //this.iconArray[iTempIndex] = register.registerIcon("refamished:variant/crops/bush_mono_stage_"+ iTempIndex);
            if (iTempIndex >= 6)
            {
                this.iconArray[iTempIndex] = register.registerIcon("refamished:sweetberry_stage_"+ iTempIndex);
            }
            else
            {
                this.iconArray[iTempIndex] = register.registerIcon("refamished:bush_stage_" + iTempIndex);
            }
        }
        this.secondRender[0] = register.registerIcon("refamished:variant/crops/cranberry_low");
        this.secondRender[1] = register.registerIcon("refamished:variant/crops/cranberry");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        int iGrowthLevel = iMetadata & 7;
        return this.iconArray[iGrowthLevel];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess blockAccess, int iNeighborI, int iNeighborJ, int iNeighborK, int iSide) {
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    public static boolean secondPass;

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getBlockTexture(IBlockAccess blockAccess, int x, int y, int z, int side) {
        if (!secondPass) {
            int iGrowthLevel = blockAccess.getBlockMetadata(x,y,z) & 7;
            return this.iconArray[iGrowthLevel];
        }
        return this.getBlockTextureSecondPass(blockAccess, x, y, z, side);
    }

    @Environment(value=EnvType.CLIENT)
    public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Icon ic = null;
        int iGrowthLevel = blockAccess.getBlockMetadata(x,y,z) & 7;
        if (iGrowthLevel >= 6) {
            ic = this.secondRender[iGrowthLevel-6];
        }
        return ic;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        this.renderCrops2(renderer, i, j, k);
        secondPass = true;
        this.renderCropsSecondary(renderer, i, j, k);
        secondPass = false;
        BTWBlocks.weeds.renderWeeds(this, renderer, i, j, k);
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    protected void renderCrops2(RenderBlocks renderer, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, i, j, k));

        int color = this.colorMultiplier(renderer.blockAccess, i, j, k);
        float r = (float)(color >> 16 & 255) / 255.0F;
        float g = (float)(color >> 8 & 255) / 255.0F;
        float b = (float)(color & 255) / 255.0F;

        r = r * 0.5f + 0.5f;
        g = g * 0.5f + 0.5f;
        b = b * 0.5f + 0.5f;

        double dVerticalOffset = 0.0;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId(i, j - 1, k)];
        if (blockBelow != null) {
            dVerticalOffset = blockBelow.groundCoverRestingOnVisualOffset(renderer.blockAccess, i, j - 1, k);
        }
        Icon get = this.getBlockTexture(renderer.blockAccess, i, j, k, 0);
        if (get != null) {
            tessellator.setColorOpaque_F(r, g, b);
            this.renderCrossHatch(renderer, i, j, k, get, 0.25, dVerticalOffset);
        }
    }


    @Environment(value=EnvType.CLIENT)
    protected void renderCropsSecondary(RenderBlocks renderer, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, i, j, k));

        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double dVerticalOffset = 0.0;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId(i, j - 1, k)];
        if (blockBelow != null) {
            dVerticalOffset = blockBelow.groundCoverRestingOnVisualOffset(renderer.blockAccess, i, j - 1, k);
        }
        Icon get = this.getBlockTextureSecondPass(renderer.blockAccess, i, j, k, 0);
        if (get != null) {
            this.renderCrossHatch(renderer, i, j, k, get, 0.25, dVerticalOffset);
        }
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

    @Override
    @Environment(value=EnvType.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
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
}
