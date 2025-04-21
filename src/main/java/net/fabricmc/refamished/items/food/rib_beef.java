package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import net.minecraft.src.CreativeTabs;

public class rib_beef extends FoodItem {
    public rib_beef(int iItemID) {
        super(iItemID, 2, 0.25F, false, "");

        setIncineratedInCrucible();
        setUnlocalizedName( "rib_cow" );
        maxStackSize = 1;
        this.setCreativeTab(CreativeTabs.tabFood);
        setIncreasedFoodPoisoningEffect();
        setTextureName("refamished:rib_beef");
    }
}
