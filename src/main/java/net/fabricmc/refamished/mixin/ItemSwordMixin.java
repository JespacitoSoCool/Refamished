package net.fabricmc.refamished.mixin;

import btw.entity.attribute.AttributeOperation;
import btw.item.items.ToolItem;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemSword.class)
public abstract class ItemSwordMixin {

	@Inject(method = "getItemAttributeModifiers", at = @At("TAIL"), cancellable = true)
	private void assignToolQuality(CallbackInfoReturnable<Multimap> cir) {
		Multimap var1 = cir.getReturnValue();
		var1.put(ResharedMonsterAttributes.attackRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedMod.toolRangeUUID, "Attack Range", 0.25, AttributeOperation.ADDITIVE.value));
		cir.setReturnValue(var1);
	}
}
