package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.BrickStairsBlock;
import btw.block.blocks.MortarReceiverStairsBlock;
import btw.block.blocks.StairsBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.World;

public class soft_bricks_stair extends StairsBlock {
    public soft_bricks_stair(int iBlockID) {
        super(iBlockID,RefamishedBlocks.softBrickMortar, 0);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("soft_bricks_stair");
        this.setTextureName("refamished:soft_bricks");
    }
}
