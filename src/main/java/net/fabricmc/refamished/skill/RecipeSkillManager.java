package net.fabricmc.refamished.skill;

import java.util.HashMap;
import java.util.Map;

public class RecipeSkillManager {
    private static final Map<Integer, RecipeSkillRequirement> recipeRequirements = new HashMap<>();

    public static void addRecipe(int itemID, RecipeSkillRequirement requirements) {
        recipeRequirements.put(itemID, requirements);
    }

    public static boolean canPlayerCraft(int itemID, PlayerSkillData playerSkillData) {
        RecipeSkillRequirement requirements = recipeRequirements.get(itemID);
        if (requirements == null) {
            return true; // No requirements, player can craft
        }
        return requirements.doesPlayerMeetRequirements(playerSkillData);
    }

    public static RecipeSkillRequirement getRequirements(int itemID) {
        return recipeRequirements.get(itemID);
    }
}
