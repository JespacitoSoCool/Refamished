package net.fabricmc.refamished;

import btw.AddonHandler;
import btw.community.refamished.RefamishedAddon;
import btw.item.BTWItems;
import btw.network.packet.BTWPacketManager;
import btw.world.util.WorldUtils;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.refamished.gui.ModelPreviewGui;
import net.fabricmc.refamished.gui.Models.Test;
import net.fabricmc.refamished.gui.SkillsGui;
import net.fabricmc.refamished.interfaces.IGridPotion;
import net.fabricmc.refamished.misc.*;
import net.fabricmc.refamished.misc.Commands.SkillCmd;
import net.fabricmc.refamished.misc.Potion.RePotion;
import net.fabricmc.refamished.skill.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.UUID;

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
}
