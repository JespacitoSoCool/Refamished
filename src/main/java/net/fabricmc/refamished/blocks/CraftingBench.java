package net.fabricmc.refamished.blocks;

import btw.block.BTWBlocks;
import btw.crafting.util.FurnaceBurnTime;
import btw.item.BTWItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;

import java.util.List;

public class CraftingBench extends BlockWorkbench {

    public CraftingBench(int i) {
        super(i);
        this.setAxesEffectiveOn();
        this.setStepSound(soundWoodFootstep);
        this.setCreativeTab(CreativeTabs.tabBlock);
        this.setHardness(1f);
        this.setResistance(3.0f);
        this.setTextureName("crafting_table");
        this.setFireProperties(5, 20);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }

    private Icon workbenchIconTop;
    private Icon workbenchIconFront;

    @Override
    public Icon getIcon(int par1, int par2) {
        return par1 == 1 ? this.workbenchIconTop : (par1 == 0 ? Block.planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.workbenchIconFront));
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.blockIcon = par1IconRegister.registerIcon(this.getTextureName() + "_side");
        this.workbenchIconTop = par1IconRegister.registerIcon(this.getTextureName() + "_top");
        this.workbenchIconFront = par1IconRegister.registerIcon(this.getTextureName() + "_front");
    }

    @Override
    public boolean dropComponentItemsOnBadBreak(World world, int i, int j, int k, int iMetadata, float fChanceOfDrop) {
        this.dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID, 12, 0, fChanceOfDrop);
        this.dropItemsIndividually(world, i, j, k, Item.stick.itemID, 6, 0, fChanceOfDrop);
        this.dropItemsIndividually(world, i, j, k, BTWItems.ironNugget.itemID, 7, 0, fChanceOfDrop);
        return true;
    }

    @Override
    public void dropBlockAsItemWithChance( World world, int i, int j, int k, int iMetaData, float fChance, int iFortuneModifier ) {
        this.dropItemsIndividually(world, i, j, k, BTWItems.sawDust.itemID, 12, 0, fChance);
        this.dropItemsIndividually(world, i, j, k, Item.stick.itemID, 6, 0, fChance);
        this.dropItemsIndividually(world, i, j, k, BTWItems.ironNugget.itemID, 7, 0, fChance);
    }
}
