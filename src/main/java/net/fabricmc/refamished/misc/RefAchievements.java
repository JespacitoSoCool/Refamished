package net.fabricmc.refamished.misc;

import btw.achievement.AchievementProvider;
import btw.achievement.AchievementTab;
import btw.achievement.BTWAchievements;
import btw.achievement.event.BTWAchievementEvents;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.Arrays;
import java.util.function.Predicate;

public class RefAchievements extends BTWAchievements {
    public static final AchievementTab TAB_RP = new AchievementTab("refamished.beginning").setIcon(RefamishedItems.copperPickaxe);
    public static final AchievementProvider.NameStep <ItemStack> builder = AchievementProvider.getBuilder(BTWAchievementEvents.ItemEvent.class);
    public static final Achievement<ItemStack> WELCOME = builder.name(loc("ambush")).icon(RefamishedItems.branch).displayLocation(0,0).triggerCondition(itemID(RefamishedItems.branch.itemID)).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> AMBUSH = builder.name(loc("ambush")).icon(RefamishedItems.branch).displayLocation(0, 3).triggerCondition(itemID(RefamishedItems.branch.itemID)).parents(WELCOME).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> FLINT = builder.name(loc("flint")).icon(Item.flint).displayLocation(-1, 3).triggerCondition(itemID(Item.flint.itemID)).parents(AMBUSH).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> BLADES = builder.name(loc("blades")).icon(RefamishedItems.flintBlade).displayLocation(-2, 3).triggerCondition(itemID(RefamishedItems.flintBlade.itemID)).parents(FLINT).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> MACHETES = builder.name(loc("flint_machete")).icon(RefamishedItems.flint_machete).displayLocation(-4, 3).triggerCondition(itemID(RefamishedItems.flint_machete.itemID)).parents(BLADES).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> SHARPENER = builder.name(loc("sharpener")).icon(RefamishedItems.flint_sharpener).displayLocation(-4, 2).triggerCondition(itemID(RefamishedItems.flint_sharpener.itemID)).parents(BLADES).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> BINDING_WOOL = builder.name(loc("wool_string")).icon(RefamishedItems.wool_string).displayLocation(-5, 2).triggerCondition(itemID(RefamishedItems.wool_string.itemID)).parents(SHARPENER).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> SHARP = builder.name(loc("sharp_stone")).icon(BTWItems.sharpStone).displayLocation(1, 3).triggerCondition(itemID(BTWItems.sharpStone.itemID)).parents(AMBUSH).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> STONE_HEAD = builder.name(loc("stone_head")).icon(RefamishedItems.chippedAxeHead).displayLocation(2, 3).triggerCondition(itemStack ->
            itemStack.itemID == RefamishedItems.chippedAxeHead.itemID || itemStack.itemID == RefamishedItems.chippedShovelHead.itemID ||
                    itemStack.itemID == RefamishedItems.chippedHoeHead.itemID || itemStack.itemID == RefamishedItems.chippedPickaxeHead.itemID || itemStack.itemID == RefamishedItems.chippedHammerHead.itemID).parents(SHARP).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> STONE_TOOL = builder.name(loc("stone_tool")).icon(Item.axeStone).displayLocation(3, 3).triggerCondition(itemStack ->
            itemStack.itemID == Item.axeStone.itemID || itemStack.itemID == Item.shovelStone.itemID || itemStack.itemID == Item.hoeStone.itemID || itemStack.itemID == Item.pickaxeStone.itemID || itemStack.itemID == RefamishedItems.stoneHammer.itemID).parents(STONE_HEAD).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> RIB_PICK = builder.name(loc("rib_pick")).icon(RefamishedItems.bone_pickaxe).displayLocation(4, 3).triggerCondition(itemID(RefamishedItems.bone_pickaxe.itemID)).parents(STONE_HEAD).build().registerAchievement(TAB_RP);

    public static void initialize() {
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation("refamished", name);
    }

    private static Predicate<ItemStack> itemID(int ... itemIDs) {
        return itemStack -> Arrays.stream(itemIDs).anyMatch(id -> id == itemStack.itemID);
    }
}