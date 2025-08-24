package net.fabricmc.refamished.misc.materialTrait;

import net.minecraft.src.EnumChatFormatting;

import java.util.HashMap;

public class materialTraitHandler {
	static final HashMap<String, materialTrait> traits = new HashMap<>();

	static {
		traits.put("anchor", new materialTrait(1,false, EnumChatFormatting.GOLD,1));
		traits.put("heattrap", new materialTrait(0,true, EnumChatFormatting.GRAY,2));
		traits.put("cumbersome", new materialTrait(0,true, EnumChatFormatting.GRAY,1));
		traits.put("temper", new materialTrait(2,false, EnumChatFormatting.GRAY,2));
		traits.put("softness", new materialTrait(3,false, EnumChatFormatting.YELLOW,1));
		traits.put("reckless", new materialTrait(3,false, EnumChatFormatting.AQUA,1));
		traits.put("brutal", new materialTrait(3,false, EnumChatFormatting.GOLD,1));
		traits.put("enchant", new materialTrait(4,false, EnumChatFormatting.DARK_PURPLE,1));
		traits.put("necrotic", new materialTrait(0,true, EnumChatFormatting.DARK_RED,1));
		traits.put("vessel", new materialTrait(5,false, EnumChatFormatting.DARK_RED,1));
	}

	public static materialTrait getTrait(String trait) {
		return traits.get(trait);
	}
}