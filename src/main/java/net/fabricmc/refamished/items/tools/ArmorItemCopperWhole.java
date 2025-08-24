package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItemMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class ArmorItemCopperWhole extends ArmorItemMod {
    public ArmorItemCopperWhole(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, ReMaterials.COPPERWHOLEARMOR, 2, iArmorType, iWeight);
        this.setInfernalMaxEnchantmentCost(15);
        this.setInfernalMaxNumEnchants(1);
    }
    private Icon iconOverlay;
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/";
    }
    @Override
    public String getWornTexturePrefix() {
        return "copper_hole";
    }
}
