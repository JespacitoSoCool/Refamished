package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.minecraft.src.*;

import java.util.List;

public class metalSheets extends Item {
    public metalSheets(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("metal_sheet");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    private static final String[] ingot = new String[]{"copper","iron","gold","gilded","diamond","steel","blood"};
    private Icon[] iconByMetadataArray = new Icon[9];


    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, ingot.length - 1);
        return super.getUnlocalizedName() + "." + ingot[meta];
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 9);
        return this.iconByMetadataArray[var2];
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < ingot.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/metal_pieces/" + ingot[i]+"_sheet");
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < ingot.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }

    public static int getPartIndex(String name) {
        int index = 0;
        for (String mat : ingot) {
            if ((mat).equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }
}
