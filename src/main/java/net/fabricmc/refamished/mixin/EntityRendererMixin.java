package net.fabricmc.refamished.mixin;

import btw.community.refamished.RefamishedAddon;
import btw.item.items.ClubItem;
import btw.item.items.SwordItem;
import btw.world.util.difficulty.Difficulties;
import btw.item.items.ToolItem;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(EntityRenderer.class)
public abstract class EntityRendererMixin {

	private Minecraft mc = Minecraft.getMinecraft();
	private Entity pointedEntity;

	private float getItemReachModifier(ItemStack stack) {
		if (stack != null)
		{
			Multimap attributeModifiers = stack.getAttributeModifiers();

			if (attributeModifiers.containsKey("generic.toolRange")) {
				for (Object modifierObj : attributeModifiers.get("generic.toolRange")) {
					if (modifierObj instanceof AttributeModifier modifier) {
						return (float) modifier.getAmount();
					}
				}
			}
		}
		return 0;
	}

	private float getItemSttackReachModifier(ItemStack stack) {
		if (stack != null)
		{
			Multimap attributeModifiers = stack.getAttributeModifiers();

			if (attributeModifiers.containsKey("generic.attackRange")) {
				for (Object modifierObj : attributeModifiers.get("generic.attackRange")) {
					if (modifierObj instanceof AttributeModifier modifier) {
						return (float) modifier.getAmount();
					}
				}
			}
		}
		return 0;
	}

	@Inject(method = "getMouseOver", at = @At("HEAD"), cancellable = true)
	public void getMouseOver(float par1, CallbackInfo ci)
	{
		if (this.mc.renderViewEntity != null)
		{
			if (this.mc.theWorld != null)
			{
				this.mc.pointedEntityLiving = null;

				EntityPlayer player = Minecraft.getMinecraft().thePlayer;
				if (player == null || player.worldObj == null) {
					return;
				}
				World world = player.worldObj;
				ItemStack stack = player.getCurrentEquippedItem();
				float baseReach = 3f+getItemReachModifier(stack);
				float baseAttackReach = 2.5f+getItemSttackReachModifier(stack);;
				float reachAdjustment = 0.0f;

				// Get the tool's quality and calculate reach adjustment
				if (stack != null) {
					ToolQuality quality = ToolQualityHelper.getToolQuality(stack);
					float applier = ToolQualityHelper.getReachAdjustment(quality) / 2;
					reachAdjustment = applier;
					Item the = stack.getItem();
					if (this.mc.theWorld.getDifficulty() == Difficulties.CLASSIC)
					{
						baseAttackReach = (Float) Minecraft.getMinecraft().playerController.getBlockReachDistance()+getItemReachModifier(stack);
						reachAdjustment = (Float) Minecraft.getMinecraft().playerController.getBlockReachDistance()+getItemSttackReachModifier(stack);
					}
					else if (this.mc.theWorld.getDifficulty() == Difficulties.RELAXED)
					{
						baseReach =  3.5f+getItemReachModifier(stack);
						baseAttackReach = 3.5f+getItemSttackReachModifier(stack);
					}
					else if (this.mc.theWorld.getDifficulty() == Difficulties.STANDARD)
					{
						if (the instanceof ToolItem || the instanceof SwordItem || the instanceof ClubItem)
						{
							baseReach += applier;
						}
					}
					else if (this.mc.theWorld.getDifficulty() == Difficulties.HOSTILE)
					{
						reachAdjustment /= 2;
						if (the != null && the instanceof ToolItem)
						{
							baseReach = 2.5f;
						}
						else {
							baseReach = 2f;
						}
					}
					else if (this.mc.theWorld.getDifficulty() == RefamishedAddon.CRUEL)
					{
						reachAdjustment /= 1.5F;
						if (the != null && the instanceof ToolItem)
						{
							baseReach = 2.5f;
						}
						else {
							baseReach = 2.5f;
						}
					}
				}
				if (player.capabilities.isCreativeMode)
				{
					baseReach =+ 5;
					baseAttackReach =+ 5;
				}

				double d0 = baseReach + reachAdjustment;
				this.mc.objectMouseOver = this.mc.renderViewEntity.rayTrace(d0, par1);
				double d1 = d0;
				Vec3 vec3 = this.mc.renderViewEntity.getPosition(par1);
				double attackReach = baseAttackReach + reachAdjustment;

				if (d0 > attackReach)
				{
					d1 = attackReach;
				}

				d0 = d1;

				if (this.mc.objectMouseOver != null)
				{
					d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3);
				}

				Vec3 vec31 = this.mc.renderViewEntity.getLook(par1);
				Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
				this.pointedEntity = null;
				float f1 = 1.0F;
				List list = this.mc.theWorld.getEntitiesWithinAABBExcludingEntity(this.mc.renderViewEntity, this.mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
				double d2 = d1;

				for (int i = 0; i < list.size(); ++i)
				{
					Entity entity = (Entity)list.get(i);

					if (entity.canBeCollidedWith())
					{
						float f2 = entity.getCollisionBorderSize();
						AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
						MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

						if (axisalignedbb.isVecInside(vec3))
						{
							if (0.0D < d2 || d2 == 0.0D)
							{
								this.pointedEntity = entity;
								d2 = 0.0D;
							}
						}
						else if (movingobjectposition != null)
						{
							double d3 = vec3.distanceTo(movingobjectposition.hitVec);

							if (d3 < d2 || d2 == 0.0D)
							{
								if (entity == this.mc.renderViewEntity.ridingEntity)
								{
									if (d2 == 0.0D)
									{
										this.pointedEntity = entity;
									}
								}
								else
								{
									this.pointedEntity = entity;
									d2 = d3;
								}
							}
						}
					}
				}

				if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null))
				{
					this.mc.objectMouseOver = new MovingObjectPosition(this.pointedEntity);

					if (this.pointedEntity instanceof EntityLivingBase)
					{
						this.mc.pointedEntityLiving = (EntityLivingBase)this.pointedEntity;
					}
				}
			}
		}
		ci.cancel();
	}
}
