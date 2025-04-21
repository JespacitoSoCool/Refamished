package net.fabricmc.refamished.quality;

import net.minecraft.src.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum ArmorQuality {
	AVERAGE("Average", 3, 120, EnumChatFormatting.WHITE),

	BROKEN("Broken", -5, 5, EnumChatFormatting.RED),
	CRACKED("Cracked", -3, 10, EnumChatFormatting.RED),
	UNSTABLE("Unstable", -3, 10, EnumChatFormatting.RED),
	RUSTED("Rusted", 0, 0, EnumChatFormatting.RED),
	CORRODED("Corroded", 0, 0, EnumChatFormatting.RED),

	TOUGH("Tough", 3, 15, EnumChatFormatting.YELLOW),
	DENSE("Dense", 3, 15, EnumChatFormatting.YELLOW),
	LIGHT("Light", 3, 15, EnumChatFormatting.YELLOW),
	HEAVY("Heavy", 3, 15, EnumChatFormatting.YELLOW),
	CHAIN("Chain", 3, 15, EnumChatFormatting.YELLOW),

	EXCEPTIONAL("Exceptional", 3, 25, EnumChatFormatting.AQUA),

	MASTERWORK("Masterwork", 3, 5, EnumChatFormatting.LIGHT_PURPLE);


	private final String name;
	private final int bonus;
	private final int weight;
	private final EnumChatFormatting color;

	ArmorQuality(String name, int bonus, int weight, EnumChatFormatting color) {
		this.name = name;
		this.bonus = bonus;
		this.weight = weight;
		this.color = color;
	}

	public EnumChatFormatting getColor() {
		return color;
	}

	public String getName() {
		return name;
	}

	public int getBonus() {
		return bonus;
	}

	public int getWeight() {
		return weight;
	}

	public static ArmorQuality getRandomQuality() {
		int totalWeight = 0;

		for (ArmorQuality quality : ArmorQuality.values()) {
			totalWeight += quality.getWeight();
		}

		int random = new Random().nextInt(totalWeight);
		int currentWeight = 0;

		for (ArmorQuality quality : ArmorQuality.values()) {
			currentWeight += quality.getWeight();
			if (random < currentWeight) {
				return quality;
			}
		}

		return AVERAGE; // Default fallback
	}

	public static ArmorQuality getRandomNegativeQuality() {
		List<ArmorQuality> negativeQualities = new ArrayList<>();

		for (ArmorQuality quality : ArmorQuality.values()) {
			if (quality.getColor() == EnumChatFormatting.RED) {
				negativeQualities.add(quality);
			}
		}

		if (negativeQualities.isEmpty()) {
			return AVERAGE; // Fallback if something goes wrong
		}

		return negativeQualities.get(new Random().nextInt(negativeQualities.size()));
	}
}
