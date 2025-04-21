package net.fabricmc.refamished.items.materials;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class chippedHead extends Item {

    public chippedHead(int id) {
        super(id);
        setMaxStackSize(8); // Max stack size for the item
        setUnlocalizedName("chippedToolHead"); // Unique identifier for the item
        setCreativeTab(CreativeTabs.tabMaterials);
    }
}