package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.LooseBrickSlabBlock;
import btw.block.blocks.SlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class soft_bricks_slab extends SlabBlock {
    public soft_bricks_slab(int iBlockID) {
        super(iBlockID, Material.rock);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("soft_bricks_slab");
    }

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.softBrickMortar.blockID;
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:soft_bricks");
    }
}
