package net.fabricmc.refamished.misc.CustomRecipes.crafting;

import btw.item.BTWItems;
import net.minecraft.src.*;

import java.util.List;

public class WoodenClubRecipe extends ShapelessRecipes {
    private final ItemStack result;
    private final List<ItemStack> ingredients;

    public WoodenClubRecipe(ItemStack recipeOutput, List<ItemStack> recipeItems) {
        super(recipeOutput, WoolCutting.toListToTag(recipeItems));
        this.result = recipeOutput;
        this.ingredients = recipeItems;
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting craftingGrid) {
        ItemStack output = super.getCraftingResult(craftingGrid).copy();
        int headType = 0; // Default to Oak
        int handleColor = 0; // Default (0 means no color)

        for (int i = 0; i < craftingGrid.getSizeInventory(); i++) {
            ItemStack ingredient = craftingGrid.getStackInSlot(i);

            if (ingredient != null) {
                if (isBarkItem(ingredient)) {
                    headType = ingredient.getItemDamage(); // Use Bark damage as head type
                }
                if (isHandleColoringItem(ingredient)) {
                    handleColor = getHandleColor(ingredient);
                }
            }
        }

        // Set NBT data
        if (!output.hasTagCompound()) {
            output.setTagCompound(new NBTTagCompound());
        }
        output.getTagCompound().setInteger("head", headType);
        if (handleColor != 0) {
            output.getTagCompound().setInteger("handle", handleColor);
        }

        return output;
    }

    private boolean isBarkItem(ItemStack stack) {
        return stack.getItem().itemID == BTWItems.bark.itemID;
    }

    private boolean isHandleColoringItem(ItemStack stack) {
        return stack.getUnlocalizedName().contains("dye") || stack.getUnlocalizedName().contains("pigment");
    }

    private int getHandleColor(ItemStack stack) {
        if (stack.getUnlocalizedName().contains("red")) return 0xFF0000;
        if (stack.getUnlocalizedName().contains("blue")) return 0x0000FF;
        if (stack.getUnlocalizedName().contains("green")) return 0x00FF00;
        return 0xFFFFFF; // Default white if no match
    }
}
