package net.fabricmc.refamished.misc.EMIfamished;

import btw.block.BTWBlocks;
import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.BulkRecipe;
import btw.crafting.recipe.types.customcrafting.WoolArmorRecipe;
import btw.crafting.recipe.types.customcrafting.WoolBlockRecipe;
import btw.item.BTWItems;
import emi.dev.emi.emi.EmiUtil;
import emi.dev.emi.emi.api.EmiEntrypoint;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.plugin.BTWPlugin;
import emi.dev.emi.emi.api.plugin.VanillaPlugin;
import emi.dev.emi.emi.api.recipe.*;
import emi.dev.emi.emi.api.render.EmiTexture;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.recipe.EmiCookingRecipe;
import emi.dev.emi.emi.recipe.EmiShapedRecipe;
import emi.dev.emi.emi.recipe.EmiShapelessRecipe;
import emi.dev.emi.emi.recipe.btw.EmiBulkRecipe;
import emi.dev.emi.emi.recipe.btw.EmiProgressiveRecipe;
import emi.dev.emi.emi.recipe.special.*;
import emi.dev.emi.emi.runtime.EmiReloadLog;
import emi.shims.java.net.minecraft.text.Text;
import emi.shims.java.net.minecraft.util.SyntheticIdentifier;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.fabricmc.refamished.misc.CustomRecipes.RecipesArmorPigment;
import net.fabricmc.refamished.misc.Recipes.*;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@EmiEntrypoint
public class EmiRefamishedPlugin implements EmiPlugin {
    public static final ResourceLocation WIDGETS = new ResourceLocation("emi", "textures/recipe/btwwidgets.png");
    public static final EmiTexture SMALL_PLUS = new EmiTexture(WIDGETS, 36, 0, 7, 7);

    private static void addRecipeSafe(EmiRegistry registry, Supplier<EmiRecipe> supplier) {
        try {
            registry.addRecipe(supplier.get());
        } catch (Throwable e) {
            EmiReloadLog.warn("Exception when parsing EMI recipe (no ID available)");
            EmiReloadLog.error(e);
        }
    }

    @Override
    public void register(EmiRegistry registry) {
        System.out.println("RAN EMI REFAMISHED HOLY SHIT -------------------------------------------------------------");
        // Tell EMI to add a tab for your category
        registry.addCategory(RefamishedRecipeCategories.KNAPPING);
        registry.addCategory(RefamishedRecipeCategories.SUNDRY);
        registry.addCategory(RefamishedRecipeCategories.HARDENING);
        registry.addCategory(RefamishedRecipeCategories.COKEOVEN);
        registry.addCategory(RefamishedRecipeCategories.PERCENTAGESMELT);
        registry.addCategory(RefamishedRecipeCategories.QUALITIES);
        registry.addCategory(RefamishedRecipeCategories.QUALITIESARMOR);
        registry.addCategory(RefamishedRecipeCategories.SMITHING);

        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.stoneSharpening, new ItemStack(BTWItems.sharpStone)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.flintKnapping, new ItemStack(RefamishedItems.flintBlade)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingAxeHead, new ItemStack(RefamishedItems.chippedAxeHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingShovelHead, new ItemStack(RefamishedItems.chippedShovelHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingHoeHead, new ItemStack(RefamishedItems.chippedHoeHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingPickaxeHead, new ItemStack(RefamishedItems.chippedPickaxeHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingHammerHead, new ItemStack(RefamishedItems.chippedHammerHead)));
        //addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.bow_stringing, new ItemStack(Item.bow)));
        //addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.bow_stringingflint, new ItemStack(RefamishedItems.incendiary_bow)));
        //addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.leatherCuttingFlint), new ItemStack(BTWItems.cutLeather,2)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.leatherCuttingIron), new ItemStack(BTWItems.cutLeather,2)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.ribCarvingFlint), new ItemStack(RefamishedItems.rib)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.ribCarvingIron), new ItemStack(RefamishedItems.rib)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.leatherCuttingMachete), new ItemStack(BTWItems.cutLeather,2)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.ribCarvingMachete), new ItemStack(RefamishedItems.rib)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.sugarCaneCuttingFlint), new ItemStack(RefamishedItems.cut_sugar_cane)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.sugarCaneCuttingIron), new ItemStack(RefamishedItems.cut_sugar_cane)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.sugarCaneCuttingMachete), new ItemStack(RefamishedItems.cut_sugar_cane)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.clay_mixing), new ItemStack(RefamishedItems.clay_mud)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.sugar_resin_mixing), new ItemStack(RefamishedItems.sugar_resin)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.sugar_cane_crushing), new ItemStack(RefamishedItems.crude_sugar)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.flint_sharpenerWool), new ItemStack(RefamishedItems.wool_string, 1, Short.MAX_VALUE)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.flint_sharpenerStone), new ItemStack(BTWItems.sharpStone)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.flint_sharpenerFlint), new ItemStack(RefamishedItems.flintBlade)));
        for (int i = 0; i < 4; i++) {
            int finalI = i;
            addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.wooden_club_assembling[finalI]), new ItemStack(RefamishedItems.wooden_club[finalI])));
            addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.death_club_assembling[finalI]), new ItemStack(RefamishedItems.death_club[finalI])));
        }
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.bone_club_assembling), new ItemStack(RefamishedItems.bone_club)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", "knitting"), new ItemStack(RefamishedItems.heavy_club_assembling), new ItemStack(RefamishedItems.heavy_club)));
        registry.addRecipe(EmiWorldInteractionRecipe.builder().id(new ResourceLocation("emi", "/world/block_interaction/btw/")).leftInput(EmiStack.of(RefamishedItems.clay_bowl)).rightInput(EmiStack.of(Block.waterMoving), false).output(EmiStack.of(RefamishedItems.clay_bowl_water)).supportsRecipeTree(true).build());
        registry.addRecipe(EmiWorldInteractionRecipe.builder().id(new ResourceLocation("emi", "/world/block_interaction/btw/")).leftInput(EmiStack.of(RefamishedItems.soft_clay_brick)).rightInput(EmiStack.of(RefamishedItems.stone_trowel), false).output(EmiStack.of(RefamishedItems.soft_clay_brick)).supportsRecipeTree(true).build());
        registry.addRecipe(EmiWorldInteractionRecipe.builder().id(new ResourceLocation("emi", "/world/block_interaction/btw/")).leftInput(EmiStack.of(RefamishedItems.arrow_flint)).rightInput(EmiStack.of(RefamishedBlocks.tar_tank), false).output(EmiStack.of(RefamishedItems.arrow_incendiary)).supportsRecipeTree(true).build());

        info(registry, RefamishedItems.copperChisel, "ref.copper_chisel.info");
        info(registry, RefamishedItems.copper_machete, "ref.machete.info");
        info(registry, RefamishedItems.iron_machete, "ref.machete.info");
        info(registry, RefamishedItems.gold_machete, "ref.machete.info");
        info(registry, RefamishedItems.gilded_machete, "ref.machete.info");
        info(registry, RefamishedItems.diamond_machete, "ref.machete.info");
        info(registry, RefamishedItems.flint_machete, "ref.machete.info");
        info(registry, RefamishedBlocks.cokeOven, "ref.coke_oven.info");
        info(registry, RefamishedBlocks.tar_tank, "ref.tar_tank.info");
        info(registry, RefamishedItems.arrow_gold, "ref.golden_arrow.info");
        info(registry, RefamishedBlocks.stoneAnvil, "ref.anvil.info");
        info(registry, RefamishedBlocks.copperAnvil, "ref.anvil.info");
        info(registry, RefamishedBlocks.SteelAnvil, "ref.anvil.info");
        info(registry, RefamishedItems.soft_brick, "ref.soft_brick.info");
        info(registry, RefamishedItems.soft_clay_brick, "ref.soft_brick_clay.info");
        info(registry, RefamishedItems.UnfiredIngotMold, "ref.mold_unfired.info");
        info(registry, RefamishedItems.ingotMold, "ref.mold.info");
        info(registry, RefamishedItems.crushedOres, "ref.crushed_ore.info");
        info(registry, RefamishedItems.rawIngot, "ref.raw_ingot.info");
        info(registry, RefamishedItems.arrow_incendiary, "ref.incendiary_arrow.info");
        info(registry, RefamishedItems.scorched_rock, "ref.scorched.info");
        info(registry, RefamishedItems.saltpeter, "ref.saltpeter.info");
        info(registry, RefamishedItems.stone_trowel, "ref.trowel.info");
        info(registry, RefamishedItems.copper_trowel, "ref.trowel.info");
        info(registry, RefamishedItems.iron_trowel, "ref.trowel.info");

        for (CokeOvenSmeltingRecipes.RecipeEntry bulkRecipe : CokeOvenSmeltingRecipes.getInstance().getRecipeList()) {
            addRecipeSafe(registry,()->new EmiCokeOvenRecipe(bulkRecipe.input, bulkRecipe.output,bulkRecipe));
        }
        for (BulkRecipe bulkRecipe : PercentageBasedSmelting.getInstance().getRecipeList()) {
            List<ItemStack> input = bulkRecipe.getCraftingIngrediantList();
            List<ItemStack> list = bulkRecipe.getCraftingOutputList();
            addRecipeSafe(registry,()->new EmiPercentageBasedSmelt(input.getFirst(), list));
        }
        for (Object iRecipe : registry.getRecipeManager().getRecipes()) {
            IRecipe recipe = (IRecipe)iRecipe;
            if (iRecipe instanceof RecipesArmorPigment) {
                RecipesArmorPigment dye = (RecipesArmorPigment)recipe;
                for (Item i3 : EmiArmorPigmentRecipe.DYEABLE_ITEMS) {
                    addRecipeSafe(registry, () -> new EmiArmorPigmentRecipe(i3, synthetic("crafting/dying", EmiUtil.subId(i3))));
                }
                continue;
            }
            BTWPlugin.addCustomIRecipes(recipe, registry);
        }

        addRecipeSafe(registry,()->new EmiSunDry(new ItemStack(RefamishedItems.soft_clay_brick), new ItemStack(RefamishedItems.soft_brick)));
        addRecipeSafe(registry,()->new EmiSunDry(new ItemStack(RefamishedItems.cowhide_prepared), new ItemStack(Item.leather)));
        addRecipeSafe(registry,()->new EmiHardening(new ItemStack(RefamishedItems.soft_brick), new ItemStack(Item.brick)));

        int totalWeightTool = Arrays.stream(ToolQuality.values()).mapToInt(ToolQuality::getWeight).sum();

        for (ToolQuality quality : ToolQuality.values()) {
            if (quality.getWeight() > 0) {
                registry.addRecipe(new EmiOtherQuality(quality, totalWeightTool));
            }
        }
        int totalWeightArmor = Arrays.stream(ArmorQuality.values()).mapToInt(ArmorQuality::getWeight).sum();
        for (ArmorQuality quality : ArmorQuality.values()) {
            if (quality.getWeight() > 0) {
                registry.addRecipe(new EmiOtherQuality(quality, totalWeightArmor));
            }
        }

        for (Object entryobj : FurnaceMetaRecipes.getInstance().getRecipeList()) {
            FurnaceMetaRecipes.RecipeEntry entry = (FurnaceMetaRecipes.RecipeEntry)entryobj;
            ItemStack in = entry.input;
            ItemStack out = entry.output;
            int cookTimeShift = FurnaceMetaRecipes.getInstance().getCookTimeBinaryShift(in);
            addRecipeSafe(registry, () -> new EmiCookingRecipe(new ResourceLocation("minecraft", "oven"), in, out, VanillaEmiRecipeCategories.SMELTING, cookTimeShift, false));
        }

        for (ForgingPlansRecipes.RecipeEntry entryobj : ForgingPlansRecipes.getInstance().getRecipeList()) {
            ForgingPlansRecipes.RecipeEntry entry = entryobj;
            List<ItemStack> in = entry.inputs;
            ItemStack out = entry.output;
            addRecipeSafe(registry, () -> new EmiSmithingRecipe( in, out));
        }
        for (SmithingRecipes.RecipeEntry entryobj : SmithingRecipes.getInstance().getRecipeList()) {
            SmithingRecipes.RecipeEntry entry = entryobj;
            ItemStack in = entry.input;
            ItemStack[] out = entry.firstOutput;
            addRecipeSafe(registry, () -> new EmiSmithing( in, out));
        }
    }

    private void info(EmiRegistry registry, Item item, String info) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiStack.of(item)), List.of(Text.translatable(info)), null));
    }

    private void info(EmiRegistry registry, Item item, int metadata, String info) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiStack.of(new ItemStack(item, 1, metadata))), List.of(Text.translatable(info)), null));
    }

    private void info(EmiRegistry registry, Block block, String info) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiStack.of(block)), List.of(Text.translatable(info)), null));
    }

    private void info(EmiRegistry registry, Block block, int metadata, String info) {
        registry.addRecipe(new EmiInfoRecipe(List.of(EmiStack.of(new ItemStack(block, 1, metadata))), List.of(Text.translatable(info)), null));
    }

    static {
        RefamishedRecipeCategories.KNAPPING = category("knapping", EmiStack.of(RefamishedItems.stoneSharpening));
        RefamishedRecipeCategories.SUNDRY = category("sundry", EmiStack.of(RefamishedItems.soft_brick));
        RefamishedRecipeCategories.HARDENING = category("hardening", EmiStack.of(Item.brick));
        RefamishedRecipeCategories.COKEOVEN = category("cokeoven", EmiStack.of(RefamishedBlocks.cokeOven));
        RefamishedRecipeCategories.PERCENTAGESMELT = category("percentage_smelt", EmiStack.of(BTWBlocks.crucible));
        RefamishedRecipeCategories.QUALITIES = category("qualities.tool", EmiStack.of(Item.swordIron));
        RefamishedRecipeCategories.QUALITIESARMOR = category("qualities.armor", EmiStack.of(Item.plateIron));
        RefamishedRecipeCategories.SMITHING = category("smithing", EmiStack.of(RefamishedBlocks.copperAnvil));
    }

    public static EmiRecipeCategory category(String id, EmiStack icon) {
        return new EmiRecipeCategory(new ResourceLocation("refamished", id), icon, new EmiTexture(new ResourceLocation("emi", "textures/simple_icons/" + id + ".png"), 0, 0, 16, 16, 16, 16, 16, 16));
    }
    private static ResourceLocation synthetic(String type, String name) {
        return new ResourceLocation("emi", "/" + type + "/" + name);
    }
}