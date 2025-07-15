package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.ModelBase;
import net.minecraft.src.Render;
import net.minecraft.src.RenderManager;
import net.minecraft.src.RendererLivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RendererLivingEntity.class)
public interface RenderEntityLivingInter {
    @Accessor("mainModel")
    ModelBase getMainModel();
}
