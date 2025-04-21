package net.fabricmc.refamished.quality;

public class ArmorQualityEffect {
	private final float armorBonus;
	private final float SpeedMultiplier;
	private final float knockbackResistance;
	private final int weight;

	public ArmorQualityEffect(float armor, float speed, float knock, int weight) {
		this.armorBonus = armor;
		this.SpeedMultiplier = speed;
		this.knockbackResistance = knock;
		this.weight = weight;
	}

	public float getArmorBonus() {
		return armorBonus;
	}

	public float getSpeedMultiplier() {
		return SpeedMultiplier;
	}

	public float getKnockbackResistance() {
		return knockbackResistance;
	}

	public float getWeight() {
		return weight;
	}
}
