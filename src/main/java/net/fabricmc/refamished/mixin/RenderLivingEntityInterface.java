package net.fabricmc.refamished.mixin;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RendererLivingEntity.class)
public interface RenderLivingEntityInterface {
	@Accessor("mainModel")
	ModelBase model();
	@Invoker("renderSwingProgress")
	float renderts(EntityLivingBase par1EntityLivingBase, float par2);
	@Invoker("interpolateRotation")
	float interpolate(float par1, float par2, float par3);
	@Invoker("renderLivingAt")
	void liver(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6);
	@Invoker("handleRotationFloat")
	float handleRot(EntityLivingBase par1EntityLivingBase, float par2);
	@Invoker("renderEquippedItems")
	void renderEquip(EntityLivingBase par1EntityLivingBase, float par2);
	@Invoker("rotateCorpse")
	void rotateCorp(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4);
	@Invoker("preRenderCallback")
	void preRend(EntityLivingBase par1EntityLivingBase, float par2);
	@Invoker("getColorMultiplier")
	int colormul(EntityLivingBase par1EntityLivingBase, float par2, float par3);
	@Invoker("renderModel")
	void renderMode(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4, float par5, float par6, float par7);
	@Invoker("shouldRenderPass")
	int shouldRendehPass(EntityLivingBase par1EntityLivingBase, int par2, float par3);
	@Invoker("func_82408_c")
	void idkwhatthisdoesbutseemsweird(EntityLivingBase par1EntityLivingBase, int par2, float par3);
	@Invoker("inheritRenderPass")
	int inhRen(EntityLivingBase par1EntityLivingBase, int par2, float par3);
	@Invoker("passSpecialRender")
	void passSpecial(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6);
}