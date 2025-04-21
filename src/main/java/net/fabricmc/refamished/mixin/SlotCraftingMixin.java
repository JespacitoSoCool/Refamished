package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import net.fabricmc.refamished.skill.SkillCraftingHandler;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.SlotCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SlotCrafting.class)
public class SlotCraftingMixin {
	@Shadow private EntityPlayer thePlayer;

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
}
