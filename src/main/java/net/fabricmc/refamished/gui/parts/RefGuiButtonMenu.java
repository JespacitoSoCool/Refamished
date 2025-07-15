package net.fabricmc.refamished.gui.parts;

import net.minecraft.src.FontRenderer;
import net.minecraft.src.GuiButton;
import net.minecraft.src.Minecraft;
import net.minecraft.src.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RefGuiButtonMenu extends GuiButton {
    protected static final ResourceLocation newButton = new ResourceLocation("refamished:textures/gui/menu_widgets.png");
    public RefGuiButtonMenu(int par1, int par2, int par3, String par4Str) {
        super(par1, par2, par3, par4Str);
    }

    public RefGuiButtonMenu(int par1, int par2, int par3, int i, int i1, String string) {
        super(par1,par2,par3,i,i1,string);
    }

    @Override
    public void drawButton(Minecraft par1Minecraft, int par2, int par3) {
        if (this.drawButton) {
            FontRenderer var4 = par1Minecraft.fontRenderer;
            par1Minecraft.getTextureManager().bindTexture(newButton);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
            int var5 = this.getHoverState(this.field_82253_i);
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + var5 * 20, this.width / 2, this.height);
            this.drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + var5 * 20, this.width / 2, this.height);
            this.mouseDragged(par1Minecraft, par2, par3);
            GL11.glDisable(GL11.GL_BLEND);
            int var6 = 0xE0E0E0;
            if (!this.enabled) {
                var6 = -6250336;
            } else if (this.field_82253_i) {
                var6 = 0xFFFFA0;
            }
            this.drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, var6);
        }
    }
}
