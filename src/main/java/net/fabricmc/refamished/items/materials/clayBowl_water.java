package net.fabricmc.refamished.items.materials;

import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class clayBowl_water extends Item {
    public clayBowl_water(int iItemID) {
        super(iItemID);
        this.setMaxStackSize(16);
        setTextureName("refamished:clay_bowl_water");
        setUnlocalizedName("clay_bowl_water");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            --par1ItemStack.stackSize;
        }

        if (!par3EntityPlayer.capabilities.isCreativeMode)
        {
            if (par1ItemStack.stackSize <= 0)
            {
                return new ItemStack(RefamishedItems.clay_bowl);
            }

            par3EntityPlayer.inventory.addItemStackToInventory(new ItemStack(RefamishedItems.clay_bowl));
        }

        return par1ItemStack;
    }

    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 32;
    }

    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.drink;
    }
    public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10)
    {
        return false;
    }
}
