package net.fabricmc.refamished.entities.render;

import net.fabricmc.refamished.entities.tiles.tanningHorse;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class TanningHorseRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float partialTicks) {
        tanningHorse tile = (tanningHorse) tileEntity;

        int cookLevel = tile.getCookLevel(); // value between 0–8
        if (cookLevel <= 0 || cookLevel > 8) return;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        // Enable alpha blending
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);

        this.bindTexture(new ResourceLocation("refamished:textures/blocks/variant/renderpass_tanning_" + (cookLevel - 1) + ".png"));

        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA(255, 255, 255, 255);

        // Draw top face (adjust as needed based on rotation)
        tessellator.addVertexWithUV(0, 1, 1, 0, 1);
        tessellator.addVertexWithUV(1, 1, 1, 1, 1);
        tessellator.addVertexWithUV(1, 1, 0, 1, 0);
        tessellator.addVertexWithUV(0, 1, 0, 0, 0);

        tessellator.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }
}
