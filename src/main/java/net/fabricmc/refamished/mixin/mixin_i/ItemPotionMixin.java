package net.fabricmc.refamished.mixin.mixin_i;

import btw.block.BTWBlocks;
import btw.block.blocks.BucketBlock;
import btw.item.items.ShovelItemStone;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemPotion.class)
public class ItemPotionMixin {
    @Shadow
    @Environment(EnvType.CLIENT)
    private Icon field_94590_d;

    @Shadow @Environment(EnvType.CLIENT)
    private Icon field_94591_c;

    @Shadow @Environment(EnvType.CLIENT)
    private Icon field_94592_ct;

    @Inject(method = "registerIcons", at = @At("HEAD"), cancellable = true)
    @Environment(EnvType.CLIENT)
    private void injectRegisterIcons(IconRegister register, CallbackInfo ci) {
        if (RefamishedConfig.refamishedTextures)
        {
            ItemPotion ts = (ItemPotion)(Object)this;
            this.field_94590_d = register.registerIcon("refamished:override/potion_bottle_drinkable");
            this.field_94591_c = register.registerIcon("refamished:override/potion_bottle_splash");
            this.field_94592_ct = register.registerIcon("refamished:override/potion_overlay");
            ci.cancel();
        }
    }
}
