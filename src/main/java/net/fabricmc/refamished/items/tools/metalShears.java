package net.fabricmc.refamished.items.tools;

import btw.item.items.MortarItem;
import net.minecraft.src.*;

public class metalShears extends ItemShears {
    public metalShears(int par1, int durability) {
        super(par1);
        setMaxDamage(durability);
    }
}
