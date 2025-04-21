package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.LooseBrickBlock;
import btw.block.blocks.LooseBrickSlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.IconRegister;
import net.minecraft.src.World;

public class soft_brick_loose_slab extends LooseBrickSlabBlock {
    public soft_brick_loose_slab(int iBlockID) {
        super(iBlockID);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("loose_soft_bricks_slab");
    }

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.softBrickLoose.blockID;
    }

    @Override
    public boolean onMortarApplied(World world, int i, int j, int k) {
        int iNewMetadata = 4;
        if (this.getIsUpsideDown(world, i, j, k)) {
            iNewMetadata |= 8;
        }
        world.setBlockAndMetadataWithNotify(i, j, k, RefamishedBlocks.softBrickMortarSlab.blockID, iNewMetadata);
        return true;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:soft_bricks_loose");
    }
}
