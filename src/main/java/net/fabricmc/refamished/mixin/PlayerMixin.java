package net.fabricmc.refamished.mixin;

import btw.world.util.difficulty.Difficulties;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.interfaces.EntityPlayerInterface;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.interfaces.IconLargedMultipleRender;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ArmorQualityHelper;
import net.fabricmc.refamished.skill.*;
import net.fabricmc.refamished.stats.HardcoreBarefoot;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mixin(EntityPlayer.class)
public abstract class PlayerMixin {
	@Shadow
	public PlayerCapabilities capabilities;

	private static final float BASE_MOVEMENT_SPEED = 0.088889f;

	@Inject(method = "writeEntityToNBT", at = @At("RETURN"))
	private void onSavePlayerData(NBTTagCompound tag, CallbackInfo ci) {
		if (!((EntityPlayer) (Object) this).worldObj.isRemote)
		{
			try {
				SkillManager.saveSkillData((EntityPlayer) (Object) this, tag);
			} catch (Exception e) {
				System.err.println("ERROR: Failed to save skills in writeEntityToNBT.");
				e.printStackTrace();
			}
		}
	}

	private void sendSkillsToClient(EntityPlayerMP player) {
		PlayerSkillData skillData = SkillManager.getSkillData(player);

		// Fetch all skill data
		Map<String, PlayerSkillData.SkillProgress> skills = skillData.getAllSkills();

		// Sync each skill to the client
		for (Map.Entry<String, PlayerSkillData.SkillProgress> entry : skills.entrySet()) {
			String skillName = entry.getKey();
			PlayerSkillData.SkillProgress progress = entry.getValue();

			SkillUtils.syncSkillToClient(player, skillName, progress.getExperience(), progress.getLevel());
		}
	}

	@Inject(method = "readEntityFromNBT", at = @At("RETURN"))
	private void onLoadPlayerData(NBTTagCompound tag, CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer) (Object) this;

		if (!player.worldObj.isRemote) { // Only run on the server side
			try {
				// Load global skill data for this player from NBT
				SkillManager.loadSkillData(player, tag);

				// Synchronize loaded skill data into the local player
				//syncLoadedSkillsToLocal(player);

				// Send updated skill data to the client
				//sendSkillsToClient((EntityPlayerMP) player);

				System.out.println("DEBUG: Successfully loaded and synced skills to client.");
			} catch (Exception e) {
				System.err.println("[ERROR] Failed to load and sync player skills.");
				e.printStackTrace();
			}
		}
	}

	@Inject(method = "onUpdate", at = @At("HEAD"))
	private void onPlayerInit(CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer) (Object) this;

		// Only run on the server side and for the correct player type (server-side player)
		if (!player.worldObj.isRemote && player instanceof EntityPlayerMP) {
			EntityPlayerMP playerMP = (EntityPlayerMP) player;

			// Ensure the NetServerHandler is initialized before syncing
			if (playerMP.playerNetServerHandler != null) {
				// Perform the skill sync
				sendSkillsToClient(playerMP);

				// Debug output
				//System.out.println("DEBUG: Skills synced for player " + playerMP.getEntityName());
			}
		}
	}

	@Inject(method = "getArmorExhaustionModifier", at = @At("RETURN"), cancellable = true)
	protected void GetWornArmorWeight(CallbackInfoReturnable<Float> cir)
	{
		float iWeight = 0;
		EntityPlayer player = (EntityPlayer) (Object) this;
		InventoryPlayer inventory = player.inventory;

		for ( int iSlot = 0; iSlot < inventory.armorInventory.length; iSlot++ )
		{
			ItemStack tempStack = inventory.armorInventory[iSlot];

			if ( tempStack != null )
			{
				float iArmorWeight = ArmorQualityHelper.getWeight(ArmorQualityHelper.getArmorQuality(tempStack));
				iWeight += iArmorWeight;
				//System.out.println("Armor Weight: " + iArmorWeight);
			}
		}
		if (!((EntityPlayer)(Object)this).getEntityWorld().isRemote)
		{
			//System.out.println("TOTAL Armor Weight: " + iWeight);
		}
		if (iWeight > 10) {
			iWeight = 10;
		}
		if (iWeight <= 0) {
			iWeight = 0;
		}
		cir.setReturnValue(cir.getReturnValue()+iWeight);
	}

	@Inject(method = "damageEntity", at = @At("HEAD"), cancellable = true)
	private void injectArmorBonus(DamageSource par1DamageSource, float par2, CallbackInfo ci) {
		InventoryPlayer inventory = ((EntityPlayer)(Object)this).inventory; // Player inventory
		int totalArmorBonus = 0; // Track total bonus

		for (ItemStack armorPiece : inventory.armorInventory) {
			if (armorPiece != null && armorPiece.getItem() instanceof ItemArmor) {
				ArmorQuality quality = ArmorQualityHelper.getArmorQuality(armorPiece);
				totalArmorBonus += ArmorQualityHelper.getArmorBonus(quality);
			}
		}

		// Apply the bonus (adjust damage absorption/exhaustion or GUI)
		if (totalArmorBonus > 0) {
			//((EntityPlayer)(Object)this).addChatMessage("Armor Bonus Applied: +" + totalArmorBonus);
		}
	}

	@Unique
	protected void updateItemUse(ItemStack par1ItemStack, int par2) {
		EntityPlayer whatever = (EntityPlayer)(Object)this;
		if (par1ItemStack.getItemUseAction() == EnumAction.drink) {
			whatever.playSound("random.drink", 0.5f, whatever.worldObj.rand.nextFloat() * 0.1f + 0.9f);
		}
		if (par1ItemStack.getItemUseAction() == EnumAction.eat) {
			for (int var3 = 0; var3 < par2; ++var3) {
				Vec3 var4 = whatever.worldObj.getWorldVec3Pool().getVecFromPool(((double)whatever.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
				var4.rotateAroundX(-whatever.rotationPitch * (float)Math.PI / 180.0f);
				var4.rotateAroundY(-whatever.rotationYaw * (float)Math.PI / 180.0f);
				Vec3 var5 = whatever.worldObj.getWorldVec3Pool().getVecFromPool(((double)whatever.rand.nextFloat() - 0.5) * 0.3, (double)(-whatever.rand.nextFloat()) * 0.6 - 0.3, 0.6);
				var5.rotateAroundX(-whatever.rotationPitch * (float)Math.PI / 180.0f);
				var5.rotateAroundY(-whatever.rotationYaw * (float)Math.PI / 180.0f);
				var5 = var5.addVector(whatever.posX, whatever.posY + (double)whatever.getEyeHeight(), whatever.posZ);
				whatever.worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05, var4.zCoord);
			}
			whatever.playSound("random.eat", 0.5f + 0.5f * (float)whatever.rand.nextInt(2), (whatever.rand.nextFloat() - whatever.rand.nextFloat()) * 0.2f + 1.0f);
		}
	}

	@Unique
	protected void onItemUseFinish() {
		EntityPlayer whatever = (EntityPlayer)(Object)this;
		if (whatever.getItemInUse() != null) {
			updateItemUse(whatever.getItemInUse(), 16);
			int var1 = whatever.getItemInUse().stackSize;
			ItemStack var2 = whatever.getItemInUse().onFoodEaten(whatever.worldObj, whatever);
			if (var2 != whatever.getItemInUse() || var2 != null && var2.stackSize != var1) {
				whatever.inventory.mainInventory[whatever.inventory.currentItem] = var2;
				if (var2.stackSize == 0) {
					whatever.inventory.mainInventory[whatever.inventory.currentItem] = null;
				}
			}
			whatever.clearItemInUse();
		}
	}
	@Unique
	protected void closeScreen(EntityPlayer player) {
		player.openContainer = player.inventoryContainer;
	}

	@Environment(value = EnvType.CLIENT)
	@Inject(method = "getItemIcon", at = @At("HEAD"), cancellable = true)
	private void renderItem(ItemStack itemStack, int index, CallbackInfoReturnable<Icon> cir) {
		EntityPlayer player = (EntityPlayer)(Object)this;
		if (itemStack.getItem() instanceof IconByItemStack) {
			IconByItemStack iconObtainerThing = (IconByItemStack)itemStack.getItem();
			//System.out.println("AAAAAA");
			cir.setReturnValue(iconObtainerThing.getIcon(itemStack,player));
			cir.cancel();
		}
		else if (itemStack.getItem() instanceof IconLargedMultipleRender) {
			cir.setReturnValue(itemStack.getItem().getIconFromDamageForRenderPass(itemStack.getItemDamage(), index));
			cir.cancel();
		}
	}

	@Inject(method = "updateItemUse", at = @At("HEAD"), cancellable = true)
	private void updateThing(ItemStack par1ItemStack, int par2, CallbackInfo ci) {
		EntityPlayer player = (EntityPlayer)(Object)this;
		if (par1ItemStack.getItemUseAction() == EnumAction.drink) {
			player.playSound("random.drink", 0.5f, player.worldObj.rand.nextFloat() * 0.1f + 0.9f);
		}
		if (par1ItemStack.getItemUseAction() == EnumAction.eat && par1ItemStack.getHasSubtypes()) {
			for (int var3 = 0; var3 < par2; ++var3) {
				Vec3 var4 = player.worldObj.getWorldVec3Pool().getVecFromPool(((double)player.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
				var4.rotateAroundX(-player.rotationPitch * (float)Math.PI / 180.0f);
				var4.rotateAroundY(-player.rotationYaw * (float)Math.PI / 180.0f);
				Vec3 var5 = player.worldObj.getWorldVec3Pool().getVecFromPool(((double)player.rand.nextFloat() - 0.5) * 0.3, (double)(-player.rand.nextFloat()) * 0.6 - 0.3, 0.6);
				var5.rotateAroundX(-player.rotationPitch * (float)Math.PI / 180.0f);
				var5.rotateAroundY(-player.rotationYaw * (float)Math.PI / 180.0f);
				var5 = var5.addVector(player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);
				player.worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID+"_"+par1ItemStack.getItemDamage(), var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05, var4.zCoord);
			}
			player.playSound("random.eat", 0.5f + 0.5f * (float)player.rand.nextInt(2), (player.rand.nextFloat() - player.rand.nextFloat()) * 0.2f + 1.0f);
			ci.cancel();
		}
	}
}