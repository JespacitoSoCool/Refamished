package net.fabricmc.refamished.items.materials;

import btw.util.MiscUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class clayBowl extends Item {
    public clayBowl(int iItemID) {
        super(iItemID);
        setTextureName("refamished:clay_bowl");
        setUnlocalizedName("clay_bowl");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        MovingObjectPosition posClicked = MiscUtils.getMovingObjectPositionFromPlayerHitWaterAndLava(world, player, true);
        if (posClicked != null && posClicked.typeOfHit == EnumMovingObjectType.TILE) {
            int i = posClicked.blockX;
            int j = posClicked.blockY;
            int k = posClicked.blockZ;
            int iBlockID = world.getBlockId(i, j, k);
            if (world.getBlockMaterial(i, j, k) == Material.water) {
                if (MiscUtils.doesWaterHaveValidSource(world, i, j, k, 128)) {
                    if (--itemStack.stackSize <= 0) {
                        return new ItemStack(RefamishedItems.clay_bowl_water);
                    }
                    if (!player.inventory.addItemStackToInventory(new ItemStack(RefamishedItems.clay_bowl_water))) {
                        player.dropPlayerItem(new ItemStack(RefamishedItems.clay_bowl_water.itemID, 1, 0));
                    }
                }
                return itemStack;
            }
        }
        return itemStack;
    }
}
