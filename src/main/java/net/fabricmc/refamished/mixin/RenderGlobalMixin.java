package net.fabricmc.refamished.mixin;

import btw.world.util.WorldUtils;
import btw.world.util.difficulty.Difficulties;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.mixin.interfaces.EntityLivingBaseInterface;
import net.fabricmc.refamished.mixin.interfaces.RenderGlobalInterface;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.stats.HardcoreBarefoot;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderGlobal.class)
public abstract class RenderGlobalMixin {
	@Inject(method = "doSpawnParticle", at = @At("TAIL"), cancellable = true)
	private void barefoot(String par1Str, double par2, double par4, double par6, double par8, double par10, double par12, CallbackInfoReturnable<EntityFX> cir) {
		RenderGlobal renderer = (RenderGlobal)(Object)this;
		RenderGlobalInterface r = (RenderGlobalInterface)(Object)this;
		Minecraft mc = r.getMc();
		WorldClient world = r.getWorld();
		if (mc != null && mc.renderViewEntity != null && mc.effectRenderer != null) {
			double dDistSqToPlayer;
			int var14 = mc.gameSettings.particleSetting;
			if (var14 == 1 && world.rand.nextInt(3) == 0) {
				var14 = 2;
			}
			double var15 = mc.renderViewEntity.posX - par2;
			double var17 = mc.renderViewEntity.posY - par4;
			double var19 = mc.renderViewEntity.posZ - par6;
			EntityFX var21 = null;
			if (par1Str.startsWith("iconcrack_")) {
				String[] var27 = par1Str.split("_", 3);
				int var25 = Integer.parseInt(var27[1]);
				int var26 = Integer.parseInt(var27[2]);
				int meta = Integer.parseInt(var27[3]);
				if (var27.length > 2 && meta != 0) {
					Item itemForm = Item.itemsList[var26];
					var21 = new EntityBreakingFX(world, par2, par4, par6, par8, par10, par12, Item.itemsList[var25], 3);
				}
				if (var21 != null) {
					mc.effectRenderer.addEffect(var21);
				}
			}
			if (var21 != null)
			{
				cir.setReturnValue(var21);
			}
		}
	}
}