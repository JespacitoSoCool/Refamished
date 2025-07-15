package net.fabricmc.refamished.misc.Recipes;

import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.Block;
import net.minecraft.src.FurnaceRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FurnaceMetaRecipes {
    private static final FurnaceMetaRecipes INSTANCE = new FurnaceMetaRecipes();
    private final List<RecipeEntry> recipeList = new ArrayList<>();

    public static FurnaceMetaRecipes getInstance() {
        return INSTANCE;
    }

    public static class RecipeEntry {
        public final ItemStack input;
        public final ItemStack output;
        public final int cookTime;

        public RecipeEntry(ItemStack input, ItemStack output, int cookTime) {
            this.input = input;
            this.output = output;
            this.cookTime = cookTime;
        }
    }

    public List<RecipeEntry> getRecipeList()
    {
        return recipeList;
    }

    public RecipeEntry getMatchingRecipe(ItemStack input) {
        if (input == null) return null;

        for (RecipeEntry entry : recipeList) {
//            System.out.println(entry.input.getItemDamage());
//            System.out.println(input.getItemDamage());
//            System.out.println(entry.input.getItemDamage() == input.getItemDamage());
            if (entry.input.itemID == input.itemID && entry.input.getItemDamage() == input.getItemDamage()) {
                return entry;
            }
        }
        return null;
    }

    public void addSmelting(ItemStack inputStack, ItemStack outputStack, int iCookTimeBinaryShift) {
        recipeList.add(new RecipeEntry(inputStack, outputStack, iCookTimeBinaryShift));
    }

    public int getCookTimeBinaryShift(ItemStack inputStack) {
        return getMatchingRecipe(inputStack) != null ? getMatchingRecipe(inputStack).cookTime : 0;
    }
}
