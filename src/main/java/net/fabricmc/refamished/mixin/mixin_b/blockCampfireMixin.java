package net.fabricmc.refamished.mixin.mixin_b;

import btw.block.BTWBlocks;
import btw.block.blocks.CampfireBlock;
import btw.block.blocks.SlabBlock;
import btw.block.blocks.WoodSlabBlock;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntBlock;
import net.fabricmc.refamished.blocks.decorative.burnt.baseBurntSlab;
import net.fabricmc.refamished.mixin.interfaces.blockFireInterface;
import net.minecraft.src.Block;
import net.minecraft.src.BlockFire;
import net.minecraft.src.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(CampfireBlock.class)
public class blockCampfireMixin {

    @Inject(method = "randomDisplayTick",at = @At("HEAD"))
    private void wh(World world, int x, int y, int z, Random rand, CallbackInfo ci) {
        CampfireBlock campfi = (CampfireBlock)(Object)this;
        if (campfi.fireLevel > 1) {
            for (int iTempCount = 0; iTempCount < 1; ++iTempCount) {
                double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
                double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                world.spawnParticle("firedot", xPos, yPos, zPos, 0.0, 0.0, 0.0);
            }
            int min = campfi.fireLevel == 3 ? 3 : 0;
            int max = campfi.fireLevel == 3 ? 5 : 1;
            for (int iTempCount = min; iTempCount <max; ++iTempCount) {
                double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
                double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
                world.spawnParticle("thicksmoke", xPos, yPos, zPos, 0.0, 0.0, 0.0);
            }
        }
        else if (campfi.fireLevel == 1) {
            double xPos = (double)x + 0.5 - 0.6 + rand.nextDouble() * 1.2;
            double yPos = (double)y + 0.5 + 0.25 + rand.nextDouble() * 0.25;
            double zPos = (double)z + 0.5 - 0.6 + rand.nextDouble() * 1.2;
            world.spawnParticle("firedot", xPos, yPos, zPos, 0.0, 0.0, 0.0);
        }
    }
}
