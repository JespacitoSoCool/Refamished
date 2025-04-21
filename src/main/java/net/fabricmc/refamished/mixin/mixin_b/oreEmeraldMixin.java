package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.EmeraldOreBlock;
import btw.block.blocks.IronOreBlock;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EmeraldOreBlock.class)
public class oreEmeraldMixin {
    @Inject(method = "getRequiredToolLevelForOre",at = @At("HEAD"), cancellable = true)
    public void getRequiredToolLevelForOre(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(3);
        cir.cancel();
    }
}
