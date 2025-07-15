package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.BucketBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BucketBlock.class)
public class bucketBlockMixin {

    @Shadow
    @Environment(EnvType.CLIENT)
    private Icon iconOpenTop;

    @Shadow @Environment(EnvType.CLIENT)
    private Icon iconOpenSide;

    @Inject(method = "registerIcons", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    private void injectRegisterIcons(IconRegister register, CallbackInfo ci) {
        if (RefamishedConfig.refamishedTextures)
        {
            BucketBlock ts = (BucketBlock)(Object)this;
            ts.blockIcon = register.registerIcon("refamished:override/bucket");
            this.iconOpenTop = register.registerIcon("refamished:override/bucket_top");
            this.iconOpenSide = register.registerIcon("refamished:override/bucket_top_side");
            ci.cancel();
        }
    }
}
