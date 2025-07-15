package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.ItemShears;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BoneRazors extends ItemShears {
    public BoneRazors(int par1) {
        super(par1);
        this.setMaxDamage(69);
        this.setUnlocalizedName("bone_razors");
        this.setTextureName("refamished:bone_razors");
    }
    @Override
    public boolean canHarvestBlock(ItemStack stack, World world, Block block, int i, int j, int k) {
        return false;
    }
    @Override
    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k) {
        if (this.isEfficientVsBlock(stack, world, block, i, j, k)) {
            if (block.blockID == BTWBlocks.bloodWoodLeaves.blockID || block.blockID == Block.leaves.blockID || block.blockID == Block.web.blockID || block.blockID == BTWBlocks.web.blockID) {
                return 5.0f;
            }
            return 4.0f;
        }
        return super.getStrVsBlock(stack, world, block, i, j, k);
    }
}
