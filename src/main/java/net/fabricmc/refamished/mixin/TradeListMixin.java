package net.fabricmc.refamished.mixin;

import btw.entity.mob.villager.trade.TradeList;
import net.fabricmc.refamished.entities.EntityMolotov;
import net.fabricmc.refamished.entities.EntityMolotovHellfire;
import net.fabricmc.refamished.entities.EntitySKOrb;
import net.fabricmc.refamished.misc.VillagerTrades;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TradeList.class)
public class TradeListMixin {

    @Inject(method = "addVillagerTrades", at = @At("TAIL"), remap = false)
    private static void why(CallbackInfo ci) {
        VillagerTrades.addVillagerTrades();
    }
}
