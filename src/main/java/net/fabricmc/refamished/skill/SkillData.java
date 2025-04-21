package net.fabricmc.refamished.skill;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import java.util.HashMap;
import java.util.Map;

public class SkillData {
	public static final Map<String, SkillProperties> skills = new HashMap<>();

	public int level;
	public int experience;

	public SkillData() {
		initializeSkills(); // Populate skills during instantiation
	}

	private void initializeSkills() {
		addSkill("Weaving", 125, 0.4, 6);
		addSkill("Chipping", 170, 0.3, 6);
		addSkill("Refinement", 175, 0.4, 6);
		addSkill("Endurance", 300, 0.6, 10);
		addSkill("Patience", 950, 0.6, 3);
		addSkill("Drift", 1200, 0.7, 4);
		addSkill("Artisanry", 125, 1.2, 12);

		System.out.println("[DEBUG] Skills initialized. Total skills count: " + skills.size());
	}

	public void addSkill(String name, int baseExp, double roughness, int levelCap) {
		if (skills.containsKey(name)) {
			System.err.println("[WARNING] Skill already exists: " + name);
		}
		skills.put(name, new SkillProperties(baseExp, roughness, levelCap));
		System.out.println("[DEBUG] Added skill: " + name);
	}

	public static int getExperienceRequired(String skillName, int level) {
		if (skills.isEmpty())
		{
			System.err.println("[ERROR] NO SKILLS :LIGHTNING:");
			return 100; // Default value
		}
		SkillProperties props = skills.get(skillName);
		if (props == null) {
			System.err.println("[ERROR] Skill not found while calculating XP required: " + skillName);
			return 100; // Default value
		}
		return (int) (props.baseExp * Math.pow(level + 1, props.roughness));
	}

	public static int getLevelCap(String skillName) {
		SkillProperties props = skills.get(skillName);
		if (props == null) {
			System.err.println("[ERROR] Skill not found while fetching level cap: " + skillName);
			return Integer.MAX_VALUE; // Default level cap
		}
		return props.levelCap;
	}

	public static class SkillProperties {
		final int baseExp;
		final double roughness;
		final int levelCap;

		SkillProperties(int baseExp, double roughness, int levelCap) {
			this.baseExp = baseExp;
			this.roughness = roughness;
			this.levelCap = levelCap;
		}
	}
}