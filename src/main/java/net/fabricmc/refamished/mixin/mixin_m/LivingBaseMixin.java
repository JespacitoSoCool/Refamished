package net.fabricmc.refamished.mixin.mixin_m;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityLivingBase.class)
public class LivingBaseMixin {
    @Inject(method = "getSpeedModifier",at = @At("TAIL"), cancellable = true)
    private void modify(CallbackInfoReturnable<Float> cir) {
        EntityLivingBase liver = (EntityLivingBase)(Object)this;
        if (liver.worldObj.getDifficulty() instanceof DifficultyCruel)
        {
            if ((Object) this instanceof EntityPigZombie) {
                EntityPigZombie zombie = (EntityPigZombie) (Object) this;
                float maxHealth = zombie.getMaxHealth();
                float currentHealth = zombie.getHealth();

                float healthRatio = currentHealth / maxHealth;
                float speedMultiplier = 1.0f + (1.0f - healthRatio) * 0.4f;
                System.out.println(speedMultiplier);
                cir.setReturnValue(cir.getReturnValue() * speedMultiplier);
            }
        }
    }
}
