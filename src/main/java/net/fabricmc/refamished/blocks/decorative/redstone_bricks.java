package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.BrickBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.Material;

import java.util.Random;

public class redstone_bricks extends Block {
    public redstone_bricks(int iBlockID) {
        super(iBlockID, Material.rock);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("redstone_bricks");
        this.setTextureName("refamished:redstone_bricks");
        setPicksEffectiveOn();
        this.setLightOpacity(0);
        this.setLightValue(0.4f);
        setStepSound(BTWBlocks.stoneBrickStepSound);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.redstoneBricks.blockID;
    }
}
