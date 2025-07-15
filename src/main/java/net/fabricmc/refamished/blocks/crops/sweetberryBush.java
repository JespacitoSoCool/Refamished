package net.fabricmc.refamished.blocks.crops;

import btw.block.blocks.DailyGrowthCropsBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.Random;

public class sweetberryBush extends DailyGrowthCropsBlock {
    public sweetberryBush(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("sweetberryBush");
        setTextureName("refamished:bush_stage_5");
    }

    @Override
    public void dropSeeds(World world, int i, int j, int k, int iMetadata) {

    }

    @Override
    public int quantityDropped(Random rand) {
        return 2;
    }

    @Override
    public int damageDropped(int meta) {
        return 1;
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
                this.iconArray[iTempIndex] = register.registerIcon("refamished:sweetberry_stage_"+ iTempIndex);
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
