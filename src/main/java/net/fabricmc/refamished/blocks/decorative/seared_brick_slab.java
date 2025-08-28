package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.SlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;

import java.util.Random;

public class seared_brick_slab extends SlabBlock {
    public seared_brick_slab(int iBlockID) {
        super(iBlockID, Material.rock);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("seared_bricks_slab");
    }

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.searedBrick.blockID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:seared_bricks");
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLooseSlab.blockID;
    }
}
