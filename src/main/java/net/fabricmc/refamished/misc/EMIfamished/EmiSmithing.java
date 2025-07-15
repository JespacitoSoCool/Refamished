package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.shims.java.com.unascribed.retroemi.ItemStacks;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import emi.shims.java.net.minecraft.text.Text;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiSmithing implements EmiRecipe {
    private final EmiIngredient input;
    private final EmiStack[] output;
    private final ItemStack input_;
    private final ItemStack[] output_;

    public EmiSmithing(ItemStack input, ItemStack[] output) {
        this.input = RetroEMI.wildcardIngredient(input);
        this.output = EmiSmithingRecipe.ItemArrayToEmi(output);
        this.input_ = input;
        this.output_ = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.SMITHING;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = output_[0].getIconIndex().getIconName();
        String[] parts = string.split(":");
        String mod = parts[0];
        String texturename = parts[0];
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
        return 132;
    }

    @Override
    public int getDisplayHeight() {
        return 48;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int i;
        widgets.addFillingArrow(55, 2, 10000);
        widgets.addSlot(input, 0, 4).large(true).recipeContext(this);
        for (i = 0; i < this.output.length; ++i) {
            widgets.addSlot(this.output[i], 80 + i % 2 * 26, i / 2 * 26).large(true).recipeContext(this);
        }
    }
}