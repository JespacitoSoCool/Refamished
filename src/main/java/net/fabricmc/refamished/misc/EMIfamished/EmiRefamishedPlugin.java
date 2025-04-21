package net.fabricmc.refamished.misc.EMIfamished;

import btw.block.BTWBlocks;
import btw.crafting.manager.CauldronCraftingManager;
import btw.crafting.manager.CrucibleStokedCraftingManager;
import btw.crafting.recipe.RecipeManager;
import btw.crafting.recipe.types.BulkRecipe;
import btw.item.BTWItems;
import emi.dev.emi.emi.api.EmiEntrypoint;
import emi.dev.emi.emi.api.EmiPlugin;
import emi.dev.emi.emi.api.EmiRegistry;
import emi.dev.emi.emi.api.plugin.BTWPlugin;
import emi.dev.emi.emi.api.recipe.BTWEmiRecipeCategories;
import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.recipe.EmiWorldInteractionRecipe;
import emi.dev.emi.emi.api.render.EmiTexture;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.recipe.btw.EmiBulkRecipe;
import emi.dev.emi.emi.recipe.btw.EmiProgressiveRecipe;
import emi.dev.emi.emi.runtime.EmiReloadLog;
import emi.shims.java.net.minecraft.text.Text;
import emi.shims.java.net.minecraft.util.SyntheticIdentifier;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.fabricmc.refamished.misc.Recipes.PercentageBasedSmelting;
import net.minecraft.src.*;

import java.util.List;
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

        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.stoneSharpening, new ItemStack(BTWItems.sharpStone)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.flintKnapping, new ItemStack(RefamishedItems.flintBlade)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingAxeHead, new ItemStack(RefamishedItems.chippedAxeHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingShovelHead, new ItemStack(RefamishedItems.chippedShovelHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingHoeHead, new ItemStack(RefamishedItems.chippedHoeHead)));
        addRecipeSafe(registry,()->new EmiKnapping((craftingPulling) RefamishedItems.chippingPickaxeHead, new ItemStack(RefamishedItems.chippedPickaxeHead)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.leatherCuttingFlint), new ItemStack(BTWItems.cutLeather,2)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.leatherCuttingIron), new ItemStack(BTWItems.cutLeather,2)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.ribCarvingFlint), new ItemStack(RefamishedItems.rib)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.ribCarvingIron), new ItemStack(RefamishedItems.rib)));
        addRecipeSafe(registry, () -> new EmiProgressiveRecipe(new ResourceLocation("btw", ""), new ItemStack(RefamishedItems.clay_mixing), new ItemStack(RefamishedItems.clay_mud)));
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

        addRecipeSafe(registry,()->new EmiSunDry(new ItemStack(RefamishedItems.soft_clay_brick), new ItemStack(RefamishedItems.soft_brick)));
        addRecipeSafe(registry,()->new EmiSunDry(new ItemStack(RefamishedItems.cowhide_prepared), new ItemStack(Item.leather)));
        addRecipeSafe(registry,()->new EmiHardening(new ItemStack(RefamishedItems.soft_brick), new ItemStack(Item.brick)));

        for (CokeOvenSmeltingRecipes.RecipeEntry bulkRecipe : CokeOvenSmeltingRecipes.getInstance().getRecipeList()) {
            addRecipeSafe(registry,()->new EmiCokeOvenRecipe(bulkRecipe.input, bulkRecipe.output,bulkRecipe));
        }
        for (BulkRecipe bulkRecipe : PercentageBasedSmelting.getInstance().getRecipeList()) {
            List<ItemStack> input = bulkRecipe.getCraftingIngrediantList();
            List<ItemStack> list = bulkRecipe.getCraftingOutputList();
            addRecipeSafe(registry,()->new EmiPercentageBasedSmelt(input.getFirst(), list));
        }
    }

    static {
        RefamishedRecipeCategories.KNAPPING = category("knapping", EmiStack.of(RefamishedItems.stoneSharpening));
        RefamishedRecipeCategories.SUNDRY = category("sundry", EmiStack.of(RefamishedItems.soft_brick));
        RefamishedRecipeCategories.HARDENING = category("hardening", EmiStack.of(Item.brick));
        RefamishedRecipeCategories.COKEOVEN = category("cokeoven", EmiStack.of(RefamishedBlocks.cokeOven));
        RefamishedRecipeCategories.PERCENTAGESMELT = category("percentage_smelt", EmiStack.of(BTWBlocks.crucible));
    }

    public static EmiRecipeCategory category(String id, EmiStack icon) {
        return new EmiRecipeCategory(new ResourceLocation("refamished", id), icon, new EmiTexture(new ResourceLocation("emi", "textures/simple_icons/" + id + ".png"), 0, 0, 16, 16, 16, 16, 16, 16));
    }
}