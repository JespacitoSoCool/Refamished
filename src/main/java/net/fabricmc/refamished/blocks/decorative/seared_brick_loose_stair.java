package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.MortarReceiverStairsBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.World;

import java.util.Random;

public class seared_brick_loose_stair extends MortarReceiverStairsBlock {
    public seared_brick_loose_stair(int iBlockID) {
        super(iBlockID, RefamishedBlocks.searedBrickLoose, 0);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("loose_seared_bricks_stair");
        this.setTextureName("refamished:seared_bricks_loose");
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        world.setBlockAndMetadataWithNotify(i, j, k, RefamishedBlocks.searedBrickStair.blockID, world.getBlockMetadata(i, j, k));
        return true;
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLooseStair.blockID;
    }
}
