package net.fabricmc.refamished.misc.CustomRecipes.crafting;

import btw.item.BTWItems;
import btw.item.items.WoolItem;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class BoneClubRecipe extends ShapelessRecipes {
    private final ItemStack result;
    private final List<ItemStack> ingredients;

    public BoneClubRecipe(ItemStack recipeOutput, List<ItemStack> recipeItems) {
        super(recipeOutput, WoolCutting.toListToTag(recipeItems));
        this.result = recipeOutput;
        this.ingredients = recipeItems;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftingGrid) {
        ItemStack output = super.getCraftingResult(craftingGrid).copy();
        int bindingColor = 0xFFFFFF;

        for (int i = 0; i < craftingGrid.getSizeInventory(); i++) {
            ItemStack ingredient = craftingGrid.getStackInSlot(i);
            if (ingredient != null && isBindingItem(ingredient)) {
                bindingColor = getBindingColor(ingredient);
                break;
            }
        }

        if (!output.hasTagCompound()) {
            output.setTagCompound(new NBTTagCompound());
        }
        output.getTagCompound().setInteger("color", bindingColor);

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
                    matched = true;
                    requiredIngredients.remove(required);
                    break;
                }
            }

            if (!matched) {
                return false; // If an item doesn't match, recipe fails
            }
        }

        return requiredIngredients.isEmpty();
    }

    private boolean isBindingItem(ItemStack stack) {
        // Check if the item is a valid binding material.
        return (stack.getItem() == Item.sign) ||
                (stack.getItem() == BTWItems.sinew) ||
                (stack.getItem() == BTWItems.hempFibers) ||
                (stack.getItem() == RefamishedItems.wool_string);
    }

    private int getBindingColor(ItemStack stack) {
        if (stack.getItem() == Item.sign) return 0xFFFFFF;
        if (stack.getItem() == BTWItems.sinew) return 0xD39B96;
        if (stack.getItem() == BTWItems.hempFibers) return 0x736A5E;
        if (stack.getItem() == RefamishedItems.wool_string) return WoolItem.woolColors[stack.getItemDamage()];

        return 0xFFFFFF;
    }
}
