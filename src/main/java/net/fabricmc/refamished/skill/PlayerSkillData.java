package net.fabricmc.refamished.skill;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PlayerSkillData {
	public final Map<String, SkillProgress> skills = new HashMap<>();
	public final Set<String> pinnedSkills = new HashSet<>();

	public Map<String, SkillProgress> getAllSkills() {
		return skills;
	}

	public SkillProgress getSkillProgress(String skillName) {
		return skills.computeIfAbsent(skillName, (key) -> new SkillProgress());
	}

	public void saveToNBT(NBTTagCompound compound) {
		NBTTagList skillList = new NBTTagList();
		for (Map.Entry<String, SkillProgress> entry : skills.entrySet()) {
			String skillName = entry.getKey();
			SkillProgress progress = entry.getValue();

			NBTTagCompound skillTag = new NBTTagCompound();
			skillTag.setString("SkillName", skillName);
			skillTag.setInteger("Level", progress.getLevel());
			skillTag.setInteger("Experience", progress.getExperience());

			skillList.appendTag(skillTag);
		}
		compound.setTag("Skills", skillList);

		NBTTagList pinnedSkillsTag = new NBTTagList();
		for (String pinnedSkill : pinnedSkills) {
			NBTTagCompound tag = new NBTTagCompound();
			tag.setString("PinnedSkill", pinnedSkill);
			pinnedSkillsTag.appendTag(tag);
		}
		compound.setTag("PinnedSkills", pinnedSkillsTag);
	}

	public void loadFromNBT(NBTTagCompound compound) {
		skills.clear();

		if (compound.hasKey("Skills")) {
			NBTTagList skillList = compound.getTagList("Skills");
			for (int i = 0; i < skillList.tagCount(); i++) {
				NBTTagCompound skillTag = (NBTTagCompound) skillList.tagAt(i);
				String skillName = skillTag.getString("SkillName");
				int level = skillTag.getInteger("Level");
				int experience = skillTag.getInteger("Experience");
				skills.put(skillName, new SkillProgress(level, experience));
			}
		}

		pinnedSkills.clear();
		if (compound.hasKey("PinnedSkills")) {
			NBTTagList pinnedSkillsTag = compound.getTagList("PinnedSkills");
			for (int i = 0; i < pinnedSkillsTag.tagCount(); i++) {
				NBTTagCompound tag = (NBTTagCompound) pinnedSkillsTag.tagAt(i);
				pinnedSkills.add(tag.getString("PinnedSkill"));
			}
		}
	}

	public static class SkillProgress {
		private int level;
		private int experience;

		public SkillProgress() {
			this.level = 0;
			this.experience = 0;
		}

		public SkillProgress(int level, int experience) {
			this.level = level;
			this.experience = experience;
		}

		public int getLevel() {
			return level;
		}

		public void setLevel(int level) {
			this.level = level;
		}

		public int getExperience() {
			return experience;
		}

		public void setExperience(int experience) {
			this.experience = experience;
		}
	}
}