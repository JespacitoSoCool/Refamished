package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.render.EmiTexture;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.dev.emi.emi.recipe.btw.EmiBulkRecipe;
import emi.shims.java.com.unascribed.retroemi.ItemStacks;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiPercentageBasedSmelt implements EmiRecipe {
    private final EmiIngredient input;
    private final List<EmiStack> output;
    private final ItemStack input_;
    private final List<ItemStack> output_;
    private final ResourceLocation HIBACHI = new ResourceLocation("btw", "textures/blocks/hibachi.png");

    public EmiPercentageBasedSmelt(ItemStack input, List<ItemStack> output) {
        this.input = RetroEMI.wildcardIngredient(input);
        this.output = EmiBulkRecipe.convertOutput(output);
        this.input_ = input;
        this.output_ = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.PERCENTAGESMELT;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = output_.get(0).getItem().itemIcon.getIconName();
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
        return this.output;
    }

    @Override
    public int getDisplayWidth() {
        return 132;
    }

    @Override
    public int getDisplayHeight() {
        return 62;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int i;
        widgets.addFillingArrow(55, 2, 10000);
        widgets.addTexture(this.HIBACHI, 61, 36, 16, 16, 0, 0, 16, 16, 16, 16);
        widgets.addSlot(EmiStack.of(new ItemStack(251, 1, 0)), 60, 19).drawBack(false);
        for (i = 0; i < 1; ++i) {
            widgets.addSlot(this.input, i % 2 * 26, 4 + i / 2 * 26).large(true);
        }
        for (i = 0; i < this.output.size(); ++i) {
            widgets.addSlot(this.output.get(i), 80 + i % 2 * 26, i / 2 * 26).large(true).recipeContext(this);
        }
    }
}