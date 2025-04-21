package net.fabricmc.refamished.mixin.mixin_i;

import btw.block.BTWBlocks;
import btw.item.items.ShovelItemStone;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.Block;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ShovelItemStone.class)
public class ShovelItemStoneMixin {
    @Inject(method = "canHarvestBlock", at = @At("RETURN"), cancellable = true)
    public void CANHARVEST(ItemStack stack, World world, Block block, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(block == Block.blockClay || block == RefamishedBlocks.flintOre || block == BTWBlocks.ashCoverBlock);
        cir.cancel();
    }
}
