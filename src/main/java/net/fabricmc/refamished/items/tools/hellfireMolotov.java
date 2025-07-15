package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import btw.world.util.WorldUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityMolotov;
import net.fabricmc.refamished.entities.EntityMolotovHellfire;
import net.minecraft.src.*;

public class hellfireMolotov extends Item {
    public hellfireMolotov(int par1) {
        super(par1);
        setUnlocalizedName("hellfire_molotov");
        setTextureName("refamished:hellfire_molotov");
        setMaxDamage(0);
        setMaxStackSize(1);
        hideFromEMI();
    }

    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer player) {
        player.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
        return par1ItemStack;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 20;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.bow;
    }

    @Override
    public ItemStack onEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        if (!par3EntityPlayer.capabilities.isCreativeMode) {
            --par1ItemStack.stackSize;
        }
        par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 0.5f, 0.4f / (itemRand.nextFloat() * 0.4f + 0.8f));
        if (!par2World.isRemote) {
            par2World.spawnEntityInWorld(new EntityMolotovHellfire(par2World, par3EntityPlayer));
        }
        return par1ItemStack;
    }
}