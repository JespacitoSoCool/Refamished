package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class leatherWorkIron extends leatherWorkFlint {
    public leatherWorkIron(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 30;
    }

    @Override
    public ItemStack onEaten(ItemStack stack, World world, EntityPlayer player) {

        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        if (internalBladeDamage >= 80)
        {

        }
        else
        {
            ItemStack res = new ItemStack(RefamishedItems.ironBlade, 1, internalBladeDamage);
            if (!world.isRemote)
            {
                String quality = stack.getTagCompound().getString("quality");
                if (!res.hasTagCompound()) {
                    res.setTagCompound(new NBTTagCompound());
                }
                res.getTagCompound().setString("ToolQuality", quality);
            }
            UtilItem.GivePlayerStackOrEject( player, res);
        }

        SkillManager.addExperience(player,"Artisanry", 2);

        return new ItemStack( BTWItems.cutLeather, 2);
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_iron");

        // Register recipient icons
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/leatherwork_" + i);
        }

    }
}
