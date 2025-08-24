package net.fabricmc.refamished.misc.Recipes;

import net.fabricmc.refamished.misc.CustomRecipes.forging.ArmorCombination;
import net.minecraft.src.*;
import org.lwjgl.Sys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ForgingPlansRecipes {
    private static final ForgingPlansRecipes INSTANCE = new ForgingPlansRecipes();
    public static final int WILDCARD_DAMAGE = 32767;

    public static ForgingPlansRecipes getInstance() {
        return INSTANCE;
    }

    public static class RecipeEntry {
        public final List<ItemStack> inputs;
        public final ItemStack output;
        public final int forgeTime;
        public final int bonus;

        public RecipeEntry(List<ItemStack> inputs, ItemStack output, int forgeTime, int bonusReq) {
            this.inputs = inputs;
            this.output = output;
            this.forgeTime = forgeTime;
            this.bonus = bonusReq;
        }

        public boolean matches(List<ItemStack> inventoryInputs) {
            if (inputs == null || inputs.isEmpty()) return false;
            if (inventoryInputs == null || inventoryInputs.isEmpty()) return false;

            // For each required item, ensure there's enough total of that item in inventoryInputs
            for (ItemStack required : inputs) {
                int needed = required.stackSize;
                for (ItemStack given : inventoryInputs) {
                    if (given == null) continue;
                    if (given.itemID != required.itemID) continue;

                    // damage wildcard support
                    if (required.getItemDamage() != WILDCARD_DAMAGE && given.getItemDamage() != required.getItemDamage()) {
                        continue;
                    }

                    // consume as if counting available quantity (but do not mutate here)
                    int take = Math.min(given.stackSize, needed);
                    needed -= take;
                    if (needed <= 0) break;
                }

                if (needed > 0) return false; // not enough of this required input
            }

            return true;
        }

        public String getOutputNbt(List<ItemStack> inventoryInputs) {
            return "";
        }

        public ItemStack getOutput(List<ItemStack> inventoryInputs) {
            return output;
        }

        public List<ItemStack> getDynamicInput(List<ItemStack> inventoryInputs) {
            return inputs;
        }

        public Boolean isCombinationRecipe() {
            return false;
        }
    }

    private static String compactNBTToString(NBTTagCompound tag) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : tag.getTags()) {
            NBTBase base = (NBTBase) obj;
            String key = base.getName();
            if (base instanceof NBTTagString) {
                sb.append(key).append("=").append(((NBTTagString) base).data).append(";");
            } else if (base instanceof NBTTagInt) {
                sb.append(key).append("=").append(((NBTTagInt) base).data).append(";");
            }
        }
        return sb.toString();
    }

    private final List<RecipeEntry> recipeList = new ArrayList<RecipeEntry>();

    public List<RecipeEntry> getRecipeList() {
        return recipeList;
    }

    public void addRecipe(List<ItemStack> inputs, ItemStack output, int forgeTime, int anvilBonus) {
        recipeList.add(new RecipeEntry(inputs, output, forgeTime, anvilBonus));
    }

    public void addRecipe(List<ItemStack> inputs, ItemStack output, int forgeTime) {
        addRecipe(inputs, output, forgeTime, 0);
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

