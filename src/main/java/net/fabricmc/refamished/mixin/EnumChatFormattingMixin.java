package net.fabricmc.refamished.mixin;

import emi.shims.java.net.minecraft.util.Formatting;
import net.fabricmc.refamished.interfaces.EnumChatFormattingInterface;
import net.fabricmc.refamished.interfaces.EnumFormattingInterface;
import net.minecraft.src.EnumChatFormatting;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

@Mixin(EnumChatFormatting.class)
public class EnumChatFormattingMixin implements EnumChatFormattingInterface {
    @Shadow
    @Final
    @Mutable
    private static Map<Character, EnumChatFormatting> field_96321_w;

    @Shadow
    @Final
    private static Map<String, EnumChatFormatting> field_96331_x;

    // Define the custom material
    @Unique
    private static EnumChatFormatting DIAMOND_STEEL = addCustomMaterial("DIAMOND_STEEL", 'j');

    @Invoker("<init>")
    public static EnumChatFormatting theExpansionInit(String internalName, int internalId, char c, boolean coloridk) {
        throw new AssertionError();
    }

    private static EnumChatFormatting addCustomMaterial(String internalName, char c) {
        int nextOrdinal = EnumChatFormatting.values().length;
        EnumChatFormatting newFormat = theExpansionInit(internalName, nextOrdinal, c, false);

        field_96321_w.put(c, newFormat);
        field_96331_x.put(internalName.toLowerCase(), newFormat);

        return newFormat;
    }


    @Override
    public EnumChatFormatting getDiamondSteel() {
        return DIAMOND_STEEL;
    }
}