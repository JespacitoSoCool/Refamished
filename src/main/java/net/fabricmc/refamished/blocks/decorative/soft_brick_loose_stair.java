package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.LooseBrickSlabBlock;
import btw.block.blocks.LooseBrickStairsBlock;
import btw.block.blocks.MortarReceiverStairsBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

import java.util.Random;

public class soft_brick_loose_stair extends MortarReceiverStairsBlock {
    public soft_brick_loose_stair(int iBlockID) {
        super(iBlockID, RefamishedBlocks.softBrickLoose, 0);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("loose_soft_bricks_stair");
        this.setTextureName("refamished:soft_bricks_loose");
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        world.setBlockAndMetadataWithNotify(i, j, k, RefamishedBlocks.softBrickMortarStairs.blockID, world.getBlockMetadata(i, j, k));
        return true;
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.softBrickLooseStairs.blockID;
    }
}
