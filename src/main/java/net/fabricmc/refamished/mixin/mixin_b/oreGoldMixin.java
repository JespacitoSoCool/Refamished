package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.GoldOreBlock;
import btw.block.blocks.IronOreBlock;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GoldOreBlock.class)
public class oreGoldMixin {
    @Inject(method = "getRequiredToolLevelForOre",at = @At("HEAD"), cancellable = true)
    public void getRequiredToolLevelForOre(IBlockAccess blockAccess, int i, int j, int k, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(2);
        cir.cancel();
    }
}
