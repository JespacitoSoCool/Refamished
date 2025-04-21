package net.fabricmc.refamished.items.tools;

import btw.item.BTWItems;
import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.minecraft.src.*;

public class flintSharpenerStone extends ProgressiveCraftingItem {

    public flintSharpenerStone(int par1) {
        super(par1);
        setUnlocalizedName( "flintSharpenerStone" );
        this.setMaxStackSize(1);
        setTextureName("refamished:progressive/flintSharpenerStone");
    }
    protected int getProgressiveCraftingMaxDamage()
    {
        return 60;
    }

    protected void playCraftingFX(ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "mob.sheep.shear", 0.75F + 0.75F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.1F ) + 0.8F );

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

                player.worldObj.spawnParticle( "iconcrack_" + Item.flint.itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
            }
        }
    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, "dig.stone", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        if (internalBladeDamage >= 15)
        {

        }
        else
        {
            UtilItem.GivePlayerStackOrEject( player, new ItemStack(RefamishedItems.flint_sharpener, 1, internalBladeDamage));
        }

        return new ItemStack( BTWItems.sharpStone, 1);
    }


    public void onCreated( ItemStack stack, World world, EntityPlayer player )
    {
        super.onCreated( stack, world, player );

    }
}
