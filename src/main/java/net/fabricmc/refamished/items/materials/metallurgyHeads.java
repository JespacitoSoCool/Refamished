package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class metallurgyHeads extends Item {
    public metallurgyHeads(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("metallurgy_head");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(4);
    }
    private static final String[] material = new String[]{"copper","iron","gold","diamond","gilded","cobalt"};
    public static final String[] heads = new String[]{"sword","pickaxe","axe","shovel","hoe","rivet","crossguard","machete"};
    private static final String[] separatedHeads = new String[]{"tip_sword","tip_pickaxe","tip_axe","tip_shovel","tip_hoe","copper_hammer","copper_chisel",
            "iron_chisel","diamond_chisel","tong_copper","tong_iron","tong_steel","iron_hammer","steel_hammer",
            "iron_shear_body","iron_shear_razor","diamond_shear_body","diamond_shear_razor","copper_shear_body","copper_shear_razor","gilded_shear_body","gilded_shear_razor",
    "copper_trowel","iron_trowel","gilded_hammer","tong_gilded","gilded_trowel","gilded_chisel"};
    public static final String[] parts = new String[material.length * heads.length + separatedHeads.length];
    private Icon[] iconByMetadataArray = new Icon[parts.length];
    static {
        int index = 0;
        for (int i = 0; i < material.length; i++) {
            String name_ = material[i];
            for (int j = 0; j < heads.length; j++) {
                String part = heads[j];
                parts[index++] = name_ + "_" + part;
            }
        }
        for (int i = 0; i < separatedHeads.length; i++) {
            String name_ = separatedHeads[i];
            parts[index++] = name_;
        }
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, parts.length - 1);
        return super.getUnlocalizedName() + "." + parts[meta];
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, parts.length);
        return this.iconByMetadataArray[var2];
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < parts.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/heads/" + parts[i]);
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < parts.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }

    public static int getPartIndex(String name) {
        int index = 0;
        for (String mat : material) {
            for (String head : heads) {
                if ((mat + "_" + head).equals(name)) {
                    return index;
                }
                index++;
            }
        }
        for (String sep : separatedHeads) {
            if (sep.equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
