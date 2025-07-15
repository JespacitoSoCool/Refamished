package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItem;
import net.minecraft.src.EnumArmorMaterial;

public class ArmorItemDiamondRefDoSomething extends ArmorItem {
    public ArmorItemDiamondRefDoSomething(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, EnumArmorMaterial.DIAMOND, 3, iArmorType, iWeight, 0.05f);
        this.setInfernalMaxEnchantmentCost(30);
        this.setInfernalMaxNumEnchants(2);
    }
}
