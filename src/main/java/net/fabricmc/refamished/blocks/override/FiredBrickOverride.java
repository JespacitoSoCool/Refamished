package net.fabricmc.refamished.blocks.override;

import btw.block.blocks.FiredBrickBlock;
import net.fabricmc.refamished.misc.Utils.UtilInventory;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class FiredBrickOverride extends FiredBrickBlock {
    public FiredBrickOverride(int iBlockID) {
        super(iBlockID);
    }
    @Override
    public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer player, int iFacing, float fXClick, float fYClick, float fZClick )
    {
        ItemStack brick = new ItemStack(Item.brick);

        if (UtilInventory.AddItemStackToInventoryInSlotRange( player.inventory, brick, 0, player.inventory.getSizeInventory() - 5 ))
        {
            player.worldObj.playSoundAtEntity( player, "random.pop", 0.2F,
                    ((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
            world.setBlockToAir(i, j, k);
            return true;
        }
        else
        {
            dropBlockAsItem(world, i, j, k, world.getBlockMetadata( i, j, k ), 0 );
            world.setBlockToAir(i, j, k);
            return true;
        }
    }
}
