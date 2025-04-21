package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.world.BranchGen;
import net.fabricmc.refamished.world.FlintMinableGen;
import net.fabricmc.refamished.world.ScorchedStoneGen;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(BiomeDecorator.class)
public abstract class BiomeDecoratorMixin {

    @Shadow protected World currentWorld;
    private static WorldGenMinable copperGen;
    private static FlintMinableGen flintOre;
    private static WorldGenerator scorchedStoner;
    private static WorldGenerator branchGen;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void initCopper(CallbackInfo ci) {
        copperGen = new WorldGenMinable(RefamishedBlocks.copperOre.blockID, 10);
        flintOre = new FlintMinableGen(RefamishedBlocks.flintOre.blockID, 7);
        scorchedStoner = new ScorchedStoneGen(10);
        branchGen = new BranchGen(RefamishedBlocks.branch.blockID);
    }
    @Inject(method = "decorate()V", at = @At("TAIL"))
    private void what(CallbackInfo ci) {
        BiomeDecorator self = (BiomeDecorator) (Object) this;
        Random random = ((BiomeDecoratorAccessor) self).getRandomGenerator();
        World World = ((BiomeDecoratorAccessor) self).getWorld();
        int chunkX = ((BiomeDecoratorAccessor) self).getChunkX();
        int chunkZ = ((BiomeDecoratorAccessor) self).getChunkZ();
        int yThing = random.nextInt(100) + 6;
        int var4;
        int var3;
        int var2;
        int var1;
        int var7;
        for (var1 = 0; var1 < 5; ++var1) {
            var2 = chunkX + random.nextInt(16) + 8;
            var3 = chunkZ + random.nextInt(16) + 8;
            scorchedStoner.generate(World, random, var2, yThing, var3);
        }
        for (var1 = 0; var1 < 2; ++var1) {
            var2 = chunkX + random.nextInt(16) + 8;
            var3 = chunkZ + random.nextInt(16) + 8;
            scorchedStoner.generate(World, random, var2, currentWorld.getTopSolidOrLiquidBlock(var2, var3), var3);
        }
        for (var2 = 0; var2 < 17; ++var2)
        {
            var3 = chunkX + random.nextInt(16) + 8;
            var4 = random.nextInt(88)+40;
            var7 = chunkZ + random.nextInt(16) + 8;
            branchGen.generate(this.currentWorld, random, var3, var4, var7);
        }
    }

    @Inject(method = "generateOres", at = @At("TAIL"))
    private void generateCopperOre(CallbackInfo ci) {
        BiomeDecorator self = (BiomeDecorator) (Object) this;
        genStandardOre1(18, copperGen, 0, 64);
        genStandardOre1(10, flintOre, 0, 128);
    }

    @Unique
    protected void genStandardOre1(int par1, WorldGenerator par2WorldGenerator, int par3, int par4) {
        BiomeDecorator self = (BiomeDecorator) (Object) this;
        Random random = ((BiomeDecoratorAccessor) self).getRandomGenerator();
        int chunkX = ((BiomeDecoratorAccessor) self).getChunkX();
        int chunkZ = ((BiomeDecoratorAccessor) self).getChunkZ();
        World world = ((BiomeDecoratorAccessor) self).getWorld();
        for (int var5 = 0; var5 < par1; ++var5) {
            int var6 = chunkX + random.nextInt(16);
            int var7 = random.nextInt(par4 - par3) + par3;
            int var8 = chunkZ + random.nextInt(16);
            par2WorldGenerator.generate(world, random, var6, var7, var8);
            //System.out.println(par2WorldGenerator.getClass());
            //System.out.println(var6+" "+var7+" "+var8);
        }
    }

}
