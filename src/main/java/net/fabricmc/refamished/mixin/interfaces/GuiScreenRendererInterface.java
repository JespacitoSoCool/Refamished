package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GuiScreen.class)
public interface GuiScreenRendererInterface {
    @Accessor("mc")
    Minecraft getMc();
}
