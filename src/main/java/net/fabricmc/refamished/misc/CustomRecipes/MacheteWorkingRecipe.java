package net.fabricmc.refamished.misc.CustomRecipes;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class MacheteWorkingRecipe extends ShapelessRecipes {
    private final ItemStack result;
    private final List<ItemStack> ingredients;

    public MacheteWorkingRecipe(ItemStack recipeOutput, List<ItemStack> recipeItems) {
        super(recipeOutput, recipeItems);
        this.result = recipeOutput;
        this.ingredients = recipeItems;
    }

    public static int getBindingColor(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("binding")) {
            int id = stack.getTagCompound().getInteger("binding");
            return id;
        }
        return 0;
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

                // Add damage but ensure it doesn't exceed the max damage
                int newDamage = Math.min(currentDamage, maxDamage);
                String quality = ToolQualityHelper.getToolQuality(ingredient).getName();
                int binding = getBindingColor(ingredient);

                // Set the custom NBT tag
                output.getTagCompound().setInteger("damage", newDamage);
                output.getTagCompound().setString("quality", quality);
                output.getTagCompound().setInteger("binding", binding);

                break; // Stop after processing the first damageable ingredient
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
                    if ((required.getItem() == RefamishedItems.flint_machete) || required.getItemDamage() == ingredient.getItemDamage()) {
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
