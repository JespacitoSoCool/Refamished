package net.fabricmc.refamished.mixin.mixin_b;

import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.itemsbase.machete;
import net.minecraft.src.BlockWeb;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockWeb.class)
public class BlockWebMixin {
    @Inject(method = "isEffectiveItemConversionTool",at = @At("RETURN"), cancellable = true)
    private void canUseBlade(ItemStack stack, World world, int i, int j, int k, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof blade || stack.getItem() instanceof machete)
        {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
