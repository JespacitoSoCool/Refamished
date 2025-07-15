package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class chippingHammer extends craftingPulling
{
    public chippingHammer(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "chippingHammerHead" );
        this.setMaxDamage(18);
    }

    @Override
    public int expAmount() {
        return 8;
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( RefamishedItems.chippedHammerHead, 1);
    }

}