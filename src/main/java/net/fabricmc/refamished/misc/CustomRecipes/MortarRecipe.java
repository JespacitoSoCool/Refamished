package net.fabricmc.refamished.misc.CustomRecipes;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class MortarRecipe extends ShapelessRecipes {
    private final ItemStack result;
    private final List<ItemStack> ingredients;

    public MortarRecipe(ItemStack recipeOutput, List<ItemStack> recipeItems) {
        super(recipeOutput, recipeItems);
        this.result = recipeOutput;
        this.ingredients = recipeItems;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftingInventory) {
        // Get the default output
        ItemStack output = super.getCraftingResult(craftingInventory);

        if (output == null) {
            return null; // No valid output, return null
        }

        // Initialize NBT if it doesn't exist
        if (!output.hasTagCompound()) {
            output.setTagCompound(new NBTTagCompound());
        }

        // Loop through crafting ingredients to find damageable items
        for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
            ItemStack ingredient = craftingInventory.getStackInSlot(i);

            if (ingredient != null && ingredient.isItemStackDamageable()) {
                int currentDamage = ingredient.getItemDamage();
                int maxDamage = ingredient.getMaxDamage();

                int newDamage = Math.min(currentDamage, maxDamage);
                String quality = ToolQualityHelper.getToolQuality(ingredient).getName();

                output.getTagCompound().setInteger("damage", newDamage);

                break;
            }
        }

        return output;
    }

    @Override
    public boolean matches(InventoryCrafting craftingInventory, World world) {
        List<ItemStack> requiredIngredients = new ArrayList<>(ingredients);

        for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
            ItemStack ingredient = craftingInventory.getStackInSlot(i);

            if (ingredient == null) {
                continue; // Ignore empty slots
            }

            boolean matched = false;

            for (ItemStack required : requiredIngredients) {
                if (required.itemID == ingredient.itemID) {
                    // Check if it's the flintBlade and ignore damage
                    if ((required.getItem() == BTWItems.stone) || required.getItemDamage() == ingredient.getItemDamage()) {
                        matched = true;
                        requiredIngredients.remove(required);
                        break;
                    }
                }
            }

            if (!matched) {
                return false; // If an item doesn't match, recipe fails
            }
        }

        return requiredIngredients.isEmpty();
    }
}
