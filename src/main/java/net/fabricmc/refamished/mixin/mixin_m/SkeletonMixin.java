package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.InfiniteArrowEntity;
import btw.entity.RottenArrowEntity;
import btw.entity.mob.behavior.SkeletonArrowAttackBehavior;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntitySkeleton.class)
public abstract class SkeletonMixin {
    @Inject(method = "<init>", at = @At("TAIL"))
    private void WAAAA(CallbackInfo ci) {
        EntitySkeleton self = (EntitySkeleton)(Object)this;
        SkeletonInterface thisthing = (SkeletonInterface)self;
        LivingInterface thisthing2 = (LivingInterface)self;
        EntityInterface ent = (EntityInterface)self;
        DataWatcher dataWatcher = ent.getWatcher();

        int dimension = (self!= null && self.worldObj != null) ? self.worldObj.provider.dimensionId : 0;

        if (self!= null && !self.isDead)
        {
            if (dimension == -1) {  // Nether
                thisthing.setAttackRanged(new SkeletonArrowAttackBehavior(self, 0.20F, 20, 30F));
            } else if (dimension == 1) {  // End
                thisthing.setAttackRanged(new SkeletonArrowAttackBehavior(self, 0.05F, 10, 40F));
            } else {  // Overworld
                thisthing.setAttackRanged(new SkeletonArrowAttackBehavior(self, 0.1F, 30, 20F));
            }
        }
    }
    @Inject(method = "attackEntityWithRangedAttack", at = @At("HEAD"), cancellable = true)
    private void onEntityInit(CallbackInfo ci) {
        EntitySkeleton self = (EntitySkeleton)(Object)this;
        EntityInterface ent = (EntityInterface)self;
        if (self!= null && !self.isDead)
        {
            int dimension = (self!= null && self.worldObj != null) ? self.worldObj.provider.dimensionId : 0;
            boolean cruelDif = self.worldObj != null && self.worldObj.getDifficulty() instanceof DifficultyCruel;

            int iFlameLevel;
            int iPunchLevel;
            EntityArrow arrow = null;
            if (cruelDif)
            {
                arrow = dimension == -1 ? new InfiniteArrowEntity(self.worldObj, self, self.getAttackTarget(), 2.3f, 6.0f) :
                        new EntityArrow(self.worldObj, self, self.getAttackTarget(), 1.8f, 8.0f);
                arrow.canBePickedUp = 2;
            }
            else
            {
                arrow = dimension == -1 ? new InfiniteArrowEntity(self.worldObj, self, self.getAttackTarget(), 1.6f, 12.0f) :
                        new RottenArrowEntity(self.worldObj, self, self.getAttackTarget(), 1.6f, 12.0f);
            }
            int iPowerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, self.getHeldItem());
            if (iPowerLevel > 0) {
                arrow.setDamage(arrow.getDamage() + (double)iPowerLevel * 0.5 + 0.5);
            }
            if ((iPunchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, self.getHeldItem())) > 0) {
                arrow.setKnockbackStrength(iPunchLevel);
            }
            if ((iFlameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, self.getHeldItem())) > 0 || self.getSkeletonType() == 1 || self.isBurning() && self.rand.nextFloat() < 0.3f) {
                arrow.setFire(100);
            }
            self.playSound("random.bow", 1.0f, 1.0f / (self.getRNG().nextFloat() * 0.4f + 0.8f));
            self.worldObj.spawnEntityInWorld(arrow);
        }
        ci.cancel();
    }
}
