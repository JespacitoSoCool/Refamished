package net.fabricmc.refamished.entities.behavior;

import net.minecraft.src.*;

public class CreeperSecondaryAttackBehavior extends EntityAIAttackOnCollide {
    private EntityCreeper creeper;

    public CreeperSecondaryAttackBehavior(EntityCreeper zombie) {
        super(zombie, EntityCreature.class, 1.0, true);
        this.creeper = zombie;
    }

    @Override
    public boolean continueExecuting() {
        Entity var1 = this.attacker.getAttackTarget();
        if (CreeperSecondaryTarget.isAttackable(var1,creeper)) {
            return true;
        }
        return super.continueExecuting();
    }
}
