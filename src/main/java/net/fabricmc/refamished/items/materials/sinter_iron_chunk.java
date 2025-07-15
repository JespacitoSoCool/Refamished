package net.fabricmc.refamished.items.materials;

import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.CreativeTabs;

public class sinter_iron_chunk extends PlaceAsBlockItem {

    public sinter_iron_chunk(int id) {
        super(id, RefamishedBlocks.sinteredChunkGround.blockID);
        this.setMaxStackSize(64);
        this.setUnlocalizedName("sinter_raw");
        this.setFilterableProperties(2);
        this.setTextureName("refamished:sinter_iron_chunk");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}