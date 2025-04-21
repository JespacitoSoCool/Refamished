package net.fabricmc.refamished.mixin.mixix_struc;

import net.fabricmc.refamished.itemsbase.blade;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Random;

@Mixin(ComponentVillageWell.class)
public class VillageTest {
    @Inject(method = "addComponentParts",at = @At("RETURN"), cancellable = true)
    private void canUseBlade(World par1World, Random par2Random, StructureBoundingBox par3StructureBoundingBox, CallbackInfoReturnable<Boolean> cir) {
        //ComponentVillageWell this_ = (ComponentVillageWell)(Object)this;
        //this_.fillWithBlocks(par1World, par3StructureBoundingBox, 1, 0, 1, 4, 12, 4, SReDefinitions.brittlestone.blockID, 0, false);
        //this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 12, 2, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, 0, 0, 3, 12, 2, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, 0, 0, 2, 12, 3, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, 0, 0, 3, 12, 3, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 1, 13, 1, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 1, 14, 1, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 4, 13, 1, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 4, 14, 1, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 1, 13, 4, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 1, 14, 4, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 4, 13, 4, par3StructureBoundingBox);
        //this.placeBlockAtCurrentPosition(par1World, SReDefinitions.decayedWood.blockID, 0, 4, 14, 4, par3StructureBoundingBox);
    }
}
