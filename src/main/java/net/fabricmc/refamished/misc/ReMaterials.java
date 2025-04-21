package net.fabricmc.refamished.misc;

import btw.community.refamished.RefamishedAddon;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.interfaces.EnumArmorMaterialInterface;
import net.fabricmc.refamished.interfaces.EnumToolMaterialInterface;
import net.minecraft.src.EnumArmorMaterial;
import net.minecraft.src.EnumToolMaterial;

public class ReMaterials {
    public static EnumToolMaterial COPPER;
    public static EnumToolMaterial BONE;
    public static EnumToolMaterial RUSTIRON;
    public static EnumToolMaterial CORRODEDCOPPER;
    public static EnumToolMaterial DIAMONDTIP;
    public static EnumToolMaterial DULLEDGOLD;
    public static EnumToolMaterial GILDEDIRON;
    public static EnumToolMaterial COBALTZURE;

    public static EnumArmorMaterial RUSTIRONARMOR;
    public static EnumArmorMaterial COPPERARMOR;
    public static EnumArmorMaterial DULLEDGOLDARMOR;

    public static void init() {
        EnumToolMaterialInterface tool = ((EnumToolMaterialInterface) (Object) EnumToolMaterial.WOOD);
        EnumArmorMaterialInterface armor = ((EnumArmorMaterialInterface) (Object) EnumArmorMaterial.IRON);

        COPPER = tool.getCopperMaterial();
        BONE = tool.getBoneMaterial();
        RUSTIRON = tool.getRustIronMaterial();
        CORRODEDCOPPER = tool.getCorrodedCopperMaterial();
        DIAMONDTIP = tool.getTippedDiamondMaterial();
        DULLEDGOLD = tool.getDulledGoldMaterial();
        GILDEDIRON = tool.getGildedIronMaterial();
        COBALTZURE = tool.getCobaltzureMaterial();


        RUSTIRONARMOR = armor.getRustIronMaterial();
        COPPERARMOR = armor.getCopperMaterial();
        DULLEDGOLDARMOR = armor.getDulledGoldMaterial();
    }
}
