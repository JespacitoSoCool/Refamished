package net.fabricmc.refamished.misc.CustomRecipes.deprecated_smithing;

import net.minecraft.src.ItemStack;

public abstract class DynamicRecipeEntry implements ISmithingDynamicRecipe {
    @Override
    public abstract boolean matches(ItemStack input);

    @Override
    public abstract ItemStack[] getResult(ItemStack input);

    @Override
    public abstract int getShatterAmount();
}
