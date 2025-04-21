package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.interfaces.IGridPotion;
import net.minecraft.src.Potion;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Potion.class)
public class PotionMixin implements IGridPotion {

    @Unique
    private int gridX = 0;

    @Unique
    private int gridY = 0;

    @Override
    public int getGridX() {
        return gridX;
    }

    @Override
    public int getGridY() {
        return gridY;
    }

    @Override
    public void setGridX(int x) {
        this.gridX = x;
    }

    @Override
    public void setGridY(int y) {
        this.gridY = y;
    }

    @Shadow @Final @Mutable private static Potion[] potionTypes;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void expandPotionArray(CallbackInfo ci) {
        Potion[] newArray = new Potion[256];
        System.arraycopy(potionTypes, 0, newArray, 0, potionTypes.length);
        potionTypes = newArray;
    }
}
