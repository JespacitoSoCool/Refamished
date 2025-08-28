package net.fabricmc.refamished.items.tools;

import btw.item.items.MortarItem;
import net.minecraft.src.*;

public class metalTrowel extends Item {
    private float speed;

    public metalTrowel(int par1, int durability, float speed) {
        super(par1);
        setCreativeTab(CreativeTabs.tabTools);
        setMaxDamage(durability);
        this.speed = speed;
        setMaxStackSize(1);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int iBlockID, int i, int j, int k, EntityLivingBase usingEntity) {
        if (isToolTypeEfficientVsBlockType(Block.blocksList[iBlockID])) {
            stack.damageItem(1, usingEntity);
        }
        return true;
    }

    @Override
    public boolean canHarvestBlock(ItemStack stack, World world, Block block, int i, int j, int k) {
        return this.isToolTypeEfficientVsBlockType(block);
    }

    @Override
    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k) {
        if (isToolTypeEfficientVsBlockType(block)) {
            return speed;
        }
        return super.getStrVsBlock(stack,world,block,i,j,k);
    }

    public boolean isToolTypeEfficientVsBlockType(Block block) {
        return block.areShovelsEffectiveOn();
    }

    public ItemStack getMortar(EntityPlayer player) {
        for (int iTempSlot = 0; iTempSlot < 9; ++iTempSlot) {
            ItemStack tempStack = player.inventory.getStackInSlot(iTempSlot);
            if (tempStack == null || !(tempStack.getItem() instanceof MortarItem)) continue;
            return tempStack;
        }
        return null;
    }

    @Override
    public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        ItemStack mortar = getMortar(player);
        if (mortar != null) {
            Block targetBlock;
            if (player != null && player.canPlayerEdit(i, j, k, iFacing, mortar) && (targetBlock = Block.blocksList[world.getBlockId(i, j, k)]) != null && targetBlock.onMortarApplied(world, i, j, k)) {
                if (!world.isRemote) {
                    world.playAuxSFX(2274, i, j, k, 0);
                }
                --mortar.stackSize;
                return true;
            }
        }
        return false;
    }
}
