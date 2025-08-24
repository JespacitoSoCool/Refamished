package net.fabricmc.refamished.misc.materialTrait;

import net.minecraft.src.EnumChatFormatting;

public class materialTrait {
	private final int rating;
	private final boolean isNegative;
	private final EnumChatFormatting format;
	private final int maxLevel;

	public materialTrait(int rating, boolean isNegative, EnumChatFormatting format, int maxLevel) {
		this.rating = rating;
		this.isNegative = isNegative;
		this.format = format;
		this.maxLevel = maxLevel;
	}

	public int getRating() {
		return rating;
	}

	public boolean isNegative() {
		return isNegative;
	}

	public EnumChatFormatting getFormat() {
		return format;
	}

	public int getMaxLevel() {
		return maxLevel;
	}
}
