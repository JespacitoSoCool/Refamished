package net.fabricmc.refamished.mixin;

import btw.client.gui.CraftingGuiWorkbench;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.RecipeSkillManager;
import net.fabricmc.refamished.skill.RecipeSkillRequirement;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;

@Mixin(CraftingGuiWorkbench.class)
public class GuiCraftingMixin {
	@Inject(method = "drawGuiContainerForegroundLayer", at = @At("RETURN"))
	private void renderSkillRequirements(int mouseX, int mouseY, CallbackInfo ci) {
		CraftingGuiWorkbench craftingGui = (CraftingGuiWorkbench) (Object) this;
		ContainerWorkbench workbench = (ContainerWorkbench) craftingGui.inventorySlots;

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;

		InventoryCrafting craftingGrid = workbench.craftMatrix;
        assert player != null;
        ItemStack resultItem = CraftingManager.getInstance().findMatchingRecipe(craftingGrid, player.worldObj);

		if (resultItem == null) {
			return;
		}

		RecipeSkillRequirement requirements = RecipeSkillManager.getRequirements(resultItem.itemID);
		if (requirements == null) {
			return;
		}

		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		PlayerSkillData playerSkills = SkillManager.getSkillData(player);

		int yOffset = -10;
		for (Map.Entry<String, Integer> entry : requirements.getRequirements().entrySet()) {
			String skillName = entry.getKey();
			int requiredLevel = entry.getValue();
			int playerLevel = playerSkills.getSkillProgress(skillName).getLevel();

			int color = playerLevel >= requiredLevel ? 0x42ff42 : 0xff3d3d;
			String text = skillName + " " + playerLevel + "/" + requiredLevel;
			int textWidth = fontRenderer.getStringWidth(text);
			int textHeight = 10;

			Gui.drawRect(0 - 2, yOffset - 2, textWidth + 2, yOffset + textHeight - 2, 0x80000000); // 50% transparent black
			fontRenderer.drawString(text, 0, yOffset, color);
			yOffset -= 12;
		}

	}
}