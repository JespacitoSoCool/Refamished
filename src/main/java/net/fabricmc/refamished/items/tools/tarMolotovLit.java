package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import btw.entity.InfiniteArrowEntity;
import btw.item.blockitems.FiniteBurningTorchBlockItem;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityMolotov;
import net.minecraft.src.*;

public class tarMolotovLit extends Item {
    public tarMolotovLit(int par1) {
        super(par1);
        setUnlocalizedName("tar_molotov.lit");
        setTextureName("refamished:tar_molotov_lit");
        setMaxDamage(10);
        setMaxStackSize(1);
        setCreativeTab(CreativeTabs.tabCombat);
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
            par2World.spawnEntityInWorld(new EntityMolotov(par2World, par3EntityPlayer));
        }
        return par1ItemStack;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, EntityPlayer entity, int iInventorySlot, boolean bIsHandHeldItem) {
        if (!world.isRemote && stack.stackSize > 0 && stack.hasTagCompound() && stack.getTagCompound().hasKey("outTime") && !entity.capabilities.isCreativeMode) {
            long lExpiryTime = stack.getTagCompound().getLong("outTime");
            int iCountdown = (int)(lExpiryTime - WorldUtils.getOverworldTimeServerOnly());
            if (iCountdown <= 0 || iCountdown > 400L) {
                int iFXI = MathHelper.floor_double(entity.posX);
                int iFXJ = MathHelper.floor_double(entity.posY) + 1;
                int iFXK = MathHelper.floor_double(entity.posZ);
                world.playAuxSFX(1004, iFXI, iFXJ, iFXK, 0);
                entity.inventory.mainInventory[iInventorySlot] = null;
                entity.inventory.mainInventory[iInventorySlot] = new ItemStack(Item.glassBottle);
            } else if (entity.isInWater() && entity.isInsideOfMaterial(Material.water) || entity.isBeingRainedOn() && entity.worldObj.rand.nextFloat() <= 0.0025f) {
                int iFXI = MathHelper.floor_double(entity.posX);
                int iFXJ = MathHelper.floor_double(entity.posY) + 1;
                int iFXK = MathHelper.floor_double(entity.posZ);
                world.playAuxSFX(1004, iFXI, iFXJ, iFXK, 0);
                entity.inventory.mainInventory[iInventorySlot] = null;
                entity.inventory.mainInventory[iInventorySlot] = new ItemStack(Item.glassBottle);
            } else {
                int iNewItemDamage = (int)(0.0013333333f * (float)(400L - iCountdown));
                if ((iNewItemDamage = MathHelper.clamp_int(iNewItemDamage, 1, 31)) != stack.getItemDamage()) {
                    stack.setItemDamage(iNewItemDamage);
                }
            }
        }
    }

    @Override
    public boolean ignoreDamageWhenComparingDuringUse() {
        return true;
    }

    public tarMolotovLit adjustMaxDamage(float factor) {
        this.setMaxDamage((int)(32.0f * factor));
        return this;
    }
}