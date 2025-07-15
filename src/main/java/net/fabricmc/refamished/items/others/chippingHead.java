package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.*;

public class chippingHead extends craftingPulling
{
    public chippingHead(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "chippingHammerHead" );
        this.setMaxDamage(30);
    }

    public String[] m_items = new String[]{""};

    @Override
    public int expAmount() {
        return 0;
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
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/stone_sharpening_" + i);
        }

    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        shatter(itemStack,world,player);
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            if (damage == 0) {
                long time = System.currentTimeMillis();
                int frameIndex = (int) ((time / 1000) % m_recipientIcon.length);
                return m_recipientIcon[frameIndex];
            } else {
                int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
                return m_recipientIcon[3 - progressIndex];
            }
        } else {
            return calculatePullingIcon();
        }
    }

}