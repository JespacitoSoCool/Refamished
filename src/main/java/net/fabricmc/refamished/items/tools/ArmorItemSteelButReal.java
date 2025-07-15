package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItemMod;
import net.fabricmc.refamished.misc.ReMaterials;

public class ArmorItemSteelButReal extends ArmorItemMod {
    public ArmorItemSteelButReal(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, ReMaterials.STEELARMOR, 2, iArmorType, iWeight, 0.075);
        this.setInfernalMaxEnchantmentCost(25);
        this.setInfernalMaxNumEnchants(2);
    }
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/";
    }
    @Override
    public String getWornTexturePrefix() {
        return "steel";
    }
}
