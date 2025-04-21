package net.fabricmc.refamished.skill;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.NBTTagCompound;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SkillManager {

	// Attach skill data to a player instance
	private static final String SKILL_DATA_KEY = "refamished.skills";

	private static final Map<UUID, PlayerSkillData> playerSkillDataMap = new HashMap<>();

	public static PlayerSkillData getSkillData(EntityPlayer player) {
		UUID playerId = player.getUniqueID();
		return playerSkillDataMap.computeIfAbsent(playerId, id -> {
			System.out.println("Initializing new skill data for player: " + player.getEntityName());
			return new PlayerSkillData();
		});
	}

	public static void saveSkillData(EntityPlayer player, NBTTagCompound playerNBT) {
		try {
			// Get or create the PlayerSkillData for the player
			PlayerSkillData skillData = getSkillData(player);

			// Prepare a separate NBT compound for skills
			NBTTagCompound skillsTag = new NBTTagCompound();

			// Save the player's skills to the NBT
			skillData.saveToNBT(skillsTag);

			// Attach the skills NBT to the player's NBT
			playerNBT.setTag(SKILL_DATA_KEY, skillsTag);

			//System.out.println("DEBUG: Successfully saved skills to player NBT: " + skillsTag);
		} catch (Exception e) {
			// Log any errors during the save process
			//System.err.println("ERROR: Failed to save player skills for player " + player.getEntityName());
			e.printStackTrace();
		}
	}

	/**
	 * Load player skill data from their NBT tag.
	 */
	public static void loadSkillData(EntityPlayer player, NBTTagCompound playerNBT) {
		try {
			//System.out.println("DEBUG: Loading skills for player: " + player.getEntityName());

			// Check if the NBT has "Skills" saved
			if (!playerNBT.hasKey(SKILL_DATA_KEY)) {
				//System.out.println("DEBUG: No skills data found in player NBT.");
				return;
			}

			// Retrieve the skills NBT
			NBTTagCompound skillsTag = playerNBT.getCompoundTag(SKILL_DATA_KEY);

			// Get or create the PlayerSkillData object
			PlayerSkillData skillData = getSkillData(player);

			// Load skills into the skill data object
			skillData.loadFromNBT(skillsTag);

			//System.out.println("DEBUG: Successfully loaded skills for player: " + player.getEntityName());
		} catch (Exception e) {
			// Log any errors during the load process
			//System.err.println("ERROR: Failed to load player skills for player " + player.getEntityName());
			e.printStackTrace();
		}
	}

	public static void addExperience(EntityPlayer player, String skillName, int amount) {
		if (player.worldObj.isRemote) {
			// Local-only update
			PlayerSkillData skillData = getSkillData(player);
			PlayerSkillData.SkillProgress skillProgress = skillData.getSkillProgress(skillName);

			// Update local visual progress
			if (skillProgress != null) {
				int currentXP = skillProgress.getExperience();
				int currentLevel = skillProgress.getLevel();

				// Add experience locally
				int updatedXP = currentXP + amount;

				skillProgress.setExperience(updatedXP);
				skillProgress.setLevel(currentLevel);

				// Debug logs
				//System.out.println("[CLIENT ONLY] Locally updated " + skillName + " | Lv: " + currentLevel + ", XP: " + updatedXP);
			} else {
				//System.err.println("[CLIENT ERROR] Skill progress for " + skillName + " is null!");
			}

			return; // Do not proceed with server-side logic
		}

		// Server-side logic for updating the global stats
		PlayerSkillData skillData = SkillManager.getSkillData(player);
		PlayerSkillData.SkillProgress skillProgress = skillData.getSkillProgress(skillName);

		int currentExperience = skillProgress.getExperience();
		int currentLevel = skillProgress.getLevel();

		// Add the experience points
		int newExperience = currentExperience + amount;

		// Fetch global skill requirements
		int requiredExp = SkillData.getExperienceRequired(skillName, currentLevel);
		int levelCap = SkillData.getLevelCap(skillName);

		// Process level-ups
		while (newExperience >= requiredExp) {
			if (currentLevel < levelCap) {
				newExperience -= requiredExp;
				currentLevel++;
				requiredExp = SkillData.getExperienceRequired(skillName, currentLevel);
			} else {
				break;
			}
		}

		// Update player's global skill progress
		skillProgress.setExperience(newExperience);
		skillProgress.setLevel(currentLevel);

		//System.out.println("Added " + amount + " experience to skill: " + skillName);
		//System.out.println("Updated global skill - Level: " + currentLevel + " | EXP: " + newExperience);

		// Sync with the client
		SkillUtils.syncSkillToClient((EntityPlayerMP) player, skillName, newExperience, currentLevel);
	}
}