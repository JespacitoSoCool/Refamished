package net.fabricmc.refamished.mixin;

import btw.item.items.ArmorItemMod;
import com.prupe.mcpatcher.cit.CITUtils;
import net.fabricmc.refamished.itemsbase.craftedArmor;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class RenderLivingEntityMixin {
    @Shadow
    private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");
    @Shadow
    protected ModelBase mainModel;
    @Shadow
    protected ModelBase renderPassModel;

    @Inject(method = "doRenderLiving", at = @At("HEAD"), cancellable = true)
    public void whatever(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9, CallbackInfo ci) {
        GL11.glPushMatrix();
        GL11.glDisable(2884);
        RendererLivingEntity ts = (RendererLivingEntity)(Object)this;
        RenderLivingEntityInterface re = (RenderLivingEntityInterface)(Object)this;
        RenderInterface re2 = (RenderInterface)(Object)this;
        this.mainModel.onGround = re.renderts(par1EntityLivingBase, par9);
        if (this.renderPassModel != null) {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }
        this.mainModel.isRiding = par1EntityLivingBase.isRiding();
        if (this.renderPassModel != null) {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }
        this.mainModel.isChild = par1EntityLivingBase.isChild();
        if (this.renderPassModel != null) {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }
        try {
            float var22;
            float var20;
            float var19;
            int var18;
            float var13;
            float var10 = re.interpolate(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par9);
            float var11 = re.interpolate(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par9);
            if (par1EntityLivingBase.isRiding() && par1EntityLivingBase.ridingEntity instanceof EntityLivingBase) {
                EntityLivingBase var12 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
                var10 = re.interpolate(var12.prevRenderYawOffset, var12.renderYawOffset, par9);
                var13 = MathHelper.wrapAngleTo180_float(var11 - var10);
                if (var13 < -85.0f) {
                    var13 = -85.0f;
                }
                if (var13 >= 85.0f) {
                    var13 = 85.0f;
                }
                var10 = var11 - var13;
                if (var13 * var13 > 2500.0f) {
                    var10 += var13 * 0.2f;
                }
            }
            float var26 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par9;
            if (par1EntityLivingBase.hasHeadCrabbedSquid()) {
                var26 = 0.0f;
            }
            re.liver(par1EntityLivingBase, par2, par4, par6);
            var13 = re.handleRot(par1EntityLivingBase, par9);
            re.rotateCorp(par1EntityLivingBase, var13, var10, par9);
            float var14 = 0.0625f;
            GL11.glEnable(32826);
            GL11.glScalef(-1.0f, -1.0f, 1.0f);
            re.preRend(par1EntityLivingBase, par9);
            GL11.glTranslatef(0.0f, -24.0f * var14 - 0.0078125f, 0.0f);
            float var15 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount) * par9;
            float var16 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0f - par9);
            if (par1EntityLivingBase.isChild()) {
                var16 *= 3.0f;
            }
            if (var15 > 1.0f) {
                var15 = 1.0f;
            }
            GL11.glEnable(3008);
            this.mainModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
            re.renderMode(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
            for (int var17 = 0; var17 < 4; ++var17) {
                var18 = re.shouldRendehPass(par1EntityLivingBase, var17, par9);
                if (var18 <= 0) continue;
                this.renderPassModel.setLivingAnimations(par1EntityLivingBase, var16, var15, par9);
                this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                if ((var18 & 0xF0) == 16) {
                    re.idkwhatthisdoesbutseemsweird(par1EntityLivingBase, var17, par9);
                    this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                }
                if (var18 == 69 && par1EntityLivingBase instanceof AbstractClientPlayer par1AbstractClientPlayer) {
                    Item var5;
                    ItemStack var4 = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - var17);
                    if (var4 != null && (var5 = var4.getItem()) instanceof craftedArmor) {
                        re2.bindTex(RenderBiped.getCustomArmorResources("refamished:textures/models/armor/crafted/" + craftedArmor.getWornTexturePrefix(var4,0) + "_layer_" + (var17 == 2 ? 2 : 1) + ".png"));
                        GL11.glColor3f(1.0f, 1.0f, 1.0f);
                    }
                    this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                }
                if (CITUtils.setupArmorEnchantments(par1EntityLivingBase, var17)) {
                    while (CITUtils.preRenderArmorEnchantment()) {
                        this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                        CITUtils.postRenderArmorEnchantment();
                    }
                } else if ((var18 & 0xF) == 15) {
                    var19 = (float)par1EntityLivingBase.ticksExisted + par9;
                    re2.bindTex(RES_ITEM_GLINT);
                    GL11.glEnable(3042);
                    var20 = 0.5f;
                    GL11.glColor4f(var20, var20, var20, 1.0f);
                    GL11.glDepthFunc(514);
                    GL11.glDepthMask(false);
                    for (int var21 = 0; var21 < 2; ++var21) {
                        GL11.glDisable(2896);
                        var22 = 0.76f;
                        GL11.glColor4f(0.5f * var22, 0.25f * var22, 0.8f * var22, 1.0f);
                        GL11.glBlendFunc(768, 1);
                        GL11.glMatrixMode(5890);
                        GL11.glLoadIdentity();
                        float var23 = var19 * (0.001f + (float)var21 * 0.003f) * 20.0f;
                        float var24 = 0.33333334f;
                        GL11.glScalef(var24, var24, var24);
                        GL11.glRotatef(30.0f - (float)var21 * 60.0f, 0.0f, 0.0f, 1.0f);
                        GL11.glTranslatef(0.0f, var23, 0.0f);
                        GL11.glMatrixMode(5888);
                        this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    }
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                    GL11.glMatrixMode(5890);
                    GL11.glDepthMask(true);
                    GL11.glLoadIdentity();
                    GL11.glMatrixMode(5888);
                    GL11.glEnable(2896);
                    GL11.glDisable(3042);
                    GL11.glDepthFunc(515);
                }
                GL11.glDisable(3042);
                GL11.glEnable(3008);
            }
            GL11.glDepthMask(true);
            re.renderEquip(par1EntityLivingBase, par9);
            float var27 = par1EntityLivingBase.getBrightness(par9);
            var18 = re.colormul(par1EntityLivingBase, var27, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(3553);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
            if ((var18 >> 24 & 0xFF) > 0 || par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0) {
                GL11.glDisable(3553);
                GL11.glDisable(3008);
                GL11.glEnable(3042);
                GL11.glBlendFunc(770, 771);
                GL11.glDepthFunc(514);
                if (par1EntityLivingBase.hurtTime > 0 || par1EntityLivingBase.deathTime > 0) {
                    GL11.glColor4f(var27, 0.0f, 0.0f, 0.4f);
                    this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    for (int var28 = 0; var28 < 4; ++var28) {
                        if (re.inhRen(par1EntityLivingBase, var28, par9) < 0) continue;
                        GL11.glColor4f(var27, 0.0f, 0.0f, 0.4f);
                        this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    }
                }
                if ((var18 >> 24 & 0xFF) > 0) {
                    var19 = (float)(var18 >> 16 & 0xFF) / 255.0f;
                    var20 = (float)(var18 >> 8 & 0xFF) / 255.0f;
                    float var29 = (float)(var18 & 0xFF) / 255.0f;
                    var22 = (float)(var18 >> 24 & 0xFF) / 255.0f;
                    GL11.glColor4f(var19, var20, var29, var22);
                    this.mainModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    for (int var30 = 0; var30 < 4; ++var30) {
                        if (re.inhRen(par1EntityLivingBase, var30, par9) < 0) continue;
                        GL11.glColor4f(var19, var20, var29, var22);
                        this.renderPassModel.render(par1EntityLivingBase, var16, var15, var13, var11 - var10, var26, var14);
                    }
                }
                GL11.glDepthFunc(515);
                GL11.glDisable(3042);
                GL11.glEnable(3008);
                GL11.glEnable(3553);
            }
            GL11.glDisable(32826);
        } catch (Exception var25) {
            var25.printStackTrace();
        }
        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(3553);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
        re.passSpecial(par1EntityLivingBase, par2, par4, par6);
        ci.cancel();
    }
}