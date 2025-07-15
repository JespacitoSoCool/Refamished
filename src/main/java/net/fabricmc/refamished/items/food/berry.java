package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import btw.item.items.HighResolutionFoodItem;
import net.minecraft.src.*;

import java.util.List;

public class berry extends HighResolutionFoodItem {
    public berry(int iItemID) {
        super(iItemID, 2, 0.25F, false, "");
        setUnlocalizedName("berry");

        this.setCreativeTab(CreativeTabs.tabFood);
        setTextureName("refamished:blueberry");
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    private Icon[] iconByMetadataArray = new Icon[4];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String base = super.getUnlocalizedName();
        return base + "." + (switch (stack.getItemDamage()%4) {
            default -> "blueberry";
            case 1 -> "sweetberry";
            case 2 -> "cranberry";
            case 3 -> "blackberry";
        });
    }

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 7);
        return this.iconByMetadataArray[var2];
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.iconByMetadataArray[0] = register.registerIcon("refamished:blueberry");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:sweetberry");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:cranberry");
        this.iconByMetadataArray[3] = register.registerIcon("refamished:blackberry");
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 3));
    }
}
