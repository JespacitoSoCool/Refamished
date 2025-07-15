package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class metallurgyArmorCopper extends Item {
    public metallurgyArmorCopper(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("metallurgy_armor");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
        setMaxStackSize(4);
    }
    private static final String[] separatedHeads = new String[]{"copper_plating_helmet","copper_plating_chestplate","copper_plating_leggings","copper_plating_boots"};
    public static final String[] parts = new String[separatedHeads.length];
    private Icon[] iconByMetadataArray = new Icon[parts.length];
    static {
        int index = 0;
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
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/armor/" + parts[i]);
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
        for (String sep : separatedHeads) {
            if (sep.equals(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

}
