package net.fabricmc.refamished.blocks.decorative;

import btw.block.blocks.OreBlock;
import btw.block.blocks.RoughStoneBlock;
import btw.item.BTWItems;
import btw.item.items.ChiselItem;
import btw.item.items.PickaxeItem;
import btw.item.items.ToolItem;
import btw.item.util.ItemUtils;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class OreBlockStagedOverride extends OreBlock {
    public OreBlockStagedOverride(int iBlockID) {
        super(iBlockID);
        this.setChiselsEffectiveOn();
    }

    @Override
    public boolean canConvertBlock(ItemStack stack, World world, int i, int j, int k) {
        return true;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int i, int j, int k, int iFromSide) {
        int iLevel;
        int iOldMetadata = world.getBlockMetadata(i, j, k);
        int iStrata = this.getStrata(iOldMetadata);
        world.setBlockAndMetadataWithNotify(i, j, k, RoughStoneBlock.strataLevelBlockArray[iStrata].blockID, 4);
        if (!world.isRemote && (iLevel = this.getConversionLevelForTool(stack, world, i, j, k)) > 0) {
            world.playAuxSFX(2269, i, j, k, 0);
            if (iLevel >= 3) {
                this.ejectItemsOnGoodPickConversion(stack, world, i, j, k, iOldMetadata, iFromSide);
            } else if (iLevel == 2) {
                this.ejectItemsOnStonePickConversion(stack, world, i, j, k, iOldMetadata, iFromSide);
            } else {
                this.ejectItemsOnChiselConversion(stack, world, i, j, k, iOldMetadata, iFromSide);
            }
        }
        return true;
    }

    @Override
    public boolean shouldPlayStandardConvertSound(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier) {
        super.dropBlockAsItemWithChance(world, i, j, k, iMetadata, fChance, iFortuneModifier);
        if (!world.isRemote) {
            this.dropItemsIndividually(world, i, j, k, BTWItems.stone.itemID, 6, this.getStrata(iMetadata), 1.0f);
        }
    }

    @Override
    public int getEfficientToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        return this.getRequiredToolLevelForOre(blockAccess, i, j, k);
    }

    @Override
    public int getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k) {
        int iLevelForOre = this.getRequiredToolLevelForOre(blockAccess, i, j, k);
        int iLevelForStrata = this.getRequiredToolLevelForStrata(blockAccess, i, j, k);
        if (iLevelForStrata > iLevelForOre) {
            return iLevelForStrata;
        }
        return iLevelForOre;
    }

    public int idDroppedOnConversion(Difficulty var1, int var2) {
        return 0;
    }

    public int damageDroppedOnConversion(int iMetadata) {
        return 0;
    }

    public int quantityDroppedOnConversion(Random rand) {
        return 1;
    }

    public int idDroppedOnStonePickConversion(int iMetadata, Random rand, int iFortuneModifier) {
        return this.idDropped(iMetadata, rand, iFortuneModifier);
    }

    public int damageDroppedOnStonePickConversion(int iMetadata) {
        return this.damageDropped(iMetadata);
    }

    public int quantityDroppedOnStonePickConversion(Random rand) {
        return this.quantityDropped(rand);
    }

    protected void ejectItemsOnGoodPickConversion(ItemStack stack, World world, int i, int j, int k, int iOldMetadata, int iFromSide) {
        ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(this.idDropped(iOldMetadata, world.rand, 0), this.quantityDropped(world.rand), this.damageDropped(iOldMetadata)), iFromSide);
    }

    protected void ejectItemsOnStonePickConversion(ItemStack stack, World world, int i, int j, int k, int iOldMetadata, int iFromSide) {
        ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(this.idDroppedOnStonePickConversion(iOldMetadata, world.rand, 0), this.quantityDroppedOnStonePickConversion(world.rand), this.damageDroppedOnStonePickConversion(iOldMetadata)), iFromSide);
    }

    protected void ejectItemsOnChiselConversion(ItemStack stack, World world, int i, int j, int k, int iOldMetadata, int iFromSide) {
        ItemUtils.ejectStackFromBlockTowardsFacing(world, i, j, k, new ItemStack(this.idDroppedOnConversion(world.getDifficulty(), iOldMetadata), this.quantityDroppedOnConversion(world.rand), this.damageDroppedOnConversion(iOldMetadata)), iFromSide);
    }

    public int getRequiredToolLevelForOre(IBlockAccess blockAccess, int i, int j, int k) {
        return 0;
    }

    private int getConversionLevelForTool(ItemStack stack, World world, int i, int j, int k) {
        if (stack != null) {
            int iToolLevel;
            if (stack.getItem() instanceof PickaxeItem) {
                int iToolLevel2 = ((ToolItem)stack.getItem()).toolMaterial.getHarvestLevel();
                if (iToolLevel2 >= this.getRequiredToolLevelForOre(world, i, j, k)) {
                    if (iToolLevel2 > 1 || world.getDifficulty().doesStonePickBreakStone()) {
                        return 3;
                    }
                    return 2;
                }
            } else if (stack.getItem() instanceof ChiselItem && (iToolLevel = ((ToolItem)stack.getItem()).toolMaterial.getHarvestLevel()) >= this.getRequiredToolLevelForOre(world, i, j, k)) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void getSubBlocks(int blockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(blockID, 1, 0));
        list.add(new ItemStack(blockID, 1, 1));
        list.add(new ItemStack(blockID, 1, 2));
    }
}
