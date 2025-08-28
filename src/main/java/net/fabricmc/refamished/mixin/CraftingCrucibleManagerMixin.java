package net.fabricmc.refamished.mixin;

import btw.crafting.manager.BulkCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.CrucibleRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.BulkRecipe;
import btw.item.BTWItems;
import btw.item.tag.TagInstance;
import btw.item.tag.TagOrStack;
import net.fabricmc.refamished.RefamishedItems;
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
		removeStokedCrucible(new ItemStack(BTWItems.soulforgedSteelIngot, 1), new TagOrStack[]{new ItemStack(Item.ingotIron, 1), new ItemStack(BTWItems.coalDust, 1), new ItemStack(BTWItems.soulUrn, 1), new ItemStack(BTWItems.soulFlux, 1)});
		//removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 6), new ItemStack[]{new ItemStack(Item.helmetDiamond, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 5), new ItemStack[]{new ItemStack(Item.helmetDiamond, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.soulforgedSteelIngot, 2), new ItemStack[]{new ItemStack(RefamishedItems.steelIngot, 5), new ItemStack(RefamishedItems.scorched_flux, 64), new ItemStack(BTWItems.soulUrn, 3), new ItemStack(BTWItems.soulFlux, 12), new ItemStack(BTWItems.rawMysteryMeat, 1), new ItemStack(Item.blazePowder, 1)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]
				{new ItemStack(Item.ingotIron),new ItemStack(RefamishedItems.scorched_flux,12),new ItemStack(RefamishedItems.coke_coal,2)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(Item.slimeBall)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(BTWItems.glue)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(BTWItems.nitre,22),new ItemStack(Item.slimeBall)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(BTWItems.nitre,22),new ItemStack(BTWItems.glue)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketEmpty)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketLava)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketWater)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(Item.bucketMilk)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(BTWItems.cementBucket)});
		removeStokedCrucible(new ItemStack(BTWItems.ironNugget, 5), new ItemStack[]{new ItemStack(BTWItems.milkChocolateBucket)});
		removeStokedCrucible(new ItemStack(BTWItems.soulforgedSteelIngot, 1), new ItemStack[]{new ItemStack(Item.ingotIron, 1), new ItemStack(BTWItems.coalDust, 1), new ItemStack(BTWItems.soulUrn, 1), new ItemStack(BTWItems.enderSlag, 1)});
	}


	@Unique
	private static void removeStokedCrucible(ItemStack item, TagOrStack[] list) {
		CrucibleStokedCraftingManager.getInstance().removeRecipe(item,list);
	}

	@Unique
	private static void removeStokedCrucible2(ItemStack item, TagOrStack[] list) {
		ItemStack[] outputStacks = new ItemStack[]{item.copy()};
		BulkRecipe tempRecipe = createRecipe(outputStacks,list,false);
		Iterator<BulkRecipe> List = CrucibleStokedCraftingManager.getInstance().getRecipeList().iterator();
		while (List.hasNext()) {
			BulkRecipe recipe = List.next();
			System.out.println("1 Out "+recipe.getCraftingOutputList());
			System.out.println("2 Out "+tempRecipe.getCraftingOutputList());
			System.out.println("1 In "+recipe.getCraftingIngrediantList());
			System.out.println("2 In "+tempRecipe.getCraftingIngrediantList());
			if (recipe.getCraftingOutputList() == tempRecipe.getCraftingOutputList()) {
				List.remove();
				System.out.println("YAYYYY");
				return;
			}
		}
		System.out.println("NO MATCH!");
	}

	private static BulkRecipe createRecipe(ItemStack[] outputStacks, TagOrStack[] inputStacks, boolean bMetadataExclusive) {
		ArrayList<TagOrStack> inputArrayList = new ArrayList<TagOrStack>();
		int iInputStacksArrayLength = inputStacks.length;
		for (int iTempIndex = 0; iTempIndex < iInputStacksArrayLength; ++iTempIndex) {
			TagOrStack tagOrStack = inputStacks[iTempIndex];
			if (tagOrStack instanceof ItemStack) {
				ItemStack stack = (ItemStack)tagOrStack;
				inputArrayList.add(stack.copy());
				continue;
			}
			tagOrStack = inputStacks[iTempIndex];
			if (tagOrStack instanceof TagInstance) {
				TagInstance tag = (TagInstance)tagOrStack;
				inputArrayList.add(tag);
				continue;
			}
			throw new RuntimeException("Invalid item added to bulk crafting recipe!" + inputStacks[iTempIndex]);
		}
		ArrayList<ItemStack> outputArrayList = new ArrayList<ItemStack>();
		int iOutputStacksArrayLength = outputStacks.length;
		for (int iTempIndex = 0; iTempIndex < iOutputStacksArrayLength; ++iTempIndex) {
			outputArrayList.add(outputStacks[iTempIndex].copy());
		}
		return new BulkRecipe(outputArrayList, inputArrayList, bMetadataExclusive);
	}
}
