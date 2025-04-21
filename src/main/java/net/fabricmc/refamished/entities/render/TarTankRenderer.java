package net.fabricmc.refamished.entities.render;

import btw.world.util.BlockPos;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.tarTank;
import net.fabricmc.refamished.entities.tiles.cokeovenTile;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class TarTankRenderer extends TileEntitySpecialRenderer {
    private static final ResourceLocation TAR_TEXTURE = new ResourceLocation("refamished:textures/blocks/tar.png");

    @Override
    public void renderTileEntityAt(TileEntity tile, double x, double y, double z, float partialTicks) {
        tarTankTile tank = (tarTankTile) tile;
        float fillRatio = tank.getTarAmount() / (float) tarTankTile.MAX_TAR;
        //System.out.println(fillRatio);
        if (fillRatio <= 0.01F) return; // Nothing to render

        float min = 0.01F;
        float max = 0.99F;
        float height = min + (max - min) * fillRatio;

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(TAR_TEXTURE);

        Tessellator tess = Tessellator.instance;
        tess.startDrawingQuads();

        // Render all 6 faces of the cube
        renderFace(tess, min, min, min, max, height, max);

        tess.draw();

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
    }

    private void renderFace(Tessellator tess, float x1, float y1, float z1, float x2, float y2, float z2) {
        float u1 = 0F, v1 = 0F, u2 = 1F, v2 = 1F;

        // Bottom
        tess.addVertexWithUV(x1, y1, z2, u1, v2);
        tess.addVertexWithUV(x2, y1, z2, u2, v2);
        tess.addVertexWithUV(x2, y1, z1, u2, v1);
        tess.addVertexWithUV(x1, y1, z1, u1, v1);

        // Top
        tess.addVertexWithUV(x1, y2, z1, u1, v1);
        tess.addVertexWithUV(x2, y2, z1, u2, v1);
        tess.addVertexWithUV(x2, y2, z2, u2, v2);
        tess.addVertexWithUV(x1, y2, z2, u1, v2);

        // Front
        tess.addVertexWithUV(x1, y1, z1, u1, v2);
        tess.addVertexWithUV(x2, y1, z1, u2, v2);
        tess.addVertexWithUV(x2, y2, z1, u2, v1);
        tess.addVertexWithUV(x1, y2, z1, u1, v1);

        // Back
        tess.addVertexWithUV(x1, y2, z2, u1, v1);
        tess.addVertexWithUV(x2, y2, z2, u2, v1);
        tess.addVertexWithUV(x2, y1, z2, u2, v2);
        tess.addVertexWithUV(x1, y1, z2, u1, v2);

        // Left
        tess.addVertexWithUV(x1, y1, z2, u1, v2);
        tess.addVertexWithUV(x1, y1, z1, u2, v2);
        tess.addVertexWithUV(x1, y2, z1, u2, v1);
        tess.addVertexWithUV(x1, y2, z2, u1, v1);

        // Right
        tess.addVertexWithUV(x2, y1, z1, u1, v2);
        tess.addVertexWithUV(x2, y1, z2, u2, v2);
        tess.addVertexWithUV(x2, y2, z2, u2, v1);
        tess.addVertexWithUV(x2, y2, z1, u1, v1);
    }
}
