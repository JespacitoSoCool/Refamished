package net.fabricmc.refamished;

import btw.block.BTWBlocks;
import btw.block.blocks.OreStorageBlock;
import btw.item.blockitems.SlabBlockItem;
import net.fabricmc.refamished.blocks.*;
import net.fabricmc.refamished.blocks.crops.*;
import net.fabricmc.refamished.blocks.decorative.*;
import net.fabricmc.refamished.blocks.decorative.burnt.*;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;
import net.minecraft.src.ItemBlockWithMetadata;

import static net.minecraft.src.Block.soundMetalFootstep;

public class RefamishedBlocks {

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


    private static final int
            id_freakybob= 3666,
            id_copperChunkground= 3456,
            id_copperChunkStorage= 3457,
            id_cobaltChunkGround= 3458,
            id_cobaltChunkStorage= 3459,
            id_cowhidePreparedGround= 3460,
            id_cowhideReady= 3461,
            id_blueberryBush= 3106,
            id_sweetberryBush= 3107,
            id_wildBush= 3108,
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
            id_wildSpurge= 3121,
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
            id_moved1= 3139,
            id_moved2= 3140,
            id_moved3= 3141,
            id_moved4= 3142,
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
            id_clayMudBrickGroundMud= 3414,
            id_copperBlock= 3415,
            id_cokeOven= 3416,
            id_steelBlock= 3417,
            id_cokeBlock= 3418,
            id_scorchedStone= 3419,
            id_scorchedStoneOre= 3420,
            id_retort_grate= 3421,
            id_tarTank= 3422,
            id_tarTankDummy= 3423,
            id_tarBucket= 3424,
            id_copperSlab= 3425,
            id_copperStair= 3426,
            id_copperDoubleSlab= 3427,
            id_horsehide= 3428,
            id_cranberryBush= 3429,
            id_blackberryBush= 3430,
            id_fallingLeaves= 3431,
            id_charredLeaves= 3432,
            id_charredDirt= 3433,
            id_charredDirtSlab= 3434,
            id_charredGrass= 3434,
            id_charredGrassSlab= 3435,
            id_burntPlanks= 3436,
            id_burntPlanksSlab= 3437,
            id_burntWood= 3439,
            id_burntChest= 3440,
            if_denseStone= 3441,
            id_stoneAnvil= 3442,
            id_copperAnvil= 3443,
            id_steelAnvil= 3444,
            id_workbench= 3445,
            id_gildedIronBlock= 3446
                    ;

    public static Block log;
    public static Block logWorkbench;
    public static Block planks;
    public static Block decorative1Calden;
    public static Block decorative2Calden;
    public static Block decorative1Mimosa;
    public static Block decorative2Mimosa;

    public static soft_brick softBrickGround;
    public static placedSoftClayBrick clayMudBrickGround;
    public static Block clayMudBrickGroundIdle;
    public static Block clayMudBrickGroundMudIdle;
    public static Block softBrickLoose;
    public static Block softBrickLooseSlab;
    public static Block softBrickLooseStairs;
    public static Block softBrickMortar;
    public static Block softBrickMortarSlab;
    public static Block softBrickMortarStairs;
    public static Block redstoneBricks;
    public static Block redstoneBricksSlab;
    public static Block redstoneBricksStairs;
    public static Block paintBrickStone;
    public static Block paintBrickStoneStair;
    public static Block paintBrickStoneSlab;
    public static Block paintBrickStoneLoose;
    public static Block paintBrickStoneStairLoose;
    public static Block paintBrickStoneSlabLoose;
    public static Block paintBrick;
    public static Block paintBrickStair;
    public static Block paintStone;
    public static Block paintCobble;
    public static Block paintCobbleSlab;
    public static Block paintCobbleStairs;
    public static Block paintWood;
    public static Block paintWoodStair;
    public static Block paintWoodSlab;
    public static Block paintWoodSiding;
    public static Block paintWoodMoulding;
    public static Block paintWoodCorner;
    public static Block paintMossyBrick;
    public static Block paintStoneRough1;
    public static Block paintStoneRough2;
    public static Block paintStoneRough3;
    public static Block wildSprurge;
    public static Block cokeOven;
    public static Block scorchedStone;
    public static Block scorchedStoneOre;
    public static Block retort_grate;
    public static Block tar_tank;
    public static Block tar_tankDummy;
    public static Block tar_bucket;
    public static Block stoneAnvil;
    public static Block copperAnvil;
    public static Block SteelAnvil;
    public static Block workbench;
    public static Block gildedIronBlock;

    public static Block blueberryBush;
    public static Block sweetberryBush;
    public static Block cranberryBush;
    public static Block blackberryBush;
    public static Block decayedWood;
    public static Block decayedPlanks;
    public static Block brittlestone;
    public static Block bushWild;

    public static Block copperChunkStorage;
    public static Block copperChunkGround;
    public static Block sinteredChunkStorage;
    public static Block sinteredChunkGround;
    public static Block copperBlock;
    public static Block copperSlab;
    public static Block copperStair;
    public static Block copperDoubleSlab;
    public static Block steelBlock;
    public static Block cokeBlock;
    public static Block cobaltChunkStorage;
    public static Block cobaltChunkGround;

    public static Block cobaltChunkHeavy;
    public static Block ironChunkHeavy;
    public static Block goldChunkHeavy;
    public static Block sinteredChunkHeavy;
    public static Block copperChunkHeavy;

    public static Block chargedBookshelf;
    public static Block frostforgeAnvil;
    public static Block rustRail;
    public static Block rottedWeb;
    public static Block grassThorns;

    public static cowhideTanning preparedCowhideBlock;
    public static horsehideTanning preparedHorseHide;
    public static Block cowhideReady;
    public static Block enchantmentTableOff;
    public static Block lapisWire;
    public static Block lapisWireLit;
    public static Block enchantmentTableReady;
    public static Block brewingStandBlock;
    public static Block fallingLeaves;
    public static Block charredLeaves;
    public static Block charredDirt;
    public static Block charredDirtSlab;
    public static Block charredGrass;
    public static Block burntPlanks;
    public static Block burntPlanksSlab;
    public static Block burntChest;
    public static Block thatch;
    public static Block branch;
    public static Block denseStone;

    public static Block copperOre;
    public static Block flintOre;
    public static Block saltpeterOre;

    // 3456 cobalt
    // 3457 frost
    // 3458 flint (3459 grass)

    public static void registerBlocks() {
        copperOre = new copper_ore(3455);
        flintOre = new flint_ore(3458);
        saltpeterOre = new saltpeter_ore(3454);
        copperChunkGround = new copper_ore_storage_ground(id_copperChunkground);
        copperChunkStorage = new copper_ore_storage(id_copperChunkStorage);
        sinteredChunkGround = new sinter_iron_ore_storage_ground(id_blockIronSintered);
        sinteredChunkStorage = new sinter_iron_ore_storage(id_blockIronSinteredStorage);
        copperBlock = new OreStorageBlock(id_copperBlock).setHardness(5.0f).setResistance(10.0f).setStepSound(soundMetalFootstep).setUnlocalizedName("block_copper").setTextureName("refamished:copper_block");
        copperDoubleSlab = new copperDoubleSlabBlock(id_copperDoubleSlab);
        copperSlab = new copperSlabBlock(id_copperSlab);
        copperStair = new copperStairBlock(id_copperStair);
        steelBlock = new OreStorageBlock(id_steelBlock).setHardness(6.0f).setResistance(15.0f).setStepSound(soundMetalFootstep).setUnlocalizedName("block_steel").setTextureName("refamished:steel_block");
        cokeBlock = new OreStorageBlock(id_cokeBlock).setHardness(5.0f).setResistance(10.0f).setStepSound(BTWBlocks.oreStepSound).setUnlocalizedName("block_coke").setTextureName("refamished:coke_block");
        scorchedStone = new BlockStoneScorched(id_scorchedStone);
        scorchedStoneOre = new BlockStoneScorchedOre(id_scorchedStoneOre);
        retort_grate = new retort_grate(id_retort_grate);
        tar_tankDummy = new tarTankDummy(id_tarTankDummy).hideFromEMI();
        tar_tank = new tarTank(id_tarTank);
        tar_bucket = new bucket_block_tar(id_tarBucket).hideFromEMI();;
        branch = new BranchGround(id_branchBlock);
        wildSprurge = new wildSpurge(id_wildSpurge);
        denseStone = new DenseStone(if_denseStone);
        stoneAnvil = new stoneAnvil(id_stoneAnvil).setCreativeTab(CreativeTabs.tabDecorations);
        copperAnvil = new copperAnvil(id_copperAnvil).setCreativeTab(CreativeTabs.tabDecorations);
        SteelAnvil = new steelAnvil(id_steelAnvil).setCreativeTab(CreativeTabs.tabDecorations);
        workbench = new CraftingBench(id_workbench);
        gildedIronBlock = new OreStorageBlock(id_gildedIronBlock).setHardness(6.0f).setResistance(15.0f).setStepSound(soundMetalFootstep).setUnlocalizedName("block_gilded").setTextureName("refamished:gilded_block");
        //thatch = new bucket_block_tar(id_tarBucket);

        clayMudBrickGround = new placedSoftClayBrick(id_clayMudBrickGround);
        clayMudBrickGroundIdle = new placedReadySoftClayBrick(id_clayMudBrickGroundIdle);
        softBrickGround = new soft_brick(id_softBrickGround);
        softBrickMortar = new soft_bricks(id_softBrickMortar).setCreativeTab(CreativeTabs.tabBlock);
        softBrickMortarSlab = new soft_bricks_slab(id_softBrickMortarSlab).setCreativeTab(CreativeTabs.tabBlock);
        softBrickMortarStairs = new soft_bricks_stair(id_softBrickMortarStairs);
        softBrickLoose = new soft_brick_loose(id_softBrickLoose);
        softBrickLooseSlab = new soft_brick_loose_slab(id_softBrickLooseSlab);
        softBrickLooseStairs = new soft_brick_loose_stair(id_softBrickLooseStairs);
        preparedCowhideBlock = new cowhideTanning(id_cowhidePreparedGround);
        preparedHorseHide = new horsehideTanning(id_horsehide);
        cowhideReady = new cowhideTanningFinished(id_cowhideReady);
        blueberryBush = new blueberryBush(id_blueberryBush).hideFromEMI();
        sweetberryBush = new sweetberryBush(id_sweetberryBush).hideFromEMI();
        bushWild = new wildBush(id_wildBush).hideFromEMI();
        cranberryBush = new cranberryBush(id_cranberryBush).hideFromEMI();
        blackberryBush = new blackberryBush(id_blackberryBush).hideFromEMI();
        charredDirt = new charredDirt(id_charredDirt);
        charredDirtSlab = new charredDirtSlab(id_charredDirtSlab);
        burntPlanks = new woodBurntBlock(id_burntPlanks);
        burntPlanksSlab = new woodBurntSlab(id_burntPlanksSlab);
        //burntChest = new burntChest(id_burntChest);

        cokeOven = new CokeOven(id_cokeOven,false);

        Item.itemsList[copperOre.blockID] = new ItemBlockWithMetadata(copperOre.blockID - 256, copperOre).setUnlocalizedName("ore_copper");
        Item.itemsList[saltpeterOre.blockID] = new ItemBlockWithMetadata(saltpeterOre.blockID - 256, saltpeterOre).setUnlocalizedName("ore_saltpeter");
        Item.itemsList[scorchedStone.blockID] = new ItemBlockWithMetadata(scorchedStone.blockID - 256, scorchedStone);
        Item.itemsList[copperSlab.blockID] = new SlabBlockItem(copperSlab.blockID - 256);
        Item.itemsList[softBrickLooseSlab.blockID] = new SlabBlockItem(softBrickLooseSlab.blockID - 256);
        Item.itemsList[softBrickMortarSlab.blockID] = new SlabBlockItem(softBrickMortarSlab.blockID - 256);
        Item.itemsList[burntPlanksSlab.blockID] = new SlabBlockItem(burntPlanksSlab.blockID - 256);

        Block.deadBush.setHardness(0.2F);
        Block.deadBush.setAxesEffectiveOn();

    }
}
