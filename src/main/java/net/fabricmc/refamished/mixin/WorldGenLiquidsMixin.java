package net.fabricmc.refamished.mixin;

import btw.item.items.ClubItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.mixin.interfaces.WorldGenLiquidsInterface;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorldGenLiquids.class)
public abstract class WorldGenLiquidsMixin {

	@Inject(method = "generate", at = @At("TAIL"))
	private void gen(World world, Random random, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {

	}
}