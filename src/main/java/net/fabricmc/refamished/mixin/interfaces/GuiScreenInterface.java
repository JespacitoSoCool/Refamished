package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(GuiScreen.class)
public interface GuiScreenInterface {
    @Accessor("fontRenderer")
    FontRenderer getFontRenderer();
    @Accessor("mc")
    Minecraft getMc();
    @Accessor("buttonList")
    List getButtonList();
}
