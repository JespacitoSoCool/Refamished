package net.fabricmc.refamished.entities.render;

import btw.block.tileentity.OvenTileEntity;
import btw.world.util.BlockPos;
import net.fabricmc.refamished.entities.tiles.cokeovenTile;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;

public class CokeOvenRenderer extends TileEntitySpecialRenderer {
    @Override
    public void renderTileEntityAt(TileEntity tileEntity, double xCoord, double yCoord, double zCoord, float fPartialTickCount) {
        cokeovenTile furnace = (cokeovenTile)tileEntity;
        ItemStack cookStack = furnace.getCookStack();
        if (cookStack != null) {
            this.renderCookStack(furnace, xCoord, yCoord, zCoord, cookStack, fPartialTickCount);
        }
    }

    private void renderCookStack(cokeovenTile furnace, double xCoord, double yCoord, double zCoord, ItemStack stack, float fPartialTickCount) {
        int iMetadata = furnace.worldObj.getBlockMetadata(furnace.xCoord, furnace.yCoord, furnace.zCoord);
        EntityItem entityItem = new EntityItem(furnace.worldObj, 0.0, 0.0, 0.0, stack);
        entityItem.getEntityItem().stackSize = 1;
        entityItem.hoverStart = 0.0f;
        GL11.glPushMatrix();
        GL11.glTranslatef((float)xCoord + 0.5f, (float)yCoord + 0.3f, (float)zCoord + 0.5f);
        GL11.glScalef(1.1f, 1.1f, 1.1f);
        int iFacing = iMetadata & 7;
        Vec3 vOffset = Vec3.createVectorHelper(0.0, 0.0, 0.0);
        float fYaw = 0.0f;
        if (iFacing == 2) {
            fYaw = 0.0f;
            vOffset.zCoord = -0.25;
        } else if (iFacing == 3) {
            fYaw = 180.0f;
            vOffset.zCoord = 0.25;
        } else if (iFacing == 4) {
            fYaw = 90.0f;
            vOffset.xCoord = -0.25;
        } else if (iFacing == 5) {
            fYaw = 270.0f;
            vOffset.xCoord = 0.25;
        }
        GL11.glTranslatef((float)vOffset.xCoord, (float)vOffset.yCoord, (float)vOffset.zCoord);
        if (RenderManager.instance.options.fancyGraphics) {
            GL11.glRotatef(fYaw, 0.0f, 1.0f, 0.0f);
        }
        int iBrightness = this.getItemRenderBrightnessForBlockToFacing(furnace, iFacing);
        int var11 = iBrightness % 65536;
        int var12 = iBrightness / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var11 / 1.0f, (float)var12 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderManager.instance.renderEntityWithPosYaw(entityItem, 0.0, 0.0, 0.0, 0.0f, 0.0f);
        GL11.glPopMatrix();
    }

    protected int getItemRenderBrightnessForBlockToFacing(cokeovenTile furnace, int iFacing) {
        BlockPos targetPos = new BlockPos(furnace.xCoord, furnace.yCoord, furnace.zCoord, iFacing);
        if (furnace.worldObj.blockExists(targetPos.x, targetPos.y, targetPos.z)) {
            return furnace.worldObj.getLightBrightnessForSkyBlocks(targetPos.x, targetPos.y, targetPos.z, 0);
        }
        return 0;
    }
}
