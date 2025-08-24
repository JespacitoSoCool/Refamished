package net.fabricmc.refamished.misc;

import net.fabricmc.refamished.interfaces.EnumArmorMaterialInterface;
import net.fabricmc.refamished.interfaces.EnumChatFormattingInterface;
import net.fabricmc.refamished.interfaces.EnumToolMaterialInterface;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.EnumToolMaterial;

public class ReFormatting {
    public static EnumChatFormatting chat_DIAMOND_STEEL;

    public static void init() {
        EnumChatFormattingInterface tool = ((EnumChatFormattingInterface) (Object) EnumChatFormatting.WHITE);

        chat_DIAMOND_STEEL = tool.getDiamondSteel();
    }
}
