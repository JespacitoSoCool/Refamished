package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItemMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class ArmorItemDulledGold extends ArmorItemMod {
    public ArmorItemDulledGold(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, ReMaterials.DULLEDGOLDARMOR, 2, iArmorType, iWeight);
        this.damageReduceAmount = ReMaterials.DULLEDGOLDARMOR.getDamageReductionAmount(iArmorType);
    }
    private Icon iconOverlay;
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/";
    }
    @Override
    public String getWornTexturePrefix() {
        return "dulled_gold";
    }
}
