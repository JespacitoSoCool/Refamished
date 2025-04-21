package net.fabricmc.refamished.items.decorative;

import btw.entity.RottenArrowEntity;
import btw.item.items.ArrowItem;
import btw.item.items.RottenArrowItem;
import net.fabricmc.refamished.entities.EntityIncendiaryArrow;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.World;

public class incendiaryArrow extends RottenArrowItem {
    public incendiaryArrow(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("arrow_incendiary");
        this.setTextureName("refamished:arrow_incendiary");
    }

    EntityArrow getFiredArrowEntity(World world, double dXPos, double dYPos, double dZPos) {
        EntityArrow entity = new EntityIncendiaryArrow(world, dXPos, dYPos, dZPos);
        entity.canBePickedUp = 1;
        return entity;
    }
}
