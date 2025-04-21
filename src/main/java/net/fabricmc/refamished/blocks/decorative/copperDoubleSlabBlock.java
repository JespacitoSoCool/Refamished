package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreStorageBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class copperDoubleSlabBlock extends OreStorageBlock {

    public copperDoubleSlabBlock(int par1) {
        super(par1);
        setHardness(5.0f);
        setResistance(10.0f);
        setStepSound(soundMetalFootstep);
        setUnlocalizedName("block_copper_doubleslab");
        setTextureName("refamished:copper_block");
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
}
