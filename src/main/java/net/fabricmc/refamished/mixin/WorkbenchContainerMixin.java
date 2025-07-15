package net.fabricmc.refamished.mixin;

import btw.block.BTWBlocks;
import btw.inventory.container.WorkbenchContainer;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.World;
import net.minecraft.src.WorldGenLiquids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(WorkbenchContainer.class)
public abstract class WorkbenchContainerMixin {

	@Shadow public World world;
	@Shadow public int blockX;
	@Shadow public int blockY;
	@Shadow public int blockZ;

	@Inject(method = "canInteractWith", at = @At("TAIL"), cancellable = true)
	private void AAAHHHHHHHHHHH(EntityPlayer par1EntityPlayer, CallbackInfoReturnable<Boolean> cir) {
		int iBlockID = this.world.getBlockId(this.blockX, this.blockY, this.blockZ);
		boolean isFuckingValid = (iBlockID == RefamishedBlocks.workbench.blockID);
		boolean distanceShitIdk = par1EntityPlayer.getDistanceSq((double)this.blockX + 0.5, (double)this.blockY + 0.5, (double)this.blockZ + 0.5) <= 64.0;
		cir.setReturnValue(cir.getReturnValue() || (isFuckingValid && distanceShitIdk));
	}
}