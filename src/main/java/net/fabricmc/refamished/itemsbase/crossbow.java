package net.fabricmc.refamished.itemsbase;

import btw.entity.InfiniteArrowEntity;
import btw.util.sounds.BTWSoundManager;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityBoneBolt;
import net.fabricmc.refamished.entities.EntityCopperBolt;
import net.fabricmc.refamished.entities.EntityIncendiaryArrow;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.minecraft.src.*;

public class crossbow extends ItemBow implements IconByItemStack {
    public crossbow(int par1) {
        super(par1);
        setCreativeTab(CreativeTabs.tabCombat);
        setMaxStackSize(1);
    }

    @Override
    protected float getCurrentPullStrength(EntityPlayer player, ItemStack itemStack, int iTicksInUseRemaining) {
        int iTicksInUse = this.getMaxItemUseDuration(itemStack) - iTicksInUseRemaining;
        float fPullStrength = (float)iTicksInUse / 20.0f;
        return Math.clamp(fPullStrength/GetPullLimit(),0.0f,1.0f);
    }

    @Override
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {
        float fPullStrength = getCurrentPullStrength( player, itemStack, iTicksInUseRemaining );

        if ( fPullStrength < 0.99f )
        {
            return;
        }
        world.playSoundAtEntity(player, RefamishedSoundManager.CROSSBOW_LOAD_END.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.5f);
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("LoadedBolt")) {

        }
        else {
            ItemStack getStack = GetFirstArrowStackInHotbar(player);
            if (getStack != null && CanItemBeFiredAsArrow(getStack.itemID))
            {
                setLoadedBolt(itemStack, getStack.itemID);
                player.inventory.consumeInventoryItem( getStack.itemID );
            }
        }
    }

    public void setLoadedBolt(ItemStack stack, int id) {
        if (!stack.hasTagCompound()) {
            stack.setTagCompound(new NBTTagCompound());
        }


        stack.getTagCompound().setInteger("Bolt", id);
    }
    @Override
    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bolt")) {
            int boltType = getLoadedBolt(stack);

            boolean bInfiniteArrows = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel( Enchantment.infinity.effectId, stack ) > 0;

            if ( boltType != 0 || bInfiniteArrows )
            {

                EntityArrow entityArrow;

                stack.getTagCompound().removeTag("Bolt");

                if ( boltType != 0 )
                {
                    entityArrow = CreateArrowEntityForItem( world, player, boltType, 1 );
                }
                else
                {
                    entityArrow = new InfiniteArrowEntity( world, player, GetPullStrengthToArrowVelocityMultiplier() );
                }

                if ( entityArrow != null )
                {
                    ApplyBowEnchantmentsToArrow( stack, entityArrow );

                    if ( !world.isRemote )
                    {
                        world.spawnEntityInWorld( entityArrow );
                    }
                }

                stack.damageItem( 1, player );

                world.playSoundAtEntity(player, RefamishedSoundManager.CROSSBOW_SHOOT.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);

                if ( stack.stackSize == 0 )
                {
                    player.inventory.mainInventory[player.inventory.currentItem] = null;
                }
            }
        }
        else
        {
            player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        }

        return stack;
    }

    public ItemStack GetFirstArrowStackInHotbar( EntityPlayer player )
    {
        for ( int iTempSlot = 0; iTempSlot < 9; iTempSlot++ )
        {
            ItemStack tempStack = player.inventory.getStackInSlot( iTempSlot );

            if ( tempStack != null && CanItemBeFiredAsArrow( tempStack.itemID ) )
            {
                return tempStack;
            }
        }

        return null;
    }

    public boolean CanItemBeFiredAsArrow( int iItemID )
    {
        return iItemID == RefamishedItems.bolt_bone.itemID || iItemID == RefamishedItems.bolt_iron.itemID;
    }

    public float GetPullStrengthToArrowVelocityMultiplier()
    {
        return 2.75F;
    }

    public float GetPullLimit()
    {
        return 1.5F;
    }

    protected EntityArrow CreateArrowEntityForItem( World world, EntityPlayer player, int loadedID, float fPullStrength )
    {
        EntityArrow entityArrow = null;

        if ( loadedID == RefamishedItems.bolt_bone.itemID )
        {
            entityArrow = new EntityBoneBolt( world, player, fPullStrength * GetPullStrengthToArrowVelocityMultiplier() );
        } else if ( loadedID == RefamishedItems.bolt_iron.itemID )
        {
            entityArrow = new EntityCopperBolt( world, player, fPullStrength * GetPullStrengthToArrowVelocityMultiplier() * 0.65f );
        }

        return entityArrow;
    }

    protected void ApplyBowEnchantmentsToArrow( ItemStack bowStack, EntityArrow entityArrow )
    {
        int iPowerLevel = EnchantmentHelper.getEnchantmentLevel( Enchantment.power.effectId, bowStack );

        if ( iPowerLevel > 0 )
        {
            entityArrow.setDamage( entityArrow.getDamage() + (double)iPowerLevel * 0.5D + 0.5D );
        }

        int iPunchLevel = EnchantmentHelper.getEnchantmentLevel( Enchantment.punch.effectId, bowStack );

        if (iPunchLevel > 0)
        {
            entityArrow.setKnockbackStrength(iPunchLevel);
        }

        if ( EnchantmentHelper.getEnchantmentLevel( Enchantment.flame.effectId, bowStack ) > 0 )
        {
            entityArrow.setFire(100);
        }
    }

    public int getLoadedBolt(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bolt")) {
            return stack.getTagCompound().getInteger("Bolt");
        }
        return 0;
    }

    public Icon[] drawIconArray = new Icon[3];
    public Icon[] loadedIconArray = new Icon[10];

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        drawIconArray = new Icon[3];
        itemIcon = register.registerIcon("refamished:crossbow");
        drawIconArray[0] = register.registerIcon("refamished:crossbow_pulling_0");
        drawIconArray[1] = register.registerIcon("refamished:crossbow_pulling_1");
        drawIconArray[2] = register.registerIcon("refamished:crossbow_pulling_2");

        loadedIconArray[0] = register.registerIcon("refamished:crossbow_charged_0");
        loadedIconArray[1] = register.registerIcon("refamished:crossbow_charged_1");
    }

    public Icon getItemIconForUseDuration(int par1) {
        return this.drawIconArray[par1];
    }

    public Icon getDrawIcon(int itemInUseDuration) {
        if (itemInUseDuration >= (int)(GetPullLimit()/1.1*20)) {
            return this.getItemIconForUseDuration(2);
        }
        if (itemInUseDuration > (int)(GetPullLimit()/2.2*20)) {
            return this.getItemIconForUseDuration(1);
        }
        if (itemInUseDuration > 0) {
            return this.getItemIconForUseDuration(0);
        }
        return this.itemIcon;
    }

    @Override
    public Icon getIcon(ItemStack stack, EntityPlayer player) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("Bolt")) {
            int loaded = getLoadedBolt(stack);
            if (loaded == RefamishedItems.bolt_bone.itemID){ return this.loadedIconArray[1]; }
            else if (loaded == RefamishedItems.bolt_iron.itemID){ return this.loadedIconArray[0]; }
        }
        if (player != null && stack != null && stack.itemID == this.itemID) {
            int timeInUse = stack.getMaxItemUseDuration() - player.getItemInUseCount();
            if (player.getItemInUse() == stack)
            {
                return this.getDrawIcon(timeInUse);
            }
        }
        return this.itemIcon;
    }
}
