package net.fabricmc.refamished.mixin;

import btw.block.blocks.BedBlockBase;
import btw.block.tileentity.beacon.CompanionBeaconEffect;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.mixin.interfaces.FoodStatsInterface;
import net.minecraft.src.Block;
import net.minecraft.src.DamageSource;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FoodStats;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FoodStats.class)
public class FoodStatsMixin {
    @Unique
    private boolean shouldBurnFatBeforeHunger() {
        FoodStats this_ = (FoodStats)(Object)this;
        FoodStatsInterface access = (FoodStatsInterface)this_;
        return access.getFoodSaturationLevel() > (float)((access.getFoodLevel() + 5) / 6) * 2.0f;
    }

    @Inject(method = "onUpdate", at = @At("HEAD"), cancellable = true)
    public void onUpdate(EntityPlayer player, CallbackInfo ci) {
        FoodStats this_ = (FoodStats)(Object)this;
        FoodStatsInterface access = (FoodStatsInterface)this_;

        access.setPrevFoodLevel(access.getFoodLevel());
        int difficulty = player.worldObj.difficultySetting;

        if (difficulty > 0) {
            while (access.getFoodLevel() > 0 && access.getFoodExhaustionLevel() >= 1.33f && !shouldBurnFatBeforeHunger()) {
                access.setFoodExhaustionLevel(access.getFoodExhaustionLevel() - 1.33f);
                access.setFoodLevel(Math.max(access.getFoodLevel() - 1, 0));
            }
            while (access.getFoodExhaustionLevel() >= 0.5f && shouldBurnFatBeforeHunger()) {
                access.setFoodExhaustionLevel(access.getFoodExhaustionLevel() - 0.5f);
                access.setFoodSaturationLevel(Math.max(access.getFoodSaturationLevel() - 0.125f, 0.0f));
            }
        } else {
            access.setFoodExhaustionLevel(0.0f);
        }

        if (CompanionBeaconEffect.companionStrength > 3 && player.worldObj.rand.nextInt(5000) == 0) {
            this_.attemptToShit(player);
        }

        if (access.getFoodLevel() > 24 && player.shouldHeal() && player.worldObj.getGameRules().getGameRuleBooleanValue("naturalRegeneration")) {
            access.setFoodTimer(access.getFoodTimer() + 1);
            int foodTimerMax = (int)(400.0f * player.worldObj.getDifficulty().getHealthRegenDelayMultiplier());

            if (access.getFoodTimer() >= foodTimerMax) {
                if (!player.isPotionActive(RefamishedMod.INFESTEDWOUND))
                {
                    if (player.isPlayerSleeping()) {
                        BedBlockBase bed;
                        Block block = Block.blocksList[player.worldObj.getBlockId(player.playerLocation.posX, player.playerLocation.posY, player.playerLocation.posZ)];
                        if (block instanceof BedBlockBase && !(bed = (BedBlockBase)block).blocksHealing()) {
                            player.heal(1.0f);
                        }
                    } else {
                        player.heal(1.0f);
                    }
                }

                access.setFoodTimer(0);
            }
        } else if (access.getFoodLevel() <= 0 && access.getFoodSaturationLevel() <= 0.01f) {
            access.setFoodTimer(access.getFoodTimer() + 1);

            if (access.getFoodTimer() >= 80) {
                if (difficulty > 0) {
                    player.attackEntityFrom(DamageSource.starve, 1.0f);
                }
                access.setFoodTimer(0);
            }

            access.setFoodExhaustionLevel(0.0f);
        } else {
            access.setFoodTimer(0);
        }

        ci.cancel();
    }

}
