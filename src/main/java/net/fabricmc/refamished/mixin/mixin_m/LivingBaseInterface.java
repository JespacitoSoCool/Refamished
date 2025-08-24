package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.EntityAITasks;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityLivingBase;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLivingBase.class)
public interface LivingBaseInterface {
    @Invoker("isWeightedByHeadCrab")
    boolean isWeightedByHeadCrabButCooler();
}
