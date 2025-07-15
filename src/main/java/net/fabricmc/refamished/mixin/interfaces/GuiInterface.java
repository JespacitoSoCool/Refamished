package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.Gui;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Gui.class)
public interface GuiInterface {
    @Invoker("drawGradientRect")
    void drawGradientRect_(int par1, int par2, int par3, int par4, int par5, int par6);
}
