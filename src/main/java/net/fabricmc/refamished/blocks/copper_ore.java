package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.block.blocks.OreBlock;
import btw.block.blocks.OreBlockStaged;
import btw.item.BTWItems;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Item;

import java.util.Random;

public class copper_ore extends OreBlockStaged {

    private Icon[] iconByMetadataArray;

    public copper_ore(int iBlockID) {
        super(iBlockID);
        setUnlocalizedName("copper_ore");
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
        return RefamishedItems.copperChunk.itemID;
    }

    @Override
    public int quantityDropped( Random rand )
    {
        return 1 + rand.nextInt( 1 );
    }

    @Override
    public int quantityDroppedOnConversion( Random rand )
    {
        return 2 + rand.nextInt( 2 );
    }

    @Override
    public int idDroppedOnConversion(Difficulty difficulty, int iMetadata) {
        if (difficulty.shouldOresDropPilesWhenChiseled()) {
            return RefamishedItems.copperDust.itemID;
        }
        return RefamishedItems.copperChunk.itemID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.iconByMetadataArray = new Icon[16];
        super.registerIcons(register);
        this.iconByMetadataArray[0] = register.registerIcon("refamished:copper_ore");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:copper_ore_stata_2");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:copper_ore_stata_3");
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
