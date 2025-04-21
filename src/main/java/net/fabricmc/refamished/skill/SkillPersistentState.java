package net.fabricmc.refamished.skill;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillPersistentState {
    private final Map<UUID, SkillData> playerSkills = new HashMap<>();

    public SkillData getSkillData(UUID playerId) {
        return playerSkills.computeIfAbsent(playerId, k -> new SkillData());
    }

    // SkillProgress inner class to store level and experience for each skill
    public static class SkillProgress {
        public int experience;
        public int level;

        public SkillProgress(int experience, int level) {
            this.experience = experience;
            this.level = level;
        }
    }
}