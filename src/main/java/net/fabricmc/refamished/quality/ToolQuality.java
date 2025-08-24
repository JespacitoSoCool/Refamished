package net.fabricmc.refamished.quality;

import btw.item.items.ChiselItem;
import btw.item.items.ClubItem;
import btw.item.items.ShovelItem;
import btw.item.items.ToolItem;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.minecraft.src.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum ToolQuality {
	CRUDE("Crude", 0, 0, EnumChatFormatting.RED),
	DULL("Dull", 0, 0, EnumChatFormatting.RED),
	RUSTED("Rusted", 0, 0, EnumChatFormatting.RED),
	BROKEN("Broken", -1, 10, EnumChatFormatting.RED),
	SHODDY("Shoddy", -1, 10, EnumChatFormatting.RED),
	BRITTLE("Brittle", -1, 10, EnumChatFormatting.RED),
	AVERAGE("Average", -5, 120, EnumChatFormatting.WHITE),

	STURDY("Sturdy", -1, 15, EnumChatFormatting.YELLOW),
	LIGHT("Lightweight", 1, 15, EnumChatFormatting.YELLOW),
	HEAVY("Heavy", 1, 15, EnumChatFormatting.YELLOW),
	KEEN("Keen", 1, 15, EnumChatFormatting.YELLOW),
	LONG("Long", 1, 15, EnumChatFormatting.YELLOW),
	SHORT("Short", 1, 15, EnumChatFormatting.YELLOW),
	REINFORCED("Reinforced", 1, 15, EnumChatFormatting.YELLOW),
	TEMPERED("Tempered", 1, 15, EnumChatFormatting.YELLOW),
	ETCHED("Etched", 1, 15, EnumChatFormatting.YELLOW),
	HARDENED("Hardened", 1, 15, EnumChatFormatting.YELLOW),

	P_REACH("Reach+", 0, 0, EnumChatFormatting.GOLD),
	P_DAMAGE("Damage+", 0, 0, EnumChatFormatting.GOLD),
	P_SPEED("Speed+", 0, 0, EnumChatFormatting.GOLD),

	EXCEPTIONAL("Exceptional", 5, 25, EnumChatFormatting.AQUA),
	MASTERWORK("Masterwork", 4, 5, EnumChatFormatting.LIGHT_PURPLE);


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

	public static ToolQuality getRandomQuality(int level) {
		int totalWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			totalWeight += MathHelper.clamp_int(quality.getWeight() + (quality.getBonus()*level),0,1000);
		}

		int random = new Random().nextInt(totalWeight);
		int currentWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			currentWeight += MathHelper.clamp_int(quality.getWeight() + (quality.getBonus()*level),0,1000);
			if (random < currentWeight) {
				return quality;
			}
		}
		return AVERAGE;
	}

	public static ToolQuality getRandomQuality() {
		return getRandomQuality(0);
	}

	public static ToolQuality getRandomQualityReroll(int level) {
		int totalWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			if (quality == MASTERWORK) continue;
			totalWeight += MathHelper.clamp_int(quality.getWeight() + (quality.getBonus() * level), 0, 1000);
		}

		int random = new Random().nextInt(totalWeight);
		int currentWeight = 0;

		for (ToolQuality quality : ToolQuality.values()) {
			if (quality == MASTERWORK) continue;
			currentWeight += MathHelper.clamp_int(quality.getWeight() + (quality.getBonus() * level), 0, 1000);
			if (random < currentWeight) {
				return quality;
			}
		}

		return AVERAGE;
	}

	public static ToolQuality getRandomNegativeQuality() {
		List<ToolQuality> negativeQualities = new ArrayList<>();

		for (ToolQuality quality : ToolQuality.values()) {
			if (quality.getColor() == EnumChatFormatting.RED) {
				negativeQualities.add(quality);
			}
		}

		if (negativeQualities.isEmpty()) {
			return AVERAGE;
		}

		return negativeQualities.get(new Random().nextInt(negativeQualities.size()));
	}

	public static boolean toolHaveQualities(Item item) {
		return item instanceof ItemSword || item instanceof ToolItem || item instanceof blade || item instanceof machete || item instanceof ChiselItem || item instanceof ClubItem;
	}
}
