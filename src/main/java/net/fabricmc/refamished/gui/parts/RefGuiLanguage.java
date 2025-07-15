package net.fabricmc.refamished.gui.parts;

import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class RefGuiLanguage extends GuiButtonLanguage {
    protected static final ResourceLocation newButton = new ResourceLocation("refamished:textures/gui/menu_widgets.png");

    public RefGuiLanguage(int par1, int par2, int par3) {
        super(par1,par2,par3);
    }

    @Override
    public void drawButton(Minecraft minecraft, int i, int j) {
        if (!this.drawButton) {
            return;
        }
        minecraft.getTextureManager().bindTexture(newButton);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        boolean bl = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
        int n = 106;
        if (bl) {
            n += this.height;
        }
        this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, n, this.width, this.height);
        GL11.glDisable(GL11.GL_BLEND);
    }
}
