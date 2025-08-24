package net.fabricmc.refamished.misc.CustomRecipes.deprecated_smithing;

import net.minecraft.src.ItemStack;

public interface ISmithingDynamicRecipe {
    boolean matches(ItemStack input);
    ItemStack[] getResult(ItemStack input);
    int getShatterAmount();
}
