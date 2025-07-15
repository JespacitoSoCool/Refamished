package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreChunkStorageBlock;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.World;

public class sinter_iron_ore_storage extends OreChunkStorageBlock {

    public sinter_iron_ore_storage(int iBlockID) {
        super(iBlockID);
        this.setUnlocalizedName("sinter_iron_storage");
        this.setTextureName("refamished:sinter_iron_ore_block");
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, RefamishedItems.sinterIronChunk.itemID, 9, 0, fChanceOfDrop);
        return true;
    }
}
