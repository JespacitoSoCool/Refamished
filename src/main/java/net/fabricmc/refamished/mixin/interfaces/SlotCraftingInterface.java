package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.Gui;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SlotCrafting.class)
public interface SlotCraftingInterface {
    @Invoker("onCrafting")
    void craftThisShit(ItemStack par1ItemStack);
}
