package net.fabricmc.refamished.items.tools;

import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;

public class stoneTrowel extends Item {

    public stoneTrowel(int par1) {
        super(par1);
        setUnlocalizedName("stoneTrowel");
        setTextureName("refamished:stone_trowel");
        setCreativeTab(CreativeTabs.tabTools);
        setMaxDamage(32);
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
            return 0.8F;
        }
        return super.getStrVsBlock(stack,world,block,i,j,k);
    }

    public boolean isToolTypeEfficientVsBlockType(Block block) {
        return block.areShovelsEffectiveOn();
    }
}
