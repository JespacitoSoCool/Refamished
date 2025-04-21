package net.fabricmc.refamished.misc.EMIfamished;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.render.EmiTexture;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiHardening implements EmiRecipe {
    private final EmiIngredient input;
    private final EmiIngredient output;
    private final ItemStack input_;
    private final ItemStack output_;

    public EmiHardening(ItemStack input, ItemStack output) {
        this.input = RetroEMI.wildcardIngredient(input);
        this.output = RetroEMI.wildcardIngredient(output);
        this.input_ = output;
        this.output_ = output;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.HARDENING;
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
        return 58;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(26, 19, 100000);
        //widgets.addTexture(EmiTexture.EMPTY_FLAME, 44, 38);
        //widgets.addAnimatedTexture(EmiTexture.FULL_FLAME, 4, 38, 100000, false, true, true);
        widgets.addSlot(this.input, 0, 2);
        widgets.addSlot(RetroEMI.wildcardIngredient(new ItemStack(Block.cobblestone)), 0, 20);
        widgets.addSlot(RetroEMI.wildcardIngredient(new ItemStack(BTWBlocks.largeCampfire)), 0, 38);
        widgets.addSlot(this.output, 60, 15).large(true).recipeContext(this);
    }
}