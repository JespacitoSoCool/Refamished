package net.fabricmc.refamished.misc.Recipes;

import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class SmithingRecipes {
    private static final SmithingRecipes INSTANCE = new SmithingRecipes();

    public static SmithingRecipes getInstance() {
        return INSTANCE;
    }

    public interface ISmithingRecipe {
        boolean matches(ItemStack input);
        ItemStack[] getResult(ItemStack input, Object... context);
        int getShatterAmount();
    }

    public static class RecipeEntry implements ISmithingRecipe {
        public final ItemStack input;
        public final ItemStack[] firstOutput;
        public final int shatterAmount;

        public RecipeEntry(ItemStack input, ItemStack[] output1, int shatter) {
            this.input = input;
            this.firstOutput = output1;
            this.shatterAmount = shatter;
        }

        @Override
        public boolean matches(ItemStack input) {
            if (input == null) return false;
            return this.input.itemID == input.itemID && this.input.getItemDamage() == input.getItemDamage();
        }

        @Override
        public ItemStack[] getResult(ItemStack input, Object... context) {
            return firstOutput;
        }

        @Override
        public int getShatterAmount() {
            return shatterAmount;
        }
    }

    private final List<ISmithingRecipe> recipeList = new ArrayList<>();

    public void addRecipe(ItemStack input, ItemStack[] output, int minutes) {
        recipeList.add(new RecipeEntry(input, output, minutes));
    }

    public void addRecipe(ISmithingRecipe recipe) {
        recipeList.add(recipe);
    }

    public ISmithingRecipe getMatchingRecipe(ItemStack input) {
        if (input == null) return null;

        for (ISmithingRecipe recipe : recipeList) {
            if (recipe.matches(input)) {
                return recipe;
            }
        }

        return null;
    }

    public boolean isValidRecipe(ItemStack input) {
        return getMatchingRecipe(input) != null;
    }

    public ItemStack[] getResult(ItemStack input, Object... context) {
        ISmithingRecipe recipe = getMatchingRecipe(input);
        return recipe != null ? recipe.getResult(input, context) : null;
    }

    public int getShatterAmount(ItemStack input) {
        ISmithingRecipe recipe = getMatchingRecipe(input);
        return recipe != null ? recipe.getShatterAmount() : 0;
    }

    public List<RecipeEntry> getRecipeList() {
        List<RecipeEntry> staticRecipes = new ArrayList<>();
        for (ISmithingRecipe recipe : recipeList) {
            if (recipe instanceof RecipeEntry) {
                staticRecipes.add((RecipeEntry) recipe);
            }
        }
        return staticRecipes;
    }
}