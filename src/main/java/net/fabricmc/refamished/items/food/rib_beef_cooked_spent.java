package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class rib_beef_cooked_spent extends FoodItem {
    public rib_beef_cooked_spent(int iItemID) {
        super(iItemID, 2, 0.25F, false, "");

        setIncineratedInCrucible();
        setUnlocalizedName( "rib_cow_spent" );

        this.setCreativeTab(CreativeTabs.tabFood);
        setIncreasedFoodPoisoningEffect();
        setTextureName("refamished:rib_beef_spent");
        maxStackSize = 1;
    }
}
