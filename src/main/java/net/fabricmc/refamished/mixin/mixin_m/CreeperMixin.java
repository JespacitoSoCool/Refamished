package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.mob.behavior.ZombieSecondaryAttackBehavior;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.entities.behavior.CreeperSecondaryAttackBehavior;
import net.fabricmc.refamished.entities.behavior.CreeperSecondaryTargetFilter;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityCreeper.class)
public class CreeperMixin {
    @Unique
    int cruelExplosionRadius = 4;
    @Inject(method = "<init>",at = @At("TAIL"))
    private void dropItems(World par1World, CallbackInfo ci) {
        EntityCreeper self = (EntityCreeper) (Object) this;
        LivingInterface thisthing2 = (LivingInterface) self;
        boolean cruelMode = self.worldObj.getDifficulty() instanceof DifficultyCruel;
        if (cruelMode)
        {
            thisthing2.getTasks().addTask(3, new CreeperSecondaryAttackBehavior(self));
            IEntitySelector um = new CreeperSecondaryTargetFilter(self);
            thisthing2.getTargetTasks().addTask(2, new EntityAINearestAttackableTarget(self, EntityCreature.class, 0, false, false, um));
        }
    }
    @Inject(method = "onDeath",at = @At("TAIL"))
    private void bomb(DamageSource par1DamageSource, CallbackInfo ci) {
        EntityCreeper self = (EntityCreeper) (Object) this;
        LivingInterface thisthing2 = (LivingInterface) self;
        boolean cruelMode = self.worldObj.getDifficulty() instanceof DifficultyCruel;
        if (cruelMode && self.isBurning())
        {
            boolean var2 = self.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
            if (self.getPowered()) {
                self.worldObj.newExplosion(self, self.posX, self.posY + (double)self.getEyeHeight(), self.posZ, cruelExplosionRadius * 2,true, var2);
            } else {
                self.worldObj.newExplosion(self, self.posX, self.posY + (double)self.getEyeHeight(), self.posZ, cruelExplosionRadius,true, var2);
            }
        }
    }

    @Inject(method = "onUpdate",at = @At("HEAD"), cancellable = true)
    private void what(CallbackInfo ci) {
        EntityCreeper self = (EntityCreeper) (Object) this;
        CreeperInterface thisthing2 = (CreeperInterface) self;
        boolean cruelMode = self.worldObj.getDifficulty() instanceof DifficultyCruel;
        if (cruelMode)
        {
            if (self.isEntityAlive()) {
                if (thisthing2.timeSinceIgnited() >= thisthing2.fuseTime()-5) {
                    thisthing2.timeSinceIgnited(thisthing2.fuseTime());
                    if (!self.worldObj.isRemote) {
                        boolean var2 = self.worldObj.getGameRules().getGameRuleBooleanValue("mobGriefing");
                        boolean varHesFuckingBurning = self.isBurning();
                        if (self.getPowered()) {
                            self.worldObj.newExplosion(self, self.posX, self.posY + (double)self.getEyeHeight(), self.posZ, cruelExplosionRadius * 2, varHesFuckingBurning, var2);
                        } else {
                            self.worldObj.newExplosion(self, self.posX, self.posY + (double)self.getEyeHeight(), self.posZ, cruelExplosionRadius, varHesFuckingBurning, var2);
                        }
                        self.setDead();
                    }
                }
            }
        }
    }
}