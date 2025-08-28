package net.fabricmc.refamished.gui;

import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class GuiRefamishedOptions extends GuiScreen {
    private static Object[][] relevantOptions = new Object[][]{
            {"performance", 0,
                    new Object[][]{
                            {(Supplier<Boolean>) () -> RefamishedConfig.vignette,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.vignette = v,0, "Boolean", "gui.refamished.togglevignette"},
                            {(Supplier<Boolean>) () ->RefamishedConfig.tweakedDurabilityBar,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.tweakedDurabilityBar = v,1, "Boolean", "gui.refamished.tweakedbar"},
                            {(Supplier<Boolean>) () ->RefamishedConfig.embersEnabled,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.embersEnabled = v,2, "Boolean", "gui.refamished.embers"},
                            {(Supplier<Boolean>) () ->RefamishedConfig.smokeEnabled,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.smokeEnabled = v,3, "Boolean", "gui.refamished.smoke"},
                    }
            },
            {"visuals", 1,
                    new Object[][]{
                            {(Supplier<Boolean>) () ->RefamishedConfig.oldQualityFlags,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.oldQualityFlags = v,4, "Boolean", "gui.refamished.oldflag"},
                    }
            },
            {"compatibility", 2,
                    new Object[][]{
                            {(Supplier<Boolean>) () ->RefamishedConfig.refamishedTextures,
                                    (Consumer<Boolean>) (v) -> RefamishedConfig.refamishedTextures = v,5, "Boolean", "gui.refamished.textures"},
                    }
            }
    };


    private final GuiScreen parentScreen;
    protected String screenTitle = "Options";

    private int activeCategoryIndex = 0;

    public GuiRefamishedOptions(GuiScreen parent) {
        this.parentScreen = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();

        // Category tabs (top row)
        int x = this.width / 2 - (relevantOptions.length * 100) / 2;
        for (int i = 0; i < relevantOptions.length; i++) {
            String name = (String) relevantOptions[i][0];
            this.buttonList.add(new GuiButton(1000 + i, x + i * 102, 30, 100, 20, I18n.getString("gui.refamished." + name)));
        }

        // Done button
        this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height - 28, I18n.getString("gui.done")));

        // Load option buttons for current category
        loadCategoryButtons();
    }

    private void loadCategoryButtons() {
        for (Iterator<GuiButton> it = this.buttonList.iterator(); it.hasNext();) {
            GuiButton btn = it.next();
            if (btn.id >= 300 && btn.id < 1000) {
                it.remove();
            }
        }

        Object[][] options = (Object[][]) relevantOptions[activeCategoryIndex][2];

        int cols = 2;
        int spacingX = 180;
        int spacingY = 24;

        int startX = this.width / 2 - (cols * spacingX) / 2;
        int startY = 70;

        for (int i = 0; i < options.length; i++) {
            int row = i / cols;
            int col = i % cols;

            Object[] opt = options[i];
            Supplier<Boolean> fieldRef = (Supplier<Boolean>) opt[0];
            String type = (String) opt[3];
            String key = (String) opt[4];

            int x = startX + col * spacingX;
            int y = startY + row * spacingY;

            if ("Boolean".equals(type)) {
                this.buttonList.add(new OptionButton(300 + i, x, y, opt));
            }
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.id == 200) {
            this.mc.gameSettings.saveOptions();
            this.mc.displayGuiScreen(this.parentScreen);
        } else if (button.id >= 1000) {
            // Switch category
            this.activeCategoryIndex = button.id - 1000;
            loadCategoryButtons();
        } else if (button.id >= 300 && button.id < 1000) {
            if (button instanceof OptionButton) {
                OptionButton optBtn = (OptionButton) button;
                Object[] option = optBtn.optionData;

                if ("Boolean".equals(option[3])) {
                    Supplier<Boolean> getter = (Supplier<Boolean>) option[0];
                    Consumer<Boolean> setter = (Consumer<Boolean>) option[1];

                    boolean current = getter.get();
                    setter.accept(!current);
                }

                optBtn.updateLabel();
            }
        }
    }

    private static final ResourceLocation icon = new ResourceLocation("refamished", "textures/gui/options_icons.png");

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, 15, 0xFFFFFF);


        for (GuiButton btn : (List<GuiButton>)this.buttonList) {
            if (btn.id >= 300 && btn.id < 1000) {
                int index = (btn.id - 300) % 1000;
                Object[] option = (Object[]) ((Object[][]) relevantOptions[activeCategoryIndex][2])[index];
                int iconIndex = (Integer) option[2];
                int u = (iconIndex % 12) * 20;
                int v = (iconIndex / 12) * 20;
                this.mc.getTextureManager().bindTexture(icon);
                drawTexturedModalRect(btn.xPosition + btn.width, btn.yPosition, u, v, 20, 20);
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    static class OptionButton extends GuiButton {
        public Object[] optionData; // { field, iconIndex, type, langKey }

        public OptionButton(int id, int x, int y, Object[] option) {
            super(id, x, y, 150, 20, "");
            this.optionData = option;
            updateLabel();
        }

        public void updateLabel() {
            String key = (String) optionData[4];
            String name = I18n.getString(key);

            if ("Boolean".equals(optionData[3])) {
                Supplier<Boolean> fieldRef = (Supplier<Boolean>) optionData[0];
                this.displayString = name + ": " + (fieldRef.get() ? "true" : "false");
            }
        }
    }

}
