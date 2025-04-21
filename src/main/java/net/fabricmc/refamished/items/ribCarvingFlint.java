package net.fabricmc.refamished.items;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class ribCarvingFlint extends leatherWorkFlint implements ProgressiveCraftingInterface {
    public ribCarvingFlint(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 200;
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_flint");

        m_recipientIcon[0] = register.registerIcon("refamished:rib_beef");
        // Register recipient icons
        for (int i = 1; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/rib_carve_" + i);
        }

    }

    @Override
    protected void playCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        //worldObj.playAuxSFX( FCBetterThanWolves.m_iCreeperNeuteredAuxFXID, i, j, k, 0 );
        player.playSound( "mob.slime.small",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1.75F );

        player.playSound( "random.eat", 0.5F + 0.5F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.25F ) + 1.75F );

        if ( player.worldObj.isRemote )
        {
            for (int var3 = 0; var3 < 5; ++var3)
            {
                Vec3 var4 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D );

                var4.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F );
                var4.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F );

                Vec3 var5 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.3D, (double)(-player.rand.nextFloat()) * 0.6D - 0.3D, 0.6D );

                var5.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F);
                var5.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F);

                var5 = var5.addVector( player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);

                player.worldObj.spawnParticle( "iconcrack_" + RefamishedItems.rib_beef.itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
            }
        }
    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {

        world.playSoundAtEntity( player, "step.cloth", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        //FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        if (internalBladeDamage >= 16)
        {

        }
        else
        {
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(RefamishedItems.flintBlade, 1, internalBladeDamage));
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(Item.beefRaw, 1));
        }

        SkillManager.addExperience(player,"Artisanry", 8);
        SkillManager.addExperience(player,"Chipping", 7);

        return new ItemStack( RefamishedItems.rib, 1);
    }
}
