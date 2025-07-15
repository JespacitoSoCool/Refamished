package net.fabricmc.refamished.misc.EMIfamished;

import btw.item.items.ArmorItemLeather;
import btw.item.items.ArmorItemWool;
import com.google.common.collect.Lists;
import emi.dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import emi.dev.emi.emi.api.stack.EmiIngredient;
import emi.dev.emi.emi.api.stack.EmiStack;
import emi.dev.emi.emi.api.widget.GeneratedSlotWidget;
import emi.dev.emi.emi.api.widget.SlotWidget;
import emi.shims.java.net.minecraft.item.DyeItem;
import emi.shims.java.net.minecraft.item.DyeableItem;
import emi.shims.java.net.minecraft.util.DyeColor;
import net.fabricmc.refamished.items.materials.PigmentItem;
import net.fabricmc.refamished.items.others.PigmentDyeableItem;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ResourceLocation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmiArmorPigmentRecipe extends EmiPatternCraftingRecipe {
    public static final List<Item> DYEABLE_ITEMS = Arrays.stream(Item.itemsList).filter(Objects::nonNull).filter(i -> {
        ItemArmor a;
        return i instanceof ItemArmor && ((a = (ItemArmor)i) instanceof ArmorItemWool || a instanceof ArmorItemLeather);
    }).collect(Collectors.toList());
    private static final List<EmiPigmentItem> DYES = Stream.of(DyeColor.values()).map(c -> byColor(c)).collect(Collectors.toList());
    private final Item armor;

    public static EmiPigmentItem byColor(DyeColor color) {
        return new EmiPigmentItem(color);
    }

    public EmiArmorPigmentRecipe(Item armor, ResourceLocation id) {
        super(List.of(EmiIngredient.of(DYES.stream().map(i -> EmiStack.of(i.toStack())).collect(Collectors.toList())), EmiStack.of(armor)), EmiStack.of(armor), id);
        this.armor = armor;
    }

    @Override
    public SlotWidget getInputWidget(int slot, int x, int y) {
        if (slot == 0) {
            return new SlotWidget(EmiStack.of(this.armor), x, y);
        }
        int s = slot - 1;
        return new GeneratedSlotWidget(r -> {
            List<EmiPigmentItem> dyes = this.getDyes((Random)r);
            if (s < dyes.size()) {
                return EmiStack.of(dyes.get(s).toStack());
            }
            return EmiStack.EMPTY;
        }, this.unique, x, y);
    }

    @Override
    public SlotWidget getOutputWidget(int x, int y) {
        return new GeneratedSlotWidget(r -> EmiStack.of(PigmentDyeableItem.blendAndSetColor(new ItemStack(this.armor), this.getDyes((Random)r))), this.unique, x, y);
    }

    private List<EmiPigmentItem> getDyes(Random random) {
        ArrayList<EmiPigmentItem> dyes = Lists.newArrayList();
        int amount = 1 + random.nextInt(8);
        for (int i = 0; i < amount; ++i) {
            dyes.add(DYES.get(random.nextInt(DYES.size())));
        }
        return dyes;
    }
}
