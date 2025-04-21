package net.fabricmc.refamished.mixin.mixin_m;

import btw.entity.mob.behavior.SkeletonArrowAttackBehavior;
import net.minecraft.src.EntityAIAttackOnCollide;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.EntitySkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityCreeper.class)
public interface CreeperInterface {
    @Accessor("lastActiveTime")
    int lastActiveTime();
    @Accessor("lastActiveTime")
    void lastActiveTime(int set);
    @Accessor("timeSinceIgnited")
    int timeSinceIgnited();
    @Accessor("timeSinceIgnited")
    void timeSinceIgnited(int set);
    @Accessor("patienceCounter")
    byte patienceCounter();
    @Accessor("patienceCounter")
    void patienceCounter(byte set);
    @Accessor("determinedToExplode")
    boolean determinedToExplode();
    @Accessor("determinedToExplode")
    void determinedToExplode(boolean set);
    @Accessor("fuseTime")
    int fuseTime();
    @Accessor("fuseTime")
    void fuseTime(int set);
    @Invoker("getNeuteredState")
    int getNeuteredStateUm();
}
