package net.fabricmc.refamished.items.decorative;

import btw.block.BTWBlocks;
import btw.item.items.BucketItemFull;
import btw.util.MiscUtils;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.World;

public class tarBucketItem extends BucketItemFull {

    public tarBucketItem(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("tar_bucket");
        setTextureName("refamished:tar_bucket");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    protected boolean attemptPlaceContentsAtLocation(World var1, int var2, int var3, int var4) {
        return false;
    }

    @Override
    public int getBlockID() {
        return RefamishedBlocks.tar_bucket.blockID;
    }
}
