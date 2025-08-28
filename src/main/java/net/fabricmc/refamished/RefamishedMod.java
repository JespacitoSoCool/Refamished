package net.fabricmc.refamished;

import btw.AddonHandler;
import btw.achievement.AchievementHandler;
import btw.world.util.WorldUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.refamished.interfaces.IGridPotion;
import net.fabricmc.refamished.misc.*;
import net.fabricmc.refamished.misc.Packets.RefamishedPacketManager;
import net.fabricmc.refamished.misc.Potion.RePotion;
import net.fabricmc.refamished.skill.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

public class RefamishedMod implements ModInitializer {
	private static SkillPersistentState skillPersistentState;

	public static final Potion INFESTEDWOUND = new RePotion(100, true, Integer.valueOf("904A4A", 16)).setPotionName("potion.infestedwound");
	@Override
	public void onInitialize() {
		addMaterialOverride();
		SkillData skillData = new SkillData();
		skillPersistentState = new SkillPersistentState();
		//System.out.println("Hi world!!!!!!!!111 kil me");

		//System.out.println("!!!!!!!!!loaded : "+BTWItems.sharpStone);
		RefamishedConfig.load();
		if (INFESTEDWOUND instanceof IGridPotion grid) {
			grid.setGridX(0);
			grid.setGridY(0);
		}
		RefamishedPacketManager.initPacketInfo();

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			RefamishedModClient.onInitialize();
		}
		System.out.println("Enviroment :"+ FabricLoader.getInstance().getEnvironmentType().name());
	}

	public static final boolean NMEnabled = AddonHandler.isModInstalled("nightmare_mode");

	public static void addMaterialOverride()
	{
		ReMaterials.init();
		ReFormatting.init();
	}

	public static SkillPersistentState getSkillPersistentState() {
		return skillPersistentState;
	}

	public static void serverOpenCustomInterface(EntityPlayerMP player, Container container, int containerID) {
		try {
			int windowID = player.incrementAndGetWindowID();
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			DataOutputStream dataStream = new DataOutputStream(byteStream);
			dataStream.writeInt(windowID);
			dataStream.writeInt(containerID);
			Packet250CustomPayload packet = new Packet250CustomPayload("re|OI", byteStream.toByteArray());
			WorldUtils.sendPacketToPlayer(player.playerNetServerHandler, packet);
			player.openContainer = container;
			player.openContainer.windowId = windowID;
			player.openContainer.onCraftGuiOpened(player);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void triggerAchievement(Achievement achievement, EntityPlayer player) {
			player.triggerAchievement(achievement);
			if (AchievementHandler.hasUnlocked(player, achievement)) return;
			AchievementHandler.triggerAchievement(player, achievement);
			String name = achievement.toString();
			String announce = StatCollector.translateToLocal("achievement.get");
			String formatCode = achievement.formatCode;
			String msg = String.format("%s %s %s[%s]", player.username, announce, formatCode, name);
			if (!achievement.shouldAnnounce || player.worldObj.isRemote) return;
			MinecraftServer.getServer().getConfigurationManager().sendChatMsg(ChatMessageComponent.createFromTranslationWithSubstitutions(msg, new Object[0]));
	}
}
