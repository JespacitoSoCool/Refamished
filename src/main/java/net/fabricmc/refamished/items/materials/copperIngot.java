package net.fabricmc.refamished.items.materials;

import net.minecraft.src.Item;

public class copperIngot extends Item {

    public copperIngot(int id) {
        super(id);
        this.setMaxStackSize(64); // Max stack size for the item
        this.setBuoyancy(2);
        this.setUnlocalizedName("copper_ingot"); // Unique identifier for the item
    }
}