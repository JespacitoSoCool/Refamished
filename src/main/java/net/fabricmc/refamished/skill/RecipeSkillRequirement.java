package net.fabricmc.refamished.skill;

import java.util.HashMap;
import java.util.Map;

public class RecipeSkillRequirement {
    private final Map<String, Integer> requiredSkills = new HashMap<>();

    public void addRequirement(String skillName, int level) {
        requiredSkills.put(skillName, level);
    }

    public Map<String, Integer> getRequirements() {
        return requiredSkills;
    }

    public boolean doesPlayerMeetRequirements(PlayerSkillData playerSkillData) {
        for (Map.Entry<String, Integer> entry : requiredSkills.entrySet()) {
            String skillName = entry.getKey();
            int requiredLevel = entry.getValue();

            PlayerSkillData.SkillProgress playerSkill = playerSkillData.getSkillProgress(skillName);
            if (playerSkill == null || playerSkill.getLevel() < requiredLevel) {
                System.out.println("NUH UH! : "+playerSkill.getLevel()+" LOWER THAN "+requiredLevel);
                return false;
            }
        }
        //System.out.println("YES YES YES");
        return true;
    }
}
