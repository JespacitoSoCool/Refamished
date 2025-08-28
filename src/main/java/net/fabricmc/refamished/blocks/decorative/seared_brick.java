package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.BrickBlock;
import net.fabricmc.refamished.RefamishedBlocks;

import java.util.Random;

public class seared_brick extends BrickBlock {
    public seared_brick(int iBlockID) {
        super(iBlockID);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("seared_bricks");
        this.setTextureName("refamished:seared_bricks");
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLoose.blockID;
    }
}
