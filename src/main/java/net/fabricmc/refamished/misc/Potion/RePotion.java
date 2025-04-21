package net.fabricmc.refamished.misc.Potion;

import net.fabricmc.refamished.interfaces.IGridPotion;
import net.minecraft.src.Potion;
import net.minecraft.src.PotionEffect;

public class RePotion extends Potion implements IGridPotion {
    public RePotion(int par1, boolean par2, int par3) {
        super(par1, par2, par3);
    }

    @Override
    public int getGridX() {
        return 0;
    }

    @Override
    public int getGridY() {
        return 0;
    }

    @Override
    public void setGridX(int x) {

    }

    @Override
    public void setGridY(int y) {

    }
}
