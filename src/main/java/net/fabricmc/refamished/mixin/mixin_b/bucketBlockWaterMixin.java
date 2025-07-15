package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.BucketBlock;
import btw.block.blocks.BucketBlockWater;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BucketBlockWater.class)
public class bucketBlockWaterMixin {

    @Unique
    @Environment(EnvType.CLIENT)
    private Icon iconContainer;

    @Inject(method = "registerIcons", at = @At("TAIL"), cancellable = true)
    @Environment(EnvType.CLIENT)
    private void injectRegisterIcons(IconRegister register, CallbackInfo ci) {
        BucketBlock ts = (BucketBlock)(Object)this;
        iconContainer = register.registerIcon("refamished:override/bucket_water");
    }
    @Inject(method = "getContentsIcon", at = @At("HEAD"), cancellable = true, remap = false)
    @Environment(EnvType.CLIENT)
    private void injectContainer(CallbackInfoReturnable<Icon> cir) {
        if (RefamishedConfig.refamishedTextures)
        {
            cir.setReturnValue(iconContainer);
            cir.cancel();
        }
    }
}
