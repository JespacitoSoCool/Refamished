package net.fabricmc.refamished.items.materials;

import btw.block.BTWBlocks;
import btw.item.items.CrudeUnfiredBrickItem;
import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class softClayBrick extends PlaceAsBlockItem {
    public softClayBrick(int iItemID) {
        super(iItemID, RefamishedBlocks.clayMudBrickGroundIdle.blockID);
        this.setUnlocalizedName("soft_clay_brick");
        this.setTextureName("refamished:clay_brick");
        setCreativeTab(CreativeTabs.tabMaterials);
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player) {
        if (player.timesCraftedThisTick == 0 && world.isRemote) {
            player.playSound("mob.slime.attack", 0.25f, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.1f + 0.7f);
        }
        super.onCreated(stack, world, player);
    }
}
