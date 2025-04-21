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
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiSunDry implements EmiRecipe {
    private final EmiIngredient input;
    private final EmiIngredient output;
    private final ItemStack input_;
    private final ItemStack output_;

    public EmiSunDry(ItemStack input, ItemStack output) {
        this.input = RetroEMI.wildcardIngredient(input);
        this.output = RetroEMI.wildcardIngredient(output);
        this.input_ = output;
        this.output_ = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.SUNDRY;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = output_.getItem().itemIcon.getIconName();
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
        return this.output.getEmiStacks();
    }

    @Override
    public int getDisplayWidth() {
        return 90;
    }

    @Override
    public int getDisplayHeight() {
        return 38;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(26, 5, 100000);
        widgets.addTexture(EmiTexture.EMPTY_FLAME, 1, 24);
        widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 1, 24, 100000, false, true, true);
        widgets.addSlot(this.input, 0, 4);
        widgets.addSlot(this.output, 60, 0).large(true).recipeContext(this);
    }
}