package net.fabricmc.refamished.items.materials;

import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.CreativeTabs;

public class cowhideItem extends PlaceAsBlockItem {
    public cowhideItem(int iItemID, int iBlockID) {
        super(iItemID, iBlockID);
        this.setUnlocalizedName("cowhide");
        setTextureName("refamished:cowhide");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
    @Override
    public int getBlockID() {
        return RefamishedBlocks.preparedCowhideBlock.blockID;
    }
}
