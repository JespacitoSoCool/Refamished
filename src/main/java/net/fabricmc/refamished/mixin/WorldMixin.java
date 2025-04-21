package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.skill.*;
import net.fabricmc.refamished.stats.HardcoreBarefoot;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(World.class)
public abstract class WorldMixin {
	@Inject(at = @At("HEAD"), method = "initialize")
	private void init(CallbackInfo info) {
		SkillRecipeStarter the = new SkillRecipeStarter();
		the.begin();
	}
}