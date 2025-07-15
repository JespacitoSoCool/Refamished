package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import btw.item.items.ArmorItemDiamond;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.mixin.interfaces.RenderBipedInterface;
import net.fabricmc.refamished.mixin.interfaces.RenderEntityLivingInter;
import net.fabricmc.refamished.mixin.interfaces.RenderInter;
import net.fabricmc.refamished.mixin.mixin_m.RenderInterface;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderBiped.class)
public class RenderBipedMixin {

	@Shadow protected ModelBiped modelBipedMain;
	@Inject(
			method = "func_110858_a",
			at = @At("HEAD"),
			cancellable = true
	)
	private static void overrideDiamondArmorTexture(ItemArmor armor, int layer, String suffix, CallbackInfoReturnable<ResourceLocation> cir) {
		if (armor.renderIndex == 3 && RefamishedConfig.refamishedTextures) { // diamond
			String texture = String.format("refamished:textures/models/armor/diamond_layer_%d%s.png",
					layer == 2 ? 2 : 1,
					suffix == null ? "" : "_" + suffix);
			cir.setReturnValue(new ResourceLocation(texture));
		}
	}

//	@Inject(method = "func_130005_c", at = @At("HEAD"), cancellable = true)
//	private void renderBowShit(EntityLiving par1EntityLiving, float par2, CallbackInfo ci) {
//		ci.cancel();
//		RenderBiped ts = (RenderBiped) (Object)this;
//		RenderBipedInterface ts2 = (RenderBipedInterface) ts;
//		RenderInter renderInterface = (RenderInter) ts;
//		RenderManager renderManager = renderInterface.rendManager();
//		RenderEntityLivingInter rendererLivingEntity = (RenderEntityLivingInter) ts;
//		float var6;
//		float var3 = 1.0f;
//		GL11.glColor3f(var3, var3, var3);
//		//ts2.renderItemShit(par1EntityLiving, par2);
//		ItemStack var4 = par1EntityLiving.getHeldItem();
//		ItemStack var5 = par1EntityLiving.func_130225_q(3);
//		if (var5 != null) {
//			GL11.glPushMatrix();
//			this.modelBipedMain.bipedHead.postRender(0.0625f);
//			if (var5.getItem().itemID < 4096 && Block.blocksList[var5.itemID] != null) {
//				if (Block.blocksList[var5.itemID].doesItemRenderAsBlock(var5.getItemDamage())) {
//					var6 = 0.625f;
//					GL11.glTranslatef(0.0f, -0.25f, 0.0f);
//					GL11.glRotatef(90.0f, 0.0f, 1.0f, 0.0f);
//					GL11.glScalef(var6, -var6, -var6);
//				}
//				renderManager.itemRenderer.renderItem(par1EntityLiving, var5, 0);
//			} else if (var5.getItem().itemID == Item.skull.itemID) {
//				var6 = 1.0625f;
//				GL11.glScalef(var6, -var6, -var6);
//				String var7 = "";
//				if (var5.hasTagCompound() && var5.getTagCompound().hasKey("SkullOwner")) {
//					var7 = var5.getTagCompound().getString("SkullOwner");
//				}
//				TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5f, 0.0f, -0.5f, 1, 180.0f, var5.getItemDamage(), var7);
//			}
//			GL11.glPopMatrix();
//		}
//		if (var4 != null) {
//			GL11.glPushMatrix();
//			if (rendererLivingEntity.getMainModel().isChild) {
//				var6 = 0.5f;
//				GL11.glTranslatef(0.0f, 0.625f, 0.0f);
//				GL11.glRotatef(-20.0f, -1.0f, 0.0f, 0.0f);
//				GL11.glScalef(var6, var6, var6);
//			}
//			this.modelBipedMain.bipedRightArm.postRender(0.0625f);
//			GL11.glTranslatef(-0.0625f, 0.4375f, 0.0625f);
//			if (var4.itemID < 4096 && Block.blocksList[var4.itemID] != null && Block.blocksList[var4.itemID].doesItemRenderAsBlock(var4.getItemDamage())) {
//				var6 = 0.5f;
//				GL11.glTranslatef(0.0f, 0.1875f, -0.3125f);
//				GL11.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);
//				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
//				GL11.glScalef(-(var6 *= 0.75f), -var6, var6);
//			} else if (var4.itemID == Item.bow.itemID || var4.itemID == RefamishedItems.incendiary_bow.itemID) {
//				var6 = 0.625f;
//				GL11.glTranslatef(0.0f, 0.125f, 0.3125f);
//				GL11.glRotatef(-20.0f, 0.0f, 1.0f, 0.0f);
//				GL11.glScalef(var6, -var6, var6);
//				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
//				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
//			} else if (Item.itemsList[var4.itemID].isFull3D()) {
//				var6 = 0.625f;
//				if (Item.itemsList[var4.itemID].shouldRotateAroundWhenRendering()) {
//					GL11.glRotatef(180.0f, 0.0f, 0.0f, 1.0f);
//					GL11.glTranslatef(0.0f, -0.125f, 0.0f);
//				}
//				ts2.funcIdk();
//				GL11.glScalef(var6, -var6, var6);
//				GL11.glRotatef(-100.0f, 1.0f, 0.0f, 0.0f);
//				GL11.glRotatef(45.0f, 0.0f, 1.0f, 0.0f);
//			} else {
//				var6 = 0.375f;
//				GL11.glTranslatef(0.25f, 0.1875f, -0.1875f);
//				GL11.glScalef(var6, var6, var6);
//				GL11.glRotatef(60.0f, 0.0f, 0.0f, 1.0f);
//				GL11.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
//				GL11.glRotatef(20.0f, 0.0f, 0.0f, 1.0f);
//			}
//			renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 0);
//			if (var4.getItem().requiresMultipleRenderPasses()) {
//				renderManager.itemRenderer.renderItem(par1EntityLiving, var4, 1);
//			}
//			GL11.glPopMatrix();
//		}
//	}
}
