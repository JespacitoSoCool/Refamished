package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Render.class)
public interface RenderInter {
    @Accessor("renderManager")
    RenderManager rendManager();
}
