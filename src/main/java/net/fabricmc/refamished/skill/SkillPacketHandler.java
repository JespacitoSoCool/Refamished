package net.fabricmc.refamished.skill;

import net.minecraft.src.Minecraft;
import net.minecraft.src.Packet250CustomPayload;
import java.nio.charset.StandardCharsets;

public class SkillPacketHandler {

	public static void handleServerPacket(Packet250CustomPayload packet) {
		processPacket(packet, false);
	}

	public static void handleClientPacket(Packet250CustomPayload packet) {
		processPacket(packet, true); // Existing logic

		// Debugging: Verify Artisanry skill sync
		//System.out.println("[CLIENT SYNC] Artisanry sync received from server!");
		PlayerSkillData skillData = SkillManager.getSkillData(Minecraft.getMinecraft().thePlayer);
		PlayerSkillData.SkillProgress artisanrySkill = skillData.getSkillProgress("Artisanry");

		if (artisanrySkill != null) {
			//System.out.println("[CLIENT SYNC UPDATE] Skill: Artisanry, XP: " +
					//artisanrySkill.getExperience() + ", Level: " + artisanrySkill.getLevel());
		}
	}

	private static void processPacket(Packet250CustomPayload packet, boolean isClient) {
		try {
			String payload = new String(packet.data, StandardCharsets.UTF_8);
			String[] parts = payload.split(";");
			if (parts.length == 3) {
				String skillName = parts[0];
				int experience = Integer.parseInt(parts[1]);
				int level = Integer.parseInt(parts[2]);

				if (isClient) {
					updateClientSkill(skillName, experience, level);
				} else {
					updateServerSkill(skillName, experience, level);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void updateClientSkill(String skillName, int experience, int level) {
		//System.out.println("Client updated -> Skill: " + skillName + ", XP: " + experience + ", Level: " + level);

		// Fetch current client-side skill data
		PlayerSkillData skillData = SkillManager.getSkillData(Minecraft.getMinecraft().thePlayer);

		// Update local skill data for the received skill
		PlayerSkillData.SkillProgress skillProgress = skillData.getSkillProgress(skillName);
		if (skillProgress != null) {
			skillProgress.setExperience(experience);
			skillProgress.setLevel(level);

			// Optional: Log successful updates
			//System.out.println("[CLIENT SKILL] Local update: " + skillName + " | Level: " + level + " | XP: " + experience);
		} else {
			//System.err.println("[CLIENT ERROR] Failed to update local skill progress for " + skillName);
		}
	}

	private static void updateServerSkill(String skillName, int experience, int level) {
		System.out.println("Server updated -> Skill: " + skillName + ", XP: " + experience + ", Level: " + level);
		// Add server-specific updates as needed.
	}
}