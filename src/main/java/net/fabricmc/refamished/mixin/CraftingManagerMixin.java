package net.fabricmc.refamished.mixin;

import btw.block.BTWBlocks;
import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.CauldronStokedCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.manager.MillStoneCraftingManager;
import btw.crafting.recipe.CraftingRecipeList;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.BulkRecipe;
import btw.crafting.recipe.types.customcrafting.WoolArmorRecipe;
import btw.item.BTWItems;
import btw.item.tag.Tag;
import btw.item.tag.TagInstance;
import btw.item.tag.TagOrStack;
import btw.util.color.Color;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.items.materials.PigmentItem;
import net.fabricmc.refamished.items.materials.metalSheets;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.items.materials.metallurgyHeads;
import net.fabricmc.refamished.items.tools.flintMachete;
import net.fabricmc.refamished.misc.CustomRecipes.crafting.*;
import net.fabricmc.refamished.misc.CustomRecipes.forging.ArmorCombination;
import net.fabricmc.refamished.misc.Recipes.*;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;

@Mixin(CraftingRecipeList.class)
public abstract class CraftingManagerMixin {
	@Inject(method = "addBlockRecipes",at = @At("TAIL"),remap = false)
	private static void addBlockShittyRecipes(CallbackInfo ci){
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
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.gildedIronBlock ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.gildedIngot,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.sinteredChunkStorage ), new Object[] {
				"III",
				"III",
				"III",
				Character.valueOf( 'I' ), RefamishedItems.sinterIronChunk,
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
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.workbench ), new Object[] {
				"PPP",
				"PIP",
				"PPP",
				Character.valueOf( 'P' ), Block.planks,
				Character.valueOf( 'I' ), Item.ingotIron,
		} );
		RecipeManager.addRecipe( new ItemStack( Block.blockRedstone ), new Object[] {
				"RRR",
				"RHR",
				"RRR",
				Character.valueOf( 'R' ), Item.redstone,
				Character.valueOf( 'H' ), BTWItems.hellfireDust,
		} );

		RecipeManager.addRecipe(new ItemStack(BTWBlocks.lens), new Object[]{"GDG", "G G", "G#G", Character.valueOf('#'), Block.glass, Character.valueOf('G'), RefamishedItems.gildedIngot, Character.valueOf('D'), RefamishedItems.diamondFoil});
		RecipeManager.addRecipe(new ItemStack(BTWBlocks.lens), new Object[]{"G#G", "G G", "GDG", Character.valueOf('#'), Block.glass, Character.valueOf('G'), RefamishedItems.gildedIngot, Character.valueOf('D'), RefamishedItems.diamondFoil});

	}
	@Inject(method = "addItemRecipes", at = @At("TAIL"),remap = false)
	private static void addItemShittyRecipes(CallbackInfo ci){
		CraftingManager ins = CraftingManager.getInstance();
		List recipes = ins.getRecipes();
		recipes.add(new RecipesArmorPigment());

		Set<Item> targets = new HashSet<Item>(Arrays.asList(
				BTWItems.woolHelmet,
				BTWItems.woolChest,
				BTWItems.woolLeggings,
				Item.bow,
				Item.swordIron,Item.swordGold,Item.swordDiamond,
				Item.pickaxeIron,Item.pickaxeGold,Item.pickaxeDiamond,
				Item.axeDiamond,
				Item.shovelIron,Item.shovelGold,Item.shovelDiamond,
				Item.hoeIron,Item.hoeGold,Item.hoeDiamond,
				Item.shears,
				BTWItems.diamondShears,
				BTWItems.ironChisel,
				BTWItems.diamondChisel,
				BTWItems.diamondArmorPlate,
				Item.helmetDiamond,Item.plateDiamond,Item.legsDiamond,Item.bootsDiamond
		));

		Iterator<IRecipe> it = CraftingManager.getInstance().getRecipeList().iterator();

		while (it.hasNext()) {
			IRecipe recipe = it.next();
			ItemStack output = recipe.getRecipeOutput();
			if (output != null && targets.contains(output.getItem())) {
				it.remove();
			}
		}

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
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.gildedIngot,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.gildedIronBlock,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.sinterIronChunk,9 ), new Object[] {
				"I",
				Character.valueOf( 'I' ), RefamishedBlocks.sinteredChunkStorage,
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
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.sinterIronChunk),
				new Object[]{
						new ItemStack(RefamishedItems.sinterIronDust, 1),
						new ItemStack(RefamishedItems.sinterIronDust, 1),
				});

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
					Character.valueOf( 'I' ), RefamishedItems.copperIngot,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.plateCopper ), new Object[] {
					"L L",
					"LIL",
					"III",
					Character.valueOf( 'I' ), RefamishedItems.copperIngot,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.legsCopper ), new Object[] {
					"LIL",
					"I I",
					"L L",
					Character.valueOf( 'I' ), RefamishedItems.copperIngot,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
			RecipeManager.addRecipe( new ItemStack( RefamishedItems.bootsCopper ), new Object[] {
					"L L",
					"I I",
					Character.valueOf( 'I' ), RefamishedItems.copperIngot,
					Character.valueOf( 'L' ), new ItemStack((Item) tool[0]),
			} );
		}

		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.copperChisel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.copperSword,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 15), new ItemStack[]{new ItemStack(RefamishedItems.copperPickaxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.copperAxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperShovel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperHoe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.helmetCopper,1 , Short.MAX_VALUE),});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 20), new ItemStack[]{new ItemStack(RefamishedItems.plateCopper,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 15), new ItemStack[]{new ItemStack(RefamishedItems.legsCopper,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.bootsCopper,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 10), new ItemStack[]{new ItemStack(RefamishedItems.copperShears,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronSword,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronPickaxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronAxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronShovel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.rustIronHoe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.rustIronMachete,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.helmetRustIron,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.plateRustIron,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.legsRustIron,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.bootsRustIron,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperSword,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 3), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperPickaxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 2), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperAxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperShovel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 1), new ItemStack[]{new ItemStack(RefamishedItems.corrodedCopperHoe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.ingotIron, 2), new ItemStack[]{new ItemStack(RefamishedItems.iron_machete,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperIngot, 2), new ItemStack[]{new ItemStack(RefamishedItems.copper_machete,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(Item.diamond), new ItemStack[]{new ItemStack(RefamishedItems.diamondFoil,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]{new ItemStack(RefamishedItems.diamond_machete,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(RefamishedItems.steelIngot,4),new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.compound_arbalest,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 16), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronSword,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 24), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronPickaxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 16), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronAxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronShovel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedIronHoe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedIngot, 2), new ItemStack[]{new ItemStack(RefamishedItems.gilded_machete,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 16), new ItemStack[]{new ItemStack(RefamishedItems.gildedShears,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copper_trowel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 6), new ItemStack[]{new ItemStack(RefamishedItems.iron_trowel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedIngot), new ItemStack[]{new ItemStack(Item.ingotIron),new ItemStack(Item.goldNugget,4)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,1),new ItemStack(BTWItems.ironNugget,12)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondSword,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,1),new ItemStack(Item.ingotIron,2)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondPickaxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(Item.diamond,1),new ItemStack(BTWItems.ironNugget,12)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondAxe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondShovel,1, Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack[]{new ItemStack(BTWItems.ironNugget,6)}, new ItemStack[]{new ItemStack(RefamishedItems.tipDiamondHoe,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 4), new ItemStack[]{new ItemStack(RefamishedItems.gildedChisel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedHammer,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 8), new ItemStack[]{new ItemStack(RefamishedItems.gildedTongs,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperTongs,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.gildedNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.gildedTrowel,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]{new ItemStack(RefamishedItems.steelTongs,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 6), new ItemStack[]{new ItemStack(RefamishedItems.ironTongs,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.copperNugget, 5), new ItemStack[]{new ItemStack(RefamishedItems.copperHammer,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 6), new ItemStack[]{new ItemStack(RefamishedItems.ironHammer,1 , Short.MAX_VALUE)});
		RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]{new ItemStack(RefamishedItems.steelHammer,1 , Short.MAX_VALUE)});

		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 1), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherHelmet, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherChest, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherLeggings, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 1), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherBoots, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 1), new ItemStack[]{new ItemStack(Item.helmetLeather, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(Item.plateLeather, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(Item.legsLeather, 1, Short.MAX_VALUE)});
		RecipeManager.addStokedCauldronRecipe(new ItemStack(BTWItems.glue, 1), new ItemStack[]{new ItemStack(Item.bootsLeather, 1, Short.MAX_VALUE)});

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

		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(Item.leather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutTannedLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.tannedLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutScouredLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.scouredLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.leatherStrap, 4), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.cutTannedLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.silk, 2), new Object[]{new ItemStack(BTWItems.tangledWeb), new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE)});

		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutLeather, 2), new Object[]{new ItemStack(RefamishedItems.gildedShears, 1, Short.MAX_VALUE), new ItemStack(Item.leather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutTannedLeather, 2), new Object[]{new ItemStack(RefamishedItems.gildedShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.tannedLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutScouredLeather, 2), new Object[]{new ItemStack(RefamishedItems.gildedShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.scouredLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.leatherStrap, 4), new Object[]{new ItemStack(RefamishedItems.gildedShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.cutTannedLeather)});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.silk, 2), new Object[]{new ItemStack(BTWItems.tangledWeb), new ItemStack(RefamishedItems.gildedShears, 1, Short.MAX_VALUE)});

		//System.out.println(RefamishedItems.horsehide);
		//System.out.println(RefamishedItems.horsehide_prepared);
		addCokeOvenRecipe(new ItemStack(Item.coal,1,0),RefamishedItems.coke_coal,7,2);
		addCokeOvenRecipe(new ItemStack(Item.coal,1,1),RefamishedItems.coke_coal,7,2);
		addCokeOvenRecipe(RefamishedItems.cowhide,Item.leather,2,1);
		addCokeOvenRecipe(RefamishedItems.cowhide_prepared,Item.leather,2,1);
		for (int i = 0; i < 7; i++) {
			addCokeOvenRecipe(new ItemStack(RefamishedItems.horsehide,1,i),Item.leather,2,1);
			addCokeOvenRecipe(new ItemStack(RefamishedItems.horsehide_prepared,1,i),Item.leather,2,1);
			RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.horsehide_prepared,1,i),
					new Object[]{
							new ItemStack(RefamishedItems.horsehide,1,i),
							new ItemStack(RefamishedItems.pile_ashes),
							new ItemStack(RefamishedItems.pile_ashes),
							new ItemStack(RefamishedItems.pile_ashes),
					});
		}
		for (int i = 0; i < 4; i++) {
			addCokeOvenRecipe(new ItemStack(Block.wood,1,i),new ItemStack(RefamishedItems.pile_ashes,12),5,2);
		}
		addCokeOvenRecipe(RefamishedItems.soft_clay_brick,new ItemStack(RefamishedItems.soft_brick,2),3,1);
		addCokeOvenRecipe(RefamishedItems.soft_brick,Item.brick,5,1);
		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,0), new ItemStack(RefamishedItems.ingotPreparation,1,2), 15,2);
		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,1), new ItemStack(RefamishedItems.ingotPreparation,1,2), 15,2);

		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,3), new ItemStack(RefamishedItems.ingotPreparation,1,5), 15,2);
		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,4), new ItemStack(RefamishedItems.ingotPreparation,1,5), 15,2);

		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,6), new ItemStack(RefamishedItems.ingotPreparation,1,8), 15,2);
		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,7), new ItemStack(RefamishedItems.ingotPreparation,1,8), 15,2);

		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,9), new ItemStack(RefamishedItems.ingotPreparation,1,5), 15,2);
		addCokeOvenRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,10), new ItemStack(RefamishedItems.ingotPreparation,1,5), 15,2);
		addCokeOvenRecipe(new ItemStack(Block.coalBlock), new ItemStack(RefamishedBlocks.cokeBlock), 99,2);

		addSmithingRecipe(new ItemStack(RefamishedItems.rawIngot,1,0),new ItemStack[]{new ItemStack(RefamishedItems.copperNugget,3), new ItemStack(BTWItems.stone,3,1)},65);
		addSmithingRecipe(new ItemStack(RefamishedItems.rawIngot,1,1),new ItemStack[]{new ItemStack(BTWItems.ironNugget,3), new ItemStack(BTWItems.stone,3,1)},55);
		addSmithingRecipe(new ItemStack(RefamishedItems.rawIngot,1,2),new ItemStack[]{new ItemStack(Item.goldNugget,3), new ItemStack(BTWItems.stone,3,1)},45);
		addSmithingRecipe(new ItemStack(RefamishedItems.copperChunk,1),new ItemStack[]{new ItemStack(RefamishedItems.crushedOres,2,0)},30);
		addSmithingRecipe(new ItemStack(BTWItems.ironOreChunk,1),new ItemStack[]{new ItemStack(RefamishedItems.crushedOres,2,1)},30);
		addSmithingRecipe(new ItemStack(BTWItems.goldOreChunk,1),new ItemStack[]{new ItemStack(RefamishedItems.crushedOres,2,2)},30);
		addSmithingRecipe(new ItemStack(RefamishedItems.sinterIronChunk,1),new ItemStack[]{new ItemStack(RefamishedItems.crushedOres,2,3)},30);

		//addPercentageSmeltingRecipe(new  ItemStack(RefamishedItems.cobaltzureNugget,27),new ItemStack(RefamishedItems.cobaltzureSword));

		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,0));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,1));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,2),new ItemStack(RefamishedItems.scorched_rock, 1,2));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,3));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,4));
		addMillStoneRecipe(new ItemStack(RefamishedItems.scorched_flux,3),new ItemStack(RefamishedItems.scorched_rock, 1,5));
		addMillStoneRecipe(new ItemStack(BTWItems.ironOrePile,2),new ItemStack(BTWItems.ironOreChunk));
		addMillStoneRecipe(new ItemStack(BTWItems.goldOrePile,2),new ItemStack(BTWItems.goldOreChunk));
		addMillStoneRecipe(new ItemStack(RefamishedItems.sinterIronDust,2),new ItemStack(RefamishedItems.sinterIronChunk));
		addMillStoneRecipe(new ItemStack(RefamishedItems.copperDust,2),new ItemStack(RefamishedItems.copperChunk));
		addMillStoneRecipe(new ItemStack(Item.dyePowder,4, Color.WHITE.colorID),new ItemStack(RefamishedItems.rib));
		addMillStoneRecipe(new ItemStack(RefamishedItems.berrySeeds,1, 0),new ItemStack(RefamishedItems.berries,1,0));
		addMillStoneRecipe(new ItemStack(RefamishedItems.berrySeeds,1, 1),new ItemStack(RefamishedItems.berries,1,1));
		addMillStoneRecipe(new ItemStack(RefamishedItems.berrySeeds,1, 2),new ItemStack(RefamishedItems.berries,1,2));
		addMillStoneRecipe(new ItemStack(RefamishedItems.berrySeeds,1, 3),new ItemStack(RefamishedItems.berries,1,3));

		RecipeManager.addStokedCauldronRecipe(new ItemStack(RefamishedItems.sinterIronChunk), new ItemStack[]
				{new ItemStack(BTWItems.ironOrePile),new ItemStack(BTWItems.coalDust)});

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
							new ItemStack(Item.stick),
							new ItemStack(RefamishedItems.chippedAxeHead),
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
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(RefamishedItems.bone_pickaxe),
					Arrays.asList(
							new ItemStack(Item.stick),
							new ItemStack(RefamishedItems.rib),
							new ItemStack((Item) tool[0])
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
			CraftingManager.getInstance().getRecipeList().add(new BindingToolRecipe(
					new ItemStack(RefamishedItems.stoneHammer),
					Arrays.asList(
							new ItemStack(Item.stick),
							new ItemStack(RefamishedItems.chippedHammerHead),
							new ItemStack((Item) tool[0])
					)
			));
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
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.chippingHammerHead,1,RefamishedItems.chippingHammerHead.getMaxDamage()),
				new Object[]{
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
						new ItemStack(BTWItems.stone, 1, Short.MAX_VALUE),
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
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.arrow_flint,2),
				new Object[]{
						new ItemStack(Item.flint),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.arrow_gold,2),
				new Object[]{
						new ItemStack(Item.goldNugget),
						new ItemStack(Item.goldNugget),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(Item.arrow,2),
				new Object[]{
						new ItemStack(BTWItems.ironNugget),
						new ItemStack(BTWItems.ironNugget),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_bone,2),
				new Object[]{
						new ItemStack(Item.bone),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(Item.silk),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_bone,3),
				new Object[]{
						new ItemStack(RefamishedItems.rib),
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
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_bone,2),
				new Object[]{
						new ItemStack(Item.bone),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_bone,3),
				new Object[]{
						new ItemStack(RefamishedItems.rib),
						new ItemStack(Item.stick),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.bolt_iron,3),
				new Object[]{
						new ItemStack(Item.ingotIron),
						new ItemStack(Item.feather),
						new ItemStack(BTWItems.hempFibers),
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
		PigmentItem pigment = (PigmentItem) RefamishedItems.pigment;
		int i;
		for (i = 0; i < 15; ++i) {
			RecipeManager.addCauldronRecipe(new ItemStack(Block.cloth, 8, BlockColored.getBlockFromDye(i)), new ItemStack[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(Block.cloth, 8, 0)});
			RecipeManager.addCauldronRecipe(new ItemStack(BTWBlocks.woolSlab, 16, BlockColored.getBlockFromDye(i)), new ItemStack[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(BTWBlocks.woolSlab, 16, 0)});
			RecipeManager.addCauldronRecipe(new ItemStack(BTWItems.wool, 32, i), new ItemStack[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(BTWItems.wool, 32, 15)});
			RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.candle, 1, i), new Object[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(BTWItems.candle, 1, 16)});
			RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.vase, 1, BlockColored.getBlockFromDye(i)), new Object[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(Item.itemsList[BTWBlocks.vase.blockID], 1, 0)});
			RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.woolSlab, 1, BlockColored.getBlockFromDye(i)), new Object[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(Item.itemsList[BTWBlocks.woolSlab.blockID], 1, 0)});
			RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.wool, 1, i), new Object[]{new ItemStack(RefamishedItems.pigment, 1, i), new ItemStack(BTWItems.wool, 1, 15)});
			RecipeManager.addShapelessRecipe(new ItemStack(Block.cloth, 1, BlockColored.getDyeFromBlock(i)), new Object[] {new ItemStack(pigment, 1, i), new ItemStack(Item.itemsList[Block.cloth.blockID], 1, 0)});
			RecipeManager.addRecipe(new ItemStack(Block.stainedClay, 8, BlockColored.getDyeFromBlock(i)), new Object[]{"###", "#X#", "###", Character.valueOf('#'), new ItemStack(Block.hardenedClay), Character.valueOf('X'), new ItemStack(pigment, 1, i)});
			RecipeManager.addRecipe(new ItemStack(Block.carpet, 3, i), new Object[] {"##", Character.valueOf('#'), new ItemStack(Block.cloth, 1, i)});
		}
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 9), new Object[]{new ItemStack(pigment, 1, 1), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 14), new Object[]{new ItemStack(pigment, 1, 1), new ItemStack(pigment, 1, 11)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 10), new Object[]{new ItemStack(pigment, 1, 2), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 8), new Object[]{new ItemStack(pigment, 1, 0), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 7), new Object[]{new ItemStack(pigment, 1, 8), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 3, 7), new Object[]{new ItemStack(pigment, 1, 0), new ItemStack(pigment, 1, 15), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 12), new Object[]{new ItemStack(pigment, 1, 4), new ItemStack(pigment, 1, 15)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 6), new Object[]{new ItemStack(pigment, 1, 4), new ItemStack(pigment, 1, 2)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 5), new Object[]{new ItemStack(pigment, 1, 4), new ItemStack(pigment, 1, 1)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 2, 13), new Object[]{new ItemStack(pigment, 1, 5), new ItemStack(pigment, 1, 9)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 3, 13), new Object[]{new ItemStack(pigment, 1, 4), new ItemStack(pigment, 1, 1), new ItemStack(pigment, 1, 9)});
		RecipeManager.addShapelessRecipe(new ItemStack(pigment, 4, 13), new Object[]{new ItemStack(pigment, 1, 4), new ItemStack(pigment, 1, 1), new ItemStack(pigment, 1, 1), new ItemStack(pigment, 1, 15)});

		//FurnaceRecipes.smelting().addSmelting(RefamishedItems.copperChunk.itemID, new ItemStack(RefamishedItems.copperNugget), 0.0f, 2);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperNugget), RefamishedBlocks.copperOre, (byte)5);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperIngot), RefamishedBlocks.copperChunkStorage, (byte)5);
		RecipeManager.addKilnRecipe(new ItemStack(RefamishedItems.copperNugget), RefamishedBlocks.copperChunkGround, (byte)5);

		RecipeManager.addKilnRecipe(new ItemStack(Item.ingotIron), RefamishedBlocks.sinteredChunkStorage, (byte)5);
		RecipeManager.addKilnRecipe(new ItemStack(BTWItems.ironNugget), RefamishedBlocks.sinteredChunkGround, (byte)5);

		addForPlanRecipes(RefamishedItems.copperIngot,RefamishedItems.copperNugget,5,"copper",1);
		addForPlanRecipes(Item.ingotIron,BTWItems.ironNugget,7,"iron",2);
		addForPlanRecipes(Item.ingotGold,Item.goldNugget,4,"gold",2);
		addForPlanRecipes(BTWItems.diamondIngot,null,9,"diamond",3);
		addForPlanRecipes(RefamishedItems.gildedIngot,RefamishedItems.gildedNugget,6,"gilded",2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_hammer")), 75,1);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperNugget,5)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_chisel")), 75,1);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("copper_chisel")), 150,1);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedNugget,5)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_chisel")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("gilded_chisel")), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("tong_copper")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_hammer")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(BTWItems.ironNugget,5)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_chisel")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("iron_chisel")), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("tong_iron")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.steelIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("tong_steel")), 75,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("tong_gilded")), 75,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_hammer")), 75,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_sword")),new ItemStack(RefamishedItems.diamondFoil,2)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_sword")), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_pickaxe")),new ItemStack(RefamishedItems.diamondFoil,3)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_pickaxe")), 300,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_axe")),new ItemStack(RefamishedItems.diamondFoil,2)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_axe")), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_shovel")),new ItemStack(RefamishedItems.diamondFoil)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_shovel")), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_hoe")),new ItemStack(RefamishedItems.diamondFoil)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_hoe")), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.diamond)),
				new ItemStack(RefamishedItems.diamondFoil), 200,2);

		ItemStack copperRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_rivet"));
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_crossguard"))),
				new ItemStack(RefamishedItems.copperSword), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperPickaxe), 200);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperAxe), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperShovel), 125);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperHoe), 125);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_machete"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copper_machete), 200);

		ItemStack ironRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_rivet"));
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_crossguard"))),
				new ItemStack(Item.swordIron), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(Item.pickaxeIron), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(Item.axeIron), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(Item.shovelIron), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(Item.hoeIron), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_machete"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.iron_machete), 200,2);

		ItemStack goldRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_rivet"));
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_crossguard"))),
				new ItemStack(Item.swordGold), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),goldRivet),
				new ItemStack(Item.pickaxeGold), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),goldRivet),
				new ItemStack(Item.axeGold), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),goldRivet),
				new ItemStack(Item.shovelGold), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),goldRivet),
				new ItemStack(Item.hoeGold), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gold_machete"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),goldRivet),
				new ItemStack(RefamishedItems.gold_machete), 200,2);

		ItemStack gildedRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_rivet"));
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_crossguard"))),
				new ItemStack(RefamishedItems.gildedIronSword), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedIronPickaxe), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedIronAxe), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedIronShovel), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedIronHoe), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_machete"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gilded_machete), 200,2);

		ItemStack diamondRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_rivet"));
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_crossguard"))),
				new ItemStack(Item.swordDiamond), 150,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(Item.pickaxeDiamond), 200,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(Item.axeDiamond), 150,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(Item.shovelDiamond), 125,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(Item.hoeDiamond), 125,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_machete"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(RefamishedItems.diamond_machete), 200,3);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_sword"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_crossguard"))),
				new ItemStack(RefamishedItems.tipDiamondSword), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_pickaxe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.tipDiamondPickaxe), 200,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_axe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.tipDiamondAxe), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_shovel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.tipDiamondShovel), 125,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tip_hoe"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.tipDiamondHoe), 125,2);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_copper")),
						new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_copper"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperTongs), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_iron")),
						new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_iron"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.ironTongs), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_steel")),
						new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_steel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.steelTongs), 150,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_gilded")),
						new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_gilded"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedTongs), 150,2);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_hammer"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperHammer), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_hammer"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.ironHammer), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("steel_hammer"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.steelHammer), 150,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_hammer"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedHammer), 150,2);

		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron),new ItemStack(BTWItems.cutLeather)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_shear_body")), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("iron_shear_razor")), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_shear_body"))
						,new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("iron_shear_razor")),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(Item.shears), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot),new ItemStack(BTWItems.cutLeather)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_shear_body")), 100);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("copper_shear_razor")), 100);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_shear_body"))
						,new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("copper_shear_razor")),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copperShears), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot),new ItemStack(BTWItems.cutLeather)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_shear_body")), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("gilded_shear_razor")), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_shear_body"))
						,new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("gilded_shear_razor")),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedShears), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(BTWItems.diamondIngot),new ItemStack(BTWItems.cutLeather)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_shear_body")), 175,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(BTWItems.diamondIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("diamond_shear_razor")), 175,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_shear_body"))
						,new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("diamond_shear_razor")),new ItemStack(RefamishedItems.sugar_resin),diamondRivet),
				new ItemStack(BTWItems.diamondShears), 175,3);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("steel_hammer"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.steelHammer), 150,3);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_trowel")), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_trowel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),copperRivet),
				new ItemStack(RefamishedItems.copper_trowel), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_trowel")), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_trowel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),ironRivet),
				new ItemStack(RefamishedItems.iron_trowel), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.gildedIngot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_trowel")), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_trowel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),gildedRivet),
				new ItemStack(RefamishedItems.gildedTrowel), 150,2);

		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("copper_chisel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(RefamishedItems.copperChisel), 150);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("iron_chisel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(BTWItems.ironChisel), 150,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("diamond_chisel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(BTWItems.diamondChisel), 150,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("gilded_chisel"))
						,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(RefamishedItems.gildedChisel), 150,2);

		//ForgingPlansRecipes.getInstance().addRecipe(new ArmorCombination(Arrays.asList(new ItemStack(RefamishedItems.sheet),new ItemStack(RefamishedItems.sheet)), 500, 1));

		ForgingPlansRecipes.getInstance().getRecipeList().add(new ArmorCombination());
		Item[] armor = new Item[] {
				Item.helmetIron,Item.plateIron,Item.legsIron,Item.bootsIron,
				Item.helmetGold,Item.plateGold,Item.legsGold,Item.bootsGold,
				Item.helmetDiamond,Item.plateDiamond,Item.legsDiamond,Item.bootsDiamond,
				RefamishedItems.steelHelmet,RefamishedItems.steelChestplate,RefamishedItems.steelLeggings,RefamishedItems.steelBoots,
				RefamishedItems.helmetCopperWhole,RefamishedItems.plateCopperWhole,RefamishedItems.legsCopperWhole,RefamishedItems.bootsCopperWhole,
		};
		String[] materialArmor = new String[]{"iron","gold","diamond","steel","copper"};
		int[] materialHardness = new int[]{13,20,17,11,12};
		int[] forgeLevel = new int[]{2,2,3,3,1};
		String[] heads = new String[]{"helm","coif","pauldrons","cuirass","tassets","greaves","threads","sabatons"};
		int[] materialCost = new int[]{2,3,3,5,4,3,2,2};
		int[] materialHard = new int[]{4,5,5,7,5,5,4,4};
		for (int ih = 0; ih < materialArmor.length; ih++) {
			String name_ = materialArmor[ih];
			int hardnessSet = materialHardness[ih];
			int forgeLvl = forgeLevel[ih];
			int sheetId = metalSheets.getPartIndex(name_);
			for (int jh = 0; jh < heads.length; jh++) {
				String part = heads[jh];
				int cost = materialCost[jh];
				int hardness = materialHard[jh];
				addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.sheet,cost,sheetId)),
						new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_"+part)), hardness*hardnessSet,forgeLvl);
			}
			addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_helm")),
							new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_coif")),
							new ItemStack(RefamishedItems.sugar_resin)),
					new ItemStack(armor[(ih*4)]), 7*hardnessSet,forgeLvl);
			addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_pauldrons")),
							new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_cuirass")),
							new ItemStack(RefamishedItems.sugar_resin)),
					new ItemStack(armor[(ih*4)+1]), 10*hardnessSet,forgeLvl);
			addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_tassets")),
							new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_greaves")),
							new ItemStack(RefamishedItems.sugar_resin)),
					new ItemStack(armor[(ih*4)+2]), 8*hardnessSet,forgeLvl);
			addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_threads")),
							new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_sabatons")),
							new ItemStack(RefamishedItems.sugar_resin)),
					new ItemStack(armor[(ih*4)+3]), 6*hardnessSet,forgeLvl);
		}
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.copperIngot)),
				new ItemStack(RefamishedItems.sheet,1,0), 100,1);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotIron)),
				new ItemStack(RefamishedItems.sheet,1,1), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(Item.ingotGold)),
				new ItemStack(RefamishedItems.sheet,1,2), 100,2);
		addForgePlanRecipe(Arrays.asList(new ItemStack(BTWItems.diamondIngot)),
				new ItemStack(RefamishedItems.sheet,1,4), 100,3);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.steelIngot)),
				new ItemStack(RefamishedItems.sheet,1,5), 100,3);

		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,0), new ItemStack(RefamishedItems.ingotPreparation,1,2), 3);
		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,1), new ItemStack(RefamishedItems.ingotPreparation,1,2), 3);

		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,3), new ItemStack(RefamishedItems.ingotPreparation,1,5), 3);
		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,4), new ItemStack(RefamishedItems.ingotPreparation,1,5), 3);

		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,6), new ItemStack(RefamishedItems.ingotPreparation,1,8), 3);
		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,7), new ItemStack(RefamishedItems.ingotPreparation,1,8), 3);

		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,9), new ItemStack(RefamishedItems.ingotPreparation,1,5), 3);
		addMetaFurnaceRecipe(new ItemStack(RefamishedItems.ingotPreparation,1,10), new ItemStack(RefamishedItems.ingotPreparation,1,5), 3);

		RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(RefamishedItems.rawIngot,1,0), new ItemStack(RefamishedItems.ingotMold),
				new Object[]{new ItemStack(RefamishedItems.ingotPreparation, 1, 2)});
		RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(RefamishedItems.rawIngot,1,1), new ItemStack(RefamishedItems.ingotMold),
				new Object[]{new ItemStack(RefamishedItems.ingotPreparation, 1, 5)});
		RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(RefamishedItems.rawIngot,1,2), new ItemStack(RefamishedItems.ingotMold),
				new Object[]{new ItemStack(RefamishedItems.ingotPreparation, 1, 8)});
		RecipeManager.addRecipe( new ItemStack( RefamishedItems.UnfiredIngotMold ), new Object[] {
				"CC",
				"CC",
				Character.valueOf( 'C' ), Item.clay,
		} );
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.UnfiredIngotMold.itemID, new ItemStack(RefamishedItems.ingotMold), 0.0f, 1);

		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.copperAnvil ), new Object[] {
				"CCC",
				" C ",
				"CCC",
				Character.valueOf( 'C' ), RefamishedItems.copperIngot,
		} );
		RecipeManager.addRecipe( new ItemStack( RefamishedBlocks.SteelAnvil ), new Object[] {
				"CCC",
				" C ",
				"CCC",
				Character.valueOf( 'C' ), RefamishedItems.steelIngot,
		} );

		RecipeManager.addRecipe( new ItemStack( RefamishedItems.boneRazors ), new Object[] {
				" B",
				"BF",
				Character.valueOf( 'B' ), Item.bone,
				Character.valueOf( 'F' ), RefamishedItems.flintBlade,
		} );

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
		CraftingManager.getInstance().getRecipeList().add(new MacheteWorkingRecipe(
				new ItemStack(RefamishedItems.ribCarvingMachete,1,RefamishedItems.ribCarvingMachete.getMaxDamage()),
				Arrays.asList(
						new ItemStack(RefamishedItems.rib_beef),
						new ItemStack(RefamishedItems.flint_machete)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new MacheteWorkingRecipe(
				new ItemStack(RefamishedItems.leatherCuttingMachete,1,RefamishedItems.leatherCuttingMachete.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.leather),
						new ItemStack(RefamishedItems.flint_machete)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.sugarCaneCuttingFlint,1,RefamishedItems.sugarCaneCuttingFlint.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.reed),
						new ItemStack(Item.reed),
						new ItemStack(RefamishedItems.flintBlade)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new LeatherWorkingRecipe(
				new ItemStack(RefamishedItems.sugarCaneCuttingIron,1,RefamishedItems.sugarCaneCuttingIron.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.reed),
						new ItemStack(Item.reed),
						new ItemStack(RefamishedItems.ironBlade)
				)
		));
		CraftingManager.getInstance().getRecipeList().add(new MacheteWorkingRecipe(
				new ItemStack(RefamishedItems.sugarCaneCuttingMachete,1,RefamishedItems.sugarCaneCuttingMachete.getMaxDamage()),
				Arrays.asList(
						new ItemStack(Item.reed),
						new ItemStack(Item.reed),
						new ItemStack(RefamishedItems.flint_machete)
				)
		));

		CraftingManager.getInstance().getRecipeList().add(new MortarRecipe(
				new ItemStack(RefamishedItems.sugar_cane_crushing,1,RefamishedItems.sugar_cane_crushing.getMaxDamage()),
				Arrays.asList(
						new ItemStack(BTWItems.stone),
						new ItemStack(RefamishedItems.cut_sugar_cane),
						new ItemStack(RefamishedItems.mortar_pestle)
				)
		));

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.mortar_pestle),
				new Object[]{
						new ItemStack(BTWItems.bark,1, Short.MAX_VALUE),
						new ItemStack(BTWItems.bark,1, Short.MAX_VALUE),
						new ItemStack(BTWItems.bark,1, Short.MAX_VALUE),
				});

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.sugar_resin_mixing,1,RefamishedItems.sugar_resin_mixing.getMaxDamage()),
				new Object[]{
						new ItemStack(Item.slimeBall),
						new ItemStack(RefamishedItems.crude_sugar),
				});

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.mud_mixing,1,RefamishedItems.mud_mixing.getMaxDamage()),
				new Object[]{
						Item.bucketWater,
						new ItemStack(BTWBlocks.looseDirt),
						new ItemStack(BTWItems.straw),
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(RefamishedItems.wet_clay),
						new ItemStack(RefamishedItems.wet_clay),
				});

		RecipeManager.addCauldronRecipe(new ItemStack(RefamishedItems.sugar_resin), new ItemStack[]{new ItemStack(Item.slimeBall),new ItemStack(Item.sugar)});

		RecipeManager.addCauldronRecipe(new ItemStack(RefamishedItems.redstoneBrick), new ItemStack[]{new ItemStack(Item.redstone,6)});

		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.pointyStick),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.unlitCampfire),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.unlitCampfire),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
						new ItemStack(Item.stick),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.unlitCampfire),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
						new ItemStack(Item.stick),
						new ItemStack(Item.stick),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWBlocks.unlitCampfire),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
						new ItemStack(Item.stick),
						new ItemStack(Item.stick),
						new ItemStack(Item.stick),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.firePlough),
				new Object[]{
						new ItemStack(RefamishedItems.branch),
						new ItemStack(RefamishedItems.branch),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.pie_dough),
				new Object[]{
						new ItemStack(BTWItems.flour),
						new ItemStack(BTWItems.flour),
						new ItemStack(Item.sugar),
						new ItemStack(Item.bucketWater),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.raw_pie_blueberry),
				new Object[]{
						new ItemStack(RefamishedItems.berries,1,0),
						new ItemStack(RefamishedItems.berries,1,0),
						new ItemStack(RefamishedItems.pie_dough),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.raw_pie_sweetberry),
				new Object[]{
						new ItemStack(RefamishedItems.berries,1,1),
						new ItemStack(RefamishedItems.berries,1,1),
						new ItemStack(RefamishedItems.pie_dough),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.raw_pie_cranberry),
				new Object[]{
						new ItemStack(RefamishedItems.berries,1,2),
						new ItemStack(RefamishedItems.berries,1,2),
						new ItemStack(RefamishedItems.pie_dough),
				});
		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.raw_pie_blackberry),
				new Object[]{
						new ItemStack(RefamishedItems.berries,1,3),
						new ItemStack(RefamishedItems.berries,1,3),
						new ItemStack(RefamishedItems.pie_dough),
				});

		RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.woodenTongs),
				new Object[]{
						new ItemStack(Item.silk),
						new ItemStack(Item.silk),
						new ItemStack(Item.stick),
						new ItemStack(Item.stick),
				});

		List<Object[]> machetes = Arrays.asList(
				new Object[]{RefamishedItems.gilded_machete},
				new Object[]{RefamishedItems.diamond_machete},
				new Object[]{RefamishedItems.iron_machete},
				new Object[]{RefamishedItems.copper_machete}
		);
		for (Object[] tool : machetes) {
			Item macheteItem = (Item) tool[0];
			if (!(macheteItem instanceof flintMachete)) {
				RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(new ItemStack(RefamishedItems.rib,1,2), new ItemStack(Item.beefRaw),
						new Object[]{new ItemStack(macheteItem,1, Short.MAX_VALUE),new ItemStack(RefamishedItems.rib_beef),});

				RecipeManager.addShapelessRecipe(new ItemStack(RefamishedItems.cut_sugar_cane),
						new Object[]{
								new ItemStack(macheteItem,1, Short.MAX_VALUE),
								new ItemStack(Item.reed),
						});
			}
		}

		FurnaceRecipes.smelting().addSmelting(RefamishedItems.rib_beef.itemID, new ItemStack(RefamishedItems.cooked_rib_beef), 0.0f, 1);
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.raw_pie_blueberry.itemID, new ItemStack(RefamishedItems.pie_cooked,1,0), 0.0f, 1);
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.raw_pie_sweetberry.itemID, new ItemStack(RefamishedItems.pie_cooked,1,1), 0.0f, 1);
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.raw_pie_cranberry.itemID, new ItemStack(RefamishedItems.pie_cooked,1,2), 0.0f, 1);
		FurnaceRecipes.smelting().addSmelting(RefamishedItems.raw_pie_blackberry.itemID, new ItemStack(RefamishedItems.pie_cooked,1,3), 0.0f, 1);
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
		//RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 6), new ItemStack[]{new ItemStack(Item.saddle)});
		for (int ih = 0; ih < Item.recordWait.itemID - Item.record13.itemID + 1; ih++) {
			RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.ironNugget, 7), new ItemStack[]{new ItemStack(Item.itemsList[Item.record13.itemID+ih])});
		}
		addBuildingBlocksRecipe(RefamishedItems.soft_brick,RefamishedBlocks.softBrickLoose,RefamishedBlocks.softBrickLooseSlab,RefamishedBlocks.softBrickLooseStairs);
		addBuildingBlocksRecipe(RefamishedItems.searedBrick,RefamishedBlocks.searedBrickLoose,RefamishedBlocks.searedBrickLooseSlab,RefamishedBlocks.searedBrickLooseStair);
		addBuildingBlocksRecipe(RefamishedItems.redstoneBrick,RefamishedBlocks.redstoneBricks,RefamishedBlocks.redstoneBricksSlab,RefamishedBlocks.redstoneBricksStairs);

		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolHelmet), new Object[]{"#", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolChest), new Object[]{"##", "##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolLeggings), new Object[]{"##", "# ", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolLeggings), new Object[]{"# ", "##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});
		RecipeManager.addShapedRecipeWithCustomClass(WoolArmorRecipe.class, new ItemStack(BTWItems.woolBoots), new Object[]{"##", Character.valueOf('#'), new ItemStack(BTWItems.woolKnit, 1, Short.MAX_VALUE)});

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

		RecipeManager.removeVanillaRecipe(new ItemStack(Item.helmetDiamond), new Object[]{"XXX", "XYX", Character.valueOf('X'), BTWItems.diamondIngot, Character.valueOf('Y'), BTWItems.diamondArmorPlate});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.plateDiamond), new Object[]{"Y Y", "XXX", "XXX", Character.valueOf('X'), BTWItems.diamondIngot, Character.valueOf('Y'), BTWItems.diamondArmorPlate});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.legsDiamond), new Object[]{"XXX", "Y Y", "Y Y", Character.valueOf('X'), BTWItems.diamondIngot, Character.valueOf('Y'), BTWItems.diamondArmorPlate});
		RecipeManager.removeVanillaRecipe(new ItemStack(Item.bootsDiamond), new Object[]{"X X", "X X", Character.valueOf('X'), BTWItems.diamondIngot});

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

		removeStokedCauldron(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherHelmet, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 4), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherChest, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 3), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherLeggings, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(BTWItems.tannedLeatherBoots, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(Item.helmetLeather, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 4), new ItemStack[]{new ItemStack(Item.plateLeather, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 3), new ItemStack[]{new ItemStack(Item.legsLeather, 1, Short.MAX_VALUE)});
		removeStokedCauldron(new ItemStack(BTWItems.glue, 2), new ItemStack[]{new ItemStack(Item.bootsLeather, 1, Short.MAX_VALUE)});
//		String[][] recipePatterns = new String[][]{{"XXX", " # ", " # "}, {"X", "#", "#"}, {"XX", "X#", " #"}, {"XX", " #", " #"}, {"X", " X", " #"}};
//		Object[][] recipeItems = new Object[][]{{Item.ingotIron, Item.diamond, Item.ingotGold}, {Item.pickaxeIron, Item.pickaxeDiamond, Item.pickaxeGold}, {Item.shovelIron, Item.shovelDiamond, Item.shovelGold}, {Item.axeIron, Item.axeDiamond, Item.axeGold}, {Item.hoeStone, Item.hoeIron, Item.hoeDiamond, Item.hoeGold}, {Item.swordIron, Item.sword, Item.hoeIron, Item.hoeDiamond, Item.hoeGold}};
//		for (int i = 0; i < recipeItems[0].length; ++i) {
//			Object object = recipeItems[0][i];
//			for (int j = 0; j < recipeItems.length - 1; ++j) {
//				Item item = (Item)recipeItems[j + 1][i];
//				removeVanillaRecipe2(new ItemStack(item), recipePatterns[j], Character.valueOf('#'), Item.stick, Character.valueOf('X'), object);
//				//RecipeManager.removeVanillaRecipe(new ItemStack(item), recipePatterns[j], Character.valueOf('#'), Item.stick, Character.valueOf('X'), object);
//				// Here old -> craftingManager.addRecipe(new ItemStack(item), this.recipePatterns[j], Character.valueOf('#'), Item.stick, Character.valueOf('X'), object);
//			}
//		}
		RecipeManager.removeVanillaRecipe(new ItemStack(BTWBlocks.lens), new Object[]{"GDG", "G G", "G#G", Character.valueOf('#'), Block.glass, Character.valueOf('G'), Item.ingotGold, Character.valueOf('D'), Item.diamond});
		RecipeManager.removeVanillaRecipe(new ItemStack(BTWBlocks.lens), new Object[]{"G#G", "G G", "GDG", Character.valueOf('#'), Block.glass, Character.valueOf('G'), Item.ingotGold, Character.valueOf('D'), Item.diamond});
		RecipeManager.removeVanillaRecipe(new ItemStack(Block.blockRedstone), new Object[]{"###", "###", "###", Character.valueOf('#'), Item.redstone});
		Set<Item> targets = new HashSet<Item>(Arrays.asList(
				Item.swordDiamond,
				Item.pickaxeDiamond,
				Item.axeDiamond,
				Item.shovelDiamond,
				Item.hoeDiamond,
				Item.helmetIron,Item.plateIron,Item.legsIron,Item.bootsIron,
				Item.helmetGold,Item.plateGold,Item.legsGold,Item.bootsGold,
				Item.axeIron,Item.axeGold,
				Item.helmetDiamond,Item.plateDiamond,Item.legsDiamond,Item.bootsDiamond
		));

		Iterator<IRecipe> it = CraftingManager.getInstance().getRecipeList().iterator();

		while (it.hasNext()) {
			IRecipe recipe = it.next();
			ItemStack output = recipe.getRecipeOutput();
			if (output != null && targets.contains(output.getItem())) {
				it.remove();
			}
		}
//		CraftingManager.getInstance().getRecipeList().add(new TestArmorCombination(Arrays.asList(
//				new ItemStack(RefamishedItems.metallurgyArmor,1,Short.MAX_VALUE),
//				new ItemStack(RefamishedItems.metallurgyArmor,1,Short.MAX_VALUE))));
	}

	private static void removeVanillaRecipe2(ItemStack output, String[] pattern, Object... recipeComponents) {
		Iterator<IRecipe> iter = CraftingManager.getInstance().getRecipeList().iterator();

		while (iter.hasNext()) {
			IRecipe recipe = iter.next();
			ItemStack result = recipe.getRecipeOutput();

			if (result != null && result.itemID == output.itemID && result.getItemDamage() == output.getItemDamage()) {
				if (recipe instanceof ShapedRecipes) {
					ShapedRecipes shaped = (ShapedRecipes) recipe;
					// optionally check pattern and components to match exactly
					iter.remove();
					System.out.println("Removed shaped recipe for: " + result);
					break;
				}
			}
		}
	}


	@Unique
	private static void removeStokedCrucible(ItemStack item, ItemStack[] list) {
		CrucibleStokedCraftingManager.getInstance().removeRecipe(item,list);
	}

	@Unique
	private static void removeStokedCauldron(ItemStack item, ItemStack[] list) {
		CauldronStokedCraftingManager.getInstance().removeRecipe(item,list);
	}

	@Unique
	private static void removeCauldron(ItemStack item, TagOrStack[] list) {
		CauldronCraftingManager.getInstance().removeRecipe(item,list);
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
	private static void addSmithingRecipe(ItemStack input, ItemStack[] output1, int shatter)
	{
		SmithingRecipes.getInstance().addRecipe(input, output1, shatter);
	}

	@Unique
	private static void addSmithingRecipe(ItemStack input, ItemStack output1, int shatter)
	{
		ItemStack[] re = new ItemStack[1];
		re[0] = output1;
		SmithingRecipes.getInstance().addRecipe(input, re, shatter);
	}

	@Unique
	private static void addForgePlanRecipe(List<ItemStack> input, ItemStack output1, int shatter)
	{
		ForgingPlansRecipes.getInstance().addRecipe(input, output1, shatter);
	}

	@Unique
	private static void addForgePlanRecipe(List<ItemStack> input, ItemStack output1, int shatter, int anvilLevel)
	{
		ForgingPlansRecipes.getInstance().addRecipe(input, output1, shatter,anvilLevel);
	}

	@Unique
	private static void addForPlanRecipes(Item ingot, Item nugget, int shatterMultiplier, String metalName, int anvilLevel)
	{
		String[] heads = metallurgyHeads.heads;
		/*
			for (int i = 0; i < heads.length; i++) {
			String headName = heads[i];
			addForgePlanRecipe(input,
					new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_"+headName)), 100);
		}
		 */
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot,1)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_sword")), 20*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot,3)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_pickaxe")), 60*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot,2)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_axe")), 40*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_shovel")), 20*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot,1)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_hoe")), 20*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_crossguard")), 20*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot)),
				new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString(metalName+"_rivet")), 20*shatterMultiplier,anvilLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(ingot,3)),
				new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_machete")), 60*shatterMultiplier,anvilLevel);
		if (nugget != null) {
			addForgePlanRecipe(Arrays.asList(new ItemStack(nugget,5)),
					new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString(metalName+"_rivet")), 15*shatterMultiplier,anvilLevel);
		}
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
	private static void addMetaFurnaceRecipe(ItemStack input, ItemStack output, int cookTime)
	{
		FurnaceMetaRecipes.getInstance().addSmelting(input, output,cookTime);
	}

	@Unique
	private static int getHeadIndexByString(String head)
	{
		return metallurgyHeads.getPartIndex(head);
	}

	@Unique
	private static ShapelessRecipes addShapelessRecipeWithSecondaryOutputIndicator(ItemStack itemStack, ItemStack secondaryOutput, Object[] inputs) {
		return RecipeManager.addShapelessRecipeWithSecondaryOutputIndicator(itemStack, new ItemStack[]{secondaryOutput}, inputs);
	}

	@Unique
	private static ShapelessRecipes addShapelessRecipeWithSecondaryOutputIndicator(ItemStack itemStack, ItemStack[] secondaryOutputs, Object[] inputs) {
		ShapelessRecipes recipe = CraftingManager.getInstance().addShapelessRecipes(itemStack, inputs);
		if (recipe != null) {
			recipe.setSecondaryOutput(secondaryOutputs);
		}
		return recipe;
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

	private static int[] materialCost = new int[]{2,3,3,5,4,3,2,2};
	private static int[] materialHard = new int[]{4,5,5,7,5,5,4,4};

	@Unique
	private static void addArmorSlightlyManual(Item[] armor, String name_, int hardnessSet, int forgeLevel) {
		String[] heads = new String[]{"helm","coif","pauldrons","cuirass","tassets","greaves","threads","sabatons"};
		int sheetId = metalSheets.getPartIndex(name_);
		for (int jh = 0; jh < heads.length; jh++) {
			String part = heads[jh];
			int cost = materialCost[jh];
			int hardness = materialHard[jh];
			addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.sheet,cost,sheetId)),
					new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_"+part)), hardness*hardnessSet,forgeLevel);
		}
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_helm")),
						new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_coif")),
						new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(armor[0]), 7*hardnessSet,forgeLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_pauldrons")),
						new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_cuirass")),
						new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(armor[1]), 10*hardnessSet,forgeLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_tassets")),
						new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_greaves")),
						new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(armor[2]), 8*hardnessSet,forgeLevel);
		addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_threads")),
						new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_sabatons")),
						new ItemStack(RefamishedItems.sugar_resin)),
				new ItemStack(armor[3]), 6*hardnessSet,forgeLevel);
	}
}
