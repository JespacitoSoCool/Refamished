package net.fabricmc.refamished.items.others;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class sugarCaneCuttingIron extends sugarCaneCuttingFlint implements ProgressiveCraftingInterface {
    public sugarCaneCuttingIron(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 50;
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_iron");

    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {

        player.playSound( "random.eat", 0.5F + 0.5F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.25F ) + 1.75F );

        //FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;
        String quality = stack.getTagCompound().getString("quality");

        if (internalBladeDamage >= 12)
        {

        }
        else
        {
            ItemStack res = new ItemStack(RefamishedItems.ironBlade, 1, internalBladeDamage);
            if (!world.isRemote)
            {
                if (!res.hasTagCompound()) {
                    res.setTagCompound(new NBTTagCompound());
                }
                res.getTagCompound().setString("ToolQuality", quality);
            }
            UtilItem.GivePlayerStackOrEject( player, res);
        }

        SkillManager.addExperience(player,"Artisanry", 4);
        SkillManager.addExperience(player,"Chipping", 3);

        return new ItemStack( RefamishedItems.cut_sugar_cane, 1);
    }
}
