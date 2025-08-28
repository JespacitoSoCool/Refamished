package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.Gui;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Minecraft.class)
public interface MinecraftInterface {
    @Invoker("resize")
    void re(int par1, int par2);
}
