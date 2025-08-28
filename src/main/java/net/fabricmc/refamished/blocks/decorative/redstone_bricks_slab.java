package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.SlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;

import java.util.Random;

public class redstone_bricks_slab extends SlabBlock {
    public redstone_bricks_slab(int iBlockID) {
        super(iBlockID, Material.rock);
        this.setHardness(1f);
        this.setResistance(5f);
        this.setUnlocalizedName("redstone_bricks");
        setPicksEffectiveOn();
        this.setLightOpacity(0);
        this.setLightValue(0.3f);
        setStepSound(BTWBlocks.stoneBrickStepSound);
    }

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.redstoneBricks.blockID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:redstone_bricks");
    }

    @Override
    public int idDropped(int iMetadata, Random random, int iFortuneModifier) {
        return RefamishedBlocks.redstoneBricksSlab.blockID;
    }
}
