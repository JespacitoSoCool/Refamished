package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import net.fabricmc.refamished.itemsbase.craftedArmor;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemArmor;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryPlayer.class)
public class InventoryPlayerMixin {
	@Inject(at = @At("RETURN"), method = "getTotalArmorValue")
	private void armorShit(CallbackInfoReturnable<Integer> cir) {
		InventoryPlayer this_ = (InventoryPlayer)(Object)this;
		float var1 = 0;
		for (int var2 = 0; var2 < this_.armorInventory.length; ++var2) {
			if (this_.armorInventory[var2] == null || !(this_.armorInventory[var2].getItem() instanceof craftedArmor)) continue;
			ItemStack idk = this_.armorInventory[var2];
			craftedArmor idkButItem = (craftedArmor) idk.getItem();
			float var3 = (int) craftedArmor.getTrait(idk,"protection",idkButItem.armorType);
			var1 += var3;
		}
		cir.setReturnValue((int) (cir.getReturnValue()+var1));
	}
	@Inject(at = @At("HEAD"), method = "damageArmor", cancellable = true)
	private void armorShit(float par1, CallbackInfo ci) {
		InventoryPlayer this_ = (InventoryPlayer)(Object)this;
		if ((par1 /= 4.0f) < 1.0f) {
			par1 = 1.0f;
		}
		for (int var2 = 0; var2 < this_.armorInventory.length; ++var2) {
			if (this_.armorInventory[var2] == null || !(this_.armorInventory[var2].getItem() instanceof craftedArmor)) continue;
			ItemStack itemus = this_.armorInventory[var2];
			craftedArmor shit = (craftedArmor)itemus.getItem();
			if (craftedArmor.damageArmor(itemus,(int) par1, this_.player)) {
				this_.player.renderBrokenItemStack(itemus);
				itemus.stackSize--;
			}
			ci.cancel();
			if (itemus.stackSize != 0) continue;
			this_.armorInventory[var2] = null;
		}
	}
}
