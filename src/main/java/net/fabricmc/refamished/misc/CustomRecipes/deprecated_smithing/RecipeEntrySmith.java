package net.fabricmc.refamished.misc.CustomRecipes.deprecated_smithing;

import net.minecraft.src.ItemStack;

public class RecipeEntrySmith implements ISmithingDynamicRecipe {
    public final ItemStack input;
    public final ItemStack[] firstOutput;
    public final int shatterAmount;

    public RecipeEntrySmith(ItemStack input, ItemStack[] output1, int shatter) {
        this.input = input;
        this.firstOutput = output1;
        this.shatterAmount = shatter;
    }

    @Override
    public boolean matches(ItemStack input) {
        return input != null && this.input.itemID == input.itemID && this.input.getItemDamage() == input.getItemDamage();
    }

    @Override
    public ItemStack[] getResult(ItemStack input) {
        return firstOutput;
    }

    @Override
    public int getShatterAmount() {
        return shatterAmount;
    }
}
