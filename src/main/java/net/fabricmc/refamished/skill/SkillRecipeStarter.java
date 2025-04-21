package net.fabricmc.refamished.skill;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.Item;

import java.util.Map;

public class SkillRecipeStarter {
    public void begin()
    {
        System.out.println("Started Skill-based Recipes");
        addItemFromString(Item.pickaxeStone.itemID, "Artisanry:1");

        addItemFromString(RefamishedItems.copperSword.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.copperPickaxe.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.copperAxe.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.copperShovel.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.copperHoe.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.helmetCopper.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.plateCopper.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.legsCopper.itemID, "Artisanry:1");
        addItemFromString(RefamishedItems.bootsCopper.itemID, "Artisanry:1");

        addItemFromString(Item.swordIron.itemID, "Artisanry:2");
        addItemFromString(Item.pickaxeIron.itemID, "Artisanry:2");
        addItemFromString(Item.axeIron.itemID, "Artisanry:2");
        addItemFromString(Item.shovelIron.itemID, "Artisanry:2");
        addItemFromString(Item.hoeIron.itemID, "Artisanry:2");
        addItemFromString(Item.helmetIron.itemID, "Artisanry:2");
        addItemFromString(Item.plateIron.itemID, "Artisanry:2");
        addItemFromString(Item.legsIron.itemID, "Artisanry:2");
        addItemFromString(Item.bootsIron.itemID, "Artisanry:2");
        addItemFromString(RefamishedItems.iron_machete.itemID, "Artisanry:2");

        addItemFromString(Item.swordGold.itemID, "Artisanry:2");
        addItemFromString(Item.pickaxeGold.itemID, "Artisanry:2");
        addItemFromString(Item.axeGold.itemID, "Artisanry:2");
        addItemFromString(Item.shovelGold.itemID, "Artisanry:2");
        addItemFromString(Item.hoeGold.itemID, "Artisanry:2");
        addItemFromString(Item.helmetGold.itemID, "Artisanry:2");
        addItemFromString(Item.plateGold.itemID, "Artisanry:2");
        addItemFromString(Item.legsGold.itemID, "Artisanry:2");
        addItemFromString(Item.bootsGold.itemID, "Artisanry:2");

        addItemFromString(RefamishedItems.gildedIronSword.itemID, "Artisanry:2");
        addItemFromString(RefamishedItems.gildedIronPickaxe.itemID, "Artisanry:2");
        addItemFromString(RefamishedItems.gildedIronAxe.itemID, "Artisanry:2");
        addItemFromString(RefamishedItems.gildedIronShovel.itemID, "Artisanry:2");
        addItemFromString(RefamishedItems.gildedIronHoe.itemID, "Artisanry:2");

        addItemFromString(Item.swordDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.pickaxeDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.axeDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.shovelDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.hoeDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.helmetDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.plateDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.legsDiamond.itemID, "Artisanry:3");
        addItemFromString(Item.bootsDiamond.itemID, "Artisanry:3");
        addItemFromString(RefamishedItems.diamond_machete.itemID, "Artisanry:3");
        addItemFromString(RefamishedItems.compound_arbalest.itemID, "Artisanry:3");

        addItemFromString(BTWItems.sinewExtractingBeef.itemID, "Artisanry:1");
        addItemFromString(BTWItems.sinewExtractingWolf.itemID, "Artisanry:1");
    }
    public void addItem(int itemID, Map<String, Integer> skillRequirements) {
        RecipeSkillRequirement requirement = new RecipeSkillRequirement();
        for (Map.Entry<String, Integer> entry : skillRequirements.entrySet()) {
            requirement.addRequirement(entry.getKey(), entry.getValue());
        }
        RecipeSkillManager.addRecipe(itemID, requirement);
    }

    public void addItemFromString(int itemID, String skillRequirementsString) {
        if (skillRequirementsString == null || skillRequirementsString.trim().isEmpty()) {
            throw new IllegalArgumentException("Skill requirements string cannot be null or empty.");
        }

        RecipeSkillRequirement requirement = new RecipeSkillRequirement();

        String[] skillEntries = skillRequirementsString.split(",\\s*");

        for (String entry : skillEntries) {
            String[] parts = entry.split(":");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid skill requirement format: " + entry);
            }

            String skillName = parts[0].trim();
            int requiredLevel;
            try {
                requiredLevel = Integer.parseInt(parts[1].trim());
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid skill level for skill: " + skillName);
            }

            requirement.addRequirement(skillName, requiredLevel);
        }

        RecipeSkillManager.addRecipe(itemID, requirement);
    }

}
