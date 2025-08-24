package net.fabricmc.refamished.misc.CustomRecipes.crafting;

import btw.item.BTWItems;
import btw.item.items.WoolItem;
import btw.item.tag.Tag;
import btw.item.tag.TagInstance;
import btw.item.tag.TagOrStack;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.tools.flintSharpenerWool;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;

public class WoolCutting extends ShapelessRecipes {
    private final ItemStack result;
    private final List<ItemStack> ingredients;

    public WoolCutting(ItemStack recipeOutput, List<ItemStack> recipeItems) {
        super(recipeOutput, WoolCutting.toListToTag(recipeItems));
        this.result = recipeOutput;
        this.ingredients = recipeItems;
    }

    public static ArrayList<TagOrStack> toListToTag(List<ItemStack> inputs) {
        ArrayList<TagOrStack> result = new ArrayList<TagOrStack>();

        for (Object obj : inputs) {
            if (obj instanceof ItemStack) {
                result.add(((ItemStack) obj).copy());
            }
            else if (obj instanceof Item) {
                result.add(new ItemStack((Item) obj));
            }
            else if (obj instanceof Block) {
                result.add(new ItemStack((Block) obj));
            }
            else if (obj instanceof TagInstance) {
                result.add((TagInstance) obj);
            }
            else if (obj instanceof Tag) {
                result.add(TagInstance.of((Tag) obj));
            }
            else {
                throw new RuntimeException("Invalid shapeless recipe ingredient: " + obj);
            }
        }

        return result;
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

            if (ingredient != null && ingredient.getItem() == RefamishedItems.flint_sharpener) {
                int currentDamage = ingredient.getItemDamage();
                int maxDamage = ingredient.getMaxDamage();

                int newDamage = Math.min(currentDamage, maxDamage);

                output.getTagCompound().setInteger("damage", newDamage);
            } else if (ingredient != null && ingredient.getItem() == BTWItems.wool) {
                int iWoolColor =  WoolItem.averageWoolColorsInGrid( craftingInventory );
                flintSharpenerWool.SetColor( output, iWoolColor );
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
