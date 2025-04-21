package net.fabricmc.refamished.interfaces;

import net.minecraft.src.EnumToolMaterial;

public interface ProgressiveCraftingInterface {
    default String skillName() {
        return "Weaving";
    }
}
