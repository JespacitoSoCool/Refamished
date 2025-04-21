package net.fabricmc.refamished.misc.Recipes;

import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CokeOvenSmeltingRecipes {
    private static final CokeOvenSmeltingRecipes INSTANCE = new CokeOvenSmeltingRecipes();

    public static CokeOvenSmeltingRecipes getInstance() {
        return INSTANCE;
    }

    public static class RecipeEntry {
        public final ItemStack input;
        public final ItemStack output;
        public final int minFireLevel;
        public final int cookTimeTicks; // in ticks (20 ticks = 1 second)

        public RecipeEntry(ItemStack input, ItemStack output, int minutes) {
            this(input, output, 0, minutes);
        }

        public RecipeEntry(ItemStack input, ItemStack output, int minFireLevel, int minutes) {
            this.input = input;
            this.output = output;
            this.minFireLevel = minFireLevel;
            this.cookTimeTicks = minutes * 60 * 20; // Convert minutes to ticks
        }
    }

    private final List<RecipeEntry> recipeList = new ArrayList<RecipeEntry>();

    public List<RecipeEntry> getRecipeList()
    {
        return recipeList;
    }

    public void addRecipe(ItemStack input, ItemStack output, int minFireLevel, int minutes) {
        recipeList.add(new RecipeEntry(input, output, minFireLevel, minutes));
    }

    public RecipeEntry getMatchingRecipe(ItemStack input, int firePower) {
        if (input == null) return null;

        for (RecipeEntry entry : recipeList) {
            if (entry.input.itemID == input.itemID &&
                    firePower >= entry.minFireLevel) {
                return entry;
            }
        }

        return null;
    }

    public boolean isValidRecipe(ItemStack input, int firePower) {
        return getMatchingRecipe(input, firePower) != null;
    }

    public int getRequiredFirePower(ItemStack input) {
        for (RecipeEntry entry : recipeList) {
            if (entry.input.itemID == input.itemID) {
                return entry.minFireLevel;
            }
        }
        return -1;
    }
    public int getCookTime(ItemStack input, int firePower) {
        RecipeEntry recipe = getMatchingRecipe(input, firePower);
        return recipe != null ? recipe.cookTimeTicks : -1;
    }

    public ItemStack getResult(ItemStack input, int firePower) {
        RecipeEntry recipe = getMatchingRecipe(input, firePower);
        return recipe != null ? recipe.output : null;
    }

    public boolean canBeSmelted(ItemStack input) {
        for (RecipeEntry entry : recipeList) {
            if (entry.input.itemID == input.itemID) {
                return true;
            }
        }
        return false;
    }
}
