package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.OreBlockStaged;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Item;

import java.util.Random;

public class saltpeter_ore extends OreBlockStaged {

    private Icon[] iconByMetadataArray;

    public saltpeter_ore(int iBlockID) {
        super(iBlockID);
        setUnlocalizedName("saltpeter_ore");
        setHardness(3.0f);
        setResistance(5.0f);
        setStepSound(BTWBlocks.oreStepSound);
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return super.getHarvestToolLevel(blockAccess, i, j, k);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedItems.saltpeter.itemID;
    }

    @Override
    public int idDroppedOnConversion(Difficulty difficulty, int iMetadata) {
        return RefamishedItems.saltpeter.itemID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.iconByMetadataArray = new Icon[16];
        super.registerIcons(register);
        this.iconByMetadataArray[0] = register.registerIcon("refamished:saltpeter_ore");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:saltpeter_ore_strata_2");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:saltpeter_ore_strata_3");
        for (int iTempIndex = 3; iTempIndex < 16; ++iTempIndex) {
            this.iconByMetadataArray[iTempIndex] = this.blockIcon;
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        return this.iconByMetadataArray[iMetadata];
    }
}
