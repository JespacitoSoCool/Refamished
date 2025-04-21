package net.fabricmc.refamished.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.skill.*;
import net.minecraft.src.EntityPlayer;
import net.fabricmc.refamished.skill.SkillData;
import net.minecraft.src.GuiScreen;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.ResourceLocation;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard; // Older Minecraft key binding system
import org.lwjgl.opengl.GL11;

public class SkillsGui extends GuiScreen {

    private static final int GUI_WIDTH = 176;
    private static final int GUI_HEIGHT = 166;

    private int guiLeft;
    private int guiTop;

    private final List<String> skills = new ArrayList<>();
    private final Map<String, Integer> skillLevels = new HashMap<>();
    private final Map<String, Integer> skillExperiences = new HashMap<>();

    private int scrollPosition = 0;
    private final int visibleRows = 6;
    private final int rowHeight = 20;
    private final int emblemSize = 16;

    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("refamished", "textures/gui/background_blank.png");
    private static final ResourceLocation SKILL_TEXTURE = new ResourceLocation("refamished", "textures/gui/skill.png");

    private final EntityPlayer player;

    private final List<String> pinnedSkills = new ArrayList<>();

    public SkillsGui(EntityPlayer player) {
        this.player = player;

        loadSkillsData();
    }

    private final Map<String, Integer> texInd = new HashMap<>();


    @Override
    public void initGui() {
        super.initGui();
        this.guiLeft = (this.width - GUI_WIDTH) / 2;
        this.guiTop = (this.height - GUI_HEIGHT) / 2;
        texInd.put("Weaving", 0);
        texInd.put("Chipping", 1);
        texInd.put("Artisanry", 2);
        texInd.put("Endurance", 3);
        texInd.put("Drift", 3);
        texInd.put("Refinement", 3);
    }

    private void drawProgressBar(int x, int y, int value, int max, boolean isFull) {
        int barWidth = 129;
        int barHeight = 16;

        float percentage = Math.min(1.0f, (float) value / (float) max);
        int filledWidth = (int) (barWidth * percentage);

        this.mc.renderEngine.bindTexture(SKILL_TEXTURE);

        this.drawTexturedModalRect(x, y, 0, 126, barWidth, barHeight);

        if (isFull) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawTexturedModalRect(x, y, 0, 174, barWidth, barHeight);
        } else {
            float red = 1.0f - percentage;
            float green = percentage;
            GL11.glColor4f(red, green, 0.0f, 1.0f);

            this.drawTexturedModalRect(x, y, 0, 126+barHeight, filledWidth, barHeight);
        }

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }

    private void renderToolTip(String text, int x, int y) {
        int textWidth = this.fontRenderer.getStringWidth(text);
        int padding = 4;

        drawRect(x, y - 10, x + textWidth + padding, y, 0xFF000000); // Black background
        this.fontRenderer.drawStringWithShadow(text, x + 2, y - 9, 0xFFFFFF); // White text
    }

    @Override
    public void handleInput() {
        super.handleInput();

        int delta = org.lwjgl.input.Mouse.getDWheel(); // Mouse wheel scroll
        if (delta > 0) {
            scrollPosition = Math.max(0, scrollPosition - 1);
        } else if (delta < 0) {
            scrollPosition = Math.min(skills.size() - visibleRows, scrollPosition + 1);
        }
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false; // Keep the game running while the GUI is open
    }

    private void loadSkillsData() {
        skills.clear();
        skillLevels.clear();
        skillExperiences.clear();
        pinnedSkills.clear(); // Clear local pinned skills list

        PlayerSkillData skillData = SkillManager.getSkillData(player);
        if (skillData == null) return;

        for (Map.Entry<String, PlayerSkillData.SkillProgress> entry : skillData.getAllSkills().entrySet()) {
            String skillName = entry.getKey();
            PlayerSkillData.SkillProgress skillProgress = entry.getValue();

            if (skillProgress != null) {
                skills.add(skillName);
                skillLevels.put(skillName, skillProgress.getLevel());
                skillExperiences.put(skillName, skillProgress.getExperience());
            }
        }

        // Synchronize pinned skills from player data
        pinnedSkills.addAll(skillData.pinnedSkills);

        // Ensure pinned skills are displayed first
        skills.sort((a, b) -> {
            boolean aPinned = pinnedSkills.contains(a);
            boolean bPinned = pinnedSkills.contains(b);

            if (aPinned && !bPinned) return -1; // Pinned skill comes first
            if (!aPinned && bPinned) return 1;  // Unpinned skill comes after
            return Integer.compare(skillLevels.getOrDefault(b, 0), skillLevels.getOrDefault(a, 0));
        });
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.mc.renderEngine.bindTexture(BACKGROUND_TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, GUI_WIDTH, GUI_HEIGHT);

        // Header
        this.drawCenteredString(this.fontRenderer, "Player Skills", this.width / 2, guiTop + 10, 0xFFFFFF);

        if (skills.isEmpty()) {
            // Indicate no skills are available
            this.drawCenteredString(this.fontRenderer, "No skills available", this.width / 2, guiTop + 50, 0xFFFFFF);
            return;
        }

        int startY = guiTop + 30;

        // Render each skill on the GUI
        for (int i = 0; i < visibleRows; i++) {
            int index = scrollPosition + i;

            if (index >= skills.size()) break;
            String skillName = skills.get(index);
            int level = skillLevels.getOrDefault(skillName, 1);
            int experience = skillExperiences.getOrDefault(skillName, 0);
            int xpRequired = SkillData.getExperienceRequired(skillName, level);
            int levelCap = SkillData.getLevelCap(skillName);
            boolean isFull = level == levelCap && experience >= xpRequired;
            int rowYPos = startY + (i * rowHeight);
            this.mc.renderEngine.bindTexture(SKILL_TEXTURE);
            this.drawTexturedModalRect(guiLeft, rowYPos, 0, 86, 176, 20);

            drawProgressBar(guiLeft + 41, rowYPos+2, experience, xpRequired, isFull);

            int pinIconX = guiLeft - 14; // Pin icon position

            this.mc.renderEngine.bindTexture(SKILL_TEXTURE);

            // Render pin icon: Use "pinned" texture if pinned, otherwise "unpinned" texture
            int pinTextureX = pinnedSkills.contains(skillName) ? 80 : 92;
            this.drawTexturedModalRect(pinIconX, rowYPos+4, pinTextureX, 12, 12, 12);

            //System.out.println(((float)level/(float)levelCap));
            this.drawTexturedModalRect(guiLeft+4, rowYPos+2, (int)Math.ceil(((float)level/(float)levelCap)*9)*16, 24, 16, 16);
            this.drawTexturedModalRect(guiLeft+4, rowYPos+2, texInd.get(skillName)*16, 40, 16, 16);
            this.fontRenderer.drawStringWithShadow(skillName, guiLeft + 50, rowYPos+6, 0xFFFFFF);
            String lvlText = isFull ? "L.M" : "L."+level;
            this.fontRenderer.drawStringWithShadow(lvlText, guiLeft + 22, rowYPos+6, 0xFFFFFF);

            int barX = guiLeft + 41;
            int barY = rowYPos+2;
            if (mouseX >= barX && mouseX <= barX + 129 && mouseY >= barY && mouseY <= barY + 16) {
                renderToolTip(
                        String.format(
                                "%s | Level: %d/%d | XP: %d/%d",
                                skillName, level, levelCap, experience, xpRequired
                        ), mouseX, mouseY
                );
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int startY = guiTop + 30;

        for (int i = 0; i < visibleRows; i++) {
            int index = scrollPosition + i;
            if (index >= skills.size()) break;

            String skillName = skills.get(index);

            int pinButtonX = guiLeft - 14;
            int pinButtonY = startY + (i * rowHeight)+4;

            if (mouseX >= pinButtonX && mouseX <= pinButtonX + 12 &&
                    mouseY >= pinButtonY && mouseY <= pinButtonY + 12) {

                if (pinnedSkills.contains(skillName)) {
                    pinnedSkills.remove(skillName);
                } else {
                    pinnedSkills.add(skillName);
                }

                // Sync changes to PlayerSkillData
                PlayerSkillData skillData = SkillManager.getSkillData(player);
                skillData.pinnedSkills.clear();
                skillData.pinnedSkills.addAll(pinnedSkills);
            }
        }
    }
}