package net.fabricmc.refamished.gui;

import btw.block.BTWBlocks;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.List;

import static net.minecraft.src.EnumSkyBlock.Block;

public class ModelPreviewGui extends GuiScreen {
    private final List<BlockData> structure;
    private float rotationYaw = 45.0f;
    private float rotationPitch = 30.0f;
    private int lastMouseX, lastMouseY;
    private boolean rotating;

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    public ModelPreviewGui(List<BlockData> structure) {
        this.structure = structure;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);

        GL11.glPushMatrix();
        GL11.glTranslatef(this.width / 2f, this.height / 2f + 50, 100);
        GL11.glScalef(40, -40, 40);
        GL11.glRotatef(rotationPitch, 1, 0, 0);
        GL11.glRotatef(rotationYaw, 0, 1, 0);

        RenderBlocks renderer = new RenderBlocks();

        for (BlockData block : structure) {
            GL11.glPushMatrix();
            GL11.glTranslatef(block.x, block.y, block.z);

            Block renderBlock = net.minecraft.src.Block.blocksList[block.blockID];
            if (renderBlock != null) {
                Tessellator tessellator = Tessellator.instance;
                GL11.glDisable(GL11.GL_LIGHTING);
                RenderHelper.disableStandardItemLighting();

                tessellator.startDrawingQuads();
                tessellator.setNormal(0.0F, -1.0F, 0.0F);
                tessellator.setColorOpaque_F(1.0F, 1.0F, 1.0F);
                renderer.renderBlockAsItem(renderBlock, block.metadata, 1.0F);
                tessellator.draw();

                RenderHelper.enableStandardItemLighting();
                GL11.glEnable(GL11.GL_LIGHTING);
            }

            GL11.glPopMatrix();
        }

        GL11.glPopMatrix();
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int button, long timeSinceLastClick) {
        if (rotating) {
            int dx = mouseX - lastMouseX;
            int dy = mouseY - lastMouseY;
            rotationYaw += dx * 0.5f;
            rotationPitch = clamp(rotationPitch - dy * 0.5f, -90, 90);
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        if (mouseButton == 0) {
            rotating = true;
            lastMouseX = mouseX;
            lastMouseY = mouseY;
        }
    }

    @Override
    protected void mouseMovedOrUp(int mouseX, int mouseY, int button) {
        super.mouseMovedOrUp(mouseX, mouseY, button);
        if (button == 0) {
            rotating = false;
        }
    }

    public static class BlockData {
        public final int x, y, z;
        public final int blockID;
        public final int metadata;

        public BlockData(int x, int y, int z, int blockID, int metadata) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.blockID = blockID;
            this.metadata = metadata;
        }
    }
}
