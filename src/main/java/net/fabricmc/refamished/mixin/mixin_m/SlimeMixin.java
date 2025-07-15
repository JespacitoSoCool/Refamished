package net.fabricmc.refamished.mixin.mixin_m;

import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.UUID;

@Mixin(EntitySlime.class)
public class SlimeMixin {
    @Inject(method = "getCanSpawnHere",at = @At("TAIL"),cancellable = true)
    private void SpeedThing(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof EntitySlime slime) {
            World world = slime.worldObj;
            int x = MathHelper.floor_double(slime.posX);
            int y = MathHelper.floor_double(slime.posY);
            int z = MathHelper.floor_double(slime.posZ);

            boolean isFullMoon = world.getMoonPhase() == 0;

            int chunkX = x >> 4;
            int chunkZ = z >> 4;

            Random rand = new Random();
            rand.setSeed((long)chunkX * 0x1f1f1f1fL ^ (long)chunkZ * 0x81818181L ^ world.getSeed());
            boolean isSlimeChunk = rand.nextInt(10) == 0;

            if (isFullMoon && isSlimeChunk && !world.isDaytime() && world.getTotalWorldTime() > 24000) {
                cir.setReturnValue(true);
            }
        }
    }
}
