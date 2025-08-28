package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.StairsBlock;
import net.fabricmc.refamished.RefamishedBlocks;

import java.util.Random;

public class seared_brick_stair extends StairsBlock {
    public seared_brick_stair(int iBlockID) {
        super(iBlockID,RefamishedBlocks.searedBrick, 0);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("seared_bricks_stair");
        this.setTextureName("refamished:seared_bricks");
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLooseStair.blockID;
    }
}
