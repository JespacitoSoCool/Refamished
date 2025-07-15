package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class crushedOre extends Item {
    public crushedOre(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("crushed_ore");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    private static final String[] ingot = new String[]{"copper","iron","gold","sinter_iron"};
    private Icon[] iconByMetadataArray = new Icon[9];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, ingot.length - 1);
        return super.getUnlocalizedName() + "." + ingot[meta];
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 5);
        return this.iconByMetadataArray[var2];
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < ingot.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:variant/crushed_ore_" + ingot[i]);
        }
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < ingot.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }
}
