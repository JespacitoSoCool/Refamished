package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.StairsBlock;
import net.fabricmc.refamished.RefamishedBlocks;

import java.util.Random;

public class redstone_bricks_stair extends StairsBlock {
    public redstone_bricks_stair(int iBlockID) {
        super(iBlockID,RefamishedBlocks.redstoneBricks, 0);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("redstone_bricks");
        this.setTextureName("refamished:redstone_bricks");
        setPicksEffectiveOn();
        this.setLightOpacity(0);
        this.setLightValue(0.3f);
        setStepSound(BTWBlocks.stoneBrickStepSound);
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.redstoneBricksStairs.blockID;
    }
}
