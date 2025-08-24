package net.fabricmc.refamished.misc.Recipes.Addons;

import btw.crafting.manager.CauldronStokedCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.RecipeManager;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.metalSheets;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.items.materials.metallurgyHeads;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.fabricmc.refamished.misc.Recipes.SmithingRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Unique;

import java.util.Arrays;
import java.util.List;

public class NmRecipes {
    public static void NightmareModding() {
        Item[] nmItems = RefamishedItems.nmItems;
        Item[] nmItemsAdds = RefamishedItems.nmAdd;
        Item bloodIngot = nmItems[0];
        Item bloodOrb = nmItems[10];
        addArmorSlightlyManual(new Item[] {nmItems[1],nmItems[2],nmItems[3],nmItems[4]},"blood",15);
        addForgePlanRecipe(Arrays.asList(new ItemStack(bloodIngot)),
                new ItemStack(RefamishedItems.sheet,1,6), 100,3);

        addForPlanRecipes(bloodIngot,null,9,"blood",3);
        ItemStack bloodRivet = new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_rivet"));
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_sword"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_crossguard"))),
                new ItemStack(nmItems[9]), 150,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_pickaxe"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItems[5]), 200,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_axe"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItems[6]), 150,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_shovel"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItems[7]), 125,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_hoe"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItems[8]), 125,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_machete"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItemsAdds[3]), 200,3);

        addForgePlanRecipe(Arrays.asList(new ItemStack(bloodIngot)),
                new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("tong_blood")), 75,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(bloodIngot)),
                new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_hammer")), 75,2);
        addForgePlanRecipe(Arrays.asList(new ItemStack(bloodIngot),new ItemStack(BTWItems.cutLeather)),
                new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_shear_body")), 100);
        addForgePlanRecipe(Arrays.asList(new ItemStack(bloodIngot)),
                new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("blood_shear_razor")), 100);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_shear_body"))
                        ,new ItemStack(RefamishedItems.metallurgyHeads,2,getHeadIndexByString("blood_shear_razor")),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItemsAdds[2]), 150);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_blood")),
                        new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("tong_blood"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItemsAdds[1]), 150,2);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyHeads,1,getHeadIndexByString("blood_hammer"))
                        ,new ItemStack(Item.stick),new ItemStack(RefamishedItems.sugar_resin),bloodRivet),
                new ItemStack(nmItemsAdds[0]), 150);

        addForgePlanRecipe(Arrays.asList(new ItemStack(BTWItems.diamondIngot,3),new ItemStack(bloodOrb,24),new ItemStack(BTWItems.rawMysteryMeat,2),new ItemStack(BTWItems.soulUrn)),
                new ItemStack(bloodIngot,2), 150,3);

        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(Item.leather)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutTannedLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.tannedLeather)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.cutScouredLeather, 2), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.scouredLeather)});
        RecipeManager.addShapelessRecipe(new ItemStack(BTWItems.leatherStrap, 4), new Object[]{new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE), new ItemStack(BTWItems.cutTannedLeather)});
        RecipeManager.addShapelessRecipe(new ItemStack(Item.silk, 2), new Object[]{new ItemStack(BTWItems.tangledWeb), new ItemStack(RefamishedItems.copperShears, 1, Short.MAX_VALUE)});

        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[11],1), new Object[]{"# #", "# #", "#X#", Character.valueOf('#'), BTWItems.ironNugget, Character.valueOf('X'), Item.silk});

        RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.wickerPane,1), new Object[]{new ItemStack(nmItems[11],1,Short.MAX_VALUE),Item.reed,Item.reed,Item.reed,Item.reed});
        RecipeManager.removeVanillaShapelessRecipe(new ItemStack(Item.silk,1), new Object[]{new ItemStack(nmItems[11],1,Short.MAX_VALUE),BTWItems.tangledWeb});
        RecipeManager.removeVanillaRecipe(new ItemStack(bloodIngot), new Object[]{" # ", "#X#", " # ", Character.valueOf('#'), new ItemStack(bloodOrb), Character.valueOf('X'), new ItemStack(BTWItems.diamondIngot)});
        for (int i = 0; i < 16; i++) {
            RecipeManager.removeVanillaShapelessRecipe(new ItemStack(BTWItems.woolKnit,1, i), new Object[]{new ItemStack(nmItems[11],1,Short.MAX_VALUE),new ItemStack(BTWItems.wool, 1, i),new ItemStack(BTWItems.wool, 1, i)});
        }
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[1]), new Object[]{"###", "# #", Character.valueOf('#'), new ItemStack(bloodIngot)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[2]), new Object[]{"# #", "###", "###", Character.valueOf('#'), new ItemStack(bloodIngot)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[3]), new Object[]{"###", "# #", "# #", Character.valueOf('#'), new ItemStack(bloodIngot)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[4]), new Object[]{"# #", "# #",  Character.valueOf('#'), new ItemStack(bloodIngot)});

        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[9]), new Object[]{" # ", "###", " X ", Character.valueOf('#'), new ItemStack(bloodIngot), Character.valueOf('X'), new ItemStack(Item.stick)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[5]), new Object[]{"###", " X ", " X ", Character.valueOf('#'), new ItemStack(bloodIngot), Character.valueOf('X'), new ItemStack(Item.stick)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[6]), new Object[]{"#  ", "#X ", " X ", Character.valueOf('#'), new ItemStack(bloodIngot), Character.valueOf('X'), new ItemStack(Item.stick)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[7]), new Object[]{" # ", " X ", " X ", Character.valueOf('#'), new ItemStack(bloodIngot), Character.valueOf('X'), new ItemStack(Item.stick)});
        RecipeManager.removeVanillaRecipe(new ItemStack(nmItems[8]), new Object[]{"#X ", " X ", " X ", Character.valueOf('#'), new ItemStack(bloodIngot), Character.valueOf('X'), new ItemStack(Item.stick)});

        removeStokedCrucible(new ItemStack(BTWItems.soulforgedSteelIngot, 1), new ItemStack[]{new ItemStack(Item.ingotIron, 1), new ItemStack(BTWItems.coalDust, 1), new ItemStack(BTWItems.soulUrn, 1), new ItemStack(BTWItems.enderSlag, 1)});
        removeStokedCrucible(new ItemStack(BTWItems.soulforgedSteelIngot, 2), new ItemStack[]{new ItemStack(RefamishedItems.steelIngot, 5), new ItemStack(RefamishedItems.scorched_flux, 64), new ItemStack(BTWItems.soulUrn, 3), new ItemStack(BTWItems.soulFlux, 12), new ItemStack(BTWItems.rawMysteryMeat, 1), new ItemStack(Item.blazePowder, 1)});
        removeStokedCrucible(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]
                {new ItemStack(Item.ingotIron),new ItemStack(RefamishedItems.scorched_flux,12),new ItemStack(RefamishedItems.coke_coal,2)});
        removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(Item.slimeBall)});
        removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(RefamishedItems.saltpeter,22),new ItemStack(BTWItems.glue)});
        removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(BTWItems.nitre,22),new ItemStack(Item.slimeBall)});
        removeStokedCrucible(new ItemStack(BTWItems.diamondIngot, 2), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,2),new ItemStack(Item.diamond,2),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(BTWItems.nitre,22),new ItemStack(BTWItems.glue)});

        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.soulforgedSteelIngot), new ItemStack[]{new ItemStack(RefamishedItems.steelIngot, 8), new ItemStack(RefamishedItems.scorched_flux, 64), new ItemStack(BTWItems.soulUrn, 5), new ItemStack(BTWItems.enderSlag, 40), new ItemStack(RefamishedItems.nmItems[0], 2), new ItemStack(Item.blazePowder, 24)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(RefamishedItems.steelIngot), new ItemStack[]
                {new ItemStack(Item.ingotIron),new ItemStack(RefamishedItems.scorched_flux,42),new ItemStack(RefamishedItems.coke_coal,8)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,6),new ItemStack(Item.diamond,5),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(RefamishedItems.saltpeter,48),new ItemStack(Item.slimeBall,22),new ItemStack(Item.blazePowder,4)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,6),new ItemStack(Item.diamond,5),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(RefamishedItems.saltpeter,48),new ItemStack(RefamishedItems.sugar_resin,12),new ItemStack(Item.blazePowder,4)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,6),new ItemStack(Item.diamond,5),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(BTWItems.nitre,48),new ItemStack(Item.slimeBall,22),new ItemStack(Item.blazePowder,4)});
        RecipeManager.addStokedCrucibleRecipe(new ItemStack(BTWItems.diamondIngot, 3), new ItemStack[]
                {new ItemStack(RefamishedItems.steelIngot,6),new ItemStack(Item.diamond,5),new ItemStack(BTWItems.creeperOysters,5)
                        ,new ItemStack(BTWItems.nitre,48),new ItemStack(RefamishedItems.sugar_resin,12),new ItemStack(Item.blazePowder,4)});
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
    private static void addForgePlanRecipe(List<ItemStack> input, ItemStack output1, int shatter)
    {
        ForgingPlansRecipes.getInstance().addRecipe(input, output1, shatter);
    }

    @Unique
    private static void addForgePlanRecipe(List<ItemStack> input, ItemStack output1, int shatter, int anvilLevel)
    {
        ForgingPlansRecipes.getInstance().addRecipe(input, output1, shatter,anvilLevel);
    }

    private static int[] materialCost = new int[]{2,3,3,5,4,3,2,2};
    private static int[] materialHard = new int[]{4,5,5,7,5,5,4,4};

    private static void addArmorSlightlyManual(Item[] armor, String name_, int hardnessSet) {
        String[] heads = new String[]{"helm","coif","pauldrons","cuirass","tassets","greaves","threads","sabatons"};
        int sheetId = metalSheets.getPartIndex(name_);
        for (int jh = 0; jh < heads.length; jh++) {
            String part = heads[jh];
            int cost = materialCost[jh];
            int hardness = materialHard[jh];
            addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.sheet,cost,sheetId)),
                    new ItemStack(RefamishedItems.metallurgyArmor,1, metallurgyArmor.getPartIndex(name_+"_"+part)), hardness*hardnessSet,3);
        }
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_helm")),
                        new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_coif")),
                        new ItemStack(RefamishedItems.sugar_resin)),
                new ItemStack(armor[0]), 7*hardnessSet,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_pauldrons")),
                        new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_cuirass")),
                        new ItemStack(RefamishedItems.sugar_resin)),
                new ItemStack(armor[1]), 10*hardnessSet,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_tassets")),
                        new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_greaves")),
                        new ItemStack(RefamishedItems.sugar_resin)),
                new ItemStack(armor[2]), 8*hardnessSet,3);
        addForgePlanRecipe(Arrays.asList(new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_threads")),
                        new ItemStack(RefamishedItems.metallurgyArmor,1,metallurgyArmor.getPartIndex(name_+"_sabatons")),
                        new ItemStack(RefamishedItems.sugar_resin)),
                new ItemStack(armor[3]), 6*hardnessSet,3);
    }

    @Unique
    private static int getHeadIndexByString(String head)
    {
        return metallurgyHeads.getPartIndex(head);
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
}
