package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ingotMoldRecipes {

    static class InputMetaPair {
        public final ItemStack input;
        public final int outputMeta;

        public InputMetaPair(ItemStack input, int outputMeta) {
            this.input = input;
            this.outputMeta = outputMeta;
        }
    }


    private static boolean isMatching(ItemStack a, ItemStack b) {
        return a.itemID == b.itemID && a.getItemDamage() == b.getItemDamage();
    }

    static InputMetaPair findMatchingInput(EntityPlayer player) {
        final InputMetaPair[] RECIPES = new InputMetaPair[] {
                new InputMetaPair(new ItemStack(RefamishedItems.crushedOres, 1, 0), 0),
                new InputMetaPair(new ItemStack(RefamishedItems.copperDust, 1, 0), 1),
                new InputMetaPair(new ItemStack(RefamishedItems.crushedOres, 1, 1), 3),
                new InputMetaPair(new ItemStack(BTWItems.ironOrePile, 1, 0), 4),
                new InputMetaPair(new ItemStack(RefamishedItems.crushedOres, 1, 2), 6),
                new InputMetaPair(new ItemStack(BTWItems.goldOrePile, 1, 0), 7),
                new InputMetaPair(new ItemStack(RefamishedItems.crushedOres, 1, 3), 9),
                new InputMetaPair(new ItemStack(RefamishedItems.sinterIronDust, 1), 10),
                //new InputMetaPair(new ItemStack(BTWItems.goldOrePile, 1, 0), ),
        };
        for (InputMetaPair recipe : RECIPES) {
            int count = 0;

            // Count in hotbar
            for (int slot = 0; slot < 9; slot++) {
                ItemStack inSlot = player.inventory.getStackInSlot(slot);
                if (inSlot != null && isMatching(inSlot, recipe.input)) {
                    count += inSlot.stackSize;
                }
            }

            if (count >= 8) return recipe;
        }

        return null;
    }

    static void consumeItems(EntityPlayer player, ItemStack target, int amount) {
        for (int slot = 0; slot < 9 && amount > 0; ++slot) {
            ItemStack inSlot = player.inventory.getStackInSlot(slot);
            if (inSlot != null && isMatching(inSlot, target)) {
                int used = Math.min(inSlot.stackSize, amount);
                inSlot.stackSize -= used;
                if (inSlot.stackSize <= 0) {
                    player.inventory.setInventorySlotContents(slot, null);
                }
                amount -= used;
            }
        }
    }


}