package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.EmiPort;
import emi.dev.emi.emi.api.recipe.BTWEmiRecipeCategories;
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
import net.minecraft.src.ShapedRecipes;
import org.intellij.lang.annotations.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EmiKnapping implements EmiRecipe {
    private final EmiIngredient input;
    private final EmiIngredient output;
    private final craftingPulling input_;
    private final Item output_;

    public EmiKnapping(craftingPulling input, ItemStack output) {
        this.input = RetroEMI.wildcardIngredient(new ItemStack(input));
        this.output = RetroEMI.wildcardIngredient(output);
        this.input_ = input;
        this.output_ = output.getItem();
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return RefamishedRecipeCategories.KNAPPING;
    }

    @Override
    @Nullable
    public ResourceLocation getId() {
        String string = output_.itemIcon.getIconName();
        String[] parts = string.split(":");
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
        return 78;
    }

    @Override
    public int getDisplayHeight() {
        return 40;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(27, 2, 1);
        widgets.addSlot(this.input, 5, 2);
        widgets.addSlot(this.output, 55, 2);
        widgets.addText(Text.translatable("emi.refamished.knapping.hits", this.input_.getMaxDamage()),2,22,0xffffff,true);
        widgets.addText(Text.translatable("emi.refamished.knapping.badhits", this.input_.badHitsBreak()),2,32,0xff0000,true);
    }
}