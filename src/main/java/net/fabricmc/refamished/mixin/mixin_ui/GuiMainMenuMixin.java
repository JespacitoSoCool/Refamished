package net.fabricmc.refamished.mixin.mixin_ui;
import btw.AddonHandler;
import btw.BTWAddon;
import btw.BTWMod;
import com.google.common.base.Charsets;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.gui.parts.RefGuiButtonMenu;
import net.fabricmc.refamished.gui.parts.RefGuiLanguage;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.mixin.interfaces.GuiInterface;
import net.fabricmc.refamished.mixin.interfaces.GuiScreenInterface;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.*;
import net.minecraft.src.GuiMainMenu;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Mixin(GuiMainMenu.class)
public abstract class GuiMainMenuMixin {
    @Shadow private String splashText;
    @Shadow private float updateCounter;
    @Shadow private int panoramaTimer;
    @Shadow private static ResourceLocation minecraftTitleTextures;
    @Shadow private String field_92025_p;
    @Shadow private int field_92022_t;
    @Shadow private int field_92021_u;
    @Shadow private int field_92020_v;
    @Shadow private int field_92019_w;
    @Shadow private int field_92024_r;
    @Shadow private static String field_96138_a;
    @Shadow private GuiButton buttonResetDemo;
    @Shadow private static Random rand;
    @Shadow private static ResourceLocation splashTexts;

    @Shadow protected abstract void renderSkybox(int par1, int par2, float par3);

    @Unique private String fullSplashText = "Welcome to Refamished for 3.0!!!!1 :D";
    @Unique private String currentSplashDisplay = "";
    @Unique private int splashFrameCounter = 0;
    @Unique private boolean isDeleting = false;
    @Unique private int splashChangeCooldown = 0;


    static {
        //splashTexts = new ResourceLocation("texts/splashes.txt");
        minecraftTitleTextures = new ResourceLocation("refamished:textures/gui/REFAM_ISHED.png");
        //field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    }

    @Inject(method = "drawScreen", at = @At("HEAD"), cancellable = true)
    public void thiss(int par1, int par2, float par3, CallbackInfo ci) {
        GuiMainMenu this_ = (GuiMainMenu)(Object)this;
        GuiInterface inter = (GuiInterface)(Object)this;
        GuiScreenInterface interScreen = (GuiScreenInterface)(Object)this;
        Minecraft getMc = interScreen.getMc();
        FontRenderer font = interScreen.getFontRenderer();
        Tessellator var4 = Tessellator.instance;
        this.renderSkybox(par1,par2,par3);
        int var5 = 274;
        int var6 = this_.width / 2 - 10;
        int var7 = 45;
        this_.drawGradientRect(0, 0, this_.width, this_.height, -2130706433, 0xFFFFFF);
        this_.drawGradientRect(0, 0, this_.width, this_.height, 0, Integer.MIN_VALUE);
        getMc.getTextureManager().bindTexture(minecraftTitleTextures);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        if ((double)this.updateCounter < 1.0E-4) {
            this_.drawTexturedModalRect(var6, var7, 0, 0, 99, 44);
            this_.drawTexturedModalRect(var6 + 99, var7, 129, 0, 27, 44);
            this_.drawTexturedModalRect(var6 + 99 + 26, var7, 126, 0, 3, 44);
            this_.drawTexturedModalRect(var6 + 99 + 26 + 3, var7, 99, 0, 26, 44);
            this_.drawTexturedModalRect(var6 + 155, var7, 0, 45, 155, 44);
        } else {
            this_.drawTexturedModalRect(var6 - 155, var7, 0, 0, 155, 44);
            this_.drawTexturedModalRect(var6, var7, 0, 45, 155, 44);
        }

        var4.setColorOpaque_I(0xFFFFFF);
        //GL11.glPushMatrix();
        //GL11.glTranslatef(this_.width / 2 + 90, 70.0f, 0.0f);
        //GL11.glRotatef(-20.0f, 0.0f, 0.0f, 1.0f);
        //float var8 = 1.8f - MathHelper.abs(MathHelper.sin((float)(Minecraft.getSystemTime() % 1000L) / 1000.0f * (float)Math.PI * 2.0f) * 0.1f);
       // var8 = var8 * 100.0f / (float)(interScreen.getFontRenderer().toString().length() + 32);
        //GL11.glScalef(var8, var8, var8);
        //this_.drawCenteredString(font, this.splashText, 0, -8, 0xFFFF00);
        //GL11.glPopMatrix();

        if (par1 <= this_.width * 0.1) {
            List<String> lines = new ArrayList<>();
            lines.add("Minecraft 1.6.4");
            lines.add("BTW CE V" + BTWMod.instance.getVersionString());
            lines.add("Refamished V" + getVersion("refamished"));

            List<String> addonLines = new ArrayList<>();
            for (BTWAddon addon : AddonHandler.modList.values()) {
                String modId = addon.getModID();
                if (modId.equals("refamished") || modId.equals("btw") || modId.equals("emi")) continue;

                String name = addon.getName();
                String version = addon.getVersionString();
                addonLines.add(name + " V" + version);
            }

            if (!addonLines.isEmpty()) {
                lines.add("");
                lines.add("Addons Installed :");
                lines.addAll(addonLines);
            }

            int padding = 4;
            int lineSpacing = 2;

            int maxLineWidth = 0;
            for (String line : lines) {
                int width = font.getStringWidth(line);
                if (width > maxLineWidth) maxLineWidth = width;
            }

            int boxWidth = maxLineWidth + padding * 2;
            int boxHeight = lines.size() * (font.FONT_HEIGHT + lineSpacing) - lineSpacing + padding * 2;

            Gui.drawRect(0, 0, boxWidth, boxHeight, 0x88000000);

            for (int i = 0; i < lines.size(); i++) {
                int y = padding + i * (font.FONT_HEIGHT + lineSpacing);
                this_.drawString(font, lines.get(i), padding, y, 0xFFFFFF);
            }

        }
        else {
            Gui.drawRect(0, 0, (int) (this_.width * 0.007),  this_.height, 0x88000000);
        }
        String var10 = "Copyright Mojang AB. Do not distribute!";
        this_.drawString(font, var10, this_.width - font.getStringWidth(var10) - 2, 2, 0xFFFFFF);

        if (this.field_92025_p != null && !this.field_92025_p.isEmpty()) {
            GuiMainMenu.drawRect(this.field_92022_t - 2, this.field_92021_u - 2, this.field_92020_v + 2, this.field_92019_w - 1, 0x55200000);
            this_.drawString(font, this.field_92025_p, this.field_92022_t, this.field_92021_u, 0xFFFFFF);
            this_.drawString(font, field_96138_a, (this_.width - this.field_92024_r) / 2, ((GuiButton)interScreen.getButtonList().get(0)).yPosition - 12, 0xFFFFFF);
        }

        for (Object obj : interScreen.getButtonList()) {
            if (obj instanceof GuiButton) {
                ((GuiButton)obj).drawButton(getMc, par1, par2);
            }
        }

        if (splashChangeCooldown > 0) {
            splashChangeCooldown--;
        } else {
            splashFrameCounter++;

            if (isDeleting) {
                if (splashFrameCounter % 1 == 0 && !currentSplashDisplay.isEmpty()) {
                    currentSplashDisplay = currentSplashDisplay.substring(0, currentSplashDisplay.length() - 1);
                }
                if (currentSplashDisplay.isEmpty()) {
                    fullSplashText = getRandomSplash();
                    isDeleting = false;
                    splashFrameCounter = 0;
                }
            } else {
                if (splashFrameCounter % 2 == 0 && currentSplashDisplay.length() < fullSplashText.length()) {
                    currentSplashDisplay = fullSplashText.substring(0, currentSplashDisplay.length() + 1);
                }
                if (currentSplashDisplay.equals(fullSplashText)) {
                    splashChangeCooldown = 40;
                    splashFrameCounter = 0;
                    isDeleting = true;
                }
            }
        }

        GL11.glPushMatrix();

        float time = (Minecraft.getSystemTime() % 2000L) / 2000.0f;
        //float bob = MathHelper.sin(time * (float)Math.PI / 2.0f) * 2.0f;
        float tilt = MathHelper.sin(time * (float)Math.PI * 2.0f) * 2.0f;

        int splashX = (this_.width - font.getStringWidth(currentSplashDisplay)) / 2;
        //int splashY = 85 + (int)bob;
        int splashY = 110;
        GL11.glTranslatef(splashX + font.getStringWidth(currentSplashDisplay) / 2.0f, splashY, 0.0f);
        GL11.glRotatef(tilt, 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-font.getStringWidth(currentSplashDisplay) / 2.0f, 0.0f, 0.0f);
        this_.drawString(font, currentSplashDisplay, 0, 0, 0xFFFF00);

        //GL11.glPopMatrix();

        //this_.drawString(font, "AHHHHHHH", this_.width / 2 + 100, this_.height - 16, 0xA0A0A0);

        GL11.glPopMatrix();

        ci.cancel();
    }

    @Inject(method = "addSingleplayerMultiplayerButtons", at = @At("HEAD"), cancellable = true)
    public void thiss(int par1, int par2, CallbackInfo ci) {
        GuiMainMenu this_ = (GuiMainMenu)(Object)this;
        GuiInterface inter = (GuiInterface)(Object)this;
        GuiScreenInterface interScreen = (GuiScreenInterface)(Object)this;
        Minecraft getMc = interScreen.getMc();
        FontRenderer font = interScreen.getFontRenderer();
        Tessellator var4 = Tessellator.instance;
        List bl = interScreen.getButtonList();
        int offset = par2;
        bl.add(new RefGuiButtonMenu(1, this_.width / 2 - 100, (par1)+offset, I18n.getString("menu.singleplayer")));
        bl.add(new RefGuiButtonMenu(2, this_.width / 2 - 100, (par1 + par2 * 1)+offset, I18n.getString("menu.multiplayer")));
        if (!Objects.equals(RefamishedConfig.serverIp, "")) {
            bl.add(new RefGuiButtonMenu(69, this_.width / 2 - 100, this_.height - par2, I18n.getString("Franzient")));
        }
        //this_.minecraftRealmsButton = new GuiButton(14, this_.width / 2 - 100, par1 + par2 * 2, I18n.getString("menu.online"));
        //this_.buttonList.add(this_.minecraftRealmsButton);
        //this_.minecraftRealmsButton.drawButton = false;
        ci.cancel();
    }

    @Inject(method = "initGui", at = @At("TAIL"))
    private void onInitGui(CallbackInfo ci) {
        GuiMainMenu this_ = (GuiMainMenu)(Object)this;
        GuiInterface inter = (GuiInterface)(Object)this;
        GuiScreenInterface interScreen = (GuiScreenInterface)(Object)this;
        Minecraft getMc = interScreen.getMc();
        FontRenderer font = interScreen.getFontRenderer();
        Tessellator var4 = Tessellator.instance;
        List bl = interScreen.getButtonList();
        int var3 = this_.height / 4 + 120;

        int thisthing = 24;

        // Replace options and quit buttons with RefGuiButtonMenu
        replaceButton(0, new RefGuiButtonMenu(0, this_.width / 2 - 100, var3, I18n.getString("menu.options")));
        replaceButton(4, new RefGuiButtonMenu(4, this_.width / 2 - 100, var3 + thisthing, I18n.getString("menu.quit")));
        replaceButton(5, new RefGuiLanguage(5, this_.width - 20, this_.height -20));

        // Move the language button to bottom right
        for (int i = 0; i < bl.size(); i++) {
            GuiButton button = (GuiButton) bl.get(i);
            if (button instanceof GuiButtonLanguage) {
                int langButtonWidth = 20;
                int langButtonHeight = 20;
                int padding = 5;

                button.xPosition = this_.width - langButtonWidth - padding;
                button.yPosition = this_.height - langButtonHeight - padding;
                break;
            }
        }
    }

    @Unique
    private ServerData theServerData = new ServerData("QuickServer", "RefamishedConfig.serverIp");

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void actionPerforshit(GuiButton par1GuiButton,CallbackInfo ci) {
        GuiMainMenu this_ = (GuiMainMenu)(Object)this;
        GuiInterface inter = (GuiInterface)(Object)this;
        GuiScreenInterface interScreen = (GuiScreenInterface)(Object)this;
        Minecraft getMc = interScreen.getMc();
        if (par1GuiButton.id == 69) {
            this_.confirmClicked(true, 0);
            getMc.setServerData(theServerData); // <---- Required!
            //getMc.displayGuiScreen(new GuiOptions(this_, getMc.gameSettings, true));
            getMc.displayGuiScreen(new GuiConnecting(this_, getMc, theServerData));
            ci.cancel();
        }
    }

    private void replaceButton(int id, GuiButton newButton) {
        GuiScreenInterface interScreen = (GuiScreenInterface)(Object)this;
        List bl = interScreen.getButtonList();
        for (int i = 0; i < bl.size(); i++) {
            GuiButton button = (GuiButton) bl.get(i);
            if (button.id == id) {
                bl.set(i, newButton);
                return;
            }
        }
    }

    @Unique
    private String getRandomSplash() {
        try {
            ArrayList<String> splashes = new ArrayList<>();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(),
                    Charsets.UTF_8));
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) splashes.add(line);
            }
            reader.close();
            String splash;
            do {
                splash = splashes.get(rand.nextInt(splashes.size()));
            } while (splash.hashCode() == 125780783);
            return splash;
        } catch (IOException e) {
            return "missingno";
        }
    }

    private static String getVersion(String modId) {
        return FabricLoader.getInstance()
                .getModContainer(modId)
                .map(ModContainer::getMetadata)
                .map(metadata -> metadata.getVersion().getFriendlyString())
                .orElse("unknown");
    }

}
