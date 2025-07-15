package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(BiomeGenBase.class)
public class BiomeGenBaseMixin {
    @Shadow
    protected List spawnableMonsterList;
    @Inject(method = "<init>",at = @At("TAIL"))
    private void SpeedThing(int par1, CallbackInfo ci) {
        if ((Object)this instanceof BiomeGenBase genBase) {
            List<SpawnListEntry> spawnList = (List<SpawnListEntry>) this.spawnableMonsterList;
            spawnList.add(new SpawnListEntry(EntitySlime.class, 10, 4, 4));
        }
    }
}
