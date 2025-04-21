package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.LooseBrickBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class soft_brick_loose extends LooseBrickBlock {
    public soft_brick_loose(int iBlockID) {
        super(iBlockID);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("loose_soft_bricks");
        this.setTextureName("refamished:soft_bricks_loose");
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        world.setBlockWithNotify(i, j, k, RefamishedBlocks.softBrickMortar.blockID);
        return true;
    }
}
