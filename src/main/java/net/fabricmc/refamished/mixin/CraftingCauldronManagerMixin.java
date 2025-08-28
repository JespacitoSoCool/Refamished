package net.fabricmc.refamished.mixin;

import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.CauldronRecipeList;
import btw.crafting.recipe.CrucibleRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.BulkRecipe;
import btw.item.BTWItems;
import btw.item.tag.TagOrStack;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Mixin(CauldronRecipeList.class)
public abstract class CraftingCauldronManagerMixin {

	@Inject(method = "addRecipes", at = @At("TAIL"),remap = false)
	private static void removeThisShit(CallbackInfo ci){
		removeCauldron(new ItemStack(BTWItems.nethercoal, 1), (TagOrStack[])new ItemStack[]{new ItemStack(BTWItems.hellfireDust, 1), new ItemStack(BTWItems.coalDust, 1)});
		RecipeManager.addCauldronRecipe(new ItemStack(BTWItems.nethercoal, 1), (TagOrStack[])new ItemStack[]{new ItemStack(BTWItems.hellfireDust, 1), new ItemStack(RefamishedItems.coke_coal, 1)});
	}

	@Unique
	private static void removeCauldron(ItemStack item, TagOrStack[] list) {
		CauldronCraftingManager.getInstance().removeRecipe(item,list);
	}
}
