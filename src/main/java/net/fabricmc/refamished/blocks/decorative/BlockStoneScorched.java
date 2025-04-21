package net.fabricmc.refamished.blocks.decorative;

import btw.block.BTWBlocks;
import btw.block.blocks.RoughStoneBlock;
import btw.item.BTWItems;
import btw.item.items.ChiselItem;
import btw.item.items.PickaxeItem;
import btw.item.items.ToolItem;
import btw.item.util.ItemUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.List;
import java.util.Random;

public class BlockStoneScorched extends BlockStone {
    public BlockStoneScorched(int blockID) {
        super(blockID);
        setUnlocalizedName("scorched_stone");
        setTextureName("refamished:scorched_stone_1_strata_1");
    }
    @Override
    public float getBlockHardness(World world, int i, int j, int k) {
        return super.getBlockHardness(world, i, j, k)*1.5f;
    }

    @Override
    public void dropBlockAsItemWithChance(World world, int i, int j, int k, int iMetadata, float fChance, int iFortuneModifier) {
        if (!world.isRemote) {
            this.dropBlockAsItem_do(world, i, j, k, new ItemStack(RefamishedItems.scorched_rock, 8, this.getStrata(iMetadata)));
            if (!this.getIsCracked(iMetadata)) {
                this.dropBlockAsItem_do(world, i, j, k, new ItemStack(BTWItems.gravelPile));
            }
        }
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropBlockAsItem_do(world, i, j, k, new ItemStack(RefamishedItems.scorched_rock, 8, this.getStrata(iMetadata)));
        int iNumGravel = this.getIsCracked(iMetadata) ? 2 : 3;
        this.dropItemsIndividually(world, i, j, k, BTWItems.gravelPile.itemID, iNumGravel, 0, fChanceOfDrop);
        return true;
    }
    private int getConversionLevelForTool(ItemStack stack, World world, int i, int j, int k) {
        if (stack != null) {
            int iToolLevel;
            if (stack.getItem() instanceof PickaxeItem) {
                int iToolLevel2 = ((ToolItem)stack.getItem()).toolMaterial.getHarvestLevel();
                if (iToolLevel2 >= this.getEfficientToolLevel(world, i, j, k)) {
                    return 2;
                }
            }
        }
        return 0;
    }

    @Override
    public boolean convertBlock(ItemStack stack, World world, int x, int y, int z, int iFromSide) {
        int metadata = world.getBlockMetadata(x, y, z);
        int strata = this.getStrata(metadata);
        int toolLevel = this.getConversionLevelForTool(stack, world, x, y, z);
        if (this.getIsCracked(metadata)) {
            world.setBlockAndMetadataWithNotify(x, y, z, RoughStoneBlock.strataLevelBlockArray[strata].blockID, 0);
            if (!world.isRemote && toolLevel > 0) {
                world.playAuxSFX(2269, x, y, z, 0);
                ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.stone, 1, strata), iFromSide);
            }
        } else if (toolLevel == 2) {
            if (world.getDifficulty().doesStonePickBreakStone()) {
                if (!world.isRemote) {
                    world.playAuxSFX(2001, x, y, z, this.blockID);
                }
                this.dropBlockAsItem(world, x, y, z, metadata, 0);
                world.setBlockToAir(x, y, z);
            } else {
                world.setBlockAndMetadataWithNotify(x, y, z, RoughStoneBlock.strataLevelBlockArray[strata].blockID, 4);
                if (!world.isRemote) {
                    world.playAuxSFX(2269, x, y, z, 0);
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.stone, 3, strata), iFromSide);
                    ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.gravelPile, 1), iFromSide);
                }
            }
        }
        else {
            if (!world.isRemote) {
                world.playAuxSFX(2270, x, y, z, 0);
                ItemUtils.ejectStackFromBlockTowardsFacing(world, x, y, z, new ItemStack(BTWItems.gravelPile, 1), iFromSide);
            }
            this.setIsCracked(world, x, y, z, true);
        }
        return true;
    }

    @Environment(value=EnvType.CLIENT)
    private Icon[] iconByMetadataArray;
    @Override
    @Environment(value=EnvType.CLIENT)
    public void getSubBlocks(int iBlockID, CreativeTabs creativeTabs, List list) {
        list.add(new ItemStack(iBlockID, 1, 0));
        list.add(new ItemStack(iBlockID, 1, 1));
        list.add(new ItemStack(iBlockID, 1, 2));
        list.add(new ItemStack(iBlockID, 1, 3));
        list.add(new ItemStack(iBlockID, 1, 4));
        list.add(new ItemStack(iBlockID, 1, 5));
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int getDamageValue(World world, int x, int y, int z) {
        return world.getBlockMetadata(x, y, z);
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        this.iconByMetadataArray = new Icon[16];
        super.registerIcons(register);
        this.iconByMetadataArray[0] = register.registerIcon("refamished:scorched_stone_1_strata_1");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:scorched_stone_1_strata_2");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:scorched_stone_1_strata_3");
        this.iconByMetadataArray[3] = register.registerIcon("refamished:scorched_stone_2_strata_1");
        this.iconByMetadataArray[4] = register.registerIcon("refamished:scorched_stone_2_strata_2");
        this.iconByMetadataArray[5] = register.registerIcon("refamished:scorched_stone_2_strata_3");
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIcon(int iSide, int iMetadata) {
        return this.iconByMetadataArray[iMetadata];
    }
}
