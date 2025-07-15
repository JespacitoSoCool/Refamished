package net.fabricmc.refamished.items.decorative;

import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.List;

public class wildSeeds extends PlaceAsBlockItem {
    public wildSeeds(int iItemID) {
        super(iItemID, 0);
        setUnlocalizedName("wild_seed");

        this.setCreativeTab(CreativeTabs.tabFood);
        setTextureName("refamished:blueberry_seeds");
    }
    @Override
    public int getBlockIDToPlace(int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ) {
        return (switch (iItemDamage) {
            default -> RefamishedBlocks.bushWild.blockID;
        });
    }

    @Override
    public int getMetadata(int iItemDamage) {
        return iItemDamage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String base = super.getUnlocalizedName();
        return base + "." + (switch (stack.getItemDamage()%5) {
            default -> "branch";
            case 1 -> "blueberry";
            case 2 -> "sweetberry";
            case 3 -> "cranberry";
            case 4 -> "blackberry";
        });
    }
    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 3));
        list.add(new ItemStack(par1, 1, 4));
    }
}