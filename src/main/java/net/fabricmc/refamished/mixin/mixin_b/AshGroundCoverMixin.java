package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.AshGroundCoverBlock;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AshGroundCoverBlock.class)
public class AshGroundCoverMixin {
    @Inject(method = "idDropped",at = @At("RETURN"), cancellable = true)
    public void getRequiredToolLevelForOre(int iMetadata, Random rand, int iFortuneModifier, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(RefamishedItems.pile_ashes.itemID);
        cir.cancel();
    }
}
