package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.skill.*;
import net.fabricmc.refamished.stats.HardcoreBarefoot;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class PlayerSPMixin {
	@Inject(method = "getSpeedModifier", at = @At("RETURN"))
	private void onSavePlayerData(CallbackInfoReturnable<Float> cir) {
		if (!((EntityPlayerSP) (Object) this).worldObj.isRemote)
		{

		}
	}
}