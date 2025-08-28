package net.fabricmc.refamished.mixin.uselessshit;

import btw.BTWMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BTWMod.class)
public class BTWModMixin {

	@Inject(at = @At("TAIL"), method = "initialize", remap = false)
	private void vsAfter(CallbackInfo info) {
		//RefAchievements.removeStupidAchievements();
	}
}
