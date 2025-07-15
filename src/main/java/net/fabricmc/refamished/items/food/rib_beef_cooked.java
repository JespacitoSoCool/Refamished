package net.fabricmc.refamished.items.food;

import btw.item.items.FoodItem;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class rib_beef_cooked extends FoodItem {
    public rib_beef_cooked(int iItemID) {
        super(iItemID, 3, 0.25F, false, "");

        setIncineratedInCrucible();
        setUnlocalizedName( "rib_cow_cooked" );

        this.setCreativeTab(CreativeTabs.tabFood);
        setTextureName("refamished:rib_beef_cooked");
        maxStackSize = 1;
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        --par1ItemStack.stackSize;
        par3EntityPlayer.getFoodStats().addStats(this);
        par2World.playSoundAtEntity(par3EntityPlayer, "random.burp", 0.5F, par2World.rand.nextFloat() * 0.1F + 0.9F);
        this.onFoodEaten(par1ItemStack, par2World, par3EntityPlayer);

        return new ItemStack( RefamishedItems.cooked_rib_beefPartial, 1);
    }
}
