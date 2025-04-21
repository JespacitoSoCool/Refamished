package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.DiamondOreBlock;
import net.minecraft.src.BlockObsidian;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockObsidian.class)
public class obsidianMixin {
    @Inject(method = "getHarvestToolLevel",at = @At("HEAD"), cancellable = true)
    public void getHarvestToolLevel(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(4);
        cir.cancel();
    }
}
