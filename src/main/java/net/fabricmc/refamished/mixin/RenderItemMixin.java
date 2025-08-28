package net.fabricmc.refamished.mixin;

import btw.client.texture.CustomUpdatingTexture;
import btw.item.items.ArmorItem;
import com.prupe.mcpatcher.cit.CITUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.interfaces.IconLargedByItemStack;
import net.fabricmc.refamished.interfaces.IconLargedMultipleRender;
import net.fabricmc.refamished.itemsbase.crossbow;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.mixin.mixin_m.RenderInterface;
import net.fabricmc.refamished.mixin.mixin_m.RenderItemInterface;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.Hashtable;
import java.util.Random;

import static net.minecraft.src.RenderItem.renderInFrame;

@Mixin(RenderItem.class)
@Environment(value = EnvType.CLIENT)
public class RenderItemMixin {
    @Environment(value = EnvType.CLIENT)
    @Inject(method = "renderItemIntoGUI", at = @At("HEAD"),cancellable = true)
    private void render(FontRenderer par1FontRenderer, TextureManager par2TextureManager, ItemStack par3ItemStack, int par4, int par5, CallbackInfo ci) {
        int var6 = par3ItemStack.itemID;
        int var7 = par3ItemStack.getItemDamage();
        Icon var8 = par3ItemStack.getIconIndex();
        RenderItem renderer = (RenderItem)(Object)this;
        if (par3ItemStack.getItem() instanceof IconByItemStack)
        {
            IconByItemStack iconObtainerThing = (IconByItemStack)par3ItemStack.getItem();
            var8 = iconObtainerThing.getIcon(par3ItemStack,null);
            GL11.glDisable(2896);
            ResourceLocation var15 = par2TextureManager.getResourceLocation(par3ItemStack.getItemSpriteNumber());
            par2TextureManager.bindTexture(var15);
            if (var8 == null) {
                var8 = ((TextureMap)Minecraft.getMinecraft().getTextureManager().getTexture(var15)).getAtlasSprite("missingno");
            }
            int var17 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, 0);
            float var18 = (float)(var17 >> 16 & 0xFF) / 255.0f;
            float var12 = (float)(var17 >> 8 & 0xFF) / 255.0f;
            float var13 = (float)(var17 & 0xFF) / 255.0f;
            if (renderer.renderWithColor) {
                GL11.glColor4f(var18, var12, var13, 1.0f);
            }
            renderer.renderIcon(par4, par5, var8, 16, 16);
            GL11.glEnable(2896);
            GL11.glEnable(2884);
            ci.cancel();
        }
        else if (par3ItemStack.getItem() instanceof IconLargedByItemStack iconObtainerThing)
        {
            GL11.glDisable(2896);
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
            for (int var9 = 0; var9 <= iconObtainerThing.getRenderers(par3ItemStack); ++var9) {
                Icon var10 = iconObtainerThing.getIcon(par3ItemStack,null,var9);
                int var11 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, var9);
                float var12 = (float)(var11 >> 16 & 0xFF) / 255.0f;
                float var13 = (float)(var11 >> 8 & 0xFF) / 255.0f;
                float var14 = (float)(var11 & 0xFF) / 255.0f;
                if (renderer.renderWithColor) {
                    GL11.glColor4f(var12, var13, var14, 1.0f);
                }
                renderer.renderIcon(par4, par5, var10, 16, 16);
            }
            GL11.glEnable(2896);
            ci.cancel();
        }
        else if (par3ItemStack.getItem() instanceof IconLargedMultipleRender iconObtainerThing)
        {
            GL11.glDisable(2896);
            par2TextureManager.bindTexture(TextureMap.locationItemsTexture);
            for (int var9 = 0; var9 <= iconObtainerThing.renderLayers(); ++var9) {
                Icon var10 = CITUtils.getIcon(Item.itemsList[var6].getIconFromDamageForRenderPass(var7, var9), par3ItemStack, var9);
                int var11 = Item.itemsList[var6].getColorFromItemStack(par3ItemStack, var9);
                float var12 = (float)(var11 >> 16 & 0xFF) / 255.0f;
                float var13 = (float)(var11 >> 8 & 0xFF) / 255.0f;
                float var14 = (float)(var11 & 0xFF) / 255.0f;
                if (renderer.renderWithColor) {
                    GL11.glColor4f(var12, var13, var14, 1.0f);
                }
                renderer.renderIcon(par4, par5, var10, 16, 16);
            }
            GL11.glEnable(2896);
            ci.cancel();
        }
    }
    @Environment(value = EnvType.CLIENT)
    @Inject(method = "doRenderItem", at = @At("HEAD"),cancellable = true)
    private void assignToolQuality(EntityItem par1EntityItem, double par2, double par4, double par6, float par8, float par9, CallbackInfo ci) {
        RenderItem renderer = (RenderItem)(Object)this;
        RenderInterface renderInt = (RenderInterface)renderer;
        RenderItemInterface itemInt = (RenderItemInterface)renderer;
        Random rand = itemInt.getRand();
        renderInt.bindEntityTexture_(par1EntityItem);
        rand.setSeed(187L);
        ItemStack var10 = par1EntityItem.getEntityItem();
        if (var10.getItem() != null) {
            float var11 = MathHelper.sin(((float)par1EntityItem.age + par9) / 10.0f + par1EntityItem.hoverStart) * 0.1f + 0.1f;
            float var12 = (((float)par1EntityItem.age + par9) / 20.0f + par1EntityItem.hoverStart) * 57.295776f;
            int var13 = 1;
            if (par1EntityItem.getEntityItem().stackSize > 1) {
                var13 = 2;
            }
            if (par1EntityItem.getEntityItem().stackSize > 5) {
                var13 = 3;
            }
            if (par1EntityItem.getEntityItem().stackSize > 20) {
                var13 = 4;
            }
            if (par1EntityItem.getEntityItem().stackSize > 40) {
                var13 = 5;
            }
            if (var10.getItem() instanceof IconByItemStack) {
                Icon var14 = var10.getIconIndex();
                IconByItemStack iconObtainerThing = (IconByItemStack)var10.getItem();
                var14 = iconObtainerThing.getIcon(var10,null);
                ci.cancel();
                GL11.glPushMatrix();
                GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
                GL11.glEnable(32826);
                if (renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                } else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                if (renderer.renderWithColor) {
                    int var15 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, 0);
                    float var16 = (float)(var15 >> 16 & 0xFF) / 255.0f;
                    float var17 = (float)(var15 >> 8 & 0xFF) / 255.0f;
                    float var18 = (float)(var15 & 0xFF) / 255.0f;
                    float var19 = 1.0f;
                    itemInt.render(par1EntityItem, var14, var13, par9, var16 * var19, var17 * var19, var18 * var19);
                } else {
                    itemInt.render(par1EntityItem, var14, var13, par9, 1.0f, 1.0f, 1.0f);
                }
                GL11.glDisable(32826);
                GL11.glPopMatrix();
            }
            else if (var10.getItem() instanceof IconLargedMultipleRender iconObtainerThing) {
                ci.cancel();
                GL11.glPushMatrix();
                GL11.glTranslatef((float)par2, (float)par4 + var11, (float)par6);
                GL11.glEnable(32826);
                if (renderInFrame) {
                    GL11.glScalef(0.5128205f, 0.5128205f, 0.5128205f);
                    GL11.glTranslatef(0.0f, -0.05f, 0.0f);
                } else {
                    GL11.glScalef(0.5f, 0.5f, 0.5f);
                }
                for (int var21 = 0; var21 <= iconObtainerThing.renderLayers(); ++var21) {
                    Icon var23 = CITUtils.getIcon(var10.getItem().getIconFromDamageForRenderPass(var10.getItemDamage(), var21), var10, var21);
                    float var16 = 1.0f;
                    if (renderer.renderWithColor) {
                        int var26 = Item.itemsList[var10.itemID].getColorFromItemStack(var10, var21);
                        float var18 = (float)(var26 >> 16 & 0xFF) / 255.0f;
                        float var19 = (float)(var26 >> 8 & 0xFF) / 255.0f;
                        float var20 = (float)(var26 & 0xFF) / 255.0f;
                        GL11.glColor4f(var18 * var16, var19 * var16, var20 * var16, 1.0f);
                        itemInt.render(par1EntityItem, var23, var13, par9, var18 * var16, var19 * var16, var20 * var16);
                        continue;
                    }
                    itemInt.render(par1EntityItem, var23, var13, par9, 1.0f, 1.0f, 1.0f);
                }
                GL11.glDisable(32826);
                GL11.glPopMatrix();
            }
        }
    }

    @Shadow
    protected void renderQuad(Tessellator tess, int x, int y, int width, int height, int color) {}

    /**
     * @author
     * @reason
     */
    @Environment(value = EnvType.CLIENT)
    @Overwrite
    public void renderItemOverlayIntoGUI(FontRenderer fr, TextureManager texMgr, ItemStack stack, int x, int y, String text) {
        if (stack == null) return;
        Hashtable<Integer, Long> itemCooldowns = Minecraft.getMinecraft().playerController.getItemCooldowns();
        if (itemCooldowns.containsKey(stack.itemID)) {
            long itemCooldown = stack.getItem().getItemRightClickCooldown();
            long remainingItemCooldown = itemCooldowns.get(stack.itemID) - Minecraft.getMinecraft().theWorld.getTotalWorldTime();
            int cooldownPixelHeight = MathHelper.ceiling_double_int((double)(16L * remainingItemCooldown) / (double)itemCooldown);
            if (remainingItemCooldown > 0L) {
                GL11.glDisable(2896);
                GL11.glDisable(2929);
                GL11.glDisable(3553);
                Tessellator var9 = Tessellator.instance;
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 0.5f);
                this.renderQuad(var9, x, y + 16 - cooldownPixelHeight, 4, cooldownPixelHeight, 0xFFFFFF);
                GL11.glEnable(3553);
                GL11.glEnable(2896);
                GL11.glEnable(2929);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }

        boolean lowDurBar = RefamishedConfig.tweakedDurabilityBar;

        System.out.println(lowDurBar);

        if (stack.isItemDamaged()) {
            boolean isFull = stack.getItemDamageForDisplay() == stack.getMaxDamage();
            double damageRatio = isFull ? 0 : (double) stack.getItemDamageForDisplay() / (double) stack.getMaxDamage();

            int barWidthIdk = lowDurBar ? 16 : 14;
            int barWidth = (int) Math.round(barWidthIdk - damageRatio * barWidthIdk);
            barWidth = Math.max(0, Math.min(barWidthIdk, barWidth));

            GL11.glDisable(2896);
            GL11.glDisable(2929);
            GL11.glDisable(3553);

            Tessellator tess = Tessellator.instance;

            this.renderQuad(tess, x + (lowDurBar ? 0 : 1), y + (lowDurBar ? 15 : 13), lowDurBar ? 16 : 14, lowDurBar ? 1 : 2, 0x000000);

            if (barWidth > 0) {
                float hue = (float) ((1.0 - damageRatio) * 0.33);
                int baseRGB = java.awt.Color.HSBtoRGB(hue, 1.0f, 1.0f);
                int baseR = (baseRGB >> 16) & 0xFF;
                int baseG = (baseRGB >> 8) & 0xFF;
                int baseB = baseRGB & 0xFF;

                final float maxShadeDrop = 0.5f;
                float denom = (barWidth > 1) ? (float) (barWidth - 1) : 1f;

                for (int i = 0; i < barWidth; i++) {
                    float t = (barWidth > 1) ? (float) i / denom : 0f;
                    float shade = 1.0f - t * maxShadeDrop;

                    int r = clamp(Math.round(baseR * shade), 0, 255);
                    int g = clamp(Math.round(baseG * shade), 0, 255);
                    int b = clamp(Math.round(baseB * shade), 0, 255);

                    int color = (r << 16) | (g << 8) | b;
                    this.renderQuad(tess, x + (lowDurBar ? 0 : 1) + i, y + (lowDurBar ? 15 : 13), 1, 1, color);
                }
            }

            GL11.glEnable(3553);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }

        if (stack.stackSize > 1 || text != null) {
            String s = text == null ? String.valueOf(stack.stackSize) : text;
            GL11.glDisable(2896);
            GL11.glDisable(2929);
            fr.drawStringWithShadow(s, x + 19 - 2 - fr.getStringWidth(s), y + 6 + 3, 0xFFFFFF);
            GL11.glEnable(2896);
            GL11.glEnable(2929);
        }
    }

    private static int clamp(int v, int a, int b) {
        return v < a ? a : (v > b ? b : v);
    }

}
