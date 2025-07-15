package net.fabricmc.refamished.entities.tiles;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;

public class steelAnvilContainer extends stoneAnvilContainer {
    public steelAnvilTile tile;

    public steelAnvilContainer(InventoryPlayer playerInventory, steelAnvilTile tile) {
        super(playerInventory,tile);
        this.tile = tile;

        this.addSlotToContainer(new Slot(tile, 0, 80, 21));

        int slotCount = tile.sizeInv();
        int ingotSlotCount = slotCount - 1;
        int centerX = 80;
        int slotY = 46;

        //System.out.println(slotCount + " Container");

        int totalWidth = (ingotSlotCount - 1) * 18;
        int startX = centerX - totalWidth / 2;

        // Add centered ingot slots dynamically
        for (int i = 0; i < ingotSlotCount; i++) {
            int slotX = startX + i * 18;
            this.addSlotToContainer(new Slot(tile, i + 1, slotX, slotY));
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                this.addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            this.addSlotToContainer(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return tile.isUseableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int i) {
        ItemStack itemStack = null;
        Slot slot = (Slot)this.inventorySlots.get(i);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (i < this.tile.getSizeInventory() ? !this.mergeItemStack(itemStack2, this.tile.getSizeInventory(), this.inventorySlots.size(), true) : !this.mergeItemStack(itemStack2, 0, this.tile.getSizeInventory(), false)) {
                return null;
            }
            if (itemStack2.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }
        }
        return itemStack;
    }
}