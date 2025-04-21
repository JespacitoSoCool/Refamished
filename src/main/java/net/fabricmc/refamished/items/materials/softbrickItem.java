package net.fabricmc.refamished.items.materials;

import btw.item.items.BrickItem;
import net.fabricmc.refamished.RefamishedBlocks;

public class softbrickItem extends BrickItem {
    public softbrickItem(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("soft_brick");
        this.setTextureName("refamished:soft_brick");
    }

    @Override
    public int getBlockID() {
        return RefamishedBlocks.softBrickGround.blockID;
    }
}
