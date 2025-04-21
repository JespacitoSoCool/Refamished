package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreStorageBlock;
import btw.block.blocks.SlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.Material;

public class copperSlabBlock extends SlabBlock {

    public copperSlabBlock(int par1) {
        super(par1, Material.iron);
        setHardness(5.0f);
        setResistance(10.0f);
        setStepSound(soundMetalFootstep);
        setUnlocalizedName("block_copper_slab");
        setTextureName("refamished:copper_block");
        setPicksEffectiveOn();
        setCreativeTab(CreativeTabs.tabBlock);
    }

    private Icon slab;

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.slab = register.registerIcon("refamished:copper_slab");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        if (iSide == 0 || iSide == 1)
        {
            return this.blockIcon;
        }
        return this.slab;
    }

    @Override
    public int getCombinedBlockID(int var1) {
        return RefamishedBlocks.copperDoubleSlab.blockID;
    }
}
