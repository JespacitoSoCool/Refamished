package net.fabricmc.refamished.misc.Recipes;

import net.minecraft.src.ItemStack;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ForgingPlansRecipes {
    private static final ForgingPlansRecipes INSTANCE = new ForgingPlansRecipes();

    public static ForgingPlansRecipes getInstance() {
        return INSTANCE;
    }

    public static class RecipeEntry {
        public final List<ItemStack> inputs; // List of required input stacks
        public final ItemStack output;       // What gets crafted
        public final int forgeTime;
        public final int bonus;

        public RecipeEntry(List<ItemStack> inputs, ItemStack output, int forgeTime, int bonusReq) {
            this.inputs = inputs;
            this.output = output;
            this.forgeTime = forgeTime;
            this.bonus = bonusReq;
        }

        public boolean matches(List<ItemStack> inventoryInputs) {
            for (ItemStack required : inputs) {
                boolean found = false;
                for (ItemStack given : inventoryInputs) {
                    if (given != null && given.itemID == required.itemID && given.stackSize >= required.stackSize && given.getItemDamage() == required.getItemDamage()) {
                        found = true;
                        break;
                    }
                }
                if (!found) return false;
            }
            return true;
        }
    }

    private final List<RecipeEntry> recipeList = new ArrayList<RecipeEntry>();

    public List<RecipeEntry> getRecipeList()
    {
        return recipeList;
    }

    public void addRecipe(List<ItemStack> inputs, ItemStack output, int forgeTime, int anvilBonus) {
        recipeList.add(new RecipeEntry(inputs, output, forgeTime,anvilBonus));
    }

    public void addRecipe(List<ItemStack> inputs, ItemStack output, int forgeTime) {
        addRecipe(inputs,output,forgeTime,0);
    }

    public List<RecipeEntry> getMatchingRecipes(List<ItemStack> inventoryInputs) {
        List<RecipeEntry> matches = new ArrayList<>();
        for (RecipeEntry entry : recipeList) {
            if (entry.matches(inventoryInputs)) {
                matches.add(entry);
            }
        }
        return matches;
    }

    public List<RecipeEntry> getMatchingRecipes(List<ItemStack> inventoryInputs, int anvil) {
        List<RecipeEntry> matches = new ArrayList<>();
        //System.out.println(anvil);
        for (RecipeEntry entry : recipeList) {
            if (entry.matches(inventoryInputs) && entry.bonus <= anvil) {
                matches.add(entry);
            }
        }
        return matches;
    }

    public RecipeEntry getMatchingRecipes(String outPutId) {
        for (RecipeEntry entry : recipeList) {
            if (Objects.equals(entry.output.getUnlocalizedName(), outPutId)) {
                return entry;
            }
        }
        return null;
    }
}
