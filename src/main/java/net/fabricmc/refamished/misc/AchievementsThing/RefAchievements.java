package net.fabricmc.refamished.misc.AchievementsThing;

import btw.achievement.AchievementProvider;
import btw.achievement.AchievementTab;
import btw.achievement.BTWAchievements;
import btw.achievement.event.BTWAchievementEvents;
import btw.block.BTWBlocks;
import btw.block.blocks.WorkStumpBlock;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

import java.util.*;
import java.util.function.Predicate;

public class RefAchievements extends BTWAchievements {
    public static final AchievementTab TAB_RP = new AchievementTab("refamished.beginning").setIcon(RefamishedItems.copperPickaxe);
    public static final AchievementProvider.NameStep <ItemStack> builder = AchievementProvider.getBuilder(BTWAchievementEvents.ItemEvent.class);
    public static final AchievementProvider.NameStep <BTWAchievementEvents.None> b_wel = AchievementProvider.getBuilder(RefAchievementsEvents.WelcomeEvent.class);
    public static final AchievementProvider.NameStep <RefAchievementsEvents.SkillEventData> b_skill = AchievementProvider.getBuilder(RefAchievementsEvents.Skill.class);
    public static final Achievement<BTWAchievementEvents.None> WELCOME = b_wel.name(loc("welcome")).icon(Item.skull).displayLocation(0,0).triggerCondition(itemStack -> true).build().registerAchievement(TAB_RP);
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
    public static final Achievement<ItemStack> STONE_SHOVEL = builder.name(loc("stone_shovel")).icon(Item.shovelStone).displayLocation(2, 4).triggerCondition(itemID(Item.shovelStone.itemID)).parents(STONE_HEAD).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> ASHES = builder.name(loc("ashes")).icon(RefamishedItems.pile_ashes).displayLocation(2, 5).triggerCondition(itemID(RefamishedItems.pile_ashes.itemID)).parents(STONE_SHOVEL).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> PREPARED = builder.name(loc("prepared")).icon(RefamishedItems.cowhide_prepared).displayLocation(2, 6).triggerCondition(itemID(RefamishedItems.cowhide_prepared.itemID,RefamishedItems.horsehide_prepared.itemID)).parents(ASHES).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> LEATHER = builder.name(loc("leather")).icon(Item.leather).displayLocation(2, 7).triggerCondition(itemID(Item.leather.itemID)).parents(PREPARED).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> STONE_TOOL = builder.name(loc("stone_tool")).icon(Item.axeStone).displayLocation(3, 3).triggerCondition(itemStack ->
            itemStack.itemID == Item.axeStone.itemID || itemStack.itemID == Item.shovelStone.itemID || itemStack.itemID == Item.hoeStone.itemID || itemStack.itemID == Item.pickaxeStone.itemID || itemStack.itemID == RefamishedItems.stoneHammer.itemID).parents(STONE_HEAD).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> RIB_PICK = builder.name(loc("rib_pick")).icon(RefamishedItems.bone_pickaxe).displayLocation(4, 3).triggerCondition(itemID(RefamishedItems.bone_pickaxe.itemID)).parents(STONE_HEAD).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_CHUNK = builder.name(loc("copper_chunk")).icon(RefamishedItems.copperChunk).displayLocation(4, 2).triggerCondition(itemID(RefamishedItems.copperChunk.itemID)).parents(RIB_PICK).build().registerAchievement(TAB_RP);
    public static final Achievement<BTWAchievementEvents.None> TROWEL = AchievementProvider.getBuilder(RefAchievementsEvents.Troweling.class).name(loc("trowel")).icon(RefamishedItems.soft_clay_brick).displayLocation(4, 1).triggerCondition(itemStack -> false).parents(COPPER_CHUNK).build().registerAchievement(TAB_RP);
    public static final Achievement<BTWAchievementEvents.None> STONE_ANVIL = AchievementProvider.getBuilder(RefAchievementsEvents.StoneAnvil.class).name(loc("stone_anvil")).icon(RefamishedBlocks.stoneAnvil).displayLocation(4, 0).triggerCondition(itemStack -> false).parents(TROWEL).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> SOFT_BRICK = builder.name(loc("soft_brick")).icon(RefamishedItems.soft_brick).displayLocation(5, 0).triggerCondition(itemID(RefamishedItems.soft_brick.itemID)).parents(STONE_ANVIL).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> BRICK = builder.name(loc("brick")).icon(Item.brick).displayLocation(5, -1).triggerCondition(itemID(Item.brick.itemID)).parents(SOFT_BRICK).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> BRICK_OVEN = builder.name(loc("brick_oven")).icon(BTWBlocks.idleOven).displayLocation(6, -1).triggerCondition(itemID(BTWBlocks.idleOven.blockID)).parents(BRICK).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> MOLD = builder.name(loc("mold")).icon(RefamishedItems.ingotMold).displayLocation(4, -1).triggerCondition(itemID(RefamishedItems.ingotMold.itemID)).parents(BRICK).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> RAW_INGOT = builder.name(loc("raw_ingot")).icon(RefamishedItems.rawIngot).displayLocation(7, -1).triggerCondition(itemID(RefamishedItems.rawIngot.itemID)).parents(MOLD).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_NUGGET = builder.name(loc("copper_nugget")).icon(RefamishedItems.copperNugget).displayLocation(8, -1).triggerCondition(itemID(RefamishedItems.copperNugget.itemID)).parents(RAW_INGOT).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_SWORD = builder.name(loc("copper_sword")).icon(RefamishedItems.copperSword).displayLocation(9, -6).triggerCondition(itemID(RefamishedItems.copperSword.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_AXE = builder.name(loc("copper_axe")).icon(RefamishedItems.copperAxe).displayLocation(9, -5).triggerCondition(itemID(RefamishedItems.copperAxe.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_SHOVEL = builder.name(loc("copper_shovel")).icon(RefamishedItems.copperShovel).displayLocation(9, -4).triggerCondition(itemID(RefamishedItems.copperShovel.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_HOE = builder.name(loc("copper_hoe")).icon(RefamishedItems.copperHoe).displayLocation(9, -3).triggerCondition(itemID(RefamishedItems.copperHoe.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_MACHETE = builder.name(loc("copper_machete")).icon(RefamishedItems.copper_machete).displayLocation(9, -2).triggerCondition(itemID(RefamishedItems.copper_machete.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_SHEARS = builder.name(loc("copper_shears")).icon(RefamishedItems.copperShears).displayLocation(9, -1).triggerCondition(itemID(RefamishedItems.copperShears.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_TONGS = builder.name(loc("copper_tongs")).icon(RefamishedItems.copperTongs).displayLocation(9, 0).triggerCondition(itemID(RefamishedItems.copperTongs.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_HAMMER = builder.name(loc("copper_hammer")).icon(RefamishedItems.copperHammer).displayLocation(9, 1).triggerCondition(itemID(RefamishedItems.copperHammer.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> BUCKET = builder.name(loc("bucket")).icon(Item.bucketEmpty).displayLocation(9, 2).triggerCondition(itemID(Item.bucketEmpty.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> MUDDY = builder.name(loc("muddy")).icon(RefamishedItems.mud_mixing).displayLocation(10, 2).triggerCondition(itemID(RefamishedItems.mud_mixing.itemID)).parents(BUCKET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_TROWEL = builder.name(loc("copper_trowel")).icon(RefamishedItems.copper_trowel).displayLocation(9, 4).triggerCondition(itemID(RefamishedItems.copper_trowel.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_CHISEL = builder.name(loc("copper_chisel")).icon(RefamishedItems.copperChisel).displayLocation(2, -6).triggerCondition(itemID(RefamishedItems.copperChisel.itemID)).parents(COPPER_NUGGET).build().registerAchievement(TAB_RP);
    public static final Achievement<BTWAchievementEvents.BlockConvertedEventData> MAKE_WORK_STUMP = AchievementProvider.getBuilder(BTWAchievementEvents.BlockConvertedEvent.class).name(loc("work_stump")).icon(BTWBlocks.workStump).displayLocation(2, -5).triggerCondition(data -> data.newID() == BTWBlocks.workStump.blockID && ((WorkStumpBlock)BTWBlocks.workStump).isFinishedWorkStump(data.newMetadata())).parents(COPPER_CHISEL).build().registerAchievement(TAB_RP).setSpecial();
    public static final Achievement<ItemStack> STONE_PICKAXE = builder.name(loc("stone_pickaxe")).icon(Item.pickaxeStone).displayLocation(1, -5).triggerCondition(itemID(Item.pickaxeStone.itemID)).parents(MAKE_WORK_STUMP).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_PICKAXE = builder.name(loc("copper_pickaxe")).icon(RefamishedItems.copperPickaxe).displayLocation(1, -4).triggerCondition(itemID(RefamishedItems.copperPickaxe.itemID)).parents(MAKE_WORK_STUMP).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> IRON_CHUNK = builder.name(loc("iron_chunk")).icon(BTWItems.ironOreChunk).displayLocation(0, -4).triggerCondition(itemID(BTWItems.ironOreChunk.itemID)).parents(COPPER_PICKAXE).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> IRON_NUGGET = builder.name(loc("iron_nugget")).icon(BTWItems.ironNugget).displayLocation(-1, -4).triggerCondition(itemID(BTWItems.ironNugget.itemID)).parents(COPPER_PICKAXE).build().registerAchievement(TAB_RP);
    public static final Achievement<ItemStack> COPPER_ANVIL = builder.name(loc("copper_anvil")).icon(RefamishedBlocks.copperAnvil).displayLocation(-3, -5).triggerCondition(itemID(RefamishedBlocks.copperAnvil.blockID)).parents(IRON_CHUNK).build().registerAchievement(TAB_RP).setSpecial();

    public static final AchievementTab TAB_SK = new AchievementTab("refamished.skill").setIcon(BTWItems.knittingNeedles);
    public static final Achievement<RefAchievementsEvents.SkillEventData> ARTISANRY1 = registerSkill(b_skill.name(loc("artisanry1")).icon(RefamishedItems.stoneHammer).displayLocation(0, 0).triggerCondition(skill("Artisanry", 1)).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> ARTISANRY2 = registerSkill(b_skill.name(loc("artisanry2")).icon(RefamishedItems.copperHammer).displayLocation(0, 1).triggerCondition(skill("Artisanry", 6)).parents(ARTISANRY1).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> ARTISANRY3 = registerSkill(b_skill.name(loc("artisanry3")).icon(RefamishedItems.ironHammer).displayLocation(0, 2).triggerCondition(skill("Artisanry", 12)).parents(ARTISANRY2).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> WEAVING1 = registerSkill(b_skill.name(loc("weaving1")).icon(BTWItems.wickerPane).displayLocation(-1, 0).triggerCondition(skill("Weaving", 1)).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> WEAVING2 = registerSkill(b_skill.name(loc("weaving2")).icon(BTWBlocks.hamper).displayLocation(-1, 1).triggerCondition(skill("Weaving", 3)).parents(WEAVING1).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> WEAVING3 = registerSkill(b_skill.name(loc("weaving3")).icon(BTWBlocks.woolSlab).displayLocation(-1, 2).triggerCondition(skill("Weaving", 6)).parents(WEAVING2).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> CHIPPING1 = registerSkill(b_skill.name(loc("chipping1")).icon(RefamishedBlocks.stoneAnvil).displayLocation(1, 0).triggerCondition(skill("Chipping", 1)).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> CHIPPING2 = registerSkill(b_skill.name(loc("chipping2")).icon(RefamishedBlocks.copperAnvil).displayLocation(1, 1).triggerCondition(skill("Chipping", 3)).parents(CHIPPING1).build().registerAchievement(TAB_SK));
    public static final Achievement<RefAchievementsEvents.SkillEventData> CHIPPING3 = registerSkill(b_skill.name(loc("chipping3")).icon(RefamishedBlocks.SteelAnvil).displayLocation(1, 2).triggerCondition(skill("Chipping", 6)).parents(CHIPPING2).build().registerAchievement(TAB_SK));

    public static final Achievement<RefAchievementsEvents.SkillEventData> MASTER_SKILL = registerSkill(b_skill.name(loc("master_skill")).icon(Item.skull).displayLocation(0, 4).triggerCondition(skill("All", 5)).parents(ARTISANRY3,WEAVING3,CHIPPING3).build().registerAchievement(TAB_SK));

    public static void initialize() {
    }

    private static ResourceLocation loc(String name) {
        return new ResourceLocation("refamished", name);
    }

    private static Predicate<ItemStack> itemID(int ... itemIDs) {
        return itemStack -> Arrays.stream(itemIDs).anyMatch(id -> id == itemStack.itemID);
    }
    private static Predicate<RefAchievementsEvents.SkillEventData> skill(String skill,int level) {
        return data -> data.skill().equals(skill) && data.Level() >= level;
    }
    public static Achievement<RefAchievementsEvents.SkillEventData> registerSkill(
            Achievement<RefAchievementsEvents.SkillEventData> a) {
        SkillAchievementRegistry.add(a);
        return a;
    }
    public static final class SkillAchievementRegistry {
        private static final Set<Achievement<RefAchievementsEvents.SkillEventData>> SKILL_ACHIEVEMENTS =
                new HashSet<Achievement<RefAchievementsEvents.SkillEventData>>();

        private SkillAchievementRegistry() {}

        public static void add(Achievement<RefAchievementsEvents.SkillEventData> a) {
            SKILL_ACHIEVEMENTS.add(a);
        }

        public static Set<Achievement<RefAchievementsEvents.SkillEventData>> all() {
            return SKILL_ACHIEVEMENTS;
        }
    }

    public static List<Achievement<?>> getAchievementsInTab(AchievementTab targetTab) {
        List<Achievement<?>> result = new ArrayList<>();

        for (Set<Achievement<?>> set : AchievementList.achievementsByEventType.values()) {
            for (Achievement<?> achievement : set) {
                if (achievement.tab == targetTab) {
                    result.add(achievement);
                }
            }
        }

        return result;
    }

}