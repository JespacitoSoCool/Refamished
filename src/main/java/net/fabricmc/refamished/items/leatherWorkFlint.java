package net.fabricmc.refamished.items;

import btw.item.BTWItems;
import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class leatherWorkFlint extends ProgressiveCraftingItem {
    public leatherWorkFlint(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 150;
    }

    @Override
    protected void playCraftingFX(ItemStack stack, World world, EntityPlayer player) {
        player.playSound( "step.cloth",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {

        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        if (internalBladeDamage >= 16)
        {

        }
        else
        {
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(RefamishedItems.flintBlade, 1, internalBladeDamage));
        }

        SkillManager.addExperience(player,"Artisanry", 7);

        return new ItemStack( BTWItems.cutLeather, 2);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (player.timesCraftedThisTick == 0 && world.isRemote) {
            world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 1F );
        }
        super.onCreated(stack, world, player);
    }

    public Icon m_itemIcon;
    public Icon[] m_recipientIcon = new Icon[5];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_flint");

        // Register recipient icons
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/leatherwork_" + i);
        }

    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            if (damage == 0) {
                // Calculate animation frame based on time
                long time = System.currentTimeMillis();
                int frameIndex = (int) ((time / 1000) % m_recipientIcon.length); // Changes every second
                return m_recipientIcon[frameIndex];
            } else {
                // Calculate progress-based icon
                int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
                return m_recipientIcon[4 - progressIndex];
            }
        } else {
            return m_itemIcon;
        }
    }
}
