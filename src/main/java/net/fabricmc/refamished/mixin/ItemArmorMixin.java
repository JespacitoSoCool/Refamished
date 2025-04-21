package net.fabricmc.refamished.mixin;

import btw.item.items.ArmorItem;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ArmorItem.class)
public abstract class ItemArmorMixin {

	@Inject(method = "onCreated", at = @At("HEAD"))
	private void assignToolQuality(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, CallbackInfo ci) {
		SkillManager.addExperience(par3EntityPlayer,"Artisanry",5);
		if (!par2World.isRemote)
		{
			if (!par1ItemStack.hasTagCompound()) {
				par1ItemStack.setTagCompound(new NBTTagCompound());
			}
			ArmorQuality quality = ArmorQuality.getRandomQuality();
			par1ItemStack.getTagCompound().setString("ToolQuality", quality.getName());
		}
	}
}
