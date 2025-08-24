package net.fabricmc.refamished.mixin.uselessshit;

import btw.BTWMod;
import btw.achievement.BTWAchievements;
import net.fabricmc.refamished.misc.RefAchievements;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BTWAchievements.class)
public class AchievementsMixin {
	@Inject(at = @At("HEAD"), method = "initialize", remap = false)
	private static void init(CallbackInfo info) {
		RefAchievements.initialize();
	}
}
