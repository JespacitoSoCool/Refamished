package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.LooseBrickBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.World;

import java.util.Random;

public class seared_brick_loose extends LooseBrickBlock {
    public seared_brick_loose(int iBlockID) {
        super(iBlockID);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("loose_seared_bricks");
        this.setTextureName("refamished:seared_bricks_loose");
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        world.setBlockWithNotify(i, j, k, RefamishedBlocks.searedBrick.blockID);
        return true;
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLoose.blockID;
    }
}
