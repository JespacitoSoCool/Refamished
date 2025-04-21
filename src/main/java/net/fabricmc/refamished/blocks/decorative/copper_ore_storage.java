package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreChunkStorageBlock;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.World;

public class copper_ore_storage extends OreChunkStorageBlock {

    public copper_ore_storage(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("copper_ore_storage");
        this.setTextureName("refamished:copper_ore_storage");
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, RefamishedItems.copperChunk.itemID, 9, 0, fChanceOfDrop);
        return true;
    }
}
