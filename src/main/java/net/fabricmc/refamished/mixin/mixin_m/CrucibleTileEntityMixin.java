package net.fabricmc.refamished.mixin.mixin_m;

import btw.block.tileentity.CrucibleTileEntity;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(CrucibleTileEntity.class)
public class CrucibleTileEntityMixin {
//    @Inject(method = "validateContentsForState",at = @At("HEAD"), cancellable = true)
//    private void givearmor(CallbackInfo ci) {
//
//    }
}
