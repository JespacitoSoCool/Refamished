package net.fabricmc.refamished.mixin;


import btw.item.BTWItems;
import btw.item.items.*;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.tools.axeDecorated;
import net.fabricmc.refamished.items.tools.hoeDecorated;
import net.fabricmc.refamished.items.tools.pickaxeDecorated;
import net.fabricmc.refamished.items.tools.shovelDecorated;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.quality.*;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BTWItems.class)
public abstract class ItemBTWMixin {
	@Inject(method = "instantiateModItems", at = @At("TAIL"), remap = false)
	private static void replaceItem(CallbackInfo ci) {
		RefamishedItems.replaceBTWItems();
	}
}
