package net.fabricmc.refamished.mixin;

import btw.client.texture.CustomUpdatingTexture;
import com.prupe.mcpatcher.cit.CITUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.interfaces.IconLargedMultipleRender;
import net.fabricmc.refamished.mixin.mixin_m.RenderInterface;
import net.fabricmc.refamished.mixin.mixin_m.RenderItemInterface;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.src.RenderItem.renderInFrame;

@Mixin(ItemRenderer.class)
@Environment(value = EnvType.CLIENT)
public class ItemRendererMixin {

    @Environment(value = EnvType.CLIENT)
    @Shadow
    private float equippedProgress;
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private float prevEquippedProgress;
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private Minecraft mc;
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private ItemStack itemToRender;
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private static final ResourceLocation RES_MAP_BACKGROUND = new ResourceLocation("textures/map/map_background.png");
    @Environment(value = EnvType.CLIENT)
    @Shadow
    private static final ResourceLocation RES_UNDERWATER_OVERLAY = new ResourceLocation("textures/misc/underwater.png");

    @Environment(value = EnvType.CLIENT)
    @Inject(method = "renderItemInFirstPerson", at = @At("HEAD"),cancellable = true)
    private void assignToolQuality(float par1, CallbackInfo ci) {
        ItemRenderer renderer = (ItemRenderer)(Object)this;
        //RenderInterface renderInt = (RenderInterface)renderer;
        //RenderItemInterface itemInt = (RenderItemInterface)renderer;
        //Random rand = itemInt.getRand();
        float var13;
        float var23;
        float var21;
        float var2 = this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * par1;
        EntityClientPlayerMP var3 = this.mc.thePlayer;
        float var4 = var3.prevRotationPitch + (var3.rotationPitch - var3.prevRotationPitch) * par1;
        GL11.glPushMatrix();
        GL11.glRotatef(var4, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef(var3.prevRotationYaw + (var3.rotationYaw - var3.prevRotationYaw) * par1, 0.0f, 1.0f, 0.0f);
        RenderHelper.enableStandardItemLighting();
        GL11.glPopMatrix();
        EntityClientPlayerMP var5 = var3;
        float var6 = var5.prevRenderArmPitch + (var5.renderArmPitch - var5.prevRenderArmPitch) * par1;
        float var7 = var5.prevRenderArmYaw + (var5.renderArmYaw - var5.prevRenderArmYaw) * par1;
        GL11.glRotatef((var3.rotationPitch - var6) * 0.1f, 1.0f, 0.0f, 0.0f);
        GL11.glRotatef((var3.rotationYaw - var7) * 0.1f, 0.0f, 1.0f, 0.0f);
        ItemStack var8 = this.itemToRender;
        float var9 = this.mc.theWorld.getLightBrightness(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ));
        var9 = 1.0f;
        int var10 = this.mc.theWorld.getLightBrightnessForSkyBlocks(MathHelper.floor_double(var3.posX), MathHelper.floor_double(var3.posY), MathHelper.floor_double(var3.posZ), 0);
        int var11 = var10 % 65536;
        int var12 = var10 / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)var11 / 1.0f, (float)var12 / 1.0f);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        if (var8 != null) {
            var10 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 0);
            var21 = (float)(var10 >> 16 & 0xFF) / 255.0f;
            var23 = (float)(var10 >> 8 & 0xFF) / 255.0f;
            var13 = (float)(var10 & 0xFF) / 255.0f;
            GL11.glColor4f(var9 * var21, var9 * var23, var9 * var13, 1.0f);
        } else {
            GL11.glColor4f(var9, var9, var9, 1.0f);
        }
        if (var8 != null && var8.itemID == Item.map.itemID) {
            float var16;
            GL11.glPushMatrix();
            float var20 = 0.8f;
            var21 = var3.getSwingProgress(par1);
            var23 = MathHelper.sin(var21 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI);
            GL11.glTranslatef(-var13 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI * 2.0f) * 0.2f, -var23 * 0.2f);
            var21 = 1.0f - var4 / 45.0f + 0.1f;
            if (var21 < 0.0f) {
                var21 = 0.0f;
            }
            if (var21 > 1.0f) {
                var21 = 1.0f;
            }
            var21 = -MathHelper.cos(var21 * (float)Math.PI) * 0.5f + 0.5f;
            GL11.glTranslatef(0.0f, 0.0f * var20 - (1.0f - var2) * 1.2f - var21 * 0.5f + 0.04f, -0.9f * var20);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(var21 * -85.0f, 0.0f, 0.0f, 1.0f);
            GL11.glEnable(32826);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            for (var12 = 0; var12 < 2; ++var12) {
                int var24 = var12 * 2 - 1;
                GL11.glPushMatrix();
                GL11.glTranslatef(-0.0f, -0.6f, 1.1f * (float)var24);
                GL11.glRotatef(-45 * var24, 1.0f, 0.0f, 0.0f);
                GL11.glRotatef(-90.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(59.0f, 0.0f, 0.0f, 1.0f);
                GL11.glRotatef(-65 * var24, 0.0f, 1.0f, 0.0f);
                Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
                RenderPlayer var28 = (RenderPlayer)var26;
                var16 = 1.0f;
                GL11.glScalef(var16, var16, var16);
                var28.renderFirstPersonArm(this.mc.thePlayer);
                GL11.glPopMatrix();
            }
            var23 = var3.getSwingProgress(par1);
            var13 = MathHelper.sin(var23 * var23 * (float)Math.PI);
            float var14 = MathHelper.sin(MathHelper.sqrt_float(var23) * (float)Math.PI);
            GL11.glRotatef(-var13 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var14 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var14 * 80.0f, 1.0f, 0.0f, 0.0f);
            float var15 = 0.38f;
            GL11.glScalef(var15, var15, var15);
            GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
            GL11.glTranslatef(-1.0f, -1.0f, 0.0f);
            var16 = 0.015625f;
            GL11.glScalef(var16, var16, var16);
            this.mc.getTextureManager().bindTexture(RES_MAP_BACKGROUND);
            Tessellator var29 = Tessellator.instance;
            GL11.glNormal3f(0.0f, 0.0f, -1.0f);
            var29.startDrawingQuads();
            int var30 = 7;
            var29.addVertexWithUV(0 - var30, 128 + var30, 0.0, 0.0, 1.0);
            var29.addVertexWithUV(128 + var30, 128 + var30, 0.0, 1.0, 1.0);
            var29.addVertexWithUV(128 + var30, 0 - var30, 0.0, 1.0, 0.0);
            var29.addVertexWithUV(0 - var30, 0 - var30, 0.0, 0.0, 0.0);
            var29.draw();
            MapData var19 = Item.map.getMapData(var8, this.mc.theWorld);
            if (var19 != null) {
                renderer.mapItemRenderer.renderMap(this.mc.thePlayer, this.mc.getTextureManager(), var19);
            }
            GL11.glPopMatrix();
        } else if (var8 != null) {
            float var16;
            float var14;
            GL11.glPushMatrix();
            float var20 = 0.8f;
            if (var3.getItemInUseCount() > 0) {
                EnumAction var22 = var8.getItemUseAction();
                if (var22 == EnumAction.eat || var22 == EnumAction.drink) {
                    var23 = (float)var3.getItemInUseCount() - par1 + 1.0f;
                    var13 = 1.0f - var23 / (float)var8.getMaxItemUseDuration();
                    var14 = 1.0f - var13;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    float var15 = 1.0f - var14;
                    GL11.glTranslatef(0.0f, MathHelper.abs(MathHelper.cos(var23 / 4.0f * (float)Math.PI) * 0.1f) * (float)((double)var13 > 0.2 ? 1 : 0), 0.0f);
                    GL11.glTranslatef(var15 * 0.6f, -var15 * 0.5f, 0.0f);
                    GL11.glRotatef(var15 * 90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var15 * 10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(var15 * 30.0f, 0.0f, 0.0f, 1.0f);
                } else if (var22 == EnumAction.miscUse) {
                    var23 = (float)var3.getItemInUseCount() - par1 + 1.0f;
                    var14 = var23 / (float)var8.getMaxItemUseDuration();
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    float var15 = 1.0f - var14;
                    GL11.glTranslatef(0.0f, MathHelper.abs(MathHelper.cos(var23 / 4.0f * (float)Math.PI) * 0.1f) * (var8.getMaxItemUseDuration() - var3.getItemInUseCount() >= var8.getItem().getItemUseWarmupDuration() ? 1.0f : 0.0f), 0.0f);
                    int iItemInUseCount = MathHelper.clamp_int(32 - (var8.getMaxItemUseDuration() - var3.getItemInUseCount()), 0, 32);
                    var23 = (float)iItemInUseCount - par1 + 1.0f;
                    var14 = var23 / 32.0f;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var14 = var14 * var14 * var14;
                    var15 = 1.0f - var14;
                    GL11.glTranslatef(var15 * 0.6f, -var15 * 0.5f, 0.0f);
                    GL11.glRotatef(var15 * 90.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(var15 * 10.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(var15 * 30.0f, 0.0f, 0.0f, 1.0f);
                }
            } else {
                var21 = var3.getSwingProgress(par1);
                var23 = MathHelper.sin(var21 * (float)Math.PI);
                var13 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI);
                GL11.glTranslatef(-var13 * 0.4f, MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI * 2.0f) * 0.2f, -var23 * 0.2f);
            }
            GL11.glTranslatef(0.7f * var20, -0.65f * var20 - (1.0f - var2) * 0.6f, -0.9f * var20);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            var21 = var3.getSwingProgress(par1);
            var23 = MathHelper.sin(var21 * var21 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI);
            GL11.glRotatef(-var23 * 20.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var13 * 20.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(-var13 * 80.0f, 1.0f, 0.0f, 0.0f);
            var14 = 0.4f;
            GL11.glScalef(var14, var14, var14);
            if (var3.getItemInUseCount() > 0) {
                EnumAction var25 = var8.getItemUseAction();
                if (var25 == EnumAction.block) {
                    GL11.glTranslatef(-0.5f, 0.2f, 0.0f);
                    GL11.glRotatef(30.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-80.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glRotatef(60.0f, 0.0f, 1.0f, 0.0f);
                } else if (var25 == EnumAction.bow) {
                    GL11.glRotatef(-18.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-12.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(-8.0f, 1.0f, 0.0f, 0.0f);
                    GL11.glTranslatef(-0.9f, 0.2f, 0.0f);
                    var16 = (float)var8.getMaxItemUseDuration() - ((float)var3.getItemInUseCount() - par1 + 1.0f);
                    float var17 = var16 / 20.0f;
                    var17 = (var17 * var17 + var17 * 2.0f) / 3.0f;
                    if (var17 > 1.0f) {
                        var17 = 1.0f;
                    }
                    if (var17 > 0.1f) {
                        GL11.glTranslatef(0.0f, MathHelper.sin((var16 - 0.1f) * 1.3f) * 0.01f * (var17 - 0.1f), 0.0f);
                    }
                    GL11.glTranslatef(0.0f, 0.0f, var17 * 0.1f);
                    GL11.glRotatef(-335.0f, 0.0f, 0.0f, 1.0f);
                    GL11.glRotatef(-50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glTranslatef(0.0f, 0.5f, 0.0f);
                    float var18 = 1.0f + var17 * 0.2f;
                    GL11.glScalef(1.0f, 1.0f, var18);
                    GL11.glTranslatef(0.0f, -0.5f, 0.0f);
                    GL11.glRotatef(50.0f, 0.0f, 1.0f, 0.0f);
                    GL11.glRotatef(335.0f, 0.0f, 0.0f, 1.0f);
                }
            }
            if (var8.getItem().shouldRotateAroundWhenRendering()) {
                GL11.glRotatef(180.0f, 0.0f, 1.0f, 0.0f);
            }
            int iItemID = var8.getItem().itemID;
            Icon icon = var8.getItem().getIconFromDamage(0);
            if (icon instanceof CustomUpdatingTexture) {
                CustomUpdatingTexture customUpdateTexture = (CustomUpdatingTexture)((Object)icon);
                customUpdateTexture.updateActive(CustomUpdatingTexture.FIRST_PERSON);
            }
            if (var8.getItem().requiresMultipleRenderPasses()) {
                if (var8.getItem() instanceof IconLargedMultipleRender multipleRender) {
                    for (int varLayer = 0; varLayer <= multipleRender.renderLayers(); ++varLayer) {
                        int var27 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, varLayer);
                        var16 = (float)(var27 >> 16 & 0xFF) / 255.0f;
                        float var17 = (float)(var27 >> 8 & 0xFF) / 255.0f;
                        float var18 = (float)(var27 & 0xFF) / 255.0f;
                        GL11.glColor4f(var9 * var16, var9 * var17, var9 * var18, 1.0f);
                        renderer.renderItem(var3, var8, varLayer);
                    }
                } else {
                    renderer.renderItem(var3, var8, 0);
                    int var27 = Item.itemsList[var8.itemID].getColorFromItemStack(var8, 1);
                    var16 = (float)(var27 >> 16 & 0xFF) / 255.0f;
                    float var17 = (float)(var27 >> 8 & 0xFF) / 255.0f;
                    float var18 = (float)(var27 & 0xFF) / 255.0f;
                    GL11.glColor4f(var9 * var16, var9 * var17, var9 * var18, 1.0f);
                    renderer.renderItem(var3, var8, 1);
                }
            } else {
                renderer.renderItem(var3, var8, 0);
            }
            icon = var8.getItem().getIconFromDamage(0);
            if (icon instanceof CustomUpdatingTexture) {
                CustomUpdatingTexture customUpdateTexture = (CustomUpdatingTexture)((Object)icon);
                customUpdateTexture.updateInert(CustomUpdatingTexture.FIRST_PERSON);
            }
            GL11.glPopMatrix();
        } else if (!var3.isInvisible()) {
            GL11.glPushMatrix();
            float var20 = 0.8f;
            var21 = var3.getSwingProgress(par1);
            var23 = MathHelper.sin(var21 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI);
            GL11.glTranslatef(-var13 * 0.3f, MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI * 2.0f) * 0.4f, -var23 * 0.4f);
            GL11.glTranslatef(0.8f * var20, -0.75f * var20 - (1.0f - var2) * 0.6f, -0.9f * var20);
            GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
            GL11.glEnable(32826);
            var21 = var3.getSwingProgress(par1);
            var23 = MathHelper.sin(var21 * var21 * (float)Math.PI);
            var13 = MathHelper.sin(MathHelper.sqrt_float(var21) * (float)Math.PI);
            GL11.glRotatef(var13 * 70.0f, 0.0f, 1.0f, 0.0f);
            GL11.glRotatef(-var23 * 20.0f, 0.0f, 0.0f, 1.0f);
            this.mc.getTextureManager().bindTexture(var3.getLocationSkin());
            GL11.glTranslatef(-1.0f, 3.6f, 3.5f);
            GL11.glRotatef(120.0f, 0.0f, 0.0f, 1.0f);
            GL11.glRotatef(200.0f, 1.0f, 0.0f, 0.0f);
            GL11.glRotatef(-135.0f, 0.0f, 1.0f, 0.0f);
            GL11.glScalef(1.0f, 1.0f, 1.0f);
            GL11.glTranslatef(5.6f, 0.0f, 0.0f);
            Render var26 = RenderManager.instance.getEntityRenderObject(this.mc.thePlayer);
            RenderPlayer var28 = (RenderPlayer)var26;
            float var16 = 1.0f;
            GL11.glScalef(var16, var16, var16);
            var28.renderFirstPersonArm(this.mc.thePlayer);
            GL11.glPopMatrix();
        }
        GL11.glDisable(32826);
        RenderHelper.disableStandardItemLighting();
        ci.cancel();
    }
}
