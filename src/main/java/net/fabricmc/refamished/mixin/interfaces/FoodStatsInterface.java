package net.fabricmc.refamished.mixin.interfaces;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.FoodStats;
import net.minecraft.src.ItemFood;
import net.minecraft.src.NBTTagCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(FoodStats.class)
public interface FoodStatsInterface {
    @Accessor("prevFoodLevel")
    void setPrevFoodLevel(int value);

    @Accessor("foodLevel")
    void setFoodLevel(int value);
    @Accessor("foodLevel")
    int getFoodLevel();

    @Accessor("foodExhaustionLevel")
    void setFoodExhaustionLevel(float value);
    @Accessor("foodExhaustionLevel")
    float getFoodExhaustionLevel();

    @Accessor("foodExhaustionLevel")
    void setFoodSaturationLevel(float value);
    @Accessor("foodExhaustionLevel")
    float getFoodSaturationLevel();

    @Accessor("foodTimer")
    void setFoodTimer(int value);
    @Accessor("foodTimer")
    int getFoodTimer();
}
