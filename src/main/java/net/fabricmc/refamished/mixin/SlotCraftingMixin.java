package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import btw.item.util.ItemUtils;
import net.fabricmc.refamished.mixin.interfaces.SlotCraftingInterface;
import net.fabricmc.refamished.skill.SkillCraftingHandler;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotCrafting.class)
public class SlotCraftingMixin {
	@Shadow private EntityPlayer thePlayer;
	@Shadow private IInventory craftMatrix;
	@Shadow private IRecipe currentRecipe;
	@Shadow private int amountCrafted;

	@Inject(at = @At("RETURN"), method = "onCrafting(Lnet/minecraft/src/ItemStack;)V", cancellable = true)
	private void init(ItemStack par1ItemStack, CallbackInfo ci) {
		if (!SkillCraftingHandler.canPlayerCraft(this.thePlayer, par1ItemStack))
		{
			ci.cancel();
		}
	}

	@Inject(at = @At("RETURN"), method = "onCrafting(Lnet/minecraft/src/ItemStack;I)V", cancellable = true)
	private void init(ItemStack par1ItemStack, int par2, CallbackInfo ci) {
		if (!SkillCraftingHandler.canPlayerCraft(this.thePlayer, par1ItemStack))
		{
			ci.cancel();
		}
	}
	/*
		@Inject(at = @At("HEAD"), method = "onPickupFromSlot", cancellable = true)
	private void init(EntityPlayer player, ItemStack par2ItemStack, CallbackInfo ci) {
		ci.cancel();
		SlotCrafting thisShit = (SlotCrafting)(Object)this;
		SlotCraftingInterface wha = (SlotCraftingInterface)(Object)this;
		wha.craftThisShit(par2ItemStack);
		if (this.currentRecipe.getSecondaryOutput(this.craftMatrix) != null) {
			for (ItemStack stack : this.currentRecipe.getSecondaryOutput(this.craftMatrix)) {
				ItemUtils.givePlayerStackOrEjectSilent(player, stack.copy());
				if (player.timesCraftedThisTick != 0) continue;
				player.worldObj.playSoundAtEntity(player, "random.pop", 0.2f, ((player.rand.nextFloat() - player.rand.nextFloat()) * 0.7f + 1.0f) * 2.0f);
			}
		}
		for (int var3 = 0; var3 < this.craftMatrix.getSizeInventory(); ++var3) {
			ItemStack var4 = this.craftMatrix.getStackInSlot(var3);
			if (var4 == null) continue;
			var4.getItem().onUsedInCrafting(var4.getItemDamage(), player, par2ItemStack);
			if (!var4.getItem().isConsumedInCrafting()) continue;
			if (var4.getItem().isDamagedInCrafting()) {
				if (var4.getItemDamage() >= var4.getMaxDamage() - 1) {
					var4.getItem().onBrokenInCrafting(player);
					this.craftMatrix.decrStackSize(var3, 1);
					continue;
				}
				var4.getItem().onDamagedInCrafting(player);
				var4.damageItem(1, player);
				continue;
			}
			this.craftMatrix.decrStackSize(var3, 1);
			if (!var4.getItem().hasContainerItem() || par2ItemStack.getItem().doesConsumeContainerItemWhenCrafted(var4.getItem().getContainerItem())) continue;
			ItemStack var5 = new ItemStack(var4.getItem().getContainerItem());
			if (var4.getItem().doesContainerItemLeaveCraftingGrid(var4) && this.thePlayer.inventory.addItemStackToInventory(var5)) continue;
			if (this.craftMatrix.getStackInSlot(var3) == null) {
				this.craftMatrix.setInventorySlotContents(var3, var5);
				continue;
			}
			this.thePlayer.dropPlayerItem(var5);
		}
		++player.timesCraftedThisTick;
	}
	 */
}
