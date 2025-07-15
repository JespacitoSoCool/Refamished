package net.fabricmc.refamished.items.others;

import btw.block.blocks.CropsBlock;
import btw.entity.attribute.AttributeOperation;
import btw.item.BTWItems;
import btw.item.items.MortarItem;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.minecraft.src.*;

public class ingotMold extends Item {
    public ingotMold(int par1) {
        super(par1);
        setTextureName("refamished:mold_ingot");
        setUnlocalizedName("mold_ingot");
        setMaxStackSize(1);
    }
    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World world, EntityPlayer player) {
        ingotMoldRecipes.InputMetaPair recipe = ingotMoldRecipes.findMatchingInput(player);
        if (recipe == null) {
            if (!world.isRemote) {
                player.addChatMessage("You need 8 matching materials in your hotbar.");
            }
            return par1ItemStack;
        }

        ingotMoldRecipes.consumeItems(player, recipe.input, 8);
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(RefamishedItems.ingotPreparation,1,recipe.outputMeta);
        return par1ItemStack;
    }
}