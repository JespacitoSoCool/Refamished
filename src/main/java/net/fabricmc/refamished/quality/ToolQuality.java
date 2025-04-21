package net.fabricmc.refamished.quality;

import net.minecraft.src.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum ToolQuality {
	CRUDE("Crude", 0, 0, EnumChatFormatting.RED),
	DULL("Dull", 0, 0, EnumChatFormatting.RED),
	RUSTED("Rusted", 0, 0, EnumChatFormatting.RED),
	BROKEN("Broken", -35, 5, EnumChatFormatting.RED),
	SHODDY("Shoddy", -10, 10, EnumChatFormatting.RED),
	BRITTLE("Brittle", -10, 10, EnumChatFormatting.RED),
	STURDY("Sturdy", -10, 15, EnumChatFormatting.YELLOW),
	AVERAGE("Average", 0, 120, EnumChatFormatting.WHITE),
	EXCEPTIONAL("Exceptional", 10, 25, EnumChatFormatting.AQUA),
	MASTERWORK("Masterwork", 20, 5, EnumChatFormatting.LIGHT_PURPLE),

	LIGHT("Lightweight", -15, 15, EnumChatFormatting.YELLOW),
	HEAVY("Heavy", 40, 15, EnumChatFormatting.YELLOW),
	KEEN("Keen", -5, 15, EnumChatFormatting.YELLOW),
	LONG("Long", -15, 15, EnumChatFormatting.YELLOW),
	SHORT("Short", -15, 15, EnumChatFormatting.YELLOW),
	REINFORCED("Reinforced", 30, 10, EnumChatFormatting.YELLOW);

	private final String name;
	private final int bonus;
	private final int weight;
	private final EnumChatFormatting color;

	ToolQuality(String name, int bonus, int weight, EnumChatFormatting color) {
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

	public static ToolQuality getRandomQuality() {
		int totalWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			totalWeight += quality.getWeight();
		}

		int random = new Random().nextInt(totalWeight);
		int currentWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			currentWeight += quality.getWeight();
			if (random < currentWeight) {
				return quality;
			}
		}

		return AVERAGE; // Default fallback
	}

	public static ToolQuality getRandomNegativeQuality() {
		List<ToolQuality> negativeQualities = new ArrayList<>();

		for (ToolQuality quality : ToolQuality.values()) {
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
