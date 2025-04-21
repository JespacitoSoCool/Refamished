package net.fabricmc.refamished.mixin;

import btw.item.items.ToolItem;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ToolItem.class)
public abstract class ItemToolMixin {

	@Inject(method = "onCreated", at = @At("HEAD"))
	private void assignToolQuality(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, CallbackInfo ci) {
		int maxDurability = par1ItemStack.getMaxDamage();
		int experience = (int) (maxDurability/7);

		SkillManager.addExperience(par3EntityPlayer,"Artisanry", experience);
		if (!par2World.isRemote)
		{
			if (!par1ItemStack.hasTagCompound()) {
				par1ItemStack.setTagCompound(new NBTTagCompound());
			}
			ToolQuality quality = ToolQuality.getRandomQuality();
			par1ItemStack.getTagCompound().setString("ToolQuality", quality.getName());
		}
	}

	@Inject(method = "getStrVsBlock", at = @At("RETURN"), cancellable = true)
	private void qualitySpeed(ItemStack stack, World world, Block block, int i, int j, int k, CallbackInfoReturnable<Float> cir) {
		// Retrieve the tool quality using the helper class
		ToolQuality quality = ToolQualityHelper.getToolQuality(stack);

		// Get the base dig speed
		float baseSpeed = cir.getReturnValue();

		// Apply quality-based modifiers
		float modifiedSpeed = baseSpeed * ToolQualityHelper.getDigSpeedMultiplier(quality);

		// Return the modified strength
        cir.setReturnValue(modifiedSpeed);
	}

	@Inject(method = "hitEntity", at = @At("HEAD"))
	private void applyToolQualityDamage(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker, CallbackInfoReturnable<Boolean> cir) {
		// Fetch ToolQuality from the ItemStack
		ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
		if (quality != null) {
			float bonusDamage = ToolQualityHelper.getAttackDamageBonus(quality);

			target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), bonusDamage);
			System.out.println("Applied tool quality bonus: " + bonusDamage + " for quality: " + quality.getName());
		}
	}
}
