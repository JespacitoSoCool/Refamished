package net.fabricmc.refamished.skill;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;

public class SkillCraftingHandler {
    public static boolean canPlayerCraft(EntityPlayer player, ItemStack resultItem) {
        if (resultItem == null) {
            return true; // Allow crafting empty or unknown recipes
        }

        int itemID = resultItem.itemID;
        RecipeSkillRequirement requirements = RecipeSkillManager.getRequirements(itemID);

        if (requirements == null) {
            return true; // No skill requirements for this recipe
        }

        PlayerSkillData playerSkillData = SkillManager.getSkillData(player);
        return requirements.doesPlayerMeetRequirements(playerSkillData);
    }
}
