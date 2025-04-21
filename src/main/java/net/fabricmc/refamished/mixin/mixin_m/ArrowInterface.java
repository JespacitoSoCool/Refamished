package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.EntityArrow;
import net.minecraft.src.EntityLiving;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityArrow.class)
public interface ArrowInterface {
    @Accessor("damage")
    double getDamage();
    @Accessor("knockbackStrength")
    int getKnockbackStrength();
    @Accessor("ticksInAir")
    int getTicksInAir();
    @Accessor("ticksInAir")
    void setTicksInAir(int set);
    @Accessor("ticksInGround")
    int getTicksInGround();
    @Accessor("ticksInGround")
    void setTicksInGround(int set);
    @Invoker("notifyCollidingBlockOfImpact")
    void setNotify();
}
