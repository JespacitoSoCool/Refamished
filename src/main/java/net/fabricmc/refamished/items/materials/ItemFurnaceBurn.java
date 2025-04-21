package net.fabricmc.refamished.items.materials;

import net.minecraft.src.BlockWood;
import net.minecraft.src.Item;

public class ItemFurnaceBurn extends Item {
    public static int burnTime;
    public ItemFurnaceBurn(int par1,int fuckingSacrificeForJustToSmeltSomeFoodButEndsExtinguishingByTheRain) {
        super(par1);
        burnTime = fuckingSacrificeForJustToSmeltSomeFoodButEndsExtinguishingByTheRain;
    }
    @Override
    public int getFurnaceBurnTime(int iItemDamage) {
        return burnTime;
    }
}
