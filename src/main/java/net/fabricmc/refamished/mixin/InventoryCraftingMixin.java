package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import net.minecraft.src.Container;
import net.minecraft.src.ContainerWorkbench;
import net.minecraft.src.InventoryCrafting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryCrafting.class)
public class InventoryCraftingMixin {
	@Shadow private Container eventHandler;

	@Unique
	public ContainerWorkbench getContainerWorkbench() {
		return (ContainerWorkbench) this.eventHandler;
	}
}
