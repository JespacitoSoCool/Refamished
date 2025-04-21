package net.fabricmc.refamished;

import btw.block.BTWBlocks;
import btw.crafting.util.FurnaceBurnTime;
import btw.item.BTWItems;
import btw.item.items.*;
import net.fabricmc.refamished.blocks.override.FiredBrickOverride;
import net.fabricmc.refamished.items.*;
import net.fabricmc.refamished.items.decorative.*;
import net.fabricmc.refamished.items.food.rib_beef;
import net.fabricmc.refamished.items.food.rib_beef_cooked;
import net.fabricmc.refamished.items.food.rib_beef_cooked_partial;
import net.fabricmc.refamished.items.food.rib_beef_cooked_spent;
import net.fabricmc.refamished.items.materials.*;
import net.fabricmc.refamished.items.tools.*;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.misc.ReMaterials;
import net.minecraft.src.*;

public class RefamishedItems {

	private static final int

			id_saltpeter= 4452,
			id_advancedHandle= 4453,
			id_stoneSharpening= 4454,
			id_cowhidePrepared= 4455,
			id_plateObsidian= 5455,
			id_pileAsh= 5456,
			id_cowhide= 5457,
			id_rottedString= 5458,
			id_ember= 5459,
			id_emberSmolder= 5460,
			id_knifeRib = 19999,
			id_ribCarvingBone = 19998,
			id_leatherCuttingBone = 19997,
			id_testingstick = 29942,
			id_clubWoodenSpruce = 29943,
			id_clubWoodenBirch = 29944,
			id_clubWoodenJungle = 29945,
			id_clubWoodenAssembling = 29946,
			id_clubWoodenAssemblingSpruce = 29947,
			id_clubWoodenAssemblingBirch = 29948,
			id_clubWoodenAssemblingJungle = 29949,
			id_clubDeath = 29950,
			id_clubDeathSpruce = 29951,
			id_clubDeathBirch = 29952,
			id_clubDeathJungle = 29953,
			id_clubDeathAssembling = 29954,
			id_clubDeathAssemblingSpruce = 29955,
			id_clubDeathAssemblingBirch = 29956,
			id_clubDeathAssemblingJungle = 29957,
			id_diamondMachete = 29958,
			id_rustIronMachete = 29959,
			id_flintMacheteAssembling = 29960,
			id_corrodedCopperSword = 29961,
			id_corrodedCopperPickaxe = 29962,
			id_corrodedCopperAxe = 29963,
			id_corrodedCopperShovel = 29964,
			id_corrodedCopperHoe = 29965,
			id_steelIngot = 29966,
			id_cokeCoal = 29967,
			id_dulledGoldSword = 29968,
			id_dulledGoldPickaxe = 29969,
			id_dulledGoldAxe = 29970,
			id_dulledGoldShovel = 29971,
			id_dulledGoldHoe = 29972,
			id_dulledGoldHelmet = 29973,
			id_dulledGoldChestplate = 29974,
			id_dulledGoldLeggings = 29975,
			id_dulledGoldBoots = 29976,
			id_scorchedRock = 29977,
			id_scorchedFlux = 29978,
			id_tarBucket = 29979,
			id_tarBottle = 29980,
			id_tarMolotov = 29981,
			id_tarMolotovLit = 29982,
			id_incendiaryBow = 29983,
			id_flintBowIgniter = 29984,
			id_bowStringingFlint = 29985,


	id_softBrick= 6860,
			id_clayMud= 6861,
			id_clayWet= 6862,
			id_clayBowl= 6863,
			id_clayBowlWater= 6864,
			id_stoneTrowel= 6865,
			id_clayMudMixing= 6866,
			id_clayMudBrick= 6867,
			id_redstoneBrick= 6868,
			id_clayMudBrickMud= 6869,

	id_flintIgniter= 5461,
			id_flintHew= 5462,
			id_flintSpade= 5463,
			id_flintMachete= 5464,
			id_ironMachete= 5465,
			id_frostforgeMachete= 5466,
			id_flintSharpener= 5467,
			id_stoneSharpeningFlint= 5468,
			id_flintSharpeningFlint= 5469,
			id_flintCuttingWool= 5470,
			id_woolString= 5471,
			id_woolStoneAxe= 5472,
			id_woolStoneShovel= 5473,
			id_woolStoneHoe= 5474,
			id_woolStonePickaxe= 5475,
			id_woolBonePickaxe= 5476,
			id_woolBoneClub= 5477,
			id_woolHeavyClub= 5478,
			id_woolBoneClubAssembling= 5479,
			id_woolHeavyClubAssembling= 5480,
			id_woolFlintMachete= 5481,
			id_paintedBrick= 5482,

	id_lapisWireItem= 5601,
			id_enchantingBook= 5602,
			id_brewingStand= 5603,
			id_brewingStandReady= 5604,
			id_serpentEye= 5605,
			id_emberBlaze= 5606,
			id_witchKnot= 5607,
			id_infusedChalice= 5608,
			id_paintBucket= 5609,

	id_GildedIngot= 4000,
			id_GildedNugget= 4001,

	id_pileCopper= 4002,
			id_chunkCopper= 4003,

	id_copperIngot= 4004,
			id_copperNugget= 4005,

	id_cobaltIngot= 4006,
			id_cobaltNugget= 4007,
			id_chunkCobalt= 4008,
			id_cobaltzureIngot= 4009,

	id_frostforgeCrystal= 4010,
			id_frostforgeIngot= 4011,

	id_heavyIron= 4041,
			id_heavyCopper= 4042,
			id_heavyGold= 4043,
			id_heavySintered= 4044,
			id_heavyCobalt= 4045,

	id_gildedSword= 4101,
			id_gildedPickaxe=4103,
			id_gildedAxe=4104,
			id_gildedShovel=4105,
			id_gildedHoe=4106,

	id_tippedSword= 4107,
			id_tippedPickaxe=4108,
			id_tippedAxe=4109,
			id_tippedShovel=4110,
			id_tippedHoe=4119,

	id_copperSword= 4120,
			id_copperPickaxe=4121,
			id_copperAxe=4122,
			id_copperShovel=4123,
			id_copperHoe=4124,
			id_copperArrow= 4125,
			id_copperHelmet= 4126,
			id_copperChestplate= 4127,
			id_copperLeggings= 4128,
			id_copperBoots= 4129,

	id_cobaltSword= 4130,
			id_cobaltPickaxe=4131,
			id_cobaltAxe=4132,
			id_cobaltShovel=4133,
			id_cobaltHoe=4134,

	id_rustSword= 4135,
			id_rustPickaxe=4136,
			id_rustAxe=4137,
			id_rustShovel=4138,
			id_rustHoe=4139,
			id_rustHelmet= 4140,
			id_rustChestplate= 4141,
			id_rustLeggings= 4142,
			id_rustBoots= 4143,

	id_cobaltHelmet= 4144,
			id_cobaltChestplate=4145,
			id_cobaltLeggings=4146,
			id_cobaltBoots=4147,

	id_frostforgeSword= 4148,
			id_frostforgePickaxe=4149,
			id_frostforgeAxe=4150,
			id_frostforgeShovel=4151,
			id_frostforgeHoe=4152,

	id_emeraldiumPickaxe= 4153,

	id_diamondChisel= 4305,
			id_gildedchisel= 4306,
			id_copperChisel= 4307,
			id_heavyClub= 4308,
			id_assemblingWooden= 4309,
			id_clubWooden= 4310,
			id_assemblingBone= 4311,
			id_clubBone= 4312,
			id_assemblingDeath= 4313,
			id_assemblingHeavy= 4314,
			id_clubCore= 4315,
			id_clubCoreBone= 4316,
			id_clubHandle= 4317,

	id_diamondShears= 4502,
			id_boneShears= 4503,

	id_heartstew= 3852,
			id_funnySandwich= 3853,
			id_carnageDinner= 3854,
			id_pumpkinBucket= 3855,
			id_pumpkinRaw= 3856,
			id_pumpkinSoup= 3857,
			id_blueberry= 3858,
			id_blueberryBushSeeds= 3859,
			id_pieDough= 3860,
			id_pieBlueberryRaw= 3861,
			id_pieBlueberry= 3862,
			id_blueberryWildSeeds= 3863,
			id_meatDirty= 3864,

	id_woodenLeggings= 4200,
			id_woodenChestplate= 4201,
			id_miningHelmet= 4294,
			id_quiver= 4295,

	id_woodChestOak= 4202,
			id_woodLeggingsOak= 4203,
			id_woodChestBirch= 4204,
			id_woodLeggingsBirch= 4205,
			id_woodChestSpruce= 4206,
			id_woodLeggingsSpruce= 4207,
			id_woodChestJungle= 4208,
			id_woodLeggingsJungle= 4209,

	id_chippedAxe= 4214,
			id_chippedShovel= 4215,
			id_chippedHoe= 4216,
			id_chippedPickaxe= 4217,

	id_chippingAxe= 4388,
			id_chippingShovel= 4389,
			id_chippingHoe= 4290,
			id_chippingPickaxe= 4291,

	id_woodenCrossbow= 4210,
			id_boltWood= 4212,
			id_boltCopper= 4213,
			id_heavyCrossbow= 4292,
			id_boltCobalt= 4293,
			id_boltCobaltQuarrel= 4299,
			id_arrowFrost= 4300,
			id_arrowFlint= 4301,
			id_arrowGold= 4302,
			id_arrowIncendiary= 4303
					;

	private static final int
			id_freakybob= 3666,
			id_copperChunkground= 3456,
			id_copperChunkStorage= 3457,
			id_cobaltChunkGround= 3458,
			id_cobaltChunkStorage= 3459,
			id_cowhidePreparedGround= 3460,
			id_cowhideReady= 3461,
			id_blueberryBush= 3106,
			id_blueberryWild= 3107,
			id_chargedBookshelf= 3108,
			id_rustRail= 3109,
			id_decayedWood= 3110,
			id_decayedPlanks= 3111,
			id_rottedWeb= 3112,
			id_grassThorns= 3113,
			id_frostforgeAnvil= 3114,
			id_brittlestone= 3115,
			id_cobaltChunkHeavy= 3116,
			id_copperChunkHeavy= 3117,
			id_goldChunkHeavy= 3118,
			id_ironChunkHeavy= 3119,
			id_sinteredChunkHeavy= 3120,
			id_branchBush= 3121,
			id_softBrickGround= 3122,
			id_clayMudBrickGround= 3123,
			id_softBrickLoose= 3124,
			id_softBrickLooseSlab= 3125,
			id_softBrickLooseStairs= 3126,
			id_clayMudBrickGroundIdle= 3127,
			id_softBrickMortar= 3128,
			id_softBrickMortarSlab= 3129,
			id_softBrickMortarStairs= 3130,
			id_enchantmentTableOff= 3131,
			id_lapisWire = 2132,
			id_lapisWireLit = 2133,
			id_enchantmentTableReady= 3134,
			id_brewingStandBlock= 3135,
			id_redstoneBricks= 3136,
			id_redstoneBricksSlab= 3137,
			id_redstoneBricksStairs= 3138,
			id_fallingLeaves= 3139,
			id_charredLeaves= 3140,
			id_charredDirt= 3141,
			id_charredGrass= 3142,
			id_paintBrickStone= 3143,
			id_paintBrickStoneSlab= 3144,
			id_paintBrickStoneStair= 3145,
			id_paintCobble= 3146,
			id_paintCobbleSlab= 3147,
			id_paintCobbleStair= 3148,
			id_paintWood= 3149,
			id_paintWoodStairs= 3150,
			id_paintWoodSlab= 3151,
			id_paintWoodSiding= 3152,
			id_paintWoodMoulding= 3153,
			id_paintWoodCorner= 3154,
			id_paintStone= 3155,
			id_paintStoneRough1= 3156,
			id_paintStoneRough2= 3157,
			id_paintStoneRough3= 3158,
			id_paintBrickStoneLoose= 3159,
			id_log= 3405,
			id_logWorkbench= 3406,
			id_planks= 3407,
			id_decorative1Calden= 3408,
			id_decorative2Calden= 3409,
			id_decorative1Mimosa= 3410,
			id_decorative2Mimosa= 3411,
			id_decorative1Unknown1= 3412,
			id_decorative2Unknown1= 3413,
			id_decorative1Unknown2= 3412,
			id_decorative2Unknown2= 3413,
			id_clayMudBrickGroundMud= 3414
					;

	private static final int
			id_entityCopperArrow= 3103,
			id_entityBoltWood= 3104,
			id_entityBoltCopper= 3105,
			id_entityBoltCobalt= 3106,
			id_entityArrowFrost= 3107,
			id_entityArrowFlint= 3108,
			id_entityArrowGold= 3109,
			id_entityRottedWeb= 3334,
			id_entityGhoul= 3333,
			id_savageCow= 3335,
			id_savageSheep=3336,
			id_savagePig=3337,
			id_savageChicken=3338,
			id_savageWolf=3339,
			id_husk=3370,
			id_stray=3371
					;

	private static final int

			id_leatherWorking = 20000,
			id_flintKnapping = 20001,

	id_flintBlade = 20002,
			id_ironBlade = 20003,

	id_cowRib = 20004,
			id_rib = 20005,
			id_cookedCowRib = 20006,
			id_bonePickaxe = 20007,

	id_leatherWorkingIron = 20008,

	id_wetMudBrickItem = 20009,
			id_wetAdobe = 20010,
			id_adobe = 20011,

	id_branch = 20012,

	id_ribCarving = 20013,
			id_ribCarvingIron = 20014,

	id_cookedCowRibPartial = 20015,
			id_cookedCowRibSpent = 20016,

	id_hoeStoneNew = 20017,

	id_bowStringing = 20018,

	id_deathClub = 20019,

	id_bedroll = 20020,

	id_mortarBucket = 20021,

	id_trowel = 20022,

	id_envelopeOpen = 20023,
			id_envelopeClosed = 20024,
			id_tombstonePlacer = 20025, //an item that places a tombstone

	id_pipeEmpty = 20026,
//		id_pipePacked = 20027,
//		id_pipeLit = 20028,

	id_treat = 20029,
			id_badTreat = 20030,

	id_chunkIronSintered = 20031;

	//GOURD MANIA!
	private static final int
			id_waterMelonSeeds = 31020,
			id_canaryMelonSeeds = 31021,
			id_honeydewMelonSeeds = 31022,
			id_cantaloupeMelonSeeds = 31023,

	id_foulSeeds = 31024,


	id_orangePumpkinSeeds = 31029,
			id_greenPumpkinSeeds = 31030,
			id_yellowPumpkinSeeds = 31031,
			id_whitePumpkinSeeds = 31032;

	private static final int
			id_branchBlock = 2000,
			id_sunflower = 2001,
			id_gravestone = 2002,
			id_meatCube = 2003,
			id_blockBedroll = 2004,
			id_timeCube = 2005,
			id_wetMudBrick = 2006,
			id_gloryHole = 2007,
			id_reedThatchSlab = 2008,
			id_reedThatch = 2009,
			id_strawThatchSlab = 2010,
			id_strawThatch = 2011,
			id_stickBundleLooseSlab = 2012,
			id_stickBundleLoose = 2013,
			id_superGlass = 2014,
			id_superBlock = 2015,
	// 2016-2020 saved
	id_terracotta = 2021,
			id_stainedTerracotta = 2022,
			id_unfiredTerracotta = 2023,
			id_terracottaSlab = 2024,
			id_terracottaSlab2 = 2025,
			id_terracottaSlabDefault = 2026,
			id_whiteStoneBrick = 2027,
			id_whiteStoneBrickStairs = 2028,
			id_whiteStoneBrickSlab = 2029,
			id_sandstoneBrickLarge = 2030,
			id_sandstoneBrickLargeStairs = 2031,
			id_sandstoneBrickLargeSlab = 2032,
			id_blockIronSintered = 2033,
			id_blockIronSinteredStorage = 2034;
//		id_decoStoneSlab = 2032;

	public static Item cooked_rib_beef;
	public static Item cooked_rib_beefPartial;
	public static Item cooked_rib_beefSpent;

	public static Item chippedPickaxeHead;
	public static Item chippedAxeHead;
	public static Item chippedShovelHead;
	public static Item chippedHoeHead;
	public static Item flintBlade;
	public static Item ironBlade;

	public static Item flintKnapping;
	public static Item stoneSharpening;
	public static Item chippingAxeHead;
	public static Item chippingShovelHead;
	public static Item chippingHoeHead;
	public static Item chippingPickaxeHead;
	public static Item leatherCuttingFlint;
	public static Item leatherCuttingIron;
	public static Item ribCarvingFlint;
	public static Item ribCarvingIron;

	public static Item copperChunk;
	public static Item copperDust;
	public static Item copperIngot;
	public static Item copperNugget;
	public static Item gildedIngot;
	public static Item gildedNugget;
	public static Item steelIngot;
	public static Item cobaltzureIngot;
	public static Item cobaltzureNugget;

	public static Item copperSword;
	public static Item copperPickaxe;
	public static Item copperAxe;
	public static Item copperShovel;
	public static Item copperHoe;
	public static Item copperChisel;
	public static Item bone_pickaxe;
	public static Item[] wooden_club = new Item[4];
	public static Item[] wooden_club_assembling = new Item[4];
	public static Item[] death_club = new Item[4];
	public static Item[] death_club_assembling = new Item[4];
	public static Item bone_club;
	public static Item heavy_club;
	public static Item bone_club_assembling;
	public static Item heavy_club_assembling;
	public static Item club_component;
	public static Item flint_machete;
	public static Item iron_machete;
	public static Item diamond_machete;
	public static Item helmetRustIron;
	public static Item plateRustIron;
	public static Item legsRustIron;
	public static Item bootsRustIron;
	public static Item rustIronSword;
	public static Item rustIronPickaxe;
	public static Item rustIronAxe;
	public static Item rustIronShovel;
	public static Item rustIronHoe;
	public static Item rustIronMachete;
	public static Item helmetCopper;
	public static Item plateCopper;
	public static Item legsCopper;
	public static Item bootsCopper;
	public static Item corrodedCopperSword;
	public static Item corrodedCopperPickaxe;
	public static Item corrodedCopperAxe;
	public static Item corrodedCopperShovel;
	public static Item corrodedCopperHoe;
	public static Item tipDiamondSword;
	public static Item tipDiamondPickaxe;
	public static Item tipDiamondAxe;
	public static Item tipDiamondShovel;
	public static Item tipDiamondHoe;
	public static Item helmetDulledGold;
	public static Item plateDulledGold;
	public static Item legsDulledGold;
	public static Item bootsDulledGold;
	public static Item dulledGoldSword;
	public static Item dulledGoldPickaxe;
	public static Item dulledGoldAxe;
	public static Item dulledGoldShovel;
	public static Item dulledGoldHoe;
	public static Item gildedIronSword;
	public static Item gildedIronPickaxe;
	public static Item gildedIronAxe;
	public static Item gildedIronShovel;
	public static Item gildedIronHoe;
	public static Item cobaltzureSword;
	public static Item cobaltzurePickaxe;
	public static Item cobaltzureAxe;
	public static Item cobaltzureShovel;
	public static Item cobaltzureHoe;

	public static Item rib_beef;
	public static Item rib;
	public static Item saltpeter;
	public static Item soft_brick;
	public static Item clay_bowl;
	public static Item clay_bowl_water;
	public static Item clay_mixing;
	public static Item wet_clay;
	public static Item clay_mud;
	public static Item soft_clay_brick;
	public static Item stone_trowel;
	public static Item wool_string;
	public static Item flint_sharpener;
	public static Item flint_sharpenerStone;
	public static Item flint_sharpenerFlint;
	public static Item flint_sharpenerWool;
	public static Item cowhide;
	public static Item cowhide_prepared;
	public static Item pile_ashes;
	public static Item crude_sugar;
	public static Item water_w_crude_sugar;
	public static Item sugar_resin;
	public static Item cut_sugar_cane;
	public static Item sugar_cane_cutting;
	public static Item sugar_cane_crushing;
	public static Item flint_machete_assembling;
	public static Item scorched_rock;
	public static Item scorched_flux;
	public static Item tar_bucket;
	public static Item tar_bottle;
	public static Item tar_molotov;
	public static Item tar_molotov_lit;
	public static Item coke_coal;
	public static Item arrow_flint;
	public static Item arrow_gold;
	public static Item arrow_frozen;
	public static Item arrow_incendiary;
	public static Item bolt_bone;
	public static Item bolt_iron;
	public static Item incendiary_bow;
	public static Item flint_bow_igniter;
	public static Item bow_stringing;
	public static Item bow_stringingflint;
	public static Item crossbow;
	public static Item compound_arbalest;
	public static Item branch;

	public static Item test;

	public static void registerItems() {
		chippedAxeHead = new chippedHead(id_chippedAxe-256).setUnlocalizedName("chippedAxeHead").setTextureName("refamished:chipped_stone_axe");
		chippedShovelHead = new chippedHead(id_chippedShovel-256).setUnlocalizedName("chippedShovelHead").setTextureName("refamished:chipped_stone_shovel");
		chippedHoeHead = new chippedHead(id_chippedHoe-256).setUnlocalizedName("chippedHoeHead").setTextureName("refamished:chipped_stone_hoe");
		chippedPickaxeHead = new chippedHead(id_chippedPickaxe-256).setUnlocalizedName("chippedPickaxeHead").setTextureName("refamished:chipped_stone_pickaxe");
		flintBlade = new bladeFlint(id_flintBlade-256).setUnlocalizedName("flintBlade").setTextureName("refamished:knife_flint");
		ironBlade = new bladeIron(id_ironBlade-256).setUnlocalizedName("ironBlade").setTextureName("refamished:knife_iron");

		flintKnapping = new KnappingFlint(id_flintKnapping-256).setUnlocalizedName("flintKnapping");
		stoneSharpening = new craftingPulling(id_stoneSharpening-256).setUnlocalizedName("stoneSharpening");
		chippingAxeHead = new chippingAxe(id_chippingAxe-256).setUnlocalizedName("chippingAxeHead");
		chippingShovelHead = new chippingShovel(id_chippingShovel-256).setUnlocalizedName("chippingShovelHead");
		chippingHoeHead = new chippingHoe(id_chippingHoe-256).setUnlocalizedName("chippingHoeHead");
		chippingPickaxeHead = new chippingPickaxe(id_chippingPickaxe-256).setUnlocalizedName("chippingPickaxeHead");
		leatherCuttingFlint = new leatherWorkFlint(id_leatherWorking-256).setUnlocalizedName("leatherWorkingFlint");
		leatherCuttingIron = new leatherWorkIron(id_leatherWorkingIron-256).setUnlocalizedName("leatherWorkingIron");
		ribCarvingFlint = new ribCarvingFlint(id_ribCarving-256).setUnlocalizedName("ribCarvingFlint");
		ribCarvingIron = new ribCarvingIron(id_ribCarvingIron-256).setUnlocalizedName("ribCarvingIron");

		copperChunk = new copper_chunk(id_chunkCopper-256);
		copperDust = new Item(id_pileCopper-256).setUnlocalizedName("copper_dust").setTextureName("refamished:copper_dust").setCreativeTab(CreativeTabs.tabMaterials);
		copperIngot = new Item(id_copperIngot-256).setUnlocalizedName("copper_ingot").setTextureName("refamished:copper_ingot").setCreativeTab(CreativeTabs.tabMaterials);
		copperNugget = new Item(id_copperNugget-256).setUnlocalizedName("copper_nugget").setTextureName("refamished:copper_nugget").setCreativeTab(CreativeTabs.tabMaterials);
		steelIngot = new Item(id_steelIngot-256).setUnlocalizedName("steel_ingot").setTextureName("refamished:steel_ingot").setCreativeTab(CreativeTabs.tabMaterials);
		gildedIngot = new Item(id_GildedIngot-256).setUnlocalizedName("gilded_iron_ingot").setTextureName("refamished:gilded_iron_ingot").setCreativeTab(CreativeTabs.tabMaterials);
		gildedNugget = new Item(id_GildedNugget-256).setUnlocalizedName("gilded_iron_nugget").setTextureName("refamished:gilded_iron_nugget").setCreativeTab(CreativeTabs.tabMaterials);
		cobaltzureIngot = new Item(id_cobaltzureIngot-256).setUnlocalizedName("cobalt_ingot").setTextureName("refamished:cobalt_ingot").setCreativeTab(CreativeTabs.tabMaterials);
		cobaltzureNugget = new Item(id_cobaltNugget-256).setUnlocalizedName("cobalt_nugget").setTextureName("refamished:cobalt_nugget").setCreativeTab(CreativeTabs.tabMaterials);

		copperSword = new SwordItem(id_copperSword-256,ReMaterials.COPPER).setUnlocalizedName("copper_sword").setTextureName("refamished:copper_sword");
		copperPickaxe = new PickaxeItem(id_copperPickaxe-256,ReMaterials.COPPER).setUnlocalizedName("copper_pickaxe").setTextureName("refamished:copper_pickaxe");
		copperAxe = new AxeItem(id_copperAxe-256,ReMaterials.COPPER).setUnlocalizedName("copper_axe").setTextureName("refamished:copper_axe");
		copperShovel = new ShovelItem(id_copperShovel-256,ReMaterials.COPPER).setUnlocalizedName("copper_shovel").setTextureName("refamished:copper_shovel");
		copperHoe = new HoeItem(id_copperHoe-256,ReMaterials.COPPER).setUnlocalizedName("copper_hoe").setTextureName("refamished:copper_hoe");
		copperChisel = new copper_chisel(id_copperChisel-256).setUnlocalizedName("copper_chisel").setTextureName("refamished:copper_chisel");

		tipDiamondSword = new SwordItem(id_tippedSword-256,ReMaterials.DIAMONDTIP).setUnlocalizedName("tipped_diamond_sword").setTextureName("refamished:tip_diamond_sword");
		tipDiamondPickaxe = new PickaxeItem(id_tippedPickaxe-256,ReMaterials.DIAMONDTIP).setUnlocalizedName("tipped_diamond_pickaxe").setTextureName("refamished:tip_diamond_pickaxe");
		tipDiamondAxe = new AxeItem(id_tippedAxe-256,ReMaterials.DIAMONDTIP).setUnlocalizedName("tipped_diamond_axe").setTextureName("refamished:tip_diamond_axe");
		tipDiamondShovel = new ShovelItem(id_tippedShovel-256,ReMaterials.DIAMONDTIP).setUnlocalizedName("tipped_diamond_shovel").setTextureName("refamished:tip_diamond_shovel");
		tipDiamondHoe = new HoeItem(id_tippedHoe-256,ReMaterials.DIAMONDTIP).setUnlocalizedName("tipped_diamond_hoe").setTextureName("refamished:tip_diamond_hoe");

		gildedIronSword = new SwordItem(id_gildedSword-256,ReMaterials.GILDEDIRON).setUnlocalizedName("gilded_iron_sword").setTextureName("refamished:gilded_iron_sword");
		gildedIronPickaxe = new PickaxeItem(id_gildedPickaxe-256,ReMaterials.GILDEDIRON).setUnlocalizedName("gilded_iron_pickaxe").setTextureName("refamished:gilded_iron_pickaxe");
		gildedIronAxe = new AxeItem(id_gildedAxe-256,ReMaterials.GILDEDIRON).setUnlocalizedName("gilded_iron_axe").setTextureName("refamished:gilded_iron_axe");
		gildedIronShovel = new ShovelItem(id_gildedShovel-256,ReMaterials.GILDEDIRON).setUnlocalizedName("gilded_iron_shovel").setTextureName("refamished:gilded_iron_shovel");
		gildedIronHoe = new HoeItem(id_gildedHoe-256,ReMaterials.GILDEDIRON).setUnlocalizedName("gilded_iron_hoe").setTextureName("refamished:gilded_iron_hoe");

		cobaltzureSword = new SwordItem(id_cobaltSword-256,ReMaterials.COBALTZURE).setUnlocalizedName("cobaltzure_sword").setTextureName("refamished:cobalt_sword");
		cobaltzurePickaxe = new PickaxeItem(id_cobaltPickaxe-256,ReMaterials.COBALTZURE).setUnlocalizedName("cobaltzure_pickaxe").setTextureName("refamished:cobalt_pickaxe");
		cobaltzureAxe = new AxeItem(id_cobaltAxe-256,ReMaterials.COBALTZURE).setUnlocalizedName("cobaltzure_axe").setTextureName("refamished:cobalt_axe");
		cobaltzureShovel = new ShovelItem(id_cobaltShovel-256,ReMaterials.COBALTZURE).setUnlocalizedName("cobaltzure_shovel").setTextureName("refamished:cobalt_shovel");
		cobaltzureHoe = new HoeItem(id_cobaltHoe-256,ReMaterials.COBALTZURE).setUnlocalizedName("cobaltzure_hoe").setTextureName("refamished:cobalt_hoe");

		rib_beef = new rib_beef(id_cowRib-256);
		cooked_rib_beef = new rib_beef_cooked(id_cookedCowRib-256);
		cooked_rib_beefPartial = new rib_beef_cooked_partial(id_cookedCowRibPartial-256);
		cooked_rib_beefSpent = new rib_beef_cooked_spent(id_cookedCowRibSpent-256);
		rib = new Item(id_rib-256).setUnlocalizedName("rib").setTextureName("refamished:rib").setCreativeTab(CreativeTabs.tabMaterials);
		bone_pickaxe = new PickaxeItem(id_bonePickaxe-256,ReMaterials.BONE).setUnlocalizedName("bone_pickaxe").setTextureName("refamished:rib_pickaxe");
		saltpeter = new saltpeter(id_saltpeter-256).setUnlocalizedName("saltpeter").setTextureName("refamished:saltpeter_chunk");
		soft_brick = new softbrickItem(id_softBrick-256);
		clay_bowl = new clayBowl(id_clayBowl-256);
		clay_bowl_water = new clayBowl_water(id_clayBowlWater-256);
		clay_mud = new ClayItem(id_clayMud-256).setUnlocalizedName("clay_mud").setTextureName("refamished:clay_mud");
		wet_clay = new ClayItem(id_clayWet-256).setUnlocalizedName("wet_clay").setTextureName("refamished:wet_clay");
		clay_mixing = new clayMixing(id_clayMudMixing-256);
		soft_clay_brick = new softClayBrick(id_clayMudBrick-256);
		stone_trowel = new stoneTrowel(id_stoneTrowel-256);
		wool_string = new woolString(id_woolString-256);
		flint_sharpener = new flintSharpener(id_flintSharpener-256);
		flint_sharpenerStone = new flintSharpenerStone(id_stoneSharpeningFlint-256);
		flint_sharpenerFlint = new flintSharpenerFlint(id_flintSharpeningFlint-256);
		flint_sharpenerWool = new flintSharpenerWool(id_flintCuttingWool-256);
		cowhide = new Item(id_cowhide-256).setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("cowhide").setTextureName("refamished:cowhide");
		cowhide_prepared = new PlaceAsBlockItem(id_cowhidePrepared-256,RefamishedBlocks.preparedCowhideBlock.blockID).setCreativeTab(CreativeTabs.tabMaterials).setUnlocalizedName("cowhide_prepared").setTextureName("refamished:cowhide_prepared");
		wooden_club[0] = new WoodenClub(id_clubWooden-256, 20, 1, "wooden_club.oak",0).setTextureName("refamished:pieces/oak_club");
		wooden_club[1] = new WoodenClub(id_clubWoodenSpruce-256, 20, 1, "wooden_club.spruce",1).setTextureName("refamished:pieces/spruce_club");
		wooden_club[2] = new WoodenClub(id_clubWoodenBirch-256, 20, 1, "wooden_club.birch",2).setTextureName("refamished:pieces/birch_club");
		wooden_club[3] = new WoodenClub(id_clubWoodenJungle-256, 20, 1, "wooden_club.jungle",3).setTextureName("refamished:pieces/jungle_club");
		wooden_club_assembling[0] = new WoodenClubAssembling(id_clubWoodenAssembling-256, 0,wooden_club[0]).setUnlocalizedName("assembling_wooden.oak").setTextureName("refamished:pieces/assembling_oak");
		wooden_club_assembling[1] = new WoodenClubAssembling(id_clubWoodenAssemblingSpruce-256, 1, wooden_club[1]).setUnlocalizedName("assembling_wooden.spruce").setTextureName("refamished:pieces/assembling_spruce");
		wooden_club_assembling[2] = new WoodenClubAssembling(id_clubWoodenAssemblingBirch-256, 2, wooden_club[2]).setUnlocalizedName("assembling_wooden.birch").setTextureName("refamished:pieces/assembling_birch");
		wooden_club_assembling[3] = new WoodenClubAssembling(id_clubWoodenAssemblingJungle-256, 3, wooden_club[3]).setUnlocalizedName("assembling_wooden.jungle").setTextureName("refamished:pieces/assembling_jungle");
		death_club[0] = new deathClub(id_clubDeath-256, 42, 4, "death_club.oak",0).setTextureName("refamished:pieces/oak_club");
		death_club[1] = new deathClub(id_clubDeathSpruce-256, 42, 4, "death_club.spruce",1).setTextureName("refamished:pieces/spruce_club");
		death_club[2] = new deathClub(id_clubDeathBirch-256, 42, 4, "death_club.birch",2).setTextureName("refamished:pieces/birch_club");
		death_club[3] = new deathClub(id_clubDeathJungle-256, 42, 4, "death_club.jungle",3).setTextureName("refamished:pieces/jungle_club");
		death_club_assembling[0] = new deathClubAssembling(id_clubDeathAssembling-256, 0,death_club[0]).setUnlocalizedName("assembling_death.oak").setTextureName("refamished:pieces/assembling_oak");
		death_club_assembling[1] = new deathClubAssembling(id_clubDeathAssemblingSpruce-256, 1, death_club[1]).setUnlocalizedName("assembling_death.spruce").setTextureName("refamished:pieces/assembling_spruce");
		death_club_assembling[2] = new deathClubAssembling(id_clubDeathAssemblingBirch-256, 2, death_club[2]).setUnlocalizedName("assembling_death.birch").setTextureName("refamished:pieces/assembling_birch");
		death_club_assembling[3] = new deathClubAssembling(id_clubDeathAssemblingJungle-256, 3, death_club[3]).setUnlocalizedName("assembling_death.jungle").setTextureName("refamished:pieces/assembling_jungle");
		bone_club = new BoneClub(id_clubBone-256, 38, 2, "bone_club").setTextureName("refamished:bone_club");
		heavy_club = new BoneClub(id_heavyClub-256, 52, 3, "heavy_club").setTextureName("refamished:heavy_club");
		bone_club_assembling = new boneClubAssembling(id_assemblingBone-256, 38, bone_club).setUnlocalizedName("assembling_bone").setTextureName("refamished:progressive/bone_club_assembling");
		heavy_club_assembling = new boneClubAssembling(id_assemblingHeavy-256, 52, heavy_club).setUnlocalizedName("assembling_heavy").setTextureName("refamished:progressive/heavy_club_assembling");
		club_component = new clubComponents(id_clubHandle-256);
		flint_machete = new flintMachete(id_flintMachete-256);
		iron_machete = new machete(id_ironMachete-256,4.5F,0.4F, EnumToolMaterial.IRON).setTextureName("refamished:iron_machete").setUnlocalizedName("iron_machete");
		diamond_machete = new machete(id_diamondMachete-256,5.5F,0.4F, EnumToolMaterial.EMERALD).setTextureName("refamished:diamond_machete").setUnlocalizedName("diamond_machete");
		helmetRustIron = new ArmorItemIronRust(id_rustHelmet-256, 0, 3).setUnlocalizedName("helmet_rust_iron").setTextureName("refamished:rusted_iron_helmet");
		plateRustIron = new ArmorItemIronRust(id_rustChestplate-256, 1, 5).setUnlocalizedName("chestplate_rust_iron").setTextureName("refamished:rusted_iron_chestplate");
		legsRustIron = new ArmorItemIronRust(id_rustLeggings-256, 2, 4).setUnlocalizedName("leggings_rust_iron").setTextureName("refamished:rusted_iron_leggings");
		bootsRustIron = new ArmorItemIronRust(id_rustBoots-256, 3, 2).setUnlocalizedName("boots_rust_iron").setTextureName("refamished:rusted_iron_boots");
		rustIronSword = new SwordItem(id_rustSword-256,ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_sword").setTextureName("refamished:rusted_iron_sword");
		rustIronPickaxe = new PickaxeItem(id_rustPickaxe-256,ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_pickaxe").setTextureName("refamished:rusted_iron_pickaxe");
		rustIronAxe = new AxeItem(id_rustAxe-256,ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_axe").setTextureName("refamished:rusted_iron_axe");
		rustIronShovel = new ShovelItem(id_rustShovel-256,ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_shovel").setTextureName("refamished:rusted_iron_shovel");
		rustIronHoe = new HoeItem(id_rustHoe-256,ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_hoe").setTextureName("refamished:rusted_iron_hoe");
		rustIronMachete = new machete(id_rustIronMachete-256,4.5F,0.15F, ReMaterials.RUSTIRON).setUnlocalizedName("rusted_iron_machete").setTextureName("refamished:rusted_iron_machete");
		pile_ashes = new Item(id_pileAsh-256).setUnlocalizedName("pile_ashes").setTextureName("refamished:ash_pile").setCreativeTab(CreativeTabs.tabMaterials);
		flint_machete_assembling = new flintMacheteAssembling(id_flintMacheteAssembling-256, flint_machete).setUnlocalizedName("flint_machete_assembling").setTextureName("refamished:progressive/flint_machete_assembling");
		helmetCopper = new ArmorItemCopper(id_copperHelmet-256, 0, 2).setUnlocalizedName("helmet_copper").setTextureName("refamished:copper_helmet");
		plateCopper = new ArmorItemCopper(id_copperChestplate-256, 1, 4).setUnlocalizedName("chestplate_copper").setTextureName("refamished:copper_chestplate");
		legsCopper = new ArmorItemCopper(id_copperLeggings-256, 2, 3).setUnlocalizedName("leggings_copper").setTextureName("refamished:copper_leggings");
		bootsCopper = new ArmorItemCopper(id_copperBoots-256, 3, 2).setUnlocalizedName("boots_copper").setTextureName("refamished:copper_boots");
		corrodedCopperSword = new SwordItem(id_corrodedCopperSword-256,ReMaterials.CORRODEDCOPPER).setUnlocalizedName("corroded_copper_sword").setTextureName("refamished:corroded_copper_sword");
		corrodedCopperPickaxe = new PickaxeItem(id_corrodedCopperPickaxe-256,ReMaterials.CORRODEDCOPPER).setUnlocalizedName("corroded_copper_pickaxe").setTextureName("refamished:corroded_copper_pickaxe");
		corrodedCopperShovel = new ShovelItem(id_corrodedCopperShovel-256,ReMaterials.CORRODEDCOPPER).setUnlocalizedName("corroded_copper_shovel").setTextureName("refamished:corroded_copper_shovel");
		corrodedCopperAxe = new AxeItem(id_corrodedCopperAxe-256,ReMaterials.CORRODEDCOPPER).setUnlocalizedName("corroded_copper_axe").setTextureName("refamished:corroded_copper_axe");
		corrodedCopperHoe = new HoeItem(id_corrodedCopperHoe-256, ReMaterials.CORRODEDCOPPER).setUnlocalizedName("corroded_copper_hoe").setTextureName("refamished:corroded_copper_hoe");
		coke_coal = new ItemFurnaceBurn(id_cokeCoal-256,FurnaceBurnTime.COAL.burnTime*2).setUnlocalizedName("coke_coal").setTextureName("refamished:coke_coal").setCreativeTab(CreativeTabs.tabMaterials).setIncineratedInCrucible().setFilterableProperties(2);
		helmetDulledGold = new ArmorItemDulledGold(id_dulledGoldHelmet-256, 0, 3).setUnlocalizedName("dulled_gold_helmet").setTextureName("refamished:dulled_gold_helmet");
		plateDulledGold = new ArmorItemDulledGold(id_dulledGoldChestplate-256, 1, 5).setUnlocalizedName("dulled_gold_chestplate").setTextureName("refamished:dulled_gold_chestplate");
		legsDulledGold = new ArmorItemDulledGold(id_dulledGoldLeggings-256, 2, 4).setUnlocalizedName("dulled_gold_leggings").setTextureName("refamished:dulled_gold_leggings");
		bootsDulledGold = new ArmorItemDulledGold(id_dulledGoldBoots-256, 3, 2).setUnlocalizedName("dulled_gold_boots").setTextureName("refamished:dulled_gold_boots");
		dulledGoldSword = new SwordItem(id_dulledGoldSword-256,ReMaterials.DULLEDGOLD).setUnlocalizedName("dulled_gold_sword").setTextureName("refamished:dulled_gold_sword");
		dulledGoldPickaxe = new PickaxeItem(id_dulledGoldPickaxe-256,ReMaterials.DULLEDGOLD).setUnlocalizedName("dulled_gold_pickaxe").setTextureName("refamished:dulled_gold_pickaxe");
		dulledGoldAxe = new AxeItem(id_dulledGoldAxe-256,ReMaterials.DULLEDGOLD).setUnlocalizedName("dulled_gold_axe").setTextureName("refamished:dulled_gold_axe");
		dulledGoldShovel = new ShovelItem(id_dulledGoldShovel-256,ReMaterials.DULLEDGOLD).setUnlocalizedName("dulled_gold_shovel").setTextureName("refamished:dulled_gold_shovel");
		dulledGoldHoe = new HoeItem(id_dulledGoldHoe-256,ReMaterials.DULLEDGOLD).setUnlocalizedName("dulled_gold_hoe").setTextureName("refamished:dulled_gold_hoe");
		scorched_rock = new scorchedRock(id_scorchedRock-256).setCreativeTab(CreativeTabs.tabMaterials);
		scorched_flux = new Item(id_scorchedFlux-256).setUnlocalizedName("scorched_flux").setTextureName("refamished:scorched_flux").setCreativeTab(CreativeTabs.tabMaterials);
		tar_bucket = new tarBucketItem(id_tarBucket-256);
		tar_bottle = new Item(id_tarBottle-256).setUnlocalizedName("tar_bottle").setTextureName("refamished:tar_bottle").setCreativeTab(CreativeTabs.tabMaterials);
		arrow_incendiary = new incendiaryArrow(id_arrowIncendiary-256);
		arrow_flint = new flintArrow(id_arrowFlint-256);
		arrow_gold = new flintArrow(id_arrowGold-256).setUnlocalizedName("arrow_gold").setTextureName("refamished:arrow_gold");
		arrow_frozen = new flintArrow(id_arrowFrost-256).setUnlocalizedName("arrow_frozen").setTextureName("refamished:arrow_frost");
		bolt_bone = new flintArrow(id_boltWood-256).setUnlocalizedName("bolt_bone").setTextureName("refamished:bolt_bone");
		bolt_iron = new flintArrow(id_boltCopper-256).setUnlocalizedName("bolt_iron").setTextureName("refamished:bolt_iron");
		incendiary_bow = new incendiaryBow(id_incendiaryBow-256);
		tar_molotov = new tarMolotov(id_tarMolotov-256);
		tar_molotov_lit = new tarMolotovLit(id_tarMolotovLit-256);
		bow_stringing = new bowStringing(id_bowStringing-256);
		bow_stringingflint = new bowStringingFlint(id_bowStringingFlint-256);
		flint_bow_igniter = new Item(id_flintBowIgniter-256).setUnlocalizedName("flint_bow_igniter").setTextureName("refamished:flint_bow_igniter").setCreativeTab(CreativeTabs.tabMaterials);
		crossbow = new woodenCrossbow(id_woodenCrossbow-256);
		compound_arbalest = new compoundArbalest(id_heavyCrossbow-256);
		branch = new BranchItem(id_branch-256);

		test = new TestingStick(id_testingstick-256).setUnlocalizedName("hi").setTextureName("stick").hideFromEMI();
	}
}