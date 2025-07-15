package net.fabricmc.refamished.items.others;

import emi.shims.java.net.minecraft.item.DyeItem;
import net.fabricmc.refamished.misc.EMIfamished.EmiPigmentItem;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;

import java.util.List;

public class PigmentDyeableItem {
    public static ItemStack blendAndSetColor(ItemStack stack, List<EmiPigmentItem> colors) {
        float h;
        int[] is = new int[3];
        int i = 0;
        int j = 0;
        Item item = stack.getItem();
        ItemStack itemStack = stack.copy();
        itemStack.stackSize = 1;
        if (itemStack.getTagCompound() != null && itemStack.getTagCompound().hasKey("display") && itemStack.getTagCompound().getCompoundTag("display").hasKey("color")) {
            int k = itemStack.getTagCompound().getCompoundTag("display").getInteger("color");
            float f = (float)(k >> 16 & 0xFF) / 255.0f;
            float g = (float)(k >> 8 & 0xFF) / 255.0f;
            h = (float)(k & 0xFF) / 255.0f;
            i += (int)(Math.max(f, Math.max(g, h)) * 255.0f);
            is[0] = is[0] + (int)(f * 255.0f);
            is[1] = is[1] + (int)(g * 255.0f);
            is[2] = is[2] + (int)(h * 255.0f);
            ++j;
        }
        for (EmiPigmentItem dyeItem : colors) {
            float[] fs = dyeItem.color().getColorComponents();
            int l = (int)(fs[0] * 255.0f);
            int m = (int)(fs[1] * 255.0f);
            int n = (int)(fs[2] * 255.0f);
            i += Math.max(l, Math.max(m, n));
            is[0] = is[0] + l;
            is[1] = is[1] + m;
            is[2] = is[2] + n;
            ++j;
        }
        if (item == null) {
            return null;
        }
        int k = is[0] / j;
        int o = is[1] / j;
        int p = is[2] / j;
        h = (float)i / (float)j;
        float q = Math.max(k, Math.max(o, p));
        k = (int)((float)k * h / q);
        o = (int)((float)o * h / q);
        p = (int)((float)p * h / q);
        int var26 = (k << 8) + o;
        var26 = (var26 << 8) + p;
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
        if (!itemStack.getTagCompound().hasKey("display")) {
            itemStack.getTagCompound().setCompoundTag("display", new NBTTagCompound());
        }
        itemStack.getTagCompound().getCompoundTag("display").setInteger("color", var26);
        return itemStack;
    }
}
