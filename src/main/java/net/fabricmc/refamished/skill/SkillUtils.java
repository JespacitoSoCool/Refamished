package net.fabricmc.refamished.skill;

import emi.shims.java.net.minecraft.network.PacketByteBuf;
import net.fabricmc.refamished.RefamishedMod;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet250CustomPayload;
import org.intellij.lang.annotations.Identifier;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SkillUtils {
	public static int calculateProgress(int experience, int required) {
		return Math.min((experience * 100) / required, 100);
	}

	public static int getPlayerSkillLevel(EntityPlayer player, String skillName) {
		SkillPersistentState persistentState = RefamishedMod.getSkillPersistentState();
		if (persistentState != null) {
			SkillData skillData = persistentState.getSkillData(player.getUniqueID());
			if (skillData != null && SkillData.skills.containsKey(skillName)) {
				return skillData.level; // Return the skill's level
			}
		}
		return 0;
	}

	public static int getPlayerSkillExperience(EntityPlayer player, String skillName) {
		SkillPersistentState persistentState = RefamishedMod.getSkillPersistentState();
		if (persistentState != null) {
			SkillData skillData = persistentState.getSkillData(player.getUniqueID());
			if (skillData != null && SkillData.skills.containsKey(skillName)) {
				return skillData.experience;
			}
		}
		return 0;
	}

	public static void syncSkillToClient(EntityPlayerMP player, String skillName, int experience, int level) {
		try {
			if (player.playerNetServerHandler != null) {
				String payload = skillName + ";" + experience + ";" + level;
				byte[] data = payload.getBytes(StandardCharsets.UTF_8);

				Packet250CustomPayload packet = new Packet250CustomPayload();
				packet.channel = "SkillUpdateChannel";
				packet.data = data;
				packet.length = data.length;

				player.playerNetServerHandler.sendPacket(packet);
			} else {
				//System.err.println("ERROR: Player '" + player.getEntityName() + "' is not fully initialized (NetServerHandler is null).");
			}
		} catch (Exception e) {
			//System.err.println("[ERROR] Failed to sync skill to client for player: " + player.getEntityName());
			e.printStackTrace();
		}
	}
}
