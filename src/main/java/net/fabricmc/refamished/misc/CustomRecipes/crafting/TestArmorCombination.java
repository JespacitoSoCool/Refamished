package net.fabricmc.refamished.misc.CustomRecipes.crafting;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class TestArmorCombination extends ShapelessRecipes {
    private final List<ItemStack> ingredients;

    public TestArmorCombination(List<ItemStack> recipeItems) {
        super(new ItemStack(RefamishedItems.craftedBoots), WoolCutting.toListToTag(recipeItems));
        this.ingredients = recipeItems;
    }

    public static final Object[][] armorPairs = new Object[][] {
            { "helm", "coif", RefamishedItems.craftedHelmet },
            { "pauldrons", "cuirass" , RefamishedItems.craftedChestplate},
            { "tassets", "greaves" , RefamishedItems.craftedLeggings},
            { "threads", "sabatons" , RefamishedItems.craftedBoots},
    };

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

        for (int pairIndex = 0; pairIndex < armorPairs.length; pairIndex++) {
            String topName = (String) armorPairs[pairIndex][0];
            String bottomName = (String) armorPairs[pairIndex][1];
            Item resultItem = (Item) armorPairs[pairIndex][2];
            ItemStack newItem = new ItemStack(resultItem,1);

            NBTTagCompound tag = new NBTTagCompound();
            newItem.setTagCompound( tag );

            boolean hasTop = false;
            boolean hasBottom = false;

            for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
                ItemStack ingredient = craftingInventory.getStackInSlot(i);
                if (ingredient == null) continue;

                if (metallurgyArmor.isPartOf(ingredient, bottomName)) {
                    hasBottom = true;
                    tag.setString("Bottom", metallurgyArmor.getMaterial(ingredient, bottomName));
                }
                else if (metallurgyArmor.isPartOf(ingredient, topName)) {
                    hasTop = true;
                    tag.setString("Top", metallurgyArmor.getMaterial(ingredient, topName));
                }
            }
            if (hasTop && hasBottom) {
                return newItem;
            }
        }

        return null;
    }

    @Override
    public boolean matches(InventoryCrafting craftingInventory, World world) {
        List<ItemStack> requiredIngredients = new ArrayList<>(ingredients);

        for (int i = 0; i < craftingInventory.getSizeInventory(); i++) {
            ItemStack ingredient = craftingInventory.getStackInSlot(i);

            if (ingredient == null) {
                continue;
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
}
