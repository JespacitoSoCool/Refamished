package net.fabricmc.refamished.quality;

import net.minecraft.src.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;

import static net.fabricmc.refamished.quality.ArmorQualityHelper.qualityEffects;

public class ArmorQualityEffectHelper {

	public static ArmorQualityEffect getEffect(ArmorQuality quality) {
		ArmorQualityEffect effect = qualityEffects.get(quality);
		if (effect == null) {
			// Default to no changes if the quality isn't defined
			return new ArmorQualityEffect(0f, 1.0f, 1.0f,0);
		}
		return effect;
	}

	public static List<String> generateDescription(ArmorQuality quality) {
		List<String> description = new ArrayList<>();
		ArmorQualityEffect effect = getEffect(quality);

		if (effect.getArmorBonus() != 0.0f) {
			String reachText = (effect.getArmorBonus() > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(effect.getArmorBonus() > 0 ? "+" : "") + effect.getArmorBonus() + " Armor";
			description.add(reachText);
		}

		if (effect.getSpeedMultiplier() != 1.0f) {
			float percentSpeed = (effect.getSpeedMultiplier() - 1.0f) * 100.0f;
			String speedText = (percentSpeed > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(percentSpeed > 0 ? "+" : "") + (int) percentSpeed + "% Speed";
			description.add(speedText);
		}

		if (effect.getKnockbackResistance() != 1.0f) {
			float percentSpeed = (effect.getKnockbackResistance() - 1.0f) * 100.0f;
			String speedText = (percentSpeed > 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(percentSpeed > 0 ? "+" : "") + (int) percentSpeed + "% Knockback Resistance";
			description.add(speedText);
		}

		if (effect.getWeight() != 0.0f) {
			String damageText = (effect.getWeight() < 0 ? EnumChatFormatting.BLUE : EnumChatFormatting.RED) +
					(effect.getWeight() > 0 ? "+" : "") + effect.getWeight() + " Weight";
			description.add(damageText);
		}

		return description;
	}

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
