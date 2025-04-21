package net.fabricmc.refamished.items.tools;

import btw.entity.RottenArrowEntity;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityIncendiaryArrow;
import net.minecraft.src.*;
import org.lwjgl.Sys;

public class incendiaryBow extends ItemBow {
    public incendiaryBow(int par1) {
        super(par1);
        setUnlocalizedName("incendiary_bow");
        setTextureName("refamished:incendiary_bow");
    }
    public static final String[] bowPullIconNameArray = new String[]{"pulling_0", "pulling_1", "pulling_2"};
    private Icon[] iconArray;

    public Icon getDrawIcon(int itemInUseDuration) {
        if (itemInUseDuration >= 18) {
            return this.getItemIconForUseDuration(2);
        }
        if (itemInUseDuration > 12) {
            return this.getItemIconForUseDuration(1);
        }
        if (itemInUseDuration > 0) {
            return this.getItemIconForUseDuration(0);
        }
        return this.itemIcon;
    }

    @Override
    public Icon getAnimationIcon(EntityPlayer player) {
        ItemStack itemInUse = player.getItemInUse();
        if (itemInUse != null && itemInUse.itemID == this.itemID) {
            int timeInUse = itemInUse.getMaxItemUseDuration() - player.getItemInUseCount();
            return this.getDrawIcon(timeInUse);
        }
        return this.itemIcon;
    }

    @Override
    public boolean canItemBeFiredAsArrow(int iItemID) {
        return iItemID == RefamishedItems.arrow_incendiary.itemID;
    }

    @Override
    protected EntityArrow createArrowEntityForItem(World world, EntityPlayer player, int iItemID, float fPullStrength) {
        EntityArrow entityArrow = null;
        if (iItemID == RefamishedItems.arrow_incendiary.itemID) {
            entityArrow = new EntityIncendiaryArrow(world, player, fPullStrength * 0.55f * this.getPullStrengthToArrowVelocityMultiplier());
            if (!player.isUsingSpecialKey())
            {
                player.playSound( "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
                entityArrow.setFire(7);
            }
        }
        return entityArrow;
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(this.getIconString());
        this.iconArray = new Icon[bowPullIconNameArray.length];
        for (int var2 = 0; var2 < this.iconArray.length; ++var2) {
            this.iconArray[var2] = par1IconRegister.registerIcon(this.getIconString() + "_" + bowPullIconNameArray[var2]);
        }
    }

    public Icon getItemIconForUseDuration(int par1) {
        return this.iconArray[par1];
    }
}
