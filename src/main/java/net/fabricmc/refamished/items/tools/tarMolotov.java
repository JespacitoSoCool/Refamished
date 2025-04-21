package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import btw.entity.InfiniteArrowEntity;
import btw.item.BTWItems;
import btw.world.util.WorldUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class tarMolotov extends Item {
    public tarMolotov(int par1) {
        super(par1);
        setUnlocalizedName("tar_molotov");
        setTextureName("refamished:tar_molotov");
        setMaxDamage(0);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabCombat);
    }

    public Boolean isALightSource(int tool) {
        if (tool == Item.flintAndSteel.itemID || tool == BTWBlocks.infiniteBurningTorch.blockID
                || tool == BTWBlocks.finiteBurningTorch.blockID)
        {
            return true;
        }
        return false;
    }
    public ItemStack getFireThingIdk(EntityPlayer player) {
        for (int iTempSlot = 0; iTempSlot < 9; ++iTempSlot) {
            ItemStack tempStack = player.inventory.getStackInSlot(iTempSlot);
            if (tempStack == null || !this.isALightSource(tempStack.itemID)) continue;
            return tempStack;
        }
        return null;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
        ItemStack what = getFireThingIdk(player);
        if (what != null)
        {
            if (what.itemID == Item.flintAndSteel.itemID) //CHICKEN JOCKEY
            {
                what.damageItem(1,player);
            }
            ItemStack lit = new ItemStack( RefamishedItems.tar_molotov_lit, 1);
            player.playSound("mob.ghast.fireball", 1.0f, par2World.rand.nextFloat() * 0.4f + 0.8f);
            long iExpiryTime = WorldUtils.getOverworldTimeServerOnly() + 400L;
            lit.setTagCompound(new NBTTagCompound());
            lit.getTagCompound().setLong("outTime", iExpiryTime);
            player.inventory.mainInventory[player.inventory.currentItem] = null;
            player.inventory.mainInventory[player.inventory.currentItem] = lit;
        }
        return par1ItemStack;
    }
}