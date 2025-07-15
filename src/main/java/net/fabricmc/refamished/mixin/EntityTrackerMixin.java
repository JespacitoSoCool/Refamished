package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.entities.EntityMolotov;
import net.fabricmc.refamished.entities.EntityMolotovHellfire;
import net.fabricmc.refamished.entities.EntitySKOrb;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityTracker.class)
public class EntityTrackerMixin {

    @Inject(method = "addEntityToTracker(Lnet/minecraft/src/Entity;)V", at = @At("HEAD"), cancellable = true)
    private void injectSkillOrbTracking(Entity entity, CallbackInfo ci) {
        if (entity instanceof EntitySKOrb) {
            //System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
            ((EntityTracker) (Object) this).addEntityToTracker(entity, 160, 20, true);
            ci.cancel();
        }
        else if (entity instanceof EntityMolotov) {
            ((EntityTracker) (Object) this).addEntityToTracker(entity, 3374, 20, true);
            ci.cancel();
        }
        else if (entity instanceof EntityMolotovHellfire) {
            ((EntityTracker) (Object) this).addEntityToTracker(entity, 3904, 20, true);
            ci.cancel();
        }
    }
}
