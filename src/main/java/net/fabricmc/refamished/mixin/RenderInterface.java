package net.fabricmc.refamished.mixin;

import net.minecraft.src.Render;
import net.minecraft.src.RenderPlayer;
import net.minecraft.src.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Render.class)
public interface RenderInterface {
	@Invoker("bindTexture")
	void bindTex(ResourceLocation par1ResourceLocation);
}