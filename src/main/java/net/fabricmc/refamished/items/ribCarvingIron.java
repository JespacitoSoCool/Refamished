package net.fabricmc.refamished.items;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class ribCarvingIron extends ribCarvingFlint {
    public ribCarvingIron(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 75;
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_iron");

        m_recipientIcon[0] = register.registerIcon("refamished:rib_beef");
        // Register recipient icons
        for (int i = 1; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/rib_carve_" + i);
        }

    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {

        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        //FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        if (internalBladeDamage >= 80)
        {

        }
        else
        {
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(RefamishedItems.ironBlade, 1, internalBladeDamage));
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(Item.beefRaw, 1));
        }

        SkillManager.addExperience(player,"Artisanry", 5);
        SkillManager.addExperience(player,"Chipping", 3);

        return new ItemStack( RefamishedItems.rib, 1);
    }
}
