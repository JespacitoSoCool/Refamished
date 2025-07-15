package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.BrickBlock;
import btw.block.blocks.LooseBrickBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.World;

import java.util.Random;

public class soft_bricks extends BrickBlock {
    public soft_bricks(int iBlockID) {
        super(iBlockID);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("soft_bricks");
        this.setTextureName("refamished:soft_bricks");
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.softBrickLoose.blockID;
    }
}
