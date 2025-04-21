package net.fabricmc.refamished.quality;

import net.minecraft.src.EnumChatFormatting;

public class ToolQualityColorHelper {
	public static int getColorForQuality(ToolQuality quality) {
		if (quality == null) {
			return 0xFFFFFF; // Default white
		}
		return chatFormattingColorToRGB(quality.getColor());
	}

	public static float[] getRGBFromFormatting(EnumChatFormatting formatting) {
		switch (formatting) {
			case RED: return new float[]{1.0f, 0.0f, 0.0f};   // Red
			case WHITE: return new float[]{1.0f, 1.0f, 1.0f}; // White
			case AQUA: return new float[]{0.0f, 1.0f, 1.0f};  // Aqua
			case LIGHT_PURPLE: return new float[]{1.0f, 0.5f, 1.0f}; // Light Purple
			case YELLOW: return new float[]{1.0f, 1.0f, 0.0f}; // Yellow
			default: return new float[]{1.0f, 1.0f, 1.0f};    // Default to white
		}
	}

	public static int getIndexFromFormatting(EnumChatFormatting formatting) {
		switch (formatting) {
			case RED: return 1;   // Red
			case WHITE: return 0; // White
			case AQUA: return 2;  // Aqua
			case LIGHT_PURPLE: return 3; // Light Purple
			case YELLOW: return 4; // Yellow
			default: return 5;    // Default to white
		}
	}

	private static int chatFormattingColorToRGB(EnumChatFormatting formatting) {
		switch (formatting) {
			case RED:
				return 0xFF0000;
			case WHITE:
				return 0xFFFFFF;
			case AQUA:
				return 0x00FFFF;
			case LIGHT_PURPLE:
				return 0xFF55FF;
			case YELLOW:
				return 0xFFFF55;
			default:
				return 0xFFFF55; // Fallback to white if unknown
		}
	}
}