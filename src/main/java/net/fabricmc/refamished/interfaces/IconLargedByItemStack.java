package net.fabricmc.refamished.interfaces;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Icon;
import net.minecraft.src.ItemStack;

public interface IconLargedByItemStack {
    public Icon getIcon(ItemStack stack, EntityPlayer player, int render);
    public int getRenderers(ItemStack stack);
}
