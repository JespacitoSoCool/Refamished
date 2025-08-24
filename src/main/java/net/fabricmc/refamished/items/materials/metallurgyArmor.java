package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.minecraft.src.*;

import java.util.List;

public class metallurgyArmor extends Item {
    public metallurgyArmor(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("metallurgy_armor");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(4);
    }
    public static final String[] material = new String[]{"iron","gold","diamond","steel", "copper", "blood"};
    public static final String[] heads = new String[]{"helm", "coif", "pauldrons", "cuirass", "tassets", "greaves", "threads", "sabatons"};
    public static final Boolean[] addonMaterials = new Boolean[]{true,true,true,true,true, RefamishedMod.NMEnabled};
    public static final String[] parts = new String[material.length * heads.length];
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
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/armor/" + parts[i]);
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < parts.length; i++) {
            int materialIndex = i / heads.length;
            if (!addonMaterials[materialIndex]) {
                continue;
            }
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
        return -1;
    }

    public static boolean isPartOf(ItemStack item,String part) {
        return item.getItem().getUnlocalizedName(item).contains(part);
    }

    public static String getMaterial(ItemStack item,String part) {
        return (item.getItem().getUnlocalizedName(item).replace("item.metallurgyarmor.","")).replace(part,"").replace("_","");
    }

}
