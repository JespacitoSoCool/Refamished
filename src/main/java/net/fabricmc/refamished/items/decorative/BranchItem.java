package net.fabricmc.refamished.items.decorative;

import btw.crafting.util.FurnaceBurnTime;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemReed;

public class BranchItem extends Item {
    public BranchItem(int par1) {
        super(par1);
        this.setFull3D();
        this.setBuoyant();
        this.setfurnaceburntime(FurnaceBurnTime.SHAFT);
        this.setIncineratedInCrucible();
        this.setFilterableProperties(4);
        this.setUnlocalizedName("branch");
        this.setTextureName("refamished:branch");
        this.setCreativeTab(CreativeTabs.tabMaterials);
    }
}
