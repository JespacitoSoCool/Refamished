package net.fabricmc.refamished.entities.behavior;

import net.minecraft.src.*;

public class CreeperSecondaryTargetFilter implements IEntitySelector {
    private EntityCreeper creeper;

    public CreeperSecondaryTargetFilter(EntityCreeper zombie) {
        this.creeper = zombie;
    }

    @Override
    public boolean isEntityApplicable(Entity entity) {
        return CreeperSecondaryTarget.isAttackable(entity,creeper);
    }
}
