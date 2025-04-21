package net.fabricmc.refamished.quality;

import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.fabricmc.refamished.quality.ToolQualityHelper.qualityEffects;

public class ToolQualityEffectHelper {
	private static final Map<ToolQuality, StatModifier> qualityModifiers = new HashMap<>();

	static {
		qualityModifiers.put(ToolQuality.MASTERWORK, new StatModifier(0.10f, 20, 0)); // +10% Speed, +20 Durability
		qualityModifiers.put(ToolQuality.SHODDY, new StatModifier(-0.50f, -20, 0)); // -10% Speed, -20 Durability
	}

	public static void applyModifiers(ItemStack stack, float baseSpeed, float baseDamage) {
		ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
		if (qualityModifiers.containsKey(quality)) {
			StatModifier modifier = qualityModifiers.get(quality);
			//stack.setDigSpeed(baseSpeed * (1 + modifier.speedMultiplier));
			//stack.setMaxDamage(stack.getMaxDamage() + modifier.durabilityBonus);
		}
	}

	public static QualityEffect getEffect(ToolQuality quality) {
		QualityEffect effect = qualityEffects.get(quality);
		if (effect == null) {
			// Default to no changes if the quality isn't defined
			return new QualityEffect(1.0f, 0.0f, 0.0f);
		}
		return effect;
	}

	public static List<String> generateDescription(ToolQuality quality) {
		List<String> description = new ArrayList<>();
		QualityEffect effect = getEffect(quality);

		// Add reach adjustment
		if (effect.getReachAdjustment() != 0.0f) {
			String reachText = (effect.getReachAdjustment() > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(effect.getReachAdjustment() > 0 ? "+" : "") + effect.getReachAdjustment() + " Reach";
			description.add(reachText);
		}

		// Add dig speed multiplier
		if (effect.getDigSpeedMultiplier() != 1.0f) {
			float percentSpeed = (effect.getDigSpeedMultiplier() - 1.0f) * 100.0f;
			String speedText = (percentSpeed > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(percentSpeed > 0 ? "+" : "") + (int) percentSpeed + "% Speed";
			description.add(speedText);
		}

		// Add attack damage bonus
		if (effect.getAttackDamageBonus() != 0.0f) {
			String damageText = (effect.getAttackDamageBonus() > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(effect.getAttackDamageBonus() > 0 ? "+" : "") + effect.getAttackDamageBonus() + " Damage";
			description.add(damageText);
		}

		return description;
	}

	// Inner class for stat modifiers
	private static class StatModifier {
		public final float speedMultiplier;
		public final int durabilityBonus;
		public final int attackBonus;

		public StatModifier(float speedMultiplier, int durabilityBonus, int attackBonus) {
			this.speedMultiplier = speedMultiplier;
			this.durabilityBonus = durabilityBonus;
			this.attackBonus = attackBonus;
		}
	}
}
