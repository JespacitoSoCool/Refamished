package net.fabricmc.refamished.items.tools;

import btw.entity.InfiniteArrowEntity;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.EntityBoneBolt;
import net.fabricmc.refamished.entities.EntityCopperBolt;
import net.fabricmc.refamished.itemsbase.crossbow;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.minecraft.src.*;

public class compoundArbalest extends crossbow {
    public compoundArbalest(int par1) {
        super(par1);
        setUnlocalizedName("compound_arbalest");
        setTextureName("refamished:compound_arbalest");
        setMaxDamage(394);
    }

    @Override
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {
        float fPullStrength = getCurrentPullStrength( player, itemStack, iTicksInUseRemaining );

        if ( fPullStrength < 0.99f )
        {
            return;
        }
        world.playSoundAtEntity(player, RefamishedSoundManager.CROSSBOW_LOAD_END.sound(), 0.75f, 1.1f + world.rand.nextFloat() * 0.1f);
        if (itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey("LoadedBolt")) {

        }
        else {
            ItemStack getStack = GetFirstArrowStackInHotbar(player);
            if (getStack != null && CanItemBeFiredAsArrow(getStack.itemID))
            {
                setLoadedBolt(itemStack, getStack.itemID);
                player.inventory.consumeInventoryItem( getStack.itemID );
                player.addExhaustion(0.2f);
            }
        }
    }

    public float GetPullStrengthToArrowVelocityMultiplier()
    {
        return 3F;
    }

    protected EntityArrow CreateArrowEntityForItem( World world, EntityPlayer player, int loadedID, float fPullStrength )
    {
        EntityArrow entityArrow =  super.CreateArrowEntityForItem(world,player,loadedID,fPullStrength);;

        if ( loadedID == RefamishedItems.bolt_iron.itemID )
        {
            entityArrow = new EntityCopperBolt( world, player, fPullStrength * GetPullStrengthToArrowVelocityMultiplier() * 0.75f );
        }

        return entityArrow;
    }

    public float GetPullLimit()
    {
        return 2F;
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        drawIconArray = new Icon[3];
        itemIcon = register.registerIcon("refamished:compound_arbalest");
        drawIconArray[0] = register.registerIcon("refamished:compound_arbalest_pulling_0");
        drawIconArray[1] = register.registerIcon("refamished:compound_arbalest_pulling_1");
        drawIconArray[2] = register.registerIcon("refamished:compound_arbalest_pulling_2");

        loadedIconArray[0] = register.registerIcon("refamished:compound_arbalest_charged_0");
        loadedIconArray[1] = register.registerIcon("refamished:compound_arbalest_charged_1");
    }
}
