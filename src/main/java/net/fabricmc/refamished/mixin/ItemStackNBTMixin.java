package net.fabricmc.refamished.mixin;

import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackNBTMixin {

	@Inject(method = "writeToNBT", at = @At("HEAD"))
	private void saveToolQuality(NBTTagCompound par1NBTTagCompound, CallbackInfoReturnable<NBTTagCompound> cir) {
		ItemStack stack = (ItemStack) (Object) this;
		if (stack.hasTagCompound() && stack.getTagCompound().hasKey("ToolQuality")) {
			par1NBTTagCompound.setString("ToolQuality", stack.getTagCompound().getString("ToolQuality"));
		}
	}

	@Inject(method = "readFromNBT", at = @At("HEAD"))
	private void loadToolQuality(NBTTagCompound nbt, CallbackInfo info) {
		ItemStack stack = (ItemStack) (Object) this;
		if (nbt.hasKey("ToolQuality")) {
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setString("ToolQuality", nbt.getString("ToolQuality"));
		}
	}
}
