package net.fabricmc.refamished.mixin;

import btw.item.items.ArmorItemMod;
import com.prupe.mcpatcher.cit.CITUtils;
import net.fabricmc.refamished.itemsbase.craftedArmor;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(RenderPlayer.class)
public class RenderPlayerMixin {
	@Shadow
	private ModelBiped modelArmor;
	@Shadow
	private ModelBiped modelArmorChestplate;

	private int renderShit(ItemStack stack, int iArmorSlot, craftedArmor armorItem) {
		RenderPlayer ts = (RenderPlayer)(Object)this;
		RenderInterface ren = (RenderInterface)(Object)this;
		RenderLivingEntityInterface liv = (RenderLivingEntityInterface)(Object)this;

		ren.bindTex(RenderBiped.getCustomArmorResources(armorItem.getWornTextureDirectory() + craftedArmor.getWornTexturePrefix(stack,1) + "_layer_" + (iArmorSlot == 2 ? 2 : 1) + ".png"));
		ModelBiped model = iArmorSlot == 2 ? this.modelArmor : this.modelArmorChestplate;
		model.bipedHead.showModel = iArmorSlot == 0;
		model.bipedHeadwear.showModel = iArmorSlot == 0;
		model.bipedBody.showModel = iArmorSlot == 1 || iArmorSlot == 2;
		model.bipedRightArm.showModel = iArmorSlot == 1;
		model.bipedLeftArm.showModel = iArmorSlot == 1;
		model.bipedRightLeg.showModel = iArmorSlot == 2 || iArmorSlot == 3;
		model.bipedLeftLeg.showModel = iArmorSlot == 2 || iArmorSlot == 3;
		ts.setRenderPassModel(model);
		if (model != null) {
			model.onGround = liv.model().onGround;
			model.isRiding = liv.model().isRiding;
			model.isChild = liv.model().isChild;
		}
		if (armorItem.hasCustomColors()) {
			int iColor = armorItem.getColor(stack);
			float fRed = (float)(iColor >> 16 & 0xFF) / 255.0f;
			float fGreen = (float)(iColor >> 8 & 0xFF) / 255.0f;
			float fBlue = (float)(iColor & 0xFF) / 255.0f;
			GL11.glColor3f(fRed, fGreen, fBlue);
		} else {
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
		}
		if (stack.isItemEnchanted()) {
			return 15;
		}
		return 69;
	}

	@Inject(method = "shouldRenderPassModArmor", at = @At("HEAD"), cancellable = true)
	private void overrideDiamondArmorTexture(ItemStack stack, int iArmorSlot, ArmorItemMod armorItem, CallbackInfoReturnable<Integer> cir) {
		if (armorItem instanceof craftedArmor craftedArmorPiece) {
			RenderInterface ren = (RenderInterface)(Object)this;
			GL11.glColor3f(1.0f, 1.0f, 1.0f);
			cir.setReturnValue(renderShit(stack,iArmorSlot,craftedArmorPiece));
		}
	}
//	@Inject(method = "loadSecondLayerOfModArmorTexture", at = @At("HEAD"), cancellable = true)
//	private void secon(int iArmorSlot, ArmorItemMod armorItem, CallbackInfo ci) {
//		if (armorItem instanceof craftedArmor craftedArmorPiece) {
//			RenderInterface ren = (RenderInterface)(Object)this;
//			ren.bindTex(RenderBiped.getCustomArmorResources(armorItem.getWornTextureDirectory() + craftedArmor.getWornTexturePrefix(stack,1) + "_layer_" + (iArmorSlot == 2 ? 2 : 1) + ".png"));
//			GL11.glColor3f(1.0f, 1.0f, 1.0f);
//		}
//	}
}
