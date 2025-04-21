package net.fabricmc.refamished.interfaces;

import btw.client.texture.CustomUpdatingTexture;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;

public interface IconByItemStack {
    public Icon getIcon(ItemStack stack, EntityPlayer player);
}
