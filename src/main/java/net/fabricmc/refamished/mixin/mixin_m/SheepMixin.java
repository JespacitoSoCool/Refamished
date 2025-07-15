package net.fabricmc.refamished.mixin.mixin_m;

import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySheep.class)
public class SheepMixin {

    @Inject(method = "onLivingUpdate", at = @At("TAIL"))
    private void checkNearbyPlayersForAnxiety(CallbackInfo ci) {
//        EntitySheep sheep = (EntitySheep) (Object) this;
//        EntityPlayer closestPlayer = this.worldObj.getClosestPlayerToEntity(this, 8F);
//
//        if (closestPlayer != null) {
//            float distanceToTarget = this.getDistanceToEntity(closestPlayer);
//            ItemStack held = closestPlayer.getHeldItem();
//
//            if (held != null) {
//                ItemStack using = closestPlayer.getItemInUse();
//                boolean isProjectile = false;
//
//                if (using != null) {
//                    EnumAction useAction = using.getItemUseAction();
//                    isProjectile = useAction == EnumAction.bow;
//                }
//
////                if (held.getDamageVsEntity(this) >= 2 || isProjectile) {
////                    if (sheepAnxietyCounter > 200) {
////                        // Outburst
////                        OnNearbyPlayerStartles(closestPlayer);
////                        sheepAnxietyCounter = 0;
////                    } else {
////                        sheepAnxietyCounter += 15;
////                    }
////                }
//            }
//        }
    }
}
