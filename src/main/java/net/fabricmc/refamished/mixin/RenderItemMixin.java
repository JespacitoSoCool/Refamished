package net.fabricmc.refamished.mixin;

import btw.client.texture.CustomUpdatingTexture;
import btw.item.items.ArmorItem;
import com.prupe.mcpatcher.cit.CITUtils;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.itemsbase.crossbow;
import net.fabricmc.refamished.mixin.mixin_m.RenderInterface;
import net.fabricmc.refamished.mixin.mixin_m.RenderItemInterface;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.src.RenderItem.renderInFrame;

@Mixin(RenderItem.class)
public class RenderItemMixin {
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
    }
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
            if (var10.getItem() instanceof IconByItemStack) {
                Icon var14 = var10.getIconIndex();
                IconByItemStack iconObtainerThing = (IconByItemStack)var10.getItem();
                var14 = iconObtainerThing.getIcon(var10,null);
                ci.cancel();
                GL11.glPushMatrix();
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
        }
    }
}
