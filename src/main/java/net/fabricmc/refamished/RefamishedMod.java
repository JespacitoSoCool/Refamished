package net.fabricmc.refamished;

import btw.item.BTWItems;
import btw.network.packet.BTWPacketManager;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.refamished.gui.SkillsGui;
import net.fabricmc.refamished.interfaces.IGridPotion;
import net.fabricmc.refamished.misc.Commands.SkillCmd;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.misc.Potion.RePotion;
import net.fabricmc.refamished.misc.ReMaterials;
import net.fabricmc.refamished.misc.RefamishedPacketManager;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.fabricmc.refamished.skill.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;

import java.lang.reflect.Field;
import java.util.UUID;

public class RefamishedMod implements ModInitializer, RefamishedModExtractor {
	private static SkillPersistentState skillPersistentState;
	public static final UUID toolRangeUUID = UUID.fromString("cfde7e5c-9ccf-4aef-abd4-152fde3d0c1a");
	public static final Difficulty CRUEL = new DifficultyCruel("cruel");
	private static final KeyBinding OPEN_SKILLS_KEY = new KeyBinding("key.skillstab.open", Keyboard.KEY_K);
	public static final RefamishedSoundManager soundManageer = new RefamishedSoundManager();

	public static final Potion INFESTEDWOUND = new RePotion(100, true, Integer.valueOf("904A4A", 16)).setPotionName("potion.infestedwound");
	@Override
	public void onInitialize() {
		addMaterialOverride();
		SkillData skillData = new SkillData();
		skillPersistentState = new SkillPersistentState();
		System.out.println("Hi world!!!!!!!!111 kil me");

		System.out.println("!!!!!!!!!loaded : "+BTWItems.sharpStone);
		//RefamishedPacketManager.initPacketInfo();
		if (INFESTEDWOUND instanceof IGridPotion grid) {
			grid.setGridX(0);
			grid.setGridY(0);
		}
	}

	public static void addMaterialOverride()
	{
		//overrideMaterial(EnumToolMaterial.GOLD, 0, 50, 7.0f, 0.0f, 22, 30, 3);
		//overrideMaterial(EnumToolMaterial.STONE, 1, 75, 1.01f, 0.0f, 5, 10, 1);
		//overrideMaterial(EnumToolMaterial.IRON, 3, 500, 3.0f, 2.0f, 14, 25, 2);
		//overrideMaterial(EnumToolMaterial.EMERALD, 4, 1561, 4.0f, 3.0f, 14, 30, 2);
		//overrideMaterial(EnumToolMaterial.SOULFORGED_STEEL, 5, 2000, 6.0f, 3.0f, 8, 30, 4);
		ReMaterials.init();
	}

	public static void registerKeyBindings(GameSettings gameSettings) {
		KeyBinding[] existingKeyBindings = gameSettings.keyBindings;

		// Create a new array to add the custom key binding
		KeyBinding[] newKeyBindings = new KeyBinding[existingKeyBindings.length + 1];
		System.arraycopy(existingKeyBindings, 0, newKeyBindings, 0, existingKeyBindings.length);

		// Add custom key binding
		newKeyBindings[existingKeyBindings.length] = OPEN_SKILLS_KEY;
		gameSettings.keyBindings = newKeyBindings;

		System.out.println("Registered key binding for Skills GUI (Default key: K)");
	}

	/**
	 * Handle key press logic (called from Mixin during runTick).
	 */
	public static void handleKeyPress() {
		Minecraft minecraft = Minecraft.getMinecraft();

		// Check if the custom key is pressed
		if (OPEN_SKILLS_KEY.isPressed() && minecraft.thePlayer != null) {
			minecraft.displayGuiScreen(new SkillsGui((EntityPlayer) minecraft.thePlayer));
			System.out.println("Skills GUI opened!");
		}
	}

	public static SkillPersistentState getSkillPersistentState() {
		return skillPersistentState;
	}

	@Override
	public void onInitializeClient() {

	}

	public static void overrideMaterial(EnumToolMaterial material, int harvestLevel, int maxUses, float efficiency, float damage, int enchantability, int infernalMaxCost, int infernalMaxEnchants) {
		try {
			Field harvestLevelField = EnumToolMaterial.class.getDeclaredField("harvestLevel");
			Field maxUsesField = EnumToolMaterial.class.getDeclaredField("maxUses");
			Field efficiencyField = EnumToolMaterial.class.getDeclaredField("efficiencyOnProperMaterial");
			Field damageField = EnumToolMaterial.class.getDeclaredField("damageVsEntity");
			Field enchantabilityField = EnumToolMaterial.class.getDeclaredField("enchantability");
			Field infernalMaxCostField = EnumToolMaterial.class.getDeclaredField("infernalMaxEnchantmentCost");
			Field infernalMaxEnchantsField = EnumToolMaterial.class.getDeclaredField("infernalMaxNumEnchants");

			harvestLevelField.setAccessible(true);
			maxUsesField.setAccessible(true);
			efficiencyField.setAccessible(true);
			damageField.setAccessible(true);
			enchantabilityField.setAccessible(true);
			infernalMaxCostField.setAccessible(true);
			infernalMaxEnchantsField.setAccessible(true);

			harvestLevelField.setInt(material, harvestLevel);
			maxUsesField.setInt(material, maxUses);
			efficiencyField.setFloat(material, efficiency);
			damageField.setFloat(material, damage);
			enchantabilityField.setInt(material, enchantability);
			infernalMaxCostField.setInt(material, infernalMaxCost);
			infernalMaxEnchantsField.setInt(material, infernalMaxEnchants);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
