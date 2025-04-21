package net.fabricmc.refamished.items;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class chippingShovel extends craftingPulling
{
    public chippingShovel(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "chippingShovel" );
        this.setMaxDamage(14);
    }

    @Override
    public int expAmount() {
        return 7;
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( RefamishedItems.chippedShovelHead, 1);
    }

}