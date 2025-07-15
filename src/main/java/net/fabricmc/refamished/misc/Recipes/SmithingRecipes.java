package net.fabricmc.refamished.misc.Recipes;

import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SmithingRecipes {
    private static final SmithingRecipes INSTANCE = new SmithingRecipes();

    public static SmithingRecipes getInstance() {
        return INSTANCE;
    }

    public static class RecipeEntry {
        public final ItemStack input;
        public final ItemStack[] firstOutput;
        public final int shatterAmount;

        public RecipeEntry(ItemStack input, ItemStack[] output1, int shatter) {
            this.input = input;
            firstOutput = output1;
            shatterAmount = shatter;
        }
    }

    private final List<RecipeEntry> recipeList = new ArrayList<RecipeEntry>();

    public List<RecipeEntry> getRecipeList()
    {
        return recipeList;
    }

    public void addRecipe(ItemStack input, ItemStack[] output, int minutes) {
        recipeList.add(new RecipeEntry(input, output, minutes));
    }

    public RecipeEntry getMatchingRecipe(ItemStack input) {
        if (input == null) return null;

        for (RecipeEntry entry : recipeList) {
            if (entry.input.itemID == input.itemID && entry.input.getItemDamage() == input.getItemDamage()) {
                return entry;
            }
        }

        return null;
    }

    public boolean isValidRecipe(ItemStack input) {
        return getMatchingRecipe(input) != null;
    }

    public ItemStack[] getResult(ItemStack input) {
        RecipeEntry recipe = getMatchingRecipe(input);
        return recipe != null ? recipe.firstOutput : null;
    }

    public boolean isValid(ItemStack input) {
        for (RecipeEntry entry : recipeList) {
            if (entry.input.itemID == input.itemID && entry.input.getItemDamage() == input.getItemDamage()) {
                return true;
            }
        }
        return false;
    }
}
