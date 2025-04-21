package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.mob.behavior.SkeletonArrowAttackBehavior;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntitySkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntitySkeleton.class)
public interface SkeletonInterface {
    @Accessor("aiRangedAttack")
    SkeletonArrowAttackBehavior getAttackRanged();
    @Accessor("aiMeleeAttack")
    EntityAIAttackOnCollide getAttack();
    @Accessor("aiRangedAttack")
    void setAttackRanged(SkeletonArrowAttackBehavior set);
    @Accessor("aiMeleeAttack")
    void setAttack(EntityAIAttackOnCollide set);
}
