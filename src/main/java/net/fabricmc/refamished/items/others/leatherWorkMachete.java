package net.fabricmc.refamished.items.others;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.IconLargedMultipleRender;
import net.fabricmc.refamished.items.tools.pickaxeDecorated;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class leatherWorkMachete extends ribCarvingMachete {
    public leatherWorkMachete(int iItemID) {
        super(iItemID);
    }

    public Icon m_itemBinding;

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 100;
    }

    @Override
    protected void playCraftingFX(ItemStack stack, World world, EntityPlayer player) {
        player.playSound( "step.cloth",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );
    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {

        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        //FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;
        String quality = stack.getTagCompound().getString("quality");
        int bindColor = stack.getTagCompound().getInteger("binding");

        if (internalBladeDamage >= RefamishedItems.flint_machete.getMaxDamage())
        {

        }
        else
        {
            ItemStack res = new ItemStack(RefamishedItems.flint_machete, 1, internalBladeDamage);
            if (!world.isRemote)
            {
                if (!res.hasTagCompound()) {
                    res.setTagCompound(new NBTTagCompound());
                }
                res.getTagCompound().setString("ToolQuality", quality);
                res.getTagCompound().setInteger("binding", bindColor);
            }
            UtilItem.GivePlayerStackOrEject( player, res);
        }

        SkillManager.addExperience(player,"Artisanry", 3);

        return new ItemStack( BTWItems.cutLeather, 2);
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_machete");

        m_itemBinding = register.registerIcon("refamished:progressive/rib_carve_machete_bind");
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/leatherwork_" + i);
        }

    }
}
