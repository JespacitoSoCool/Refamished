package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.BucketBlockFull;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.Random;

public class bucket_block_tar extends BucketBlockFull {
    @Environment(value= EnvType.CLIENT)
    private Icon iconWater;

    public bucket_block_tar(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("tar_bucket");
    }

    @Override
    public int idDropped(int iMetadata, Random rand, int iFortuneMod) {
        return RefamishedItems.tar_bucket.itemID;
    }

    @Override
    public boolean attemptToSpillIntoBlock(World world, int i, int j, int k) {
        return false;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.iconWater = register.registerIcon("refamished:tar");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    protected Icon getContentsIcon() {
        return this.iconWater;
    }
}
