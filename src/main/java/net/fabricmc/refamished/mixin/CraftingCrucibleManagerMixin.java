package net.fabricmc.refamished.mixin;

import btw.block.BTWBlocks;
import btw.crafting.manager.CauldronStokedCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.manager.MillStoneCraftingManager;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.CrucibleRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.customcrafting.WoolArmorRecipe;
import btw.item.BTWItems;
import btw.util.color.Color;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.PigmentItem;
import net.fabricmc.refamished.items.materials.metalSheets;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.items.materials.metallurgyHeads;
import net.fabricmc.refamished.items.tools.flintMachete;
import net.fabricmc.refamished.misc.CustomRecipes.*;
import net.fabricmc.refamished.misc.Recipes.*;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(CrucibleRecipeList.class)
public abstract class CraftingCrucibleManagerMixin {

	@Inject(method = "addRecipes", at = @At("TAIL"),remap = false)
	private static void removeThisShit(CallbackInfo ci){
		removeStokedCrucible(new ItemStack(BTWItems.soulforgedSteelIngot, 1), new ItemStack[]{new ItemStack(Item.ingotIron, 1), new ItemStack(BTWItems.coalDust, 1), new ItemStack(BTWItems.soulUrn, 1), new ItemStack(BTWItems.soulFlux, 1)});
		removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 6), new ItemStack[]{new ItemStack(Item.helmetDiamond, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 5), new ItemStack[]{new ItemStack(Item.helmetDiamond, 1, Short.MAX_VALUE)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketEmpty)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketLava)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketWater)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketMilk)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(BTWItems.cementBucket)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(BTWItems.milkChocolateBucket)});
	}


	@Unique
	private static void removeStokedCrucible(ItemStack item, ItemStack[] list) {
		CrucibleStokedCraftingManager.getInstance().removeRecipe(item,list);
	}
}
