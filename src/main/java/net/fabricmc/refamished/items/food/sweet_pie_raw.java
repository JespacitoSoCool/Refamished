package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import net.minecraft.src.*;

import java.util.List;

public class sweet_pie_raw extends FoodItem {
    public sweet_pie_raw(int iItemID,int heal) {
        super(iItemID, heal, 0.25F, false, "");
        //setUnlocalizedName("pie_raw");

        this.setCreativeTab(CreativeTabs.tabFood);
        //setTextureName("refamished:sweet_pie_dough");
    }
//    private Icon[] iconByMetadataArray = new Icon[4];
//
//    @Override
//    public String getUnlocalizedName(ItemStack stack) {
//        String base = super.getUnlocalizedName();
//        return base + "." + (switch (stack.getItemDamage()%4) {
//            default -> "blueberry";
//            case 1 -> "sweetberry";
//            case 2 -> "cranberry";
//            case 3 -> "blackberry";
//        });
//    }
//
//    @Override
//    public Icon getIconFromDamage(int metadata) {
//        int var2 = MathHelper.clamp_int(metadata, 0, 5);
//        return this.iconByMetadataArray[var2];
//    }
//
//    @Override
//    public void registerIcons(IconRegister register) {
//        super.registerIcons(register);
//        this.iconByMetadataArray[0] = register.registerIcon("refamished:uncooked_blueberry_pie");
//        this.iconByMetadataArray[1] = register.registerIcon("refamished:uncooked_sweetberry_pie");
//        this.iconByMetadataArray[2] = register.registerIcon("refamished:uncooked_cranberry_pie");
//        this.iconByMetadataArray[3] = register.registerIcon("refamished:uncooked_blackberry_pie");
//    }
//
//    @Override
//    public void getSubItems(int par1, CreativeTabs tab, List list) {
//        list.add(new ItemStack(par1, 1, 0));
//        list.add(new ItemStack(par1, 1, 1));
//        list.add(new ItemStack(par1, 1, 2));
//        list.add(new ItemStack(par1, 1, 3));
//    }
}
