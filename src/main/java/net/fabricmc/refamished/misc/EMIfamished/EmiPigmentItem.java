package net.fabricmc.refamished.misc.EMIfamished;

import emi.shims.java.net.minecraft.util.DyeColor;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;

public record EmiPigmentItem(DyeColor color) {
    public EmiPigmentItem(DyeColor color) {
        this.color = color;
    }

    public ItemStack toStack() {
        return new ItemStack(RefamishedItems.pigment, 1, this.color.ordinal());
    }

    public static EmiPigmentItem byColor(DyeColor color) {
        return new EmiPigmentItem(color);
    }

    public DyeColor color() {
        return this.color;
    }
}
