package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLivingBase.class)
public interface EntityLivingBaseInterface {
    @Invoker("getLandMovementModifier")
    float getLandThing();
}
