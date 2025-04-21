package net.fabricmc.refamished.interfaces;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.EntityPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityPlayer.class)
public interface EntityPlayerInterface {
    @Accessor("sleepTimer")
    int getSleepTimer();

    @Accessor("sleepTimer")
    void setSleepTimer(int sleepTimer);

    @Accessor("startMinecartRidingCoordinate")
    ChunkCoordinates getStartMinecartRidingCoordinate();

    @Accessor("startMinecartRidingCoordinate")
    void setStartMinecartRidingCoordinate(ChunkCoordinates coordinates);

    @Invoker("isInBed")
    boolean callIsInBed();
}
