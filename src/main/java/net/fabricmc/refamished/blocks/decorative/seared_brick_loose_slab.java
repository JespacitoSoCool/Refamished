package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.LooseBrickSlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

import java.util.Random;

public class seared_brick_loose_slab extends LooseBrickSlabBlock {
    public seared_brick_loose_slab(int iBlockID) {
        super(iBlockID);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("loose_seared_bricks_slab");
    }

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.searedBrickLoose.blockID;
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        int iNewMetadata = 4;
        if (this.getIsUpsideDown(world, i, j, k)) {
            iNewMetadata |= 8;
        }
        world.setBlockAndMetadataWithNotify(i, j, k, RefamishedBlocks.searedBrickSlab.blockID, iNewMetadata);
        return true;
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.searedBrickLooseSlab.blockID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:seared_bricks_loose");
    }
}
