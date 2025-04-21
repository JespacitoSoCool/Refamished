package net.fabricmc.refamished.items.tools;

import btw.item.items.ArmorItemMod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;

public class ArmorItemCopper extends ArmorItemMod {
    public ArmorItemCopper(int iItemID, int iArmorType, int iWeight) {
        super(iItemID, ReMaterials.COPPERARMOR, 2, iArmorType, iWeight);
        this.setInfernalMaxEnchantmentCost(15);
        this.setInfernalMaxNumEnchants(1);
        this.damageReduceAmount = ReMaterials.COPPERARMOR.getDamageReductionAmount(iArmorType);
    }
    private Icon iconOverlay;
    @Override
    public String getWornTextureDirectory() {
        return "refamished:textures/models/armor/";
    }
    @Override
    public String getWornTexturePrefix() {
        return "copper";
    }
    @Override
    public boolean hasCustomColors() {
        return true;
    }
    @Override
    public boolean hasSecondRenderLayerWhenWorn() {
        return true;
    }
    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        if (this.armorType == 0) {
            this.iconOverlay = register.registerIcon("refamished:copper_helmet_overlay");
        }
        else if (this.armorType == 1) {
            this.iconOverlay = register.registerIcon("refamished:copper_chestplate_overlay");
        }
        else if (this.armorType == 2) {
            this.iconOverlay = register.registerIcon("refamished:copper_leggings_overlay");
        }
        else if (this.armorType == 3) {
            this.iconOverlay = register.registerIcon("refamished:copper_boots_overlay");
        }
    }
    @Override
    public int getDefaultColor() {
        return 10511680;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public boolean requiresMultipleRenderPasses() {
        return this.iconOverlay != null;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIconFromDamageForRenderPass(int iDamage, int iRenderPass) {
        if (iRenderPass == 1 && this.iconOverlay != null) {
            return this.iconOverlay;
        }
        return this.getIconFromDamage(iDamage);
    }
}
