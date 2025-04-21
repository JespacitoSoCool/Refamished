package net.fabricmc.refamished.misc.Utils;

import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class UtilItem {
    static public void EjectStackWithRandomOffset( World world, int i, int j, int k, ItemStack stack )
    {
        float xOffset = world.rand.nextFloat() * 0.7F + 0.15F;
        float yOffset = world.rand.nextFloat() * 0.2F + 0.1F;
        float zOffset = world.rand.nextFloat() * 0.7F + 0.15F;

        EjectStackWithRandomVelocity( world, (float)i + xOffset, (float)j + yOffset, (float)k + zOffset, stack );
    }

    static public void EjectStackWithRandomVelocity(World world, double xPos, double yPos, double zPos, ItemStack stack )
    {
        EntityItem entityitem =
                new EntityItem( world, xPos, yPos, zPos,
                        stack );

        float velocityFactor = 0.05F;

        entityitem.motionX = (float)world.rand.nextGaussian() * velocityFactor;
        entityitem.motionY = (float)world.rand.nextGaussian() * velocityFactor + 0.2F;
        entityitem.motionZ = (float)world.rand.nextGaussian() * velocityFactor;

        entityitem.delayBeforeCanPickup = 10;

        world.spawnEntityInWorld( entityitem );
    }

    static public void GivePlayerStackOrEject(EntityPlayer player, ItemStack stack, int i, int j, int k )
    {
        if ( player.inventory.addItemStackToInventory( stack ) )
        {
            player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F,
                    ((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        else if ( !player.worldObj.isRemote )
        {
            EjectStackWithRandomOffset( player.worldObj, i, j, k, stack );
        }
    }

    static public void GivePlayerStackOrEject( EntityPlayer player, ItemStack stack )
    {
        if ( player.inventory.addItemStackToInventory( stack ) )
        {
            player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F,
                    ((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
        }
        else if ( !player.worldObj.isRemote )
        {
            EjectStackWithRandomVelocity( player.worldObj, player.posX, player.posY, player.posZ, stack );
        }
    }
}
