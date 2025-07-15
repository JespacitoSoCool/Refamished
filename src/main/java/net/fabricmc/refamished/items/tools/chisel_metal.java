package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import btw.item.items.ChiselItem;
import btw.item.items.ChiselItemIron;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.*;

public class chisel_metal extends ChiselItem  {
    public float diggyDiggyHole;
    public chisel_metal(int iItemID, float superHardcoreDiggingSpeed, int dura, EnumToolMaterial material) {
        super(iItemID, material, dura);
        diggyDiggyHole = superHardcoreDiggingSpeed;
    }

    @Override
    public boolean getCanBePlacedAsBlock() {
        return true;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, int iBlockID, int i, int j, int k, EntityLivingBase usingEntity) {
        if (iBlockID == Block.wood.blockID && world.getBlockId(i, j, k) == BTWBlocks.workStump.blockID) {
            stack.damageItem(5, usingEntity);
            return true;
        }
        return super.onBlockDestroyed(stack, world, iBlockID, i, j, k, usingEntity);
    }

    @Override
    public void applyStandardEfficiencyModifiers() {
        super.applyStandardEfficiencyModifiers();
        this.efficiencyOnProperMaterial /= 5.5f;
    }

    @Override
    public boolean isDamagedInCrafting() {
        return true;
    }

    @Override
    public void onUsedInCrafting(EntityPlayer player, ItemStack outputStack) {
        ChiselItemIron.playStoneSplitSoundOnPlayer(player);
    }

    @Override
    public void onBrokenInCrafting(EntityPlayer player) {
        ChiselItemIron.playBreakSoundOnPlayer(player);
    }

    @Override
    public boolean canToolStickInBlock(ItemStack stack, Block block, World world, int i, int j, int k) {
        if (block.blockMaterial == Material.rock && block.blockID != Block.bedrock.blockID) {
            return true;
        }
        return super.canToolStickInBlock(stack, block, world, i, j, k);
    }

    public static void playStoneSplitSoundOnPlayer(EntityPlayer player) {
        if (player.timesCraftedThisTick == 0) {
            player.playSound("random.anvil_land", 0.5f, player.worldObj.rand.nextFloat() * 0.25f + 1.75f);
        }
    }

    public static void playBreakSoundOnPlayer(EntityPlayer player) {
        player.playSound("random.break", 0.8f, 0.8f + player.worldObj.rand.nextFloat() * 0.4f);
    }
}
