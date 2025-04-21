package net.fabricmc.refamished.items.decorative;

import btw.item.items.RottenArrowItem;
import net.fabricmc.refamished.entities.EntityFlintArrow;
import net.fabricmc.refamished.entities.EntityIncendiaryArrow;
import net.minecraft.src.EntityArrow;
import net.minecraft.src.World;

public class flintArrow extends RottenArrowItem {
    public flintArrow(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("arrow_flint");
        this.setTextureName("refamished:arrow_flint");
    }

    EntityArrow getFiredArrowEntity(World world, double dXPos, double dYPos, double dZPos) {
        EntityArrow entity = new EntityFlintArrow(world, dXPos, dYPos, dZPos);
        entity.canBePickedUp = 1;
        return entity;
    }
}
