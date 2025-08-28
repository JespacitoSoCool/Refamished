package net.fabricmc.refamished.mixin.mixin_ui;

import btw.client.gui.LockButton;
import btw.world.util.difficulty.Difficulties;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.refamished.mixin.interfaces.GuiScreenInterface;
import net.fabricmc.refamished.mixin.interfaces.GuiCreateWorldInterface;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.minecraft.src.*;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Project;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin {
    @Shadow protected abstract void updateButtonText();
    @Shadow private GuiScreen parentGuiScreen;
    @Shadow private GuiTextField textboxWorldName;
    @Shadow private GuiTextField textboxSeed;
    @Shadow private String folderName;
    @Shadow private String gameMode = "survival";
    @Shadow private int difficultyID = 0;
    @Shadow private boolean lockDifficulty = true;
    @Shadow private boolean generateStructures = true;
    @Shadow private boolean commandsAllowed;
    @Shadow private boolean commandsToggled;
    @Shadow private boolean bonusItems;
    @Shadow private boolean isHardcore;
    @Shadow private boolean createClicked;
    @Shadow private GuiButton buttonGameMode;
    @Shadow private GuiButton buttonDifficultyLevel;
    @Shadow private LockButton buttonLockDifficulty;
    @Shadow private GuiButton buttonGenerateStructures;
    @Shadow private GuiButton buttonBonusItems;
    @Shadow private GuiButton buttonWorldType;
    @Shadow private GuiButton buttonAllowCommands;
    @Shadow private GuiButton buttonCustomize;
    @Shadow private String gameModeDescriptionLine1;
    @Shadow private String gameModeDescriptionLine2;
    @Shadow private String seed;
    @Shadow private String localizedNewWorldText;
    @Shadow private int worldTypeId;
    @Shadow private static final String[] ILLEGAL_WORLD_NAMES = new String[]{"CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9"};

    @Unique
    String oldSeed = "";
    Boolean chestEnabled = false;
    Boolean cheatsEnabled = false;
    Difficulty oldDif = null;
    ResourceLocation field_110351_G;
    DynamicTexture viewportTexture;
    int[] settingIcons = new int[4];
    ResourceLocation creationWidgets = new ResourceLocation("refamished:textures/gui/creation.png");

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    private void onDrawScreen(int par1, int par2, float par3, CallbackInfo ci) {
        GuiCreateWorld idk = (GuiCreateWorld) (Object) this;
        int id = ((GuiCreateWorldInterface) this).getId();
        boolean options = ((GuiCreateWorldInterface) this).getOptions();
        GuiCreateWorldInterface inter = ((GuiCreateWorldInterface) this);
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        FontRenderer font = ((GuiScreenInterface) this).getFontRenderer();
        Difficulty selectedDifficulty = Difficulties.DIFFICULTY_LIST.get(id);
        FontRenderer renderer = screen.getFontRenderer();
        List buttonList = screen.getButtonList();
        Minecraft mc = screen.getMc();
        renderSkybox(par1,par2,par3);
        boolean changedDifficulty = oldDif != selectedDifficulty;
        idk.drawCenteredString(renderer, I18n.getString("selectWorld.create"), idk.width / 2, 15, 0xFFFFFF);
        idk.drawString(renderer, I18n.getString("selectWorld.enterName"), 15, 47, 0xA0A0A0);
        idk.drawString(renderer, I18n.getString("selectWorld.resultFolder") + " " + this.folderName, 15, 85, 0xA0A0A0);
        this.textboxWorldName.drawTextBox();
        if (selectedDifficulty instanceof DifficultyCruel) {
            if (changedDifficulty)
            {
                if (!MathHelper.stringNullOrLengthZero(inter.getTextboxSeed().getText())) {
                    oldSeed = inter.getTextboxSeed().getText();
                }
                chestEnabled = inter.bonusItems();
                cheatsEnabled = inter.commandsAllowed();
            }
            long forcedSeed = new Random().nextLong();
            inter.getTextboxSeed().setText(String.valueOf(forcedSeed));
            inter.setBonusItems(false);
            inter.setCommandValue(false);
        }
        else
        {
            if (changedDifficulty) {
                inter.getTextboxSeed().setText(String.valueOf(oldSeed));
                oldSeed = "";
                inter.setBonusItems(chestEnabled);
                inter.setCommandValue(cheatsEnabled);
            }
        }
        int hexColor = selectedDifficulty instanceof DifficultyCruel ? 0xff6969 : 0xFFFFFF;
        font.drawStringWithShadow(I18n.getString("difficulty." + Difficulties.DIFFICULTY_LIST.get((int)this.difficultyID).NAME + ".description"), 15, 125, hexColor);
        font.drawStringWithShadow(I18n.getString("difficulty." + Difficulties.DIFFICULTY_LIST.get((int)this.difficultyID).NAME + ".description2"), 15, 137, hexColor);
        font.drawStringWithShadow(I18n.getString("difficulty." + Difficulties.DIFFICULTY_LIST.get((int)this.difficultyID).NAME + ".description3"), 15, 149, hexColor);
        if (selectedDifficulty instanceof DifficultyCruel) {
            font.drawStringWithShadow(I18n.getString("difficulty.cruel.extra1"), 15, 161, hexColor);
            font.drawStringWithShadow(I18n.getString("difficulty.cruel.extra2"), 15, 173, hexColor);
        }
        updateButtonText();
        oldDif = selectedDifficulty;
        ci.cancel();

        int settingsLine = 35;
        int padding = 25;
        for (int value : settingIcons) {
            mc.getTextureManager().bindTexture(creationWidgets);
            idk.drawTexturedModalRect(idk.width-185, settingsLine, value*20, 20, 20,20);
            settingsLine += padding;
        }

        for (int var4 = 0; var4 < buttonList.size(); ++var4) {
            GuiButton var5 = (GuiButton)buttonList.get(var4);
            var5.drawButton(screen.getMc(), par1, par2);
        }

    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void actionPerformed(GuiButton par1GuiButton, CallbackInfo ci) {
        GuiCreateWorld idk = (GuiCreateWorld) (Object) this;
        GuiCreateWorldInterface inter = ((GuiCreateWorldInterface) this);
        int id = ((GuiCreateWorldInterface) this).getId();
        Difficulty selectedDifficulty = Difficulties.DIFFICULTY_LIST.get(id);
        if (selectedDifficulty instanceof DifficultyCruel) {
            if (par1GuiButton.enabled) {
                if (par1GuiButton.id == 1) {
                } else if (par1GuiButton.id == 0) {
                    long forcedSeed = new Random().nextLong();
                    inter.getTextboxSeed().setText(String.valueOf(forcedSeed));
                    inter.setBonusItems(false);
                    inter.setCommandValue(false);
                } else if (par1GuiButton.id == 9) {
                    ci.cancel();
                    inter.setId(inter.getId()+1);
                    if (!(inter.getId() >= Difficulties.DIFFICULTY_LIST.size()) && Difficulties.DIFFICULTY_LIST.get(inter.getId()).isRestricted()) {
                        inter.setId(inter.getId()+1);;
                        System.out.println("Skipped");
                    }
                    if (inter.getId() >= Difficulties.DIFFICULTY_LIST.size()) {
                        inter.setId(0);
                    }
                    this.updateButtonText();
                }
            }
        }
    }

    @Inject(method = "updateButtonText", at = @At("HEAD"), cancellable = true)
    private void update(CallbackInfo ci) {
        String gameMode = this.gameMode;
        if (this.gameMode.equals("survival") && !Difficulties.DIFFICULTY_LIST.get(this.difficultyID).hasHardcoreSpawn()) {
            gameMode = "survivalClassic";
        }
        this.buttonGameMode.displayString = I18n.getString("selectWorld.gameMode") + " " + I18n.getString("selectWorld.gameMode." + gameMode);
        this.gameModeDescriptionLine1 = I18n.getString("selectWorld.gameMode." + gameMode + ".line1");
        this.gameModeDescriptionLine2 = I18n.getString("selectWorld.gameMode." + gameMode + ".line2");
        this.buttonGenerateStructures.displayString = I18n.getString("selectWorld.mapFeatures") + " ";
        this.buttonGenerateStructures.displayString = this.generateStructures ? this.buttonGenerateStructures.displayString + I18n.getString("options.on") : this.buttonGenerateStructures.displayString + I18n.getString("options.off");
        this.buttonBonusItems.displayString = I18n.getString("selectWorld.bonusItems") + " ";
        this.buttonBonusItems.displayString = this.bonusItems && !this.isHardcore ? this.buttonBonusItems.displayString + I18n.getString("options.on") : this.buttonBonusItems.displayString + I18n.getString("options.off");
        this.buttonWorldType.displayString = I18n.getString("selectWorld.mapType") + " " + I18n.getString(WorldType.worldTypes[this.worldTypeId].getTranslateName());
        this.buttonAllowCommands.displayString = I18n.getString("selectWorld.allowCommands") + " ";
        this.buttonAllowCommands.displayString = this.commandsAllowed && !this.isHardcore ? this.buttonAllowCommands.displayString + I18n.getString("options.on") : this.buttonAllowCommands.displayString + I18n.getString("options.off");
        this.buttonDifficultyLevel.displayString = I18n.getString("selectWorld.difficulty") + ": " + Difficulties.DIFFICULTY_LIST.get(this.difficultyID).getLocalizedName();
        if (Difficulties.DIFFICULTY_LIST.get(this.difficultyID).canSwitchDifficulty()) {
            this.buttonLockDifficulty.isLocked = this.lockDifficulty;
            this.buttonLockDifficulty.enabled = true;
        } else {
            this.buttonLockDifficulty.isLocked = true;
            this.buttonLockDifficulty.enabled = false;
        }
        ci.cancel();
    }

    @Inject(method = "func_82288_a", at = @At("HEAD"), cancellable = true)
    private void update2(CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "initGui", at = @At("HEAD"), cancellable = true)
    private void init(CallbackInfo ci) {
        Keyboard.enableRepeatEvents(true);
        GuiCreateWorld ts = (GuiCreateWorld) (Object) this;
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        List buttonList = screen.getButtonList();
        FontRenderer renderer = screen.getFontRenderer();
        Minecraft mc = screen.getMc();
        this.viewportTexture = new DynamicTexture(256, 256);
        this.field_110351_G = mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        int settingsLine = 35;
        int padding = 25;
        buttonList.clear();
        buttonList.add(new GuiButton(0, 0, ts.height - 20, 150, 20, I18n.getString("selectWorld.create")));
        buttonList.add(new GuiButton(1, 150, ts.height-20, 150, 20, I18n.getString("gui.cancel")));
        this.buttonGameMode = new GuiButton(2, 15, 100, 150, 20, I18n.getString("selectWorld.gameMode"));
        buttonList.add(this.buttonGameMode);
        this.buttonAllowCommands = new GuiButton(6, 15, ts.height-55, 100, 20, I18n.getString("selectWorld.allowCommands"));
        buttonList.add(this.buttonAllowCommands);
        this.buttonCustomize = new GuiButton(8, ts.width-165, ts.height-40, 150, 20, I18n.getString("selectWorld.customizeType"));
        buttonList.add(this.buttonCustomize);
        this.textboxWorldName = new GuiTextField(renderer, 15, 60, 200, 20);
        this.textboxWorldName.setFocused(true);
        this.textboxWorldName.setText(this.localizedNewWorldText);
        this.textboxSeed = new GuiTextField(renderer, 15, 200, 200, 20);
        this.textboxSeed.setText(this.seed);

        this.buttonDifficultyLevel = new GuiButton(9, ts.width-145, settingsLine, 130, 20, I18n.getString("selectWorld.difficulty"));
        this.buttonLockDifficulty = new LockButton(300, ts.width-165, settingsLine);
        buttonList.add(this.buttonLockDifficulty);
        settingsLine += padding;
        buttonList.add(this.buttonDifficultyLevel);
        settingIcons[0]=0;

        this.buttonGenerateStructures = new GuiButton(4, ts.width-165, settingsLine, 150, 20, I18n.getString("selectWorld.mapFeatures"));
        settingsLine += padding;
        buttonList.add(this.buttonGenerateStructures);
        settingIcons[1]=3;

        this.buttonBonusItems = new GuiButton(7, ts.width-165, settingsLine, 150, 20, I18n.getString("selectWorld.bonusItems"));
        settingsLine += padding;
        buttonList.add(this.buttonBonusItems);
        settingIcons[2]=2;

        this.buttonWorldType = new GuiButton(5, ts.width-165, settingsLine, 150, 20, I18n.getString("selectWorld.mapType"));
        settingsLine += padding;
        buttonList.add(this.buttonWorldType);
        settingIcons[3]=1;

        /*
                int[] moddedSettings = new int[]{};

        for (int value : moddedSettings) {
            GuiButton button = (GuiButton) buttonList.get(value);
            if (button != null) {
                button.width = 150;
                button.xPosition = ts.width-165;
                button.yPosition = settingsLine;
                settingsLine += padding;
            }
        }
         */

        this.makeUseableName();
        this.updateButtonText();
        ci.cancel();
    }

    private void makeUseableName() {
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        this.folderName = this.textboxWorldName.getText().trim();
        for (char var4 : ChatAllowedCharacters.allowedCharactersArray) {
            this.folderName = this.folderName.replace(var4, '_');
        }
        if (MathHelper.stringNullOrLengthZero(this.folderName)) {
            this.folderName = "World";
        }
        this.folderName = GuiCreateWorld.func_73913_a(screen.getMc().getSaveLoader(), this.folderName);
    }

    private float panoramaTimer;
    private static final ResourceLocation[] titlePanoramaStandard;
    private static final ResourceLocation[] titlePanoramaPathsCruel;
    private static String dir = "refamished:textures/gui/backgrounds/";
    static {
        titlePanoramaStandard = new ResourceLocation[]{new ResourceLocation(dir+"standard/panorama_0.png"),
                new ResourceLocation(dir+"standard/panorama_1.png"), new ResourceLocation(dir+"standard/panorama_2.png"),
                new ResourceLocation(dir+"standard/panorama_3.png"), new ResourceLocation(dir+"standard/panorama_4.png"),
                new ResourceLocation(dir+"standard/panorama_5.png")};
        titlePanoramaPathsCruel = new ResourceLocation[]{new ResourceLocation(dir+"cruel/panorama_0.png"),
                new ResourceLocation(dir+"cruel/panorama_1.png"), new ResourceLocation(dir+"cruel/panorama_2.png"),
                new ResourceLocation(dir+"cruel/panorama_3.png"), new ResourceLocation(dir+"cruel/panorama_4.png"),
                new ResourceLocation(dir+"cruel/panorama_5.png")};
    }

    private void drawPanorama(int par1, int par2, float par3) {
        GuiCreateWorld ts = (GuiCreateWorld) (Object) this;
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        List buttonList = screen.getButtonList();
        FontRenderer renderer = screen.getFontRenderer();
        Minecraft mc = screen.getMc();
        Tessellator var4 = Tessellator.instance;
        GL11.glMatrixMode(5889);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        Project.gluPerspective(120.0f, 1.0f, 0.05f, 10.0f);
        GL11.glMatrixMode(5888);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glRotatef(180.0f, 1.0f, 0.0f, 0.0f);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glDepthMask(false);
        GL11.glBlendFunc(770, 771);
        int var5 = 8;
        for (int var6 = 0; var6 < var5 * var5; ++var6) {
            GL11.glPushMatrix();
            float var7 = ((float)(var6 % var5) / (float)var5 - 0.5f) / 64.0f;
            float var8 = ((float)(var6 / var5) / (float)var5 - 0.5f) / 64.0f;
            float var9 = 0.0f;
            GL11.glTranslatef(var7, var8, var9);
            panoramaTimer += 1f/60f;
            //GL11.glRotatef(MathHelper.sin(((float)this.panoramaTimer + par3) / 400.0f) * 25.0f + 20.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-((float)this.panoramaTimer + par3) * 0.1f, 0.0f, 1.0f, 0.0f);
            for (int var10 = 0; var10 < 6; ++var10) {
                GL11.glPushMatrix();
                if (var10 == 1) {
                    GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 2) {
                    GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 3) {
                    GL11.glRotatef(-90.0f, 0.0f, 1.0f, 0.0f);
                }
                if (var10 == 4) {
                    GL11.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
                }
                if (var10 == 5) {
                    GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
                }
                Difficulty selectedDifficulty = Difficulties.DIFFICULTY_LIST.get(difficultyID);
                mc.getTextureManager().bindTexture(selectedDifficulty instanceof DifficultyCruel ? titlePanoramaPathsCruel[var10] : titlePanoramaStandard[var10]);
                var4.startDrawingQuads();
                var4.setColorRGBA_I(0xFFFFFF, 255 / (var6 + 1));
                float var11 = 0.0f;
                var4.addVertexWithUV(-1.0, -1.0, 1.0, 0.0f + var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, -1.0, 1.0, 1.0f - var11, 0.0f + var11);
                var4.addVertexWithUV(1.0, 1.0, 1.0, 1.0f - var11, 1.0f - var11);
                var4.addVertexWithUV(-1.0, 1.0, 1.0, 0.0f + var11, 1.0f - var11);
                var4.draw();
                GL11.glPopMatrix();
            }
            GL11.glPopMatrix();
            GL11.glColorMask(true, true, true, false);
        }
        var4.setTranslation(0.0, 0.0, 0.0);
        GL11.glColorMask(true, true, true, true);
        GL11.glMatrixMode(5889);
        GL11.glPopMatrix();
        GL11.glMatrixMode(5888);
        GL11.glPopMatrix();
        GL11.glDepthMask(true);
        GL11.glEnable(2884);
        GL11.glEnable(3008);
        GL11.glEnable(2929);
    }

    private void rotateAndBlurSkybox(float par1) {
        GuiCreateWorld ts = (GuiCreateWorld) (Object) this;
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        List buttonList = screen.getButtonList();
        FontRenderer renderer = screen.getFontRenderer();
        Minecraft mc = screen.getMc();
        mc.getTextureManager().bindTexture(field_110351_G);
        GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, 256, 256);
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glColorMask(true, true, true, false);
        Tessellator var2 = Tessellator.instance;
        var2.startDrawingQuads();
        int var3 = 3;
        for (int var4 = 0; var4 < var3; ++var4) {
            var2.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f / (float)(var4 + 1));
            int var5 = ts.width;
            int var6 = ts.height;
            float var7 = (float)(var4 - var3 / 2) / 256.0f;
            var2.addVertexWithUV(var5, var6, ts.zLevel, 0.0f + var7, 0.0);
            var2.addVertexWithUV(var5, 0.0, ts.zLevel, 1.0f + var7, 0.0);
            var2.addVertexWithUV(0.0, 0.0, ts.zLevel, 1.0f + var7, 1.0);
            var2.addVertexWithUV(0.0, var6, ts.zLevel, 0.0f + var7, 1.0);
        }
        var2.draw();
        GL11.glColorMask(true, true, true, true);
    }

    private void renderSkybox(int par1, int par2, float par3) {
        GuiCreateWorld ts = (GuiCreateWorld) (Object) this;
        GuiScreenInterface screen = (GuiScreenInterface) (Object) this;
        List buttonList = screen.getButtonList();
        FontRenderer renderer = screen.getFontRenderer();
        Minecraft mc = screen.getMc();
        GL11.glViewport(0, 0, 256, 256);
        this.drawPanorama(par1, par2, par3);
        GL11.glDisable(3553);
        GL11.glEnable(3553);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        this.rotateAndBlurSkybox(par3);
        GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
        Tessellator var4 = Tessellator.instance;
        var4.startDrawingQuads();
        float var5 = ts.width > ts.height ? 120.0f / (float)ts.width : 120.0f / (float)ts.height;
        float var6 = (float)ts.height * var5 / 256.0f;
        float var7 = (float)ts.width * var5 / 256.0f;
        GL11.glTexParameteri(3553, 10241, 9729);
        GL11.glTexParameteri(3553, 10240, 9729);
        var4.setColorRGBA_F(1.0f, 1.0f, 1.0f, 1.0f);
        int var8 = ts.width;
        int var9 = ts.height;
        var4.addVertexWithUV(0.0, var9, ts.zLevel, 0.5f - var6, 0.5f + var7);
        var4.addVertexWithUV(var8, var9, ts.zLevel, 0.5f - var6, 0.5f - var7);
        var4.addVertexWithUV(var8, 0.0, ts.zLevel, 0.5f + var6, 0.5f - var7);
        var4.addVertexWithUV(0.0, 0.0, ts.zLevel, 0.5f + var6, 0.5f + var7);
        var4.draw();
    }
}
