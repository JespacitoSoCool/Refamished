package net.fabricmc.refamished.entities.behavior;

import net.minecraft.src.*;

public class CreeperSecondaryTarget {

    public static boolean isAttackable(Entity target, EntityCreeper creepah) {
        return target != null && (target instanceof EntitySheep || target instanceof EntityPig || target instanceof EntityCow);
    }
}
