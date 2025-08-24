package net.fabricmc.refamished.misc.EMIfamished;

import btw.block.BTWBlocks;
import com.google.common.collect.Lists;
import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.dev.emi.emi.recipe.btw.EmiBulkRecipe;
import emi.shims.java.com.unascribed.retroemi.ItemStacks;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import emi.shims.java.net.minecraft.text.Text;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EmiSmithingRecipe implements EmiRecipe {
    final List<EmiIngredient> input;
    final EmiStack[] output;
    final List<ItemStack> input_;
    private final ItemStack[] output_;

    public EmiSmithingRecipe(List<ItemStack> input, ItemStack output) {
        this.input = EmiBulkRecipe.padIngredients(input.stream().map(RetroEMI::wildcardIngredientWithStackSize).collect(Collectors.toList()));
        this.output = new EmiStack[]{EmiStack.of(output)};
        this.input_ = input;
        this.output_ = new ItemStack[] {output};
    }

    public EmiSmithingRecipe(ItemStack in, ItemStack[] out) {
        this.input = (List<EmiIngredient>) RetroEMI.wildcardIngredient(in);
        this.output = ItemArrayToEmi(out);
        this.input_ = Collections.singletonList(in);
        this.output_ = out;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.SMITHING;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = input_.get(0).getIconIndex().getIconName();
        if (string.contains(":")) {
            String[] parts = string.split(":");
            String mod = parts[0];
            String texturename = parts[1];
            return new ResourceLocation(mod, texturename);
        }
        return new ResourceLocation(string);
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return this.input;
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
        return 52;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        int i;
        widgets.addFillingArrow(55, 2, 10000);
        for (i = 0; i < Math.max(this.input.size(), 6); ++i) {
            if (i < this.input.size()) {
                widgets.addSlot(this.input.get(i), i % 3 * 18, 4 + i / 3 * 18);
                continue;
            }
            widgets.addSlot(EmiStack.of(ItemStacks.EMPTY), i % 3 * 18, 4 + i / 3 * 18);
        }
        for (i = 0; i < this.output.length; ++i) {
            widgets.addSlot(this.output[i], 80 + i % 2 * 26, i / 2 * 26).large(true).recipeContext(this);
        }
    }

    public static EmiStack[] ItemArrayToEmi(ItemStack[] input) {
        EmiStack[] list = new EmiStack[input.length];
        int i = 0;
        for (int y = 0; y < input.length; ++y) {
            list[i] = EmiStack.of(input[i]);
            ++i;
        }
        return list;
    }
}