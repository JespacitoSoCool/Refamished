package net.fabricmc.refamished.blocks.crops;

import btw.block.BTWBlocks;
import btw.block.blocks.DailyGrowthCropsBlock;
import btw.block.blocks.PlantsBlock;
import btw.block.util.Flammability;
import com.prupe.mcpatcher.cc.ColorizeBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class wildBush extends PlantsBlock {
    public wildBush(int iBlockID) {
        super(iBlockID, Material.plants);
        this.setHardness(0.4f);
        this.setBuoyant();
        this.setFireProperties(Flammability.CROPS);
        this.initBlockBounds(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
        this.setStepSound(BTWBlocks.cropStepSound);
        this.setTickRandomly(true);
        this.disableStats();
        this.setUnlocalizedName("wildBush");
        setTextureName("refamished:bush_stage_5");
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int iNeighborBlockID) {
        super.onNeighborBlockChange(world, i, j, k, iNeighborBlockID);
        this.updateIfBlockStays(world, i, j, k);
    }

    protected boolean updateIfBlockStays(World world, int i, int j, int k) {
        if (!this.canBlockStay(world, i, j, k)) {
            this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
            world.setBlockToAir(i, j, k);
            return false;
        }
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int x, int y, int z, int metadata, float chance, int fortuneModifier) {
        if (world.isRemote) {
            return;
        }
        if (metadata == 0) {
            this.dropBlockAsItem_do(world, x,y,z, new ItemStack(RefamishedItems.branch.itemID, 3, 0));
        }
        else {
            this.dropBlockAsItem_do(world, x,y,z, new ItemStack(RefamishedItems.berries.itemID, 1, metadata-1));
            this.dropBlockAsItem_do(world, x,y,z, new ItemStack(RefamishedItems.branch.itemID, 2, 0));
        }
    }

    @Environment(value= EnvType.CLIENT)
    private Icon[] iconArray;
    @Environment(value= EnvType.CLIENT)
    public Icon[] secondRender;

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register)  {
        this.iconArray = new Icon[16];
        this.secondRender = new Icon[16];
        this.iconArray[0] = register.registerIcon("refamished:branch_bush_mono");
        this.iconArray[1] = register.registerIcon("refamished:wild_blueberry");
        this.iconArray[2] = register.registerIcon("refamished:wild_sweetberry");
        this.iconArray[15] = register.registerIcon("refamished:wild_blueberry");
        this.iconArray[14] = register.registerIcon("refamished:branch_bush");
        //this.iconArray[15] = register.registerIcon("refamished:variant/crops/bush_mono_stage_7");

        this.secondRender[3] = register.registerIcon("refamished:variant/crops/wild_cranberry");
        this.secondRender[4] = register.registerIcon("refamished:variant/crops/wild_blackberry");
    }

    @Environment(value=EnvType.CLIENT)
    public static boolean secondPass;

    @Environment(value=EnvType.CLIENT)
    public Icon getBlockTextureSecondPass(IBlockAccess blockAccess, int x, int y, int z, int side) {
        Icon ic = null;
        int meta = blockAccess.getBlockMetadata(x,y,z);
        if (secondRender[meta] != null) {
            ic = this.secondRender[meta];
        }
        return ic;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean renderBlock(RenderBlocks renderer, int i, int j, int k) {
        int meta = renderer.blockAccess.getBlockMetadata(i,j,k);
        if (secondRender[meta] != null)
        {
            this.renderCrops2(renderer, i, j, k);
            secondPass = true;
            this.renderCropsSecondary(renderer, i, j, k);
            secondPass = false;
            return true;
        }
        return super.renderBlock(renderer,i,j,k);
    }

    @Environment(value=EnvType.CLIENT)
    protected void renderCrops2(RenderBlocks renderer, int i, int j, int k) {
        Tessellator tessellator = Tessellator.instance;
        tessellator.setBrightness(this.getMixedBrightnessForBlock(renderer.blockAccess, i, j, k));

//        int color = RefamishedBlocks.cranberryBush.colorMultiplier(renderer.blockAccess, i, j, k);
//        float r = (float)(color >> 16 & 255) / 255.0F;
//        float g = (float)(color >> 8 & 255) / 255.0F;
//        float b = (float)(color & 255) / 255.0F;
        tessellator.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        double dVerticalOffset = 0.0;
        Block blockBelow = Block.blocksList[renderer.blockAccess.getBlockId(i, j - 1, k)];
        if (blockBelow != null) {
            dVerticalOffset = blockBelow.groundCoverRestingOnVisualOffset(renderer.blockAccess, i, j - 1, k);
        }
        Icon get = iconArray[15];
        if (get != null) {
            //tessellator.setColorOpaque_F(r, g, b);
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
    public Icon getIcon(int iSide, int iMetadata) {
        if (this.iconArray[iMetadata] == null) {
            return this.iconArray[14];
        }
        return this.iconArray[iMetadata];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int colorMultiplier(IBlockAccess blockAccess, int x, int y, int z) {
        if (blockAccess.getBlockMetadata(x,y,z) != 0)
        {
            return super.colorMultiplier(blockAccess,x,y,z);
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

}
