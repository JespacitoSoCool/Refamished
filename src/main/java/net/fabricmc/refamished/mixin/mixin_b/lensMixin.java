package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.blocks.BucketBlock;
import btw.block.blocks.LensBlock;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.Icon;
import net.minecraft.src.IconRegister;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LensBlock.class)
public class lensMixin {

    @Shadow
    @Environment(EnvType.CLIENT)
    private Icon iconOutput;

    @Shadow @Environment(EnvType.CLIENT)
    private Icon iconInput;

    @Inject(method = "registerIcons", at = @At("TAIL"), cancellable = true)
    @Environment(EnvType.CLIENT)
    private void injectRegisterIcons(IconRegister register, CallbackInfo ci) {
        LensBlock ts = (LensBlock)(Object)this;
        ts.blockIcon = register.registerIcon("refamished:gilded_block");
        this.iconOutput = register.registerIcon("refamished:override/lens_output");
        this.iconInput = register.registerIcon("refamished:override/lens_input");
        ci.cancel();
    }
}
