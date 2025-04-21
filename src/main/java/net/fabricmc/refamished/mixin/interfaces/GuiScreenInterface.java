package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScreen.class)
public interface GuiScreenInterface {
    @Accessor("fontRenderer")
    FontRenderer getFontRenderer();
}
