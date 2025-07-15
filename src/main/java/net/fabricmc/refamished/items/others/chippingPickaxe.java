package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class chippingPickaxe extends craftingPulling
{
    public chippingPickaxe(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "chippingPickaxe" );
        this.setMaxDamage(28);
    }

    @Override
    public int expAmount() {
        return 14;
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( RefamishedItems.chippedPickaxeHead, 1);
    }

}