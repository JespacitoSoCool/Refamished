package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.LooseBrickSlabBlock;
import btw.block.blocks.SlabBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

public class retort_grate extends SlabBlock {
    public retort_grate(int iBlockID) {
        super(iBlockID, Material.rock);
        this.setPicksEffectiveOn();
        this.setChiselsEffectiveOn();
        this.setStepSound(BTWBlocks.clayBrickStepSound);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(0.7f);
        this.setResistance(3.0f);
        this.setUnlocalizedName("retort_grate");
        this.setTextureName("refamished:soft_bricks");
    }
    private Icon grate;

    @Override
    public int getCombinedBlockID(int iMetadata) {
        return RefamishedBlocks.softBrickLoose.blockID;
    }

    @Override
    public boolean convertToFullBlock(World world, int i, int j, int k) {
        return false; //NUH UH
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.blockIcon = register.registerIcon("refamished:soft_bricks");
        this.grate = register.registerIcon("refamished:retort_grate");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        if (iSide == 0 || iSide == 1)
        {
            return this.grate;
        }
        return this.blockIcon;
    }
}
