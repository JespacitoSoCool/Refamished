package net.fabricmc.refamished.mixin;

import net.minecraft.src.BiomeDecorator;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public interface BiomeDecoratorAccessor {

    @Accessor("chunk_X")
    int getChunkX();

    @Accessor("chunk_Z")
    int getChunkZ();

    @Accessor("randomGenerator")
    Random getRandomGenerator();

    @Accessor("currentWorld")
    World getWorld();
}
