package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityItem;
import net.minecraft.src.Render;
import net.minecraft.src.RenderItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Random;

@Mixin(Render.class)
public interface RenderInterface {
    @Invoker("bindEntityTexture")
    void bindEntityTexture_(Entity par1Entity);
}
