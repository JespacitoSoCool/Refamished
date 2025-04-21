package net.fabricmc.refamished.mixin.mixix_struc;

import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(StructureVillagePieces.class)
public class StructureVillagePiecesAAA {
    @Inject(method = "func_143016_a",at = @At("RETURN"), cancellable = true)
    private static void rework(CallbackInfo ci) {
        //MapGenStructureIO.func_143031_a(ComponentVillageHouse1.class, "ViBH");
        //MapGenStructureIO.func_143031_a(ComponentVillageField.class, "ViDF");
        //MapGenStructureIO.func_143031_a(ComponentVillageField2.class, "ViF");
        //MapGenStructureIO.func_143031_a(ComponentVillageTorch.class, "ViL");
        //MapGenStructureIO.func_143031_a(ComponentVillageHall.class, "ViPH");
        //MapGenStructureIO.func_143031_a(ComponentVillageHouse4_Garden.class, "ViSH");
        //MapGenStructureIO.func_143031_a(ComponentVillageWoodHut.class, "ViSmH");
        //MapGenStructureIO.func_143031_a(ComponentVillageChurch.class, "ViST");
        //MapGenStructureIO.func_143031_a(ComponentVillageHouse2.class, "ViS");
        //MapGenStructureIO.func_143031_a(ComponentVillageStartPiece.class, "ViStart");
        //MapGenStructureIO.func_143031_a(ComponentVillagePathGen.class, "ViSR");
        //MapGenStructureIO.func_143031_a(ComponentVillageHouse3.class, "ViTRH");
        //MapGenStructureIO.func_143031_a(ComponentVillageWell.class, "ViW");
    }
}
