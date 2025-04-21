package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class rib_beef_cooked_partial extends FoodItem {
    public rib_beef_cooked_partial(int iItemID) {
        super(iItemID, 2, 0.25F, false, "");

        setIncineratedInCrucible();
        setUnlocalizedName( "rib_cow_partial" );

        this.setCreativeTab(CreativeTabs.tabFood);
        setIncreasedFoodPoisoningEffect();
        setTextureName("refamished:rib_beef_partial");
        maxStackSize = 1;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);

        return new ItemStack( RefamishedItems.cooked_rib_beefSpent, 1);
    }
}
