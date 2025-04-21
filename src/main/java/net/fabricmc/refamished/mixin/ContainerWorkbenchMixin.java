package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.skill.SkillCraftingHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ContainerWorkbench.class)
public class ContainerWorkbenchMixin {
	@Unique
	private EntityPlayer craftingPlayer; // Store the player reference

	@Inject(method = "<init>", at = @At("TAIL"))
	private void capturePlayer(InventoryPlayer par1InventoryPlayer, World par2World, int par3, int par4, int par5, CallbackInfo ci) {
		this.craftingPlayer = par1InventoryPlayer.player; // Backtrack to the player via InventoryPlayer
	}

	@Inject(method = "onCraftMatrixChanged", at = @At("HEAD"), cancellable = true)
	public void onCraftAttempt(IInventory inventory, CallbackInfo ci) {
		if (this.craftingPlayer == null) {
			return; // No player reference
		}

		ItemStack resultItem = CraftingManager.getInstance().findMatchingRecipe((InventoryCrafting) inventory, this.craftingPlayer.worldObj);

		if (resultItem != null && !SkillCraftingHandler.canPlayerCraft(this.craftingPlayer, resultItem)) {
			ci.cancel(); // Cancel crafting if requirements aren't met

			if (!this.craftingPlayer.worldObj.isRemote) {
				this.craftingPlayer.addChatMessage("You do not meet the skill requirements to craft this item.");
			}
		}
	}

	public EntityPlayer getCraftingPlayer() {
		return this.craftingPlayer; // Provide access to the crafting player
	}
}
