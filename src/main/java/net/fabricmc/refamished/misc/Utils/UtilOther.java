package net.fabricmc.refamished.misc.Utils;

import net.minecraft.src.*;

public class UtilOther {
    public Explosion reversedExplosion(World world, Entity par1Entity, double par2, double par4, double par6, float par8, boolean par9, boolean par10) {
        Explosion var11 = new Explosion(world, par1Entity, par2, par4, par6, par8);
        var11.isFlaming = par9;
        var11.isSmoking = par10;
        var11.doExplosionB(true);
        var11.doExplosionA();
        return var11;
    }
}
