package net.fabricmc.refamished;

import btw.AddonHandler;
import btw.item.BTWItems;
import btw.world.util.WorldUtils;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.refamished.entities.*;
import net.fabricmc.refamished.entities.render.*;
import net.fabricmc.refamished.entities.tiles.burntChestTile;
import net.fabricmc.refamished.entities.tiles.cokeovenTile;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.fabricmc.refamished.gui.SkillsGui;
import net.fabricmc.refamished.interfaces.IGridPotion;
import net.fabricmc.refamished.misc.*;
import net.fabricmc.refamished.misc.Potion.RePotion;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillPersistentState;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.UUID;

@Environment(value = EnvType.CLIENT)
public class RefamishedModClient {
	private static KeyBinding OPEN_SKILLS_KEY = null;
	public static void onInitialize() {
		OPEN_SKILLS_KEY = new KeyBinding("key.skillstab.open", Keyboard.KEY_K);
	}

	public static void registerKeyBindings(GameSettings gameSettings) {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			KeyBinding[] existingKeyBindings = gameSettings.keyBindings;

			KeyBinding[] newKeyBindings = new KeyBinding[existingKeyBindings.length + 1];
			System.arraycopy(existingKeyBindings, 0, newKeyBindings, 0, existingKeyBindings.length);

			newKeyBindings[existingKeyBindings.length] = OPEN_SKILLS_KEY;
			gameSettings.keyBindings = newKeyBindings;

			System.out.println("Registered key binding for Skills GUI (Default key: K)");
		}
	}

	public static void handleKeyPress() {
		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			Minecraft minecraft = Minecraft.getMinecraft();

			if (OPEN_SKILLS_KEY != null && OPEN_SKILLS_KEY.isPressed() && minecraft.thePlayer != null) {
				minecraft.displayGuiScreen(new SkillsGui((EntityPlayer) minecraft.thePlayer));
				System.out.println("Skills GUI opened!");
			}
		}
	}

	public static void registerEntitiesRenderer() {
		TileEntityRenderer.instance.addSpecialRendererForClass(cokeovenTile.class, new CokeOvenRenderer());
		TileEntityRenderer.instance.addSpecialRendererForClass(tarTankTile.class, new TarTankRenderer());
		TileEntityRenderer.instance.addSpecialRendererForClass(burntChestTile.class, new BurntChestRenderer());
		//RenderManager.addEntityRenderer(EntitySKOrb.class, new RenderSKOrb());
		RenderManager.addEntityRenderer(EntityIncendiaryArrow.class, new ArrowIncendiaryRender());
		RenderManager.addEntityRenderer(EntityFlintArrow.class, new ArrowFlintRender());
		RenderManager.addEntityRenderer(EntityGoldArrow.class, new ArrowGoldRender());
		RenderManager.addEntityRenderer(EntityFrostArrow.class, new ArrowFrostRender());
		RenderManager.addEntityRenderer(EntityBoneBolt.class, new BoltBaseRender("bolt_bone"));
		RenderManager.addEntityRenderer(EntityCopperBolt.class, new BoltBaseRender("bolt_iron"));
		RenderManager.addEntityRenderer(EntityMolotov.class, new RenderSnowball(RefamishedItems.tar_molotov_lit));
		RenderManager.addEntityRenderer(EntityMolotovHellfire.class, new RenderSnowball(RefamishedItems.hellfire));
	}
}
