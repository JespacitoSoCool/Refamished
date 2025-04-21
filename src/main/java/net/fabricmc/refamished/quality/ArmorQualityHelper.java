package net.fabricmc.refamished.quality;

import net.minecraft.src.ItemStack;

import java.util.EnumMap;
import java.util.Random;

public class ArmorQualityHelper {
	private static final Random random = new Random();

	static final EnumMap<ArmorQuality, ArmorQualityEffect> qualityEffects = new EnumMap<>(ArmorQuality.class);

	static {
		// Define effects for each quality
		qualityEffects.put(ArmorQuality.AVERAGE, new ArmorQualityEffect(0f, 1.0f,1F,0));

		qualityEffects.put(ArmorQuality.BROKEN, new ArmorQualityEffect(-2f, 0.9f,0.9F,3));
		qualityEffects.put(ArmorQuality.CRACKED, new ArmorQualityEffect(-1f, 0.95f,1F,1));
		qualityEffects.put(ArmorQuality.UNSTABLE, new ArmorQualityEffect(-1f, 0.95f,0.95F,2));
		qualityEffects.put(ArmorQuality.RUSTED, new ArmorQualityEffect(-1f, 0.9f,0.9F,3));
		qualityEffects.put(ArmorQuality.CORRODED, new ArmorQualityEffect(-1f, 0.6f,0.7F,2));

		qualityEffects.put(ArmorQuality.TOUGH, new ArmorQualityEffect(1f, 0.95f,1.1F,0));
		qualityEffects.put(ArmorQuality.DENSE, new ArmorQualityEffect(1f, 1.0f,1F,1));
		qualityEffects.put(ArmorQuality.LIGHT, new ArmorQualityEffect(1f, 1.0f,1F,0));
		qualityEffects.put(ArmorQuality.HEAVY, new ArmorQualityEffect(2f, 0.95f,1.25F,2));
		qualityEffects.put(ArmorQuality.CHAIN, new ArmorQualityEffect(-1f, 1.05f,1F,-2));

		qualityEffects.put(ArmorQuality.EXCEPTIONAL, new ArmorQualityEffect(1f, 1.025f,1.05F,-1));

		qualityEffects.put(ArmorQuality.MASTERWORK, new ArmorQualityEffect(1f, 1.05f,1.2F,-2));
	}

	public static float getArmorBonus(ArmorQuality quality) {
		return qualityEffects.getOrDefault(quality, new ArmorQualityEffect(0f, 1.0f,1F,0)).getArmorBonus();
	}

	// Retrieve reach adjustment for a given quality
	public static float getSpeedMultiplier(ArmorQuality quality) {
		return qualityEffects.getOrDefault(quality, new ArmorQualityEffect(0f, 1.0f,1F,0)).getSpeedMultiplier();
	}

	public static float getKnockbackResistance(ArmorQuality quality) {
		return qualityEffects.getOrDefault(quality, new ArmorQualityEffect(0f, 1.0f,1F,0)).getKnockbackResistance();
	}

	public static float getWeight(ArmorQuality quality) {
		return qualityEffects.getOrDefault(quality, new ArmorQualityEffect(0f, 1.0f,1F,0)).getWeight();
	}

	public static ArmorQuality getArmorQuality(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ToolQuality")) {
			String qualityName = stack.getTagCompound().getString("ToolQuality");
			for (ArmorQuality quality : ArmorQuality.values()) {
				if (quality.getName().equals(qualityName)) {
					return quality;
				}
			}
		}
		return ArmorQuality.AVERAGE; // Default
	}
}
