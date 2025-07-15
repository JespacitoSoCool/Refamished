package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IconRegister;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class KnappingFlint extends craftingPulling
{
    public KnappingFlint(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "knappingFlint" );
        this.setMaxDamage(10);
    }

    @Override
    public int badHitsBreak() {
        return 3;
    }

    @Override
    public int expAmount() {
        return 3;
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( RefamishedItems.flintBlade, 1);
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        for (int i = 0; i < m_pullIcon.length; i++) {
            m_pullIcon[i] = register.registerIcon("refamished:progressive/pull_stone_" + i);
        }

        // Register recipient icons
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/flint_knapping_" + i);
        }

    }

}