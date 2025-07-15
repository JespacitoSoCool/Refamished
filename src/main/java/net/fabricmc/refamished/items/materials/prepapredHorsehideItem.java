package net.fabricmc.refamished.items.materials;

import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.List;

public class prepapredHorsehideItem extends PlaceAsBlockItem {
    public prepapredHorsehideItem(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("horsehide_prepared");
        setTextureName("refamished:variant/horsehide_brown");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    private static final String[] hideName = new String[]{"white","creamy","chestnut","brown","black","gray","darkbrown","mule"};
    private Icon[] iconByMetadataArray = new Icon[9];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, hideName.length - 1);
        return super.getUnlocalizedName() + "." + hideName[meta];
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 8);
        return this.iconByMetadataArray[var2];
    }

    @Override
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < hideName.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:variant/prepared_horsehide_" + hideName[i]);
        }
    }

    @Override
    public int getBlockID() {
        return RefamishedBlocks.preparedHorseHide.blockID;
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < hideName.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }
}
