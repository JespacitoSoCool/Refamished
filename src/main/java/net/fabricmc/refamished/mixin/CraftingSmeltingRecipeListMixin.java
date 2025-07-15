package net.fabricmc.refamished.mixin;

import btw.block.BTWBlocks;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.manager.MillStoneCraftingManager;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.SmeltingRecipeList;
import btw.crafting.recipe.types.customcrafting.WoolArmorRecipe;
import btw.item.BTWItems;
import btw.util.color.Color;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.PigmentItem;
import net.fabricmc.refamished.items.materials.metalSheets;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.items.materials.metallurgyHeads;
import net.fabricmc.refamished.misc.CustomRecipes.*;
import net.fabricmc.refamished.misc.Recipes.*;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(SmeltingRecipeList.class)
public abstract class CraftingSmeltingRecipeListMixin {
	@Inject(method = "addSmeltingRecipes",at = @At("TAIL"),remap = false)
	private static void addBlockRecipes(CallbackInfo ci){
		removeFurnaceRecipe(BTWItems.ironOreChunk.itemID);
		removeFurnaceRecipe(BTWItems.goldOreChunk.itemID);
	}

	@Unique
	private static void removeFurnaceRecipe(int ItemID) {
		FurnaceRecipes.smelting().getSmeltingList().remove(ItemID);
	}
}
