package net.fabricmc.refamished.mixin;

import btw.item.items.ClubItem;
import btw.item.items.ToolItem;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityColorHelper;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiContainer.class)
public abstract class GuiContainerMixin {
	@Unique
	boolean usesOldQualityFlags = false;

	@Inject(method = "drawScreen", at = @At(value = "INVOKE", target = "net/minecraft/src/GuiContainer.drawGuiContainerForegroundLayer(II)V"))
	public void onDrawScreen(int mouseX, int mouseY, float partialTicks, CallbackInfo ci) {
		GuiContainer gui = (GuiContainer) (Object) this;

		int guiLeft = gui.getGuiLeft();
		int guiTop = gui.getGuiTop();

		// Save all necessary OpenGL states at once
		GL11.glPushAttrib(GL11.GL_ALL_ATTRIB_BITS);
		usesOldQualityFlags = RefamishedConfig.oldQualityFlags;

		RenderHelper.disableStandardItemLighting(); // Disable inventory lighting

		for (Object slotObject : gui.inventorySlots.inventorySlots) {
			Slot slot = (Slot) slotObject;
			ItemStack stack = slot.getStack();

			int slotX = slot.xDisplayPosition;
			int slotY = slot.yDisplayPosition;

			if (stack != null && (stack.getItem() instanceof ToolItem
					|| stack.getItem() instanceof ItemSword
					|| stack.getItem() instanceof ClubItem
					|| stack.getItem() instanceof machete
					|| stack.getItem() instanceof blade)) {
				ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
				EnumChatFormatting color = quality.getColor();

				GL11.glPushMatrix(); // Save matrix state

				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDisable(GL11.GL_DEPTH_TEST);

				float[] rgb = ToolQualityColorHelper.getRGBFromFormatting(color);
				int index = ToolQualityColorHelper.getIndexFromFormatting(color);
				GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f); // Apply quality color

				Minecraft.getMinecraft().getTextureManager().bindTexture(
						new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png")
				);
				int iconOffset = usesOldQualityFlags ? 7 : 0;
				((Gui) (Object) this).drawTexturedModalRect(slotX, slotY, iconOffset, index * 7, 7, 7);

				GL11.glPopMatrix(); // Restore matrix state
			}
			else if (stack != null && stack.getItem() instanceof ItemArmor) {
				ArmorQuality quality = ArmorQualityHelper.getArmorQuality(stack);
				EnumChatFormatting color = quality.getColor();

				GL11.glPushMatrix(); // Save matrix state

				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glDisable(GL11.GL_DEPTH_TEST);

				float[] rgb = ToolQualityColorHelper.getRGBFromFormatting(color);
				int index = ToolQualityColorHelper.getIndexFromFormatting(color);
				GL11.glColor4f(rgb[0], rgb[1], rgb[2], 1.0f); // Apply quality color

				Minecraft.getMinecraft().getTextureManager().bindTexture(
						new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png")
				);
				int iconOffset = usesOldQualityFlags ? 7 : 0;
				((Gui) (Object) this).drawTexturedModalRect(slotX, slotY, iconOffset, index * 7, 7, 7);

				GL11.glPopMatrix(); // Restore matrix state
			}
		}

		// Restore all previously saved OpenGL states
		GL11.glPopAttrib();
	}
}