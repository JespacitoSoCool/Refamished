package net.fabricmc.refamished.mixin;

import btw.item.items.ClubItem;
import btw.item.items.SwordItem;
import btw.item.items.ToolItem;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.fabricmc.refamished.quality.ArmorQualityHelper;

import net.fabricmc.refamished.gui.SkillsGui;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityColorHelper;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.src.Gui.drawRect;

@Mixin(GuiIngame.class)
public abstract class GuiIngameMixin {
    @Unique
    boolean usesOldQualityFlags = false;

    @Inject(method = "renderGameOverlay", at = @At("RETURN"))
    private void renderOverlayElements(float partialTicks, boolean hasFocus, int mouseX, int mouseY, CallbackInfo ci) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayer player = mc.thePlayer;

        if (player == null) return;
        usesOldQualityFlags = RefamishedConfig.oldQualityFlags;
        // Render pinned skills
        renderPinnedSkills(mc, player);

        // Render hotbar quality flags
        renderHotbarItemQuality(mc, player);
    }

    private void renderHotbarItemQuality(Minecraft mc, EntityPlayer player) {
        int screenWidth = mc.displayWidth;
        int screenHeight = mc.displayHeight;
        ScaledResolution scaledResolution = new ScaledResolution(mc.gameSettings, screenWidth, screenHeight);

        int barStartX = (scaledResolution.getScaledWidth() - 182) / 2; // Hotbar starts at center + offset
        int barY = scaledResolution.getScaledHeight() - 22; // Bottom of the screen

        InventoryPlayer inventory = player.inventory;

        for (int slot = 0; slot < 9; slot++) { // Iterate hotbar slots
            ItemStack stack = inventory.getStackInSlot(slot);

            if (stack == null || !(ToolQuality.toolHaveQualities(stack.getItem()))) continue;

            if (stack.getItem() instanceof ToolItem ||
                    stack.getItem() instanceof SwordItem ||
                    stack.getItem() instanceof ClubItem ||
                    stack.getItem() instanceof machete ||
                    stack.getItem() instanceof blade)
            {
                ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
                renderQualityFlag(mc, barStartX + (slot * 20) + 3, barY + 3, quality);
            }
            if (stack.getItem() instanceof ItemArmor) {
                ArmorQuality quality = ArmorQualityHelper.getArmorQuality(stack);
                renderQualityFlagArmor(mc, barStartX + (slot * 20) + 3, barY + 3, quality);
            }
        }
    }

    private void renderQualityFlag(Minecraft mc, int x, int y, ToolQuality quality) {
        mc.renderEngine.bindTexture(new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png"));

        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            float[] rgb = ToolQualityColorHelper.getRGBFromFormatting(quality.getColor());
            int index = ToolQualityColorHelper.getIndexFromFormatting(quality.getColor());
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 0.5f);

            // Render quality icons (iconIndex based on quality)
            Minecraft.getMinecraft().getTextureManager().bindTexture(
                    new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png")
            );
            int iconSize = 7;
            int iconOffset = usesOldQualityFlags ? 7 : 0;
            ((Gui) (Object) this).drawTexturedModalRect(x, y, iconOffset, index * 7, 7, 7);

            GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glPopMatrix();
    }

    private void renderQualityFlagArmor(Minecraft mc, int x, int y, ArmorQuality quality) {
        mc.renderEngine.bindTexture(new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png"));

        GL11.glPushMatrix();
        {
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

            float[] rgb = ToolQualityColorHelper.getRGBFromFormatting(quality.getColor());
            int index = ToolQualityColorHelper.getIndexFromFormatting(quality.getColor());
            GL11.glColor4f(rgb[0], rgb[1], rgb[2], 0.5f);

            // Render quality icons (iconIndex based on quality)
            Minecraft.getMinecraft().getTextureManager().bindTexture(
                    new ResourceLocation("refamished", "textures/gui/tool_quality_marker.png")
            );
            int iconSize = 7;
            int iconOffset = usesOldQualityFlags ? 7 : 0;
            ((Gui) (Object) this).drawTexturedModalRect(x, y, iconOffset, index * 7, 7, 7);

            GL11.glDisable(GL11.GL_BLEND);
        }
        GL11.glPopMatrix();
    }

    private void renderPinnedSkills(Minecraft mc, EntityPlayer player) {

        if (player == null) return;

        PlayerSkillData skillData = SkillManager.getSkillData(player);
        if (skillData == null || skillData.pinnedSkills.isEmpty()) return;

        boolean isSkillsGuiOpen = mc.currentScreen instanceof SkillsGui;
        boolean isOtherGuiOpen = mc.currentScreen != null && !(mc.currentScreen instanceof SkillsGui);
        int x, y;
        boolean showArtisanry = false;
        if (isOtherGuiOpen) {
            GuiContainer container = mc.currentScreen instanceof GuiContainer ? (GuiContainer) mc.currentScreen : null;
            if (container != null) {
                x = container.getGuiLeft();
                y = 5;
                showArtisanry = true;
            }
            else {
                x = 5;
                y = 5;
            }
        } else {
            x = 5;
            y = 5;
        }

        for (String skillName : skillData.pinnedSkills) {
            if (showArtisanry && !skillName.equals("Artisanry")) continue;
            PlayerSkillData.SkillProgress progress = skillData.getSkillProgress(skillName);
            if (progress == null) continue;

            int level = progress.getLevel();
            int experience = progress.getExperience();
            int xpRequired = SkillData.getExperienceRequired(skillName, level);

            mc.fontRenderer.drawStringWithShadow(skillName + " (Lv: " + level + ")", x, y, 0xFFFFFF);
            y += 10;

            renderProgressBar(mc, x, y, experience, xpRequired, level == SkillData.getLevelCap(skillName));
            y += 10;
        }
    }

    @Unique
    private int getGradientColor(float percentage) {
        // Ensure percentage is within range (0.0 to 1.0)
        percentage = Math.max(0.0f, Math.min(1.0f, percentage));

        int red = (int) (255 * (1.0f - percentage)); // Red decreases as percentage increases
        int green = (int) (255 * percentage);       // Green increases as percentage increases

        return 0xFF000000 | (red << 16) | (green << 8); // Combine into an ARGB color (Alpha = 255)
    }

    private static final ResourceLocation SKILL_TEXTURE = new ResourceLocation("refamished", "textures/gui/skill.png");
    @Unique
    private void renderProgressBar(Minecraft mc, int x, int y, int value, int max, boolean isFull) {
        int barWidth = 80;
        float percentage = Math.min(1.0f, (float) value / (float) max);
        int filledWidth = (int) (barWidth * percentage);

        int backgroundColor = 0xFF191919;
        //int filledColor = isFull ? 0xFFffffff : getGradientColor(percentage);
        int bary = isFull ? 208 : 199;
        GuiIngame renderer = (GuiIngame)(Object)this;

        mc.renderEngine.bindTexture(SKILL_TEXTURE);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        renderer.drawTexturedModalRect(x, y, 0, 190, barWidth, 9);

        float red = 1.0f - percentage;
        GL11.glColor4f(red, percentage, 0.0f, 1.0f);
        renderer.drawTexturedModalRect(x, y, 0, bary, filledWidth, 9);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        //drawRect(x, y, x + barWidth, y + 5, backgroundColor);
        //drawRect(x, y, x + filledWidth, y + 5, filledColor);
    }

    @Inject(method = "renderVignette", at = @At("HEAD"), cancellable = true)
    private void renderOverlayElements(float par1, int par2, int par3, CallbackInfo ci) {
        if (!RefamishedConfig.vignette) {
            ci.cancel();
        }
    }
}