package net.fabricmc.refamished.items.others;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.*;

public class chippingAxe extends craftingPulling
{
    public chippingAxe(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "chippingAxe" );
        this.setMaxDamage(16);
    }

    @Override
    public int expAmount() {
        return 9;
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( RefamishedItems.chippedAxeHead, 1);
    }
}