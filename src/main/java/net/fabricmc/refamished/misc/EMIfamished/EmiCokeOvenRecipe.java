package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.EmiPort;
import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.render.EmiTexture;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import emi.shims.java.net.minecraft.client.gui.tooltip.TooltipComponent;
import emi.shims.java.net.minecraft.text.Text;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.fabricmc.refamished.misc.Recipes.CokeOvenSmeltingRecipes;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiCokeOvenRecipe implements EmiRecipe {
    private final EmiIngredient input;
    private final EmiStack output;
    private final CokeOvenSmeltingRecipes.RecipeEntry rep_;
    private final ItemStack Input_;
    private final ItemStack Output_;

    public EmiCokeOvenRecipe(ItemStack input, ItemStack output, CokeOvenSmeltingRecipes.RecipeEntry rep) {
        this.input = EmiStack.of(input);
        this.output = EmiStack.of(output);
        this.rep_ = rep;
        Input_ = input;
        Output_ = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.COKEOVEN;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = Output_.getItem().itemIcon.getIconName();
        String[] parts = string.split(":");
        if (parts.length == 1)
        {
            return new ResourceLocation(string);
        }
        String mod = parts[0];
        String texturename = parts[1];
        return new ResourceLocation(mod, texturename);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(this.input);
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(this.output);
    }

    @Override
    public int getDisplayWidth() {
        return 82;
    }

    @Override
    public int getDisplayHeight() {
        return 38;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int duration = this.rep_.cookTimeTicks;
        widgets.addFillingArrow(24, 5, 10000).tooltip((mx, my) -> List.of(TooltipComponent.of(EmiPort.ordered(EmiPort.translatable("emi.cooking.time", duration / 20)))));
        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 24, duration * 20, false, true, true);
        widgets.addText(Text.translatable("emi.refamished.cokeoven.level", this.rep_.minFireLevel),17,27,0xff0000,true);
        widgets.addSlot(this.input, 0, 4);
        widgets.addSlot(this.output, 56, 0).large(true).recipeContext(this);
    }
}