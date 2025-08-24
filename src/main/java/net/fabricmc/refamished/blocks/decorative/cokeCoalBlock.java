package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.FallingFullBlock;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class cokeCoalBlock extends FallingFullBlock {
    public cokeCoalBlock(int iBlockID) {
        super(iBlockID, Material.iron);
        this.setPicksEffectiveOn();
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, RefamishedItems.coke_coal.itemID, 9, 0, fChanceOfDrop);
        return true;
    }
}
