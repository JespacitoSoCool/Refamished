package net.fabricmc.refamished.quality;

import net.minecraft.src.ItemStack;

import java.util.EnumMap;

public class ToolQualityHelper {

	static final EnumMap<ToolQuality, QualityEffect> qualityEffects = new EnumMap<>(ToolQuality.class);

	static {
		// Define effects for each quality
		qualityEffects.put(ToolQuality.CRUDE, new QualityEffect(0.85f, -1.5f,-1F));
		qualityEffects.put(ToolQuality.DULL, new QualityEffect(0.65f, -0.75f,-1F));
		qualityEffects.put(ToolQuality.RUSTED, new QualityEffect(0.6f, -1f,-0.75F));
		qualityEffects.put(ToolQuality.BROKEN, new QualityEffect(0.75f, -1.5f,-1F));
		qualityEffects.put(ToolQuality.SHODDY, new QualityEffect(0.85f, -1f,-0.5F));
		qualityEffects.put(ToolQuality.BRITTLE, new QualityEffect(0.9f, -0.5f,0F));
		qualityEffects.put(ToolQuality.AVERAGE, new QualityEffect(1.0f, 0.0f,0F));
		qualityEffects.put(ToolQuality.EXCEPTIONAL, new QualityEffect(1.2f, 0.5f,0.5F));
		qualityEffects.put(ToolQuality.MASTERWORK, new QualityEffect(1.25f, 1f,1F));
		qualityEffects.put(ToolQuality.STURDY, new QualityEffect(1.05f, 0.25f,-0.25F));
		qualityEffects.put(ToolQuality.TEMPERED, new QualityEffect(0.9f, 0.25f,0.25F));
		qualityEffects.put(ToolQuality.ETCHED, new QualityEffect(1.1f, -0.25f,0.25F));
		qualityEffects.put(ToolQuality.HARDENED, new QualityEffect(0.8f, 0.25f,0.25F));

		qualityEffects.put(ToolQuality.LIGHT, new QualityEffect(1.25f, 0.5f,-0.5F));
		qualityEffects.put(ToolQuality.HEAVY, new QualityEffect(0.9f, -0.25f,0.5F));
		qualityEffects.put(ToolQuality.KEEN, new QualityEffect(1f, -0.5f,0.5F));
		qualityEffects.put(ToolQuality.LONG, new QualityEffect(0.7f, 2f,-1F));
		qualityEffects.put(ToolQuality.SHORT, new QualityEffect(1.35f, -1f,0.5F));
		qualityEffects.put(ToolQuality.REINFORCED, new QualityEffect(1.25f, -0.25f,0.5F));

		qualityEffects.put(ToolQuality.P_REACH, new QualityEffect(1f, 1.5f,0F));
		qualityEffects.put(ToolQuality.P_DAMAGE, new QualityEffect(1f, 0f,1.5F));
		qualityEffects.put(ToolQuality.P_SPEED, new QualityEffect(1.4f, 0f,0F));
	}

	// Retrieve dig speed multiplier for a given quality
	public static float getDigSpeedMultiplier(ToolQuality quality) {
		return qualityEffects.getOrDefault(quality, new QualityEffect(1.0f, 0.0f,1F)).getDigSpeedMultiplier();
	}

	// Retrieve reach adjustment for a given quality
	public static float getReachAdjustment(ToolQuality quality) {
		return qualityEffects.getOrDefault(quality, new QualityEffect(1.0f, 0.0f,1F)).getReachAdjustment();
	}

	public static float getAttackDamageBonus(ToolQuality quality) {
		return qualityEffects.getOrDefault(quality, new QualityEffect(1.0f, 0.0f,1F)).getAttackDamageBonus();
	}

	public static ToolQuality getToolQuality(ItemStack stack) {
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ToolQuality")) {
			String qualityName = stack.getTagCompound().getString("ToolQuality");
			for (ToolQuality quality : ToolQuality.values()) {
				if (quality.getName().equals(qualityName)) {
					return quality;
				}
			}
		}
		return ToolQuality.AVERAGE; // Default
	}
}
