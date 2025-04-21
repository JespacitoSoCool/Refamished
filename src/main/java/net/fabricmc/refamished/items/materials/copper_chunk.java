package net.fabricmc.refamished.items.materials;

import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.CreativeTabs;

public class copper_chunk extends PlaceAsBlockItem {

    public copper_chunk(int id) {
        super(id, RefamishedBlocks.copperChunkGround.blockID);
        this.setMaxStackSize(64); // Max stack size for the item
        this.setUnlocalizedName("copper_raw"); // Unique identifier for the item
        this.setFilterableProperties(2);
        this.setTextureName("refamished:copper_raw");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}