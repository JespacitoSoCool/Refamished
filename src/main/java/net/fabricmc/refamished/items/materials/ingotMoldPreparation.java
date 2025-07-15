package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.List;

public class ingotMoldPreparation extends Item {
    public ingotMoldPreparation(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("ingot_preparation");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    @Override
    public boolean isMultiUsePerClick() {
        return false;
    }

    private static final String[] ingot = new String[]{
            "copper","dust_copper","fired_copper",
            "iron","dust_iron","fired_iron",
            "gold","dust_gold","fired_gold",
            "sinter","dust_sintered",
    };
    private Icon[] iconByMetadataArray = new Icon[ingot.length];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, ingot.length - 1);
        return super.getUnlocalizedName() + "." + ingot[meta];
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, ingot.length);
        return this.iconByMetadataArray[var2];
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < ingot.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/ingot_preparing/" + ingot[i]);
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < ingot.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }
}
