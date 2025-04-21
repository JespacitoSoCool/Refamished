package net.fabricmc.refamished.mixin;

import btw.block.BTWBlocks;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.manager.MillStoneCraftingManager;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.customcrafting.WoolArmorRecipe;
import btw.item.BTWItems;
import emi.dev.emi.emi.api.plugin.BTWPlugin;
import emi.dev.emi.emi.recipe.btw.EmiProgressiveRecipe;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.CustomRecipes.*;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.fabricmc.refamished.misc.Recipes.PercentageBasedSmelting;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.List;

@Mixin(CraftingRecipeList.class)
public abstract class CraftingManagerMixin {
	@Inject(method = "addBlockRecipes",at = @At("TAIL"),remap = false)
	private static void addBlockRecipes(CallbackInfo ci){
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperChunkStorage ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.copperChunk,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperBlock ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperBlock ), new Object[] {
				"I ",
				"I ",
				Character.valueOf( 'I' ), RefamishedBlocks.copperSlab,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperSlab,6), new Object[] {
				"III",
				Character.valueOf( 'I' ), RefamishedBlocks.copperBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperStair,4), new Object[] {
				"I ",
				"II",
				Character.valueOf( 'I' ), RefamishedBlocks.copperBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperStair,8), new Object[] {
				"I  ",
				"II ",
				"III",
				Character.valueOf( 'I' ), RefamishedBlocks.copperBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.steelBlock ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.steelIngot,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.cokeBlock ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.coke_coal,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.tar_tank ), new Object[] {
				" B ",
				"B B",
				" B ",
				Character.valueOf( 'B' ), RefamishedBlocks.softBrickLooseSlab,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.retort_grate ), new Object[] {
				"IBI",
				"BIB",
				"IBI",
				Character.valueOf( 'B' ), RefamishedBlocks.softBrickLooseSlab,
				Character.valueOf( 'I' ), BTWItems.ironNugget,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.cokeOven ), new Object[] {
				" S ",
				"B B",
				" S ",
				Character.valueOf( 'S' ), RefamishedBlocks.softBrickLooseSlab,
				Character.valueOf( 'B' ), RefamishedItems.soft_brick,
		} );
		RecipeManager.addRecipe( new ItemStack( BTWBlocks.workbench ), new Object[] {
				"PPP",
				"PIP",
				"PPP",
				Character.valueOf( 'P' ), Block.planks,
				Character.valueOf( 'I' ), Item.ingotIron,
		} );
		RecipeManager.addRecipe( new ItemStack( BTWBlocks.workbench ), new Object[] {
				"PPP",
				"PIP",
				"PPP",
				Character.valueOf( 'P' ), new ItemStack(BTWItems.woodMouldingStubID, 1, Short.MAX_VALUE),
				Character.valueOf( 'I' ), Item.ingotIron,
		} );

	}
	@Inject(method = "addItemRecipes", at = @At("TAIL"),remap = false)
	private static void addItemRecipes(CallbackInfo ci){
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperIngot ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.copperNugget,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperNugget,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperChunk,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.copperChunkStorage,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperIngot,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.copperBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperIngot,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.copperDoubleSlab,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperIngot,6 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.copperStair,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIngot ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.gildedNugget,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedNugget,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
		} );

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.coke_coal,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.cokeBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.steelIngot,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.steelBlock,
		} );
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.copperChunk),
				new Object[]{
						new ItemStack(RefamishedItems.copperDust, 1),
						new ItemStack(RefamishedItems.copperDust, 1),
				});
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperSword ), new Object[] {
				"I",
				"I",
				"S",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperPickaxe ), new Object[] {
				"III",
				" S ",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperAxe ), new Object[] {
				"I ",
				"IS",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperShovel ), new Object[] {
				"I",
				"S",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperHoe ), new Object[] {
				"IS",
				" S",
				Character.valueOf( 'I' ), RefamishedItems.copperIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.copperChisel ), new Object[] {
				"NN",
				"NN",
				Character.valueOf( 'N' ), RefamishedItems.copperNugget,
		} );
		List<Object[]> leatherThings = Arrays.asList(
				new Object[]{Item.leather},
				new Object[]{BTWItems.scouredLeather},
				new Object[]{BTWItems.tannedLeather},
				new Object[]{BTWItems.cutLeather},
				new Object[]{BTWItems.cutTannedLeather},
				new Object[]{BTWItems.cutScouredLeather}
		);

		for (Object[] tool : leatherThings) {
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.helmetCopper ), new Object[] {
					"LIL",
					"LIL",
					Character.valueOf( 'I' ), RefamishedItems.copperNugget,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.plateCopper ), new Object[] {
					"L L",
					"LIL",
					"III",
					Character.valueOf( 'I' ), RefamishedItems.copperNugget,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.legsCopper ), new Object[] {
					"LIL",
					"I I",
					"L L",
					Character.valueOf( 'I' ), RefamishedItems.copperNugget,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.bootsCopper ), new Object[] {
					"L L",
					"I I",
					Character.valueOf( 'I' ), RefamishedItems.copperNugget,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
		}

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIronSword ), new Object[] {
				"I",
				"I",
				"S",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIronPickaxe ), new Object[] {
				"III",
				" S ",
				" S ",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIronAxe ), new Object[] {
				"I ",
				"IS",
				" S",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIronShovel ), new Object[] {
				"I",
				"S",
				"S",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIronHoe ), new Object[] {
				"IS",
				" S",
				" S",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
				Character.valueOf( 'S' ), Item.stick,
		} );

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.tipDiamondSword ), new Object[] {
				"ID",
				"ID",
				"S ",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'D' ), Item.diamond,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.tipDiamondPickaxe ), new Object[] {
				"III",
				"DSD",
				" S ",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'D' ), Item.diamond,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.tipDiamondAxe ), new Object[] {
				"ID ",
				"ISD",
				" S ",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'D' ), Item.diamond,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.tipDiamondShovel ), new Object[] {
				"ID",
				"S ",
				"S ",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'D' ), Item.diamond,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.tipDiamondHoe ), new Object[] {
				"IS",
				"DS",
				" S",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'D' ), Item.diamond,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.copperChisel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.copperSword)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 15), new ItemStack[]{new ItemStack(RefamishedItems.copperPickaxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.copperAxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperShovel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperHoe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.helmetCopper)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 20), new ItemStack[]{new ItemStack(RefamishedItems.plateCopper)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 15), new ItemStack[]{new ItemStack(RefamishedItems.legsCopper)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.bootsCopper)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronSword)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronPickaxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronAxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronShovel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronHoe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronMachete)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.helmetRustIron)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.plateRustIron)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.legsRustIron)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.bootsRustIron)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperSword)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperPickaxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperAxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperShovel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperHoe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 12), new ItemStack[]{new ItemStack(RefamishedItems.iron_machete)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]{new ItemStack(RefamishedItems.diamond_machete)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(RefamishedItems.steelIngot,4),new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.compound_arbalest)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 16), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronSword)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 24), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronPickaxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 16), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronAxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronShovel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronHoe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedIngot), new ItemStack[]{new ItemStack(Item.ingotIron),new ItemStack(Item.goldNugget,4)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,2),new ItemStack(BTWItems.ironNugget,12)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondSword)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,2),new ItemStack(Item.ingotIron,2)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondPickaxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,2),new ItemStack(BTWItems.ironNugget,12)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondAxe)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,1),new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondShovel)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,1),new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondHoe)});

		RecipeManager.addSoulforgeRecipe(new ItemStack(RefamishedItems.compound_arbalest),
				new Object[]{
						"SIS",
						"SGS",
						"FLF",
						" S ",
						Character.valueOf('I'), new ItemStack(Item.ingotIron, 1),
						Character.valueOf('S'), new ItemStack(RefamishedItems.steelIngot, 1),
						Character.valueOf('F'), new ItemStack(BTWItems.hempFibers, 1),
						Character.valueOf('L'), new ItemStack(Block.lever, 1),
						Character.valueOf('G'), new ItemStack(BTWItems.gear, 1),
				});

		addCokeOvenRecipe(Item.coal,RefamishedItems.coke_coal,7,2);
		addCokeOvenRecipe(RefamishedItems.cowhide,Item.leather,2,1);
		addCokeOvenRecipe(RefamishedItems.cowhide_prepared,Item.leather,2,1);
		addCokeOvenRecipe(new ItemStack(Block.wood),new ItemStack(RefamishedItems.pile_ashes,12),5,2);
		addCokeOvenRecipe(RefamishedItems.soft_clay_brick,RefamishedItems.soft_brick,3,1);
		addCokeOvenRecipe(RefamishedItems.soft_brick,Item.brick,5,1);
		addCokeOvenRecipe(BTWItems.ironNugget,BTWItems.ironOreChunk,9,3);
		addCokeOvenRecipe(Item.goldNugget,BTWItems.goldOreChunk,9,3);
		addCokeOvenRecipe(RefamishedItems.copperNugget,RefamishedItems.copperChunk,9,2);

		//addPercentageSmeltingRecipe(new  ItemStack(RefamishedItems.cobaltzureNugget,27),new ItemStack(RefamishedItems.cobaltzureSword));

		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,0));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,1));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,2));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,3));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,4));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,5));

		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(Item.slimeBall)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(BTWItems.glue)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(BTWItems.nitre,28),new ItemStack(Item.slimeBall)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
				{new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
						,new ItemStack(BTWItems.nitre,22),new ItemStack(BTWItems.glue)});

		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]
				{new ItemStack(Item.ingotIron),new ItemStack(RefamishedItems.scorched_flux,12),new ItemStack(RefamishedItems.coke_coal,2)});

		List<Object[]> toolBindings = Arrays.asList(
				new Object[]{Item.silk},
				new Object[]{BTWItems.sinew},
				new Object[]{BTWItems.hempFibers},
				new Object[]{RefamishedItems.wool_string,1, Short.MAX_VALUE}
		);

		for (Object[] tool : toolBindings) {
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(Item.axeStone), // Tool output
					Arrays.asList(
							new ItemStack(Item.stick), // Stick handle
							new ItemStack(RefamishedItems.chippedAxeHead), // Chipped tool head
							new ItemStack((Item) tool[0]) // Binding material
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(Item.shovelStone), // Tool output
					Arrays.asList(
							new ItemStack(Item.stick), // Stick handle
							new ItemStack(RefamishedItems.chippedShovelHead), // Chipped tool head
							new ItemStack((Item) tool[0]) // Binding material
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(Item.hoeStone), // Tool output
					Arrays.asList(
							new ItemStack(Item.stick), // Stick handle
							new ItemStack(RefamishedItems.chippedHoeHead), // Chipped tool head
							new ItemStack((Item) tool[0]) // Binding material
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(Item.pickaxeStone), // Tool output
					Arrays.asList(
							new ItemStack(Item.stick), // Stick handle
							new ItemStack(RefamishedItems.chippedPickaxeHead), // Chipped tool head
							new ItemStack((Item) tool[0]) // Binding material
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BoneClubRecipe(
					new ItemStack(RefamishedItems.bone_club_assembling,1,RefamishedItems.bone_club_assembling.getMaxDamage()), // Tool output
					Arrays.asList(
							new ItemStack(RefamishedItems.club_component,1,4),
							new ItemStack(RefamishedItems.club_component,1,5),
							new ItemStack((Item) tool[0])
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BoneClubRecipe(
					new ItemStack(RefamishedItems.heavy_club_assembling,1,RefamishedItems.heavy_club_assembling.getMaxDamage()), // Tool output
					Arrays.asList(
							new ItemStack(RefamishedItems.club_component,1,4),
							new ItemStack(RefamishedItems.club_component,1,5),
							new ItemStack((Item) tool[0]),
							new ItemStack(BTWItems.sharpStone),
							new ItemStack(BTWItems.sharpStone)
					)
			));
			CraftingManager.getInstance().getRecipeList().add(new BoneClubRecipe(
					new ItemStack(RefamishedItems.flint_machete_assembling,1,RefamishedItems.flint_machete_assembling.getMaxDamage()), // Tool output
					Arrays.asList(
							new ItemStack(RefamishedItems.club_component,1,5),
							new ItemStack((Item) tool[0]),
							new ItemStack(RefamishedItems.flintBlade),
							new ItemStack(RefamishedItems.flintBlade)
					)
			));
			RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bow_stringing),
					new Object[]{
							new ItemStack(Item.stick),
							new ItemStack((Item) tool[0]),
							new ItemStack((Item) tool[0]),
							new ItemStack((Item) tool[0]),
					});
			RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bow_stringingflint),
					new Object[]{
							new ItemStack(Item.stick),
							new ItemStack(RefamishedItems.flint_bow_igniter),
							new ItemStack((Item) tool[0]),
							new ItemStack((Item) tool[0]),
							new ItemStack((Item) tool[0]),
					});
		}
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.flint_bow_igniter ), new Object[] {
				"F",
				"F",
				"F",
				Character.valueOf( 'F' ), Item.flint,
		} );

		for (int i = 0; i < 4; i++) {
			Item getAssembling = RefamishedItems.wooden_club_assembling[i];
			RecipeManager.addShapelessRecipe(new ItemStack(getAssembling,1,getAssembling.getMaxDamage()),
					new Object[]{
							new ItemStack(RefamishedItems.club_component,1,5),
							new ItemStack(RefamishedItems.club_component,1,i),
					});
			Item getAssemblingDeath = RefamishedItems.death_club_assembling[i];
			RecipeManager.addShapelessRecipe(new ItemStack(getAssemblingDeath,1,getAssemblingDeath.getMaxDamage()),
					new Object[]{
							new ItemStack(RefamishedItems.club_component,1,5),
							new ItemStack(RefamishedItems.club_component,1,i),
							new ItemStack(RefamishedItems.rib),
							new ItemStack(RefamishedItems.rib)
					});
			RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.club_component,1,i),
					new Object[]{
							new ItemStack(BTWItems.bark,1, i),
							new ItemStack(BTWItems.bark,1, i)
					});
		}
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.club_component,1,4),
				new Object[]{
						new ItemStack(Item.bone),
						new ItemStack(Item.bone),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.club_component,1,5),
				new Object[]{
						new ItemStack(BTWItems.pointyStick),
						new ItemStack(Item.stick),
				});

		CraftingManager.getInstance().getRecipeList().add(new WoolCutting(
				new ItemStack(RefamishedItems.flint_sharpenerWool,1,RefamishedItems.flint_sharpenerWool.getMaxDamage()), // Tool output
				Arrays.asList(
						new ItemStack(BTWItems.wool),
						new ItemStack(RefamishedItems.flint_sharpener)
				)
		));
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.flint_sharpener ), new Object[] {
				"FF",
				"S ",
				Character.valueOf( 'F' ), RefamishedItems.flintBlade,
				Character.valueOf( 'S' ), Item.stick,
		} );
		CraftingManager.getInstance().getRecipeList().add(new FlintWorkingRecipe(
				new ItemStack(RefamishedItems.flint_sharpenerStone,1,RefamishedItems.flint_sharpenerStone.getMaxDamage()), // Tool output
				Arrays.asList(
						new ItemStack(BTWItems.stone,1, Short.MAX_VALUE),
						new ItemStack(RefamishedItems.flint_sharpener)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new FlintWorkingRecipe(
				new ItemStack(RefamishedItems.flint_sharpenerFlint,1,RefamishedItems.flint_sharpenerFlint.getMaxDamage()), // Tool output
				Arrays.asList(
						new ItemStack(Item.flint),
						new ItemStack(RefamishedItems.flint_sharpener)
				)
		));

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.chippingHoeHead,1,RefamishedItems.chippingHoeHead.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.sharpStone, 1, 0),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.chippingShovelHead,1,RefamishedItems.chippingShovelHead.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.chippingAxeHead,1,RefamishedItems.chippingAxeHead.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.chippingPickaxeHead,1,RefamishedItems.chippingPickaxeHead.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
						new ItemStack(BTWItems.sharpStone, 1, 0),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.flintKnapping,1,RefamishedItems.flintKnapping.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(Item.flint, 1),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.stoneSharpening,1,RefamishedItems.stoneSharpening.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
				});

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.flint_bow_igniter ), new Object[] {
				"F",
				"F",
				"F",
				Character.valueOf( 'F' ), Item.flint,
		} );

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.crossbow ), new Object[] {
				"MIM",
				"FGF",
				" M ",
				Character.valueOf( 'G' ), BTWItems.gear,
				Character.valueOf( 'F' ), BTWItems.hempFibers,
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'M' ), new ItemStack(BTWItems.woodMouldingStubID, 1, Short.MAX_VALUE),
		} );
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.tar_molotov),
				new Object[]{
						new ItemStack(RefamishedItems.tar_bottle),
						new ItemStack(Item.paper),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.arrow_flint,2),
				new Object[]{
						new ItemStack(Item.flint),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.arrow_gold,2),
				new Object[]{
						new ItemStack(Item.goldNugget),
						new ItemStack(Item.goldNugget),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.arrow,2),
				new Object[]{
						new ItemStack(BTWItems.ironNugget),
						new ItemStack(BTWItems.ironNugget),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_bone,2),
				new Object[]{
						new ItemStack(Item.bone),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_iron,3),
				new Object[]{
						new ItemStack(Item.ingotIron),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});

		RecipeManager.addShapelessRecipe(new ItemStack(Item.helmetLeather),
				new Object[]{
						new ItemStack(BTWItems.cutLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.bootsLeather),
				new Object[]{
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.legsLeather),
				new Object[]{
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.plateLeather),
				new Object[]{
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
						new ItemStack(BTWItems.cutLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.tannedLeatherHelmet),
				new Object[]{
						new ItemStack(BTWItems.cutTannedLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.tannedLeatherBoots),
				new Object[]{
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.tannedLeatherLeggings),
				new Object[]{
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.tannedLeatherChest),
				new Object[]{
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
						new ItemStack(BTWItems.cutTannedLeather),
				});

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.cowhide_prepared),
				new Object[]{
						new ItemStack(RefamishedItems.cowhide),
						new ItemStack(RefamishedItems.pile_ashes),
						new ItemStack(RefamishedItems.pile_ashes),
						new ItemStack(RefamishedItems.pile_ashes),
				});

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.ironBlade ), new Object[] {
				" I",
				"SI",
				Character.valueOf( 'I' ), BTWItems.ironNugget,
				Character.valueOf( 'S' ), Item.stick,
		} );
		RecipeManager.addRecipe( new ItemStack( Item.bucketEmpty ), new Object[] {
				"I I",
				"I I",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.copperNugget,
		} );

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.clay_mixing,1,RefamishedItems.clay_mixing.getMaxDamage()),
				new Object[]{
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(BTWItems.dirtPile),
						new ItemStack(BTWItems.dirtPile),
						new ItemStack(BTWItems.dirtPile),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.clay_bowl),
				new Object[]{
						new ItemStack(Item.clay),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.wet_clay),
				new Object[]{
						new ItemStack(RefamishedItems.clay_bowl_water),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.soft_clay_brick),
				new Object[]{
						new ItemStack(RefamishedItems.clay_mud),
						new ItemStack(RefamishedItems.clay_mud),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.stone_trowel),
				new Object[]{
						new ItemStack(RefamishedItems.chippedShovelHead),
						new ItemStack(Item.stick),
				});
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.iron_machete ), new Object[] {
				"I",
				"I",
				"S",
				Character.valueOf( 'I' ), Item.ingotIron,
				Character.valueOf( 'S' ), new ItemStack( RefamishedItems.club_component,1,5 ),
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.diamond_machete ), new Object[] {
				"I",
				"I",
				"S",
				Character.valueOf( 'I' ), BTWItems.diamondIngot,
				Character.valueOf( 'S' ), new ItemStack( RefamishedItems.club_component,1,5 ),
		} );


		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolHelmet), new Object[]{"#", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolChest), new Object[]{"##", "##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolLeggings), new Object[]{"##", "# ", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolLeggings), new Object[]{"# ", "##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolBoots), new Object[]{"##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});

		FurnaceRecipes.smelting().addSmelting(RefamishedItems.copperChunk.itemID, new ItemStack(RefamishedItems.copperNugget), 0.0f, 2);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperNugget), RefamishedBlocks.copperOre, (byte)5);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperIngot), RefamishedBlocks.copperChunkStorage, (byte)5);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperNugget), RefamishedBlocks.copperChunkGround, (byte)5);

		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.leatherCuttingFlint,1,RefamishedItems.leatherCuttingFlint.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.leather),
						new ItemStack(RefamishedItems.flintBlade)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.leatherCuttingIron,1,RefamishedItems.leatherCuttingIron.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.leather),
						new ItemStack(RefamishedItems.ironBlade)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.ribCarvingFlint,1,RefamishedItems.ribCarvingFlint.getMaxDamage()),
				Arrays.asList(
						new ItemStack(RefamishedItems.rib_beef),
						new ItemStack(RefamishedItems.flintBlade)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.ribCarvingIron,1,RefamishedItems.ribCarvingIron.getMaxDamage()),
				Arrays.asList(
						new ItemStack(RefamishedItems.rib_beef),
						new ItemStack(RefamishedItems.ironBlade)
				)
		));
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bone_pickaxe),
				new Object[]{
						Item.stick,
						RefamishedItems.rib,
						Item.silk
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bone_pickaxe),
				new Object[]{
						Item.stick,
						RefamishedItems.rib,
						BTWItems.sinew
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bone_pickaxe),
				new Object[]{
						Item.stick,
						RefamishedItems.rib,
						BTWItems.hempFibers
				});
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.rib_beef.itemID, new ItemStack(RefamishedItems.cooked_rib_beef), 0.0f, 1);
		RecipeManager.addStokedCauldronRecipe(new ItemStack(RefamishedItems.rib, 1), new ItemStack[]{new ItemStack(RefamishedItems.rib_beef, 1)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(RefamishedItems.rib, 1), new ItemStack[]{new ItemStack(RefamishedItems.cooked_rib_beef, 1)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(RefamishedItems.rib, 1), new ItemStack[]{new ItemStack(RefamishedItems.cooked_rib_beefPartial, 1)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(RefamishedItems.rib, 1), new ItemStack[]{new ItemStack(RefamishedItems.cooked_rib_beefSpent, 1)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(Item.bucketEmpty)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(Item.bucketLava)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(Item.bucketWater)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(Item.bucketMilk)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(BTWItems.cementBucket)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(BTWItems.milkChocolateBucket)});
		addBuildingBlocksRecipe(RefamishedItems.soft_brick,RefamishedBlocks.softBrickLoose,RefamishedBlocks.softBrickLooseSlab,RefamishedBlocks.softBrickLooseStairs);
	}

	@Inject(method = "addAlternateVanillaRecipes", at = @At("TAIL"),remap = false)
	private static void removeStupidRecipes(CallbackInfo ci){
		//RecipeManager.removeVanillaRecipe(new ItemStack(Item.helmetDiamond), new Object[]{"XXX", "XYX", 'X', BTWItems.diamondIngot, 'Y', BTWItems.diamondArmorPlate});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.unfiredCrudeBrick), new Object[]{new ItemStack(Item.clay)});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.unfiredBrick), new Object[]{new ItemStack(Item.clay)});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.shovelStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), Item.silk});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.shovelStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), BTWItems.hempFibers});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.shovelStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), BTWItems.sinew});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.axeStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), Item.silk});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.axeStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), BTWItems.hempFibers});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.axeStone), new Object[]{Item.stick, new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), BTWItems.sinew});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.pickaxeStone), new Object[]{"XXX", " #S", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), Character.valueOf('S'), Item.silk});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.pickaxeStone), new Object[]{"XXX", " #S", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), Character.valueOf('S'), BTWItems.hempFibers});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.pickaxeStone), new Object[]{"XXX", " #S", " # ", Character.valueOf('#'), Item.stick, Character.valueOf('X'), new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE), Character.valueOf('S'), BTWItems.sinew});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.sharpStone), new Object[]{new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE)});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.bow, 1), new Object[]{"ST ", "S T", "ST ", Character.valueOf('S'), BTWItems.sinew, Character.valueOf('T'), Item.stick});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.arrow, 2), new Object[]{new ItemStack(Item.feather), new ItemStack(Item.stick), new ItemStack(Item.silk), new ItemStack(Item.flint)});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.arrow, 2), new Object[]{new ItemStack(Item.feather), new ItemStack(Item.stick), new ItemStack(BTWItems.hempFibers), new ItemStack(Item.flint)});
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.arrow, 2), new Object[]{new ItemStack(Item.feather), new ItemStack(Item.stick), new ItemStack(BTWItems.sinew), new ItemStack(Item.flint)});
		RecipeManager.removeVanillaRecipe( new ItemStack( Item.bucketEmpty ), new Object[] {
				"I I",
				"I I",
				"III",
				Character.valueOf( 'I' ), BTWItems.ironNugget,
		} );
		RecipeManager.removeVanillaRecipe( new ItemStack( Item.bucketEmpty ), new Object[] {
				"I I",
				"I I",
				"III",
				Character.valueOf( 'I' ), BTWItems.ironNugget,
		} );
		RecipeManager.removeVanillaRecipe( new ItemStack( BTWItems.woodenClub ), new Object[] {
				"I",
				"I",
				Character.valueOf( 'I' ), Item.stick,
		} );
		RecipeManager.removeVanillaRecipe( new ItemStack( BTWItems.boneClub ), new Object[] {
				"I",
				"I",
				Character.valueOf( 'I' ), Item.bone,
		} );
		RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.diamondIngot), new Object[]{new ItemStack(Item.ingotIron), new ItemStack(Item.diamond), new ItemStack(BTWItems.creeperOysters)});
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

	@Unique
	private static void addStokedCrucibleRecipe(ItemStack outputStack, ItemStack[] inputStacks) {
		CrucibleStokedCraftingManager.getInstance().addRecipe(outputStack, inputStacks);
	}

	@Unique
	private static void addCokeOvenRecipe(ItemStack input, ItemStack output, int minFireLevel, int cookingTime)
	{
		CokeOvenSmeltingRecipes.getInstance().addRecipe(
				input,
				output,
				minFireLevel,
				cookingTime
		);
	}

	@Unique
	private static void addPercentageSmeltingRecipe(ItemStack input, ItemStack output)
	{
		PercentageBasedSmelting.getInstance().addRecipe(input, output);
	}
	@Unique
	private static void addPercentageSmeltingRecipe(ItemStack input, ItemStack[] output)
	{
		PercentageBasedSmelting.getInstance().addRecipe(input, output);
	}

	@Unique
	private static void addMillStoneRecipe(ItemStack outputStack, ItemStack inputStack) {
		MillStoneCraftingManager.getInstance().addRecipe(outputStack, inputStack);
	}

	@Unique
	private static void addMillStoneRecipe(ItemStack[] outputStacks, ItemStack[] inputStacks) {
		MillStoneCraftingManager.getInstance().addRecipe(outputStacks, inputStacks);
	}

	@Unique
	private static void addCokeOvenRecipe(Item input, ItemStack output, int minFireLevel, int cookingTime)
	{
		addCokeOvenRecipe(new ItemStack(input),output,minFireLevel,cookingTime);
	}

	@Unique
	private static void addCokeOvenRecipe(ItemStack input, Item output, int minFireLevel, int cookingTime)
	{
		addCokeOvenRecipe(input,new ItemStack(output),minFireLevel,cookingTime);
	}

	@Unique
	private static void addCokeOvenRecipe(Item input, Item output, int minFireLevel, int cookingTime)
	{
		addCokeOvenRecipe(new ItemStack(input),new ItemStack(output),minFireLevel,cookingTime);
	}

	@Unique
	private static void addBuildingBlocksRecipe(Item main, Block block, Block slab, Block stair) {
		RecipeManager.addRecipe( new ItemStack(slab,4), new Object[] {
				"BB",
				Character.valueOf( 'B' ), block,
		} );
		RecipeManager.addRecipe( new ItemStack(block), new Object[] {
				"B",
				"B",
				Character.valueOf( 'B' ), slab,
		} );
		RecipeManager.addRecipe( new ItemStack(stair, 2), new Object[] {
				"B ",
				"BB",
				Character.valueOf( 'B' ), slab,
		} );
		RecipeManager.addRecipe( new ItemStack(stair), new Object[] {
				"B  ",
				"BB ",
				"BBB",
				Character.valueOf( 'B' ), main,
		} );
		RecipeManager.addRecipe( new ItemStack(stair,4), new Object[] {
				"B ",
				"BB",
				Character.valueOf( 'B' ), block,
		} );
		RecipeManager.addRecipe( new ItemStack(stair,8), new Object[] {
				"B  ",
				"BB ",
				"BBB",
				Character.valueOf( 'B' ), block,
		} );
		RecipeManager.addShapelessRecipe(new ItemStack(block),
				new Object[]{
						main,main,main,main,
						main,main,main,main
				});
		RecipeManager.addShapelessRecipe(new ItemStack(slab),
				new Object[]{
						main,main,main,main
				});
		RecipeManager.addShapelessRecipe(new ItemStack(main,8),
				new Object[]{
						block
				});
		RecipeManager.addShapelessRecipe(new ItemStack(main,4),
				new Object[]{
						slab
				});
		RecipeManager.addShapelessRecipe(new ItemStack(main,6),
				new Object[]{
						stair
				});
	}
}
