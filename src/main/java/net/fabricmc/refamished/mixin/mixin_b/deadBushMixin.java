package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
import btw.item.util.ItemUtils;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(BlockDeadBush.class)
public class deadBushMixin {
    @Inject(method = "idDropped",at = @At("HEAD"), cancellable = true)
    private void canUseBlade(int par1, Random par2Random, int par3, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(RefamishedItems.branch.itemID);
    }
}
