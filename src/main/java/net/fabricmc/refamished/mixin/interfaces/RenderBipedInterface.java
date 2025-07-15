package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.Gui;
import net.minecraft.src.RenderBiped;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RenderBiped.class)
public interface RenderBipedInterface {
    @Invoker("func_82422_c")
    void funcIdk();

    @Invoker("renderEquippedItems")
    void renderItemShit(EntityLivingBase par1EntityLivingBase, float par2);
}
