package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import emi.shims.java.net.minecraft.text.Text;
import net.fabricmc.refamished.itemsbase.craftingPulling;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.EnumChatFormatting;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import org.lwjgl.opengl.GL11;

import java.util.List;
import java.util.Locale;

public class EmiOtherQuality implements EmiRecipe {
    private final String qualityName;
    private final int bonus;
    private final int weight;
    private final EnumChatFormatting textColor;
    private final int totalWeight;
    private final int qualityTypeId;

    public EmiOtherQuality(ToolQuality quality_, int totalWeight_) {
        qualityName = quality_.getName();
        bonus = quality_.getBonus();
        weight = quality_.getWeight();
        textColor = quality_.getColor();
        totalWeight = totalWeight_;
        qualityTypeId = 1;
    }

    public EmiOtherQuality(ArmorQuality quality_, int totalWeight_) {
        qualityName = quality_.getName();
        bonus = quality_.getBonus();
        weight = quality_.getWeight();
        textColor = quality_.getColor();
        totalWeight = totalWeight_;
        qualityTypeId = 2;
    }

    @Override
    public EmiRecipeCategory getCategory() {
        return qualityTypeId == 1 ? RefamishedRecipeCategories.QUALITIES : RefamishedRecipeCategories.QUALITIESARMOR;
    }

    @Override
    public @Nullable ResourceLocation getId() {

        return new ResourceLocation("refamished", "quality/" + qualityName.toLowerCase(Locale.ROOT));
    }

    @Override
    public List<EmiIngredient> getInputs() {
        return List.of(); // No inputs
    }

    @Override
    public List<EmiStack> getOutputs() {
        return List.of(); // No outputs
    }

    @Override
    public int getDisplayWidth() {
        return 138;
    }

    @Override
    public int getDisplayHeight() {
        return 44;
    }

    @Override
    public void addWidgets(WidgetHolder widgets) {
        //widgets.addText(Text.translatable("emi.refamished.knapping.hits", this.input_.getMaxDamage()),2,22,0xffffff,true);
        widgets.addText(Text.literal(textColor+"Quality: "+qualityName), 2, 2, 0xFFFFFF, true);

        float chance = (float) weight / (float) totalWeight * 100f;
        widgets.addText(Text.literal(String.format("Chance: %.2f%% (1 in %.1f)", chance, 100f / chance)), 2, 34, 0x55FF55, true);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        if (bonus != 0) {
            widgets.addText(Text.literal("Bonus: " + bonus), 2, 14, 0xFFFFFF, true);
        }

        widgets.addText(Text.literal("Weight: " + weight), 2, 24, 0xFFFFFF, true);
    }
}