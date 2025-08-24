package net.fabricmc.refamished.entities.render;

import net.fabricmc.refamished.blocks.copperConduct;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class CopperConductRenderer extends TileEntitySpecialRenderer {
    private static final float SIZE = 10f / 16f;
    private static final float HALF = SIZE / 2f;

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("refamished", "textures/blocks/copper_block.png");

    @Override
    public void renderTileEntityAt(TileEntity te, double x, double y, double z, float partialTicks) {
        Tessellator t = Tessellator.instance;
        GL11.glPushMatrix();
        GL11.glTranslated(x+0.5f, y+0.5f, z+0.5f);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        Minecraft.getMinecraft().renderEngine.bindTexture(TEXTURE);
        t.startDrawingQuads();
        renderFace(t, HALF, HALF, HALF, -HALF, -HALF, -HALF);

        // Check connections
        for (int side = 0; side < 6; side++) {
            if (copperConduct.connectsTo(te.worldObj, te.xCoord, te.yCoord, te.zCoord, side)) {
                drawExtension(t, side);
            }
        }
        t.draw();

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

    private void drawExtension(Tessellator t, int side) {
        float min = HALF;
        float max = -HALF;

        switch (side) {
            case 0: // Down (-Y)
                renderFace(t, min, -HALF, min, max, -0.5f, max);
                break;
            case 1: // Up (+Y)
                renderFace(t, min, 0.5f, min, max, HALF, max);
                break;
            case 2: // North (-Z)
                renderFace(t, min, min, -HALF, max, max, -0.5f);
                break;
            case 3: // South (+Z)
                renderFace(t, min, min, 0.5f, max, max, HALF);
                break;
            case 4: // West (-X)
                renderFace(t, -HALF, min, min, -0.5f, max, max);
                break;
            case 5: // East (+X)
                renderFace(t, 0.5f, min, min, HALF, max, max);
                break;
        }
    }
}
