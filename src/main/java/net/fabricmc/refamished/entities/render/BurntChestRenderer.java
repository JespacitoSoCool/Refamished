package net.fabricmc.refamished.entities.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.entities.EntityGoldArrow;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

import java.util.Calendar;

@Environment(value= EnvType.CLIENT)
public class BurntChestRenderer extends TileEntityChestRenderer {

    private static final ResourceLocation RES_NORMAL_DOUBLE = new ResourceLocation("refamished:textures/entity/burnt_chest_double.png");
    private static final ResourceLocation RES_NORMAL_SINGLE = new ResourceLocation("refamished:textures/entity/burnt_chest.png");
    private ModelChest chestModel = new ModelChest();
    private ModelChest largeChestModel = new ModelLargeChest();

    public BurntChestRenderer() {
        Calendar calendar = Calendar.getInstance();
    }

    public void renderTileEntityChestAt(TileEntityChest tileEntityChest, double d, double e, double f, float g) {
        float f2;
        Object object;
        int n;
        if (!tileEntityChest.hasWorldObj()) {
            n = 0;
        } else {
            object = tileEntityChest.getBlockType();
            n = tileEntityChest.getBlockMetadata();
            if (object instanceof BlockChest && n == 0) {
                ((BlockChest)object).unifyAdjacentChests(tileEntityChest.getWorldObj(), tileEntityChest.xCoord, tileEntityChest.yCoord, tileEntityChest.zCoord);
                n = tileEntityChest.getBlockMetadata();
            }
            tileEntityChest.checkForAdjacentChests();
        }
        if (tileEntityChest.adjacentChestZNeg != null || tileEntityChest.adjacentChestXNeg != null) {
            return;
        }
        if (tileEntityChest.adjacentChestXPos != null || tileEntityChest.adjacentChestZPosition != null) {
            object = this.largeChestModel;
            this.bindTexture(RES_NORMAL_DOUBLE);
        } else {
            object = this.chestModel;
            this.bindTexture(RES_NORMAL_SINGLE);
        }
        GL11.glPushMatrix();
        GL11.glEnable(32826);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float)d, (float)e + 1.0f, (float)f + 1.0f);
        GL11.glScalef(1.0f, -1.0f, -1.0f);
        GL11.glTranslatef(0.5f, 0.5f, 0.5f);
        int n2 = 0;
        if (n == 2) {
            n2 = 180;
        }
        if (n == 3) {
            n2 = 0;
        }
        if (n == 4) {
            n2 = 90;
        }
        if (n == 5) {
            n2 = -90;
        }
        if (n == 2 && tileEntityChest.adjacentChestXPos != null) {
            GL11.glTranslatef(1.0f, 0.0f, 0.0f);
        }
        if (n == 5 && tileEntityChest.adjacentChestZPosition != null) {
            GL11.glTranslatef(0.0f, 0.0f, -1.0f);
        }
        GL11.glRotatef(n2, 0.0f, 1.0f, 0.0f);
        GL11.glTranslatef(-0.5f, -0.5f, -0.5f);
        float f3 = tileEntityChest.prevLidAngle + (tileEntityChest.lidAngle - tileEntityChest.prevLidAngle) * g;
        if (tileEntityChest.adjacentChestZNeg != null && (f2 = tileEntityChest.adjacentChestZNeg.prevLidAngle + (tileEntityChest.adjacentChestZNeg.lidAngle - tileEntityChest.adjacentChestZNeg.prevLidAngle) * g) > f3) {
            f3 = f2;
        }
        if (tileEntityChest.adjacentChestXNeg != null && (f2 = tileEntityChest.adjacentChestXNeg.prevLidAngle + (tileEntityChest.adjacentChestXNeg.lidAngle - tileEntityChest.adjacentChestXNeg.prevLidAngle) * g) > f3) {
            f3 = f2;
        }
        f3 = 1.0f - f3;
        f3 = 1.0f - f3 * f3 * f3;
        ((ModelChest)object).chestLid.rotateAngleX = -(f3 * (float)Math.PI / 2.0f);
        ((ModelChest)object).renderAll();
        GL11.glDisable(32826);
        GL11.glPopMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
