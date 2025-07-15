package net.fabricmc.refamished.mixin;

import btw.world.util.WorldUtils;
import btw.world.util.difficulty.Difficulties;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.mixin.interfaces.EntityLivingBaseInterface;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.skill.*;
import net.fabricmc.refamished.stats.HardcoreBarefoot;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityLivingBase.class)
public abstract class PlayerSPMixin {
	@Inject(method = "getSpeedModifier", at = @At("TAIL"), cancellable = true)
	private void barefoot(CallbackInfoReturnable<Float> cir) {
		if ((Object)this instanceof EntityPlayerSP)
		{
			EntityPlayerSP ent = (EntityPlayerSP)(Object) this;
			EntityPlayer player = (EntityPlayer)ent;
			float speedModifier = getMainSpeedModifier();
			if (!player.worldObj.isRemote) {cir.setReturnValue(speedModifier); return;}
			InventoryPlayer inventory = player.inventory;

			float speedMultiplier = 0.85F;
			if (player.worldObj.getDifficulty() == RefamishedMod.CRUEL || player.worldObj.getDifficulty() == Difficulties.HOSTILE)
			{
				speedMultiplier = 0.7F;
			}

			for ( int iSlot = 0; iSlot < inventory.armorInventory.length; iSlot++ )
			{
				ItemStack tempStack = inventory.armorInventory[iSlot];

				if ( tempStack != null )
				{
					float iArmorWeight = ArmorQualityHelper.getSpeedMultiplier(ArmorQualityHelper.getArmorQuality(tempStack));
					speedModifier += (iArmorWeight - 1);
					//System.out.println("Armor Weight: " + iArmorWeight);
				}
			}

			ItemStack boots = inventory.armorInventory[0];
			if (boots != null) {
				int bootsId = boots.getItem().itemID;

				// Default boots multiplier
				speedMultiplier = HardcoreBarefoot.CUSTOM_SPEEDS.getOrDefault(bootsId, 1.0F);
			}
			cir.setReturnValue((speedModifier * speedMultiplier));
		}
	}

	public float getMainSpeedModifier() {
		EntityPlayerSP ent = (EntityPlayerSP)(Object) this;
		EntityPlayer player = (EntityPlayer)ent;
		EntityLivingBaseInterface inte = (EntityLivingBaseInterface)ent;
		float fMoveSpeed = 1.0f;
		if (player.onGround && player.isAffectedByMovementModifiers()) {
			Block blockOn;
			int iGroundK;
			int iGroundJ;
			int iGroundI = MathHelper.floor_double(player.posX);
			if (WorldUtils.isGroundCoverOnBlock(player.worldObj, iGroundI, iGroundJ = MathHelper.floor_double(player.posY - 0.03 - (double)player.yOffset), iGroundK = MathHelper.floor_double(player.posZ))) {
				fMoveSpeed *= 0.8f;
			}
			if ((blockOn = Block.blocksList[player.worldObj.getBlockId(iGroundI, iGroundJ, iGroundK)]) == null || blockOn.getCollisionBoundingBoxFromPool(player.worldObj, iGroundI, iGroundJ, iGroundK) == null) {
				float fHalfWidth = player.width / 2.0f;
				int iCenterGroundI = iGroundI;
				iGroundI = MathHelper.floor_double(player.posX + (double)fHalfWidth);
				blockOn = Block.blocksList[player.worldObj.getBlockId(iGroundI, iGroundJ, iGroundK)];
				if (!(blockOn != null && blockOn.getCollisionBoundingBoxFromPool(player.worldObj, iGroundI, iGroundJ, iGroundK) != null || (blockOn = Block.blocksList[player.worldObj.getBlockId(iGroundI = MathHelper.floor_double(player.posX - (double)fHalfWidth), iGroundJ, iGroundK)]) != null && blockOn.getCollisionBoundingBoxFromPool(player.worldObj, iGroundI, iGroundJ, iGroundK) != null || (blockOn = Block.blocksList[player.worldObj.getBlockId(iGroundI = iCenterGroundI, iGroundJ, iGroundK = MathHelper.floor_double(player.posZ + (double)fHalfWidth))]) != null && blockOn.getCollisionBoundingBoxFromPool(player.worldObj, iGroundI, iGroundJ, iGroundK) != null)) {
					iGroundK = MathHelper.floor_double(player.posZ - (double)fHalfWidth);
					blockOn = Block.blocksList[player.worldObj.getBlockId(iGroundI, iGroundJ, iGroundK)];
				}
			}
			if (blockOn != null) {
				fMoveSpeed *= blockOn.getMovementModifier(player.worldObj, iGroundI, iGroundJ, iGroundK);
			}
			fMoveSpeed *= inte.getLandThing();
		}
		if (fMoveSpeed < 0.0f) {
			fMoveSpeed = 0.0f;
		}
		return fMoveSpeed;
	}
}