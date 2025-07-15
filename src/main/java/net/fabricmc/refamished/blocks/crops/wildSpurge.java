package net.fabricmc.refamished.blocks.crops;

import btw.block.BTWBlocks;
import btw.block.blocks.PlantsBlock;
import btw.block.util.Flammability;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class wildSpurge extends BlockDeadBush {
    public wildSpurge(int iBlockID) {
        super(iBlockID);
        this.setHardness(0.2f);
        this.setBuoyant();
        this.setFireProperties(Flammability.CROPS);
        this.initBlockBounds(0.125, 0.0, 0.125, 0.875, 1.0, 0.875);
        this.setStepSound(BTWBlocks.cropStepSound);
        this.setTickRandomly(true);
        this.disableStats();
        this.setUnlocalizedName("spurge");
        setTextureName("refamished:dead_bush_foliage");
    }

    @Override
    public void harvestBlock(World par1World, EntityPlayer par2EntityPlayer, int par3, int par4, int par5, int par6) {
        if (!par1World.isRemote && par2EntityPlayer.getCurrentEquippedItem() != null && par2EntityPlayer.getCurrentEquippedItem().getItem() instanceof ItemShears) {
            par2EntityPlayer.addStat(StatList.mineBlockStatArray[this.blockID], 1);
            this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(Block.deadBush, 1, par6));
        } else {
            if (par1World.rand.nextInt(15) == 0) {
                this.dropBlockAsItem_do(par1World, par3, par4, par5, new ItemStack(BTWItems.hempSeeds, 1, par6));
            }
            super.harvestBlock(par1World, par2EntityPlayer, par3, par4, par5, par6);
        }
    }

}
