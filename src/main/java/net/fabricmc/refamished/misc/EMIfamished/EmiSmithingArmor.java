package net.fabricmc.refamished.misc.EMIfamished;

import emi.dev.emi.emi.api.recipe.EmiRecipe;
import emi.dev.emi.emi.api.recipe.EmiRecipeCategory;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.WidgetHolder;
import emi.dev.emi.emi.recipe.btw.EmiBulkRecipe;
import emi.shims.java.com.unascribed.retroemi.ItemStacks;
import emi.shims.java.com.unascribed.retroemi.RetroEMI;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.items.materials.metallurgyArmor;
import net.fabricmc.refamished.misc.CustomRecipes.crafting.TestArmorCombination;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class EmiSmithingArmor extends EmiSmithingRecipe {

    public EmiSmithingArmor(List<ItemStack> input, ItemStack output) {
        super(input, output);
    }
    @Override
    public void addWidgets(WidgetHolder widgets) {
        widgets.addFillingArrow(55, 2, 10000);

        ItemStack resinCandidate = null;
        for (ItemStack st : input_) {
            if (st == null) continue;
            if (st.itemID != RefamishedItems.metallurgyArmor.itemID) {
                resinCandidate = st.copy();
                break;
            }
        }
        if (resinCandidate == null) {
            try {
                resinCandidate = new ItemStack(RefamishedItems.sugar_resin, 1, 0);
            } catch (Throwable t) {
                resinCandidate = new ItemStack(RefamishedItems.metallurgyArmor, 1, 0);
            }
        }


        List<ItemStack> previewInputs = new ArrayList<>();
        long tick = System.currentTimeMillis() / 1000L;

        int baseX = 0;
        int baseY = 4;
        int slotXSpacing = 18;
        int slotYSpacing = 12;

        for (int row = 0; row < 4; row++) {
            String topName = (String) TestArmorCombination.armorPairs[row][0];
            String bottomName = (String) TestArmorCombination.armorPairs[row][1];
            int matIndex = (int) (Math.abs((new Random(tick + row)).nextInt()) % metallurgyArmor.material.length);

            int topHeadIndex = indexOf(metallurgyArmor.heads, topName);
            int bottomHeadIndex = indexOf(metallurgyArmor.heads, bottomName);

            if (topHeadIndex < 0 || bottomHeadIndex < 0) {
                continue;
            }

            // compute metadata values into single item metadata index (materials are grouped by material then head)
            int topMeta = matIndex * metallurgyArmor.heads.length + topHeadIndex;
            int bottomMeta = matIndex * metallurgyArmor.heads.length + bottomHeadIndex;

            ItemStack topPart = new ItemStack(RefamishedItems.metallurgyArmor, 1, topMeta);
            ItemStack bottomPart = new ItemStack(RefamishedItems.metallurgyArmor, 1, bottomMeta);
            ItemStack resin = resinCandidate.copy();

            // add to preview inputs (order doesn't matter for the combination logic but keep consistent)
            previewInputs.add(topPart);
            previewInputs.add(bottomPart);
            previewInputs.add(resin);

            // widget positions
            int y = baseY + row * slotYSpacing;
            int xTop = baseX + 0 * slotXSpacing;
            int xBottom = baseX + 1 * slotXSpacing;
            int xResin = baseX + 2 * slotXSpacing;

            widgets.addSlot(EmiStack.of(topPart), xTop, y);
            widgets.addSlot(EmiStack.of(bottomPart), xBottom, y);
            widgets.addSlot(EmiStack.of(resin), xResin, y);
        }

        // Build output from previewInputs using same logic your recipe uses (so textures + NBT match)
        ItemStack dynamicOutput = buildDynamicArmorOutput(previewInputs);
        if (dynamicOutput == null) {
            // fallback safe display to avoid crashes
            dynamicOutput = new ItemStack(RefamishedItems.metallurgyArmor, 1, 0);
        }

        widgets.addSlot(EmiStack.of(dynamicOutput), 80, 0).large(true).recipeContext(this);
    }

    private static int indexOf(String[] arr, String val) {
        for (int i = 0; i < arr.length; i++) {
            if (val.equals(arr[i])) return i;
        }
        return -1;
    }

    private ItemStack buildDynamicArmorOutput(List<ItemStack> inputs) {
        if (inputs == null || inputs.isEmpty()) return null;

        for (int pairIndex = 0; pairIndex < TestArmorCombination.armorPairs.length; pairIndex++) {
            String topName = (String) TestArmorCombination.armorPairs[pairIndex][0];
            String bottomName = (String) TestArmorCombination.armorPairs[pairIndex][1];
            Item resultItem = (Item) TestArmorCombination.armorPairs[pairIndex][2];

            ItemStack newItem = new ItemStack(resultItem, 1);
            NBTTagCompound tag = new NBTTagCompound();
            boolean hasTop = false;
            boolean hasBottom = false;

            for (ItemStack part : inputs) {
                if (part == null) continue;

                if (metallurgyArmor.isPartOf(part, bottomName)) {
                    hasBottom = true;
                    tag.setString("Bottom", metallurgyArmor.getMaterial(part, bottomName));
                } else if (metallurgyArmor.isPartOf(part, topName)) {
                    hasTop = true;
                    tag.setString("Top", metallurgyArmor.getMaterial(part, topName));
                }
            }

            if (hasTop && hasBottom) {
                newItem.setTagCompound(tag);
                return newItem;
            }
        }

        return null;
    }


}