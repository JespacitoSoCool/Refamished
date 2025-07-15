package net.fabricmc.refamished.blocks.crops;

import btw.block.blocks.DailyGrowthCropsBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class blueberryBush extends DailyGrowthCropsBlock {
    public blueberryBush(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("blueberryBush");
        setTextureName("refamished:bush_stage_5");
    }

    @Override
    public void dropSeeds(World world, int i, int j, int k, int iMetadata) {
        int iSeedItemID = this.getSeedItemID();
        if (iSeedItemID > 0) {
            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(getSeedItemID(), 1, damageDropped(iMetadata)));
        }
    }

    @Override
    public int damageDropped(int meta) {
        return 0;
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
    private Icon[] iconArray;

    @Override
    protected boolean requiresNaturalLight() {
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        int iTempIndex;
        this.iconArray = new Icon[8];
        for (iTempIndex = 0; iTempIndex < this.iconArray.length; ++iTempIndex) {
            if (iTempIndex >= 6)
            {
                this.iconArray[iTempIndex] = register.registerIcon("refamished:blueberry_stage_"+ iTempIndex);
            }
            else
            {
                this.iconArray[iTempIndex] = register.registerIcon("refamished:bush_stage_" + iTempIndex);
            }
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        int iGrowthLevel = iMetadata & 7;
        return this.iconArray[iGrowthLevel];
    }

}
