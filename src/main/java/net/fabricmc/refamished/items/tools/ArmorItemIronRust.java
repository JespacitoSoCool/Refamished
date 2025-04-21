package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItem;
import btw.item.items.ArmorItemMod;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.EnumArmorMaterial;

public class ArmorItemIronRust extends ArmorItemMod {
    public ArmorItemIronRust(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, ReMaterials.RUSTIRONARMOR, 2, iArmorType, iWeight);
        this.setInfernalMaxEnchantmentCost(25);
        this.setInfernalMaxNumEnchants(2);
        this.damageReduceAmount = ReMaterials.RUSTIRONARMOR.getDamageReductionAmount(iArmorType);
    }
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/";
    }
    @Override
    public String getWornTexturePrefix() {
        return "rusted_iron";
    }
}
