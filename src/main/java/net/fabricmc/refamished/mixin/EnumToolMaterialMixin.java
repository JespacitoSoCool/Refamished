package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.interfaces.EnumToolMaterialInterface;
import net.minecraft.src.EnumToolMaterial;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(EnumToolMaterial.class)
public class EnumToolMaterialMixin implements EnumToolMaterialInterface {
    @Shadow
    @Final
    @Mutable
    private static EnumToolMaterial[] $VALUES;

    // Define the custom material
    @Unique
    private static EnumToolMaterial COPPER = addCustomMaterial(
            "COPPER",
            2,
            200,
            2.0F,
            1.5F,
            15,
            20,
            2
    );

    @Unique
    private static EnumToolMaterial BONE = addCustomMaterial(
            "BONE",
            1, 20, 0.4F, 1, 5, 10, 1
    );
    @Unique
    private static EnumToolMaterial RUSTIRON = addCustomMaterial(
            "RUSTIRON",
            1, 48, 0.7F, 1, 1, 2, 1
    );
    @Unique
    private static EnumToolMaterial CORRODEDCOPPER = addCustomMaterial(
            "CORRODEDCOPPER",
            1, 36, 0.5F, 1, 1, 2, 1
    );
    @Unique
    private static EnumToolMaterial DIAMONDTIP = addCustomMaterial(
            "EMERALDTIP",
            4, 600, 3.1F, 2.0f, 14, 25, 2
    );
    @Unique
    private static EnumToolMaterial DULLEDGOLD = addCustomMaterial(
            "DULLEDGOLD",
            1, 22, 0.9F, 1, 1, 2, 1
    );
    @Unique
    private static EnumToolMaterial GILDEDIRON = addCustomMaterial(
            "GILDEDIRON",
            3, 642, 3.4F, 2.0f, 30, 40, 3
    );
    @Unique
    private static EnumToolMaterial COBALTZURE = addCustomMaterial(
            "COBALTZURE",
            5, 1972, 4.1F, 3.5f, 15, 30, 3
    );
    @Unique
    private static EnumToolMaterial STEEL = addCustomMaterial(
            "GILDEDIRON",
            3, 904, 3F, 2.0f, 8, 25, 2
    );
    @Unique
    private static EnumToolMaterial NMBLOOD = addCustomMaterial(
            "GILDEDIRON",
            3, 904, 3F, 2.0f, 8, 25, 2
    );

    @Invoker("<init>")
    public static EnumToolMaterial theExpansionInit(String internalName, int internalId, int level, int uses, float eff, float attack, int enchant, int ic, int im) {
        throw new AssertionError();
    }

    private static EnumToolMaterial addCustomMaterial(String internalName, int level, int uses, float eff, float attack, int enchant, int ic, int im) {
        ArrayList<EnumToolMaterial> variants = new ArrayList<>(Arrays.asList(EnumToolMaterialMixin.$VALUES));
        EnumToolMaterial instrument = theExpansionInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, level,uses,eff,attack,enchant,ic,im);
        variants.add(instrument);
        EnumToolMaterialMixin.$VALUES = variants.toArray(new EnumToolMaterial[0]);
        return instrument;
    }

    @Override
    public EnumToolMaterial getCopperMaterial() {
        return COPPER;
    }

    @Override
    public EnumToolMaterial getBoneMaterial() {
        return BONE;
    }

    @Override
    public EnumToolMaterial getRustIronMaterial() {
        return RUSTIRON;
    }

    @Override
    public EnumToolMaterial getCorrodedCopperMaterial() {
        return CORRODEDCOPPER;
    }

    @Override
    public EnumToolMaterial getTippedDiamondMaterial() {
        return DIAMONDTIP;
    }

    @Override
    public EnumToolMaterial getDulledGoldMaterial() {
        return DULLEDGOLD;
    }

    @Override
    public EnumToolMaterial getGildedIronMaterial() {
        return GILDEDIRON;
    }

    @Override
    public EnumToolMaterial getCobaltzureMaterial() {
        return COBALTZURE;
    }

    @Override
    public EnumToolMaterial getSteelMaterial() {
        return STEEL;
    }

    @Override
    public EnumToolMaterial getNmBlood() {
        return NMBLOOD;
    }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 4), index = 4)
    private static float modifyGoldEfficiency(float par5) {
        return 7f; }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 4), index = 3)
    private static int modifyGoldMaxUses(int par2) {
        return 50; }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 1), index = 3)
    private static int modifyStoneMaxUses(int par2) {
        return 75; }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 2), index = 2)
    private static int modifyIronLevel(int par2) {
        return 3; }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 2), index = 4)
    private static float modifyIronEfficiency(float par5) {
        return 3f; }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 3), index = 2)
    private static int modifyDiamondLevel(int par2) {
        return 4; }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 3), index = 4)
    private static float modifyDiamondEfficiency(float par5) {
        return 4f; }

    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 5), index = 2)
    private static int modifySfsLevel(int par2) {
        return 10; }
    @ModifyArg(method = "<clinit>", at = @At(value = "INVOKE", target = "Lnet/minecraft/src/EnumToolMaterial;<init>(Ljava/lang/String;IIIFFIII)V",
            ordinal = 5), index = 4)
    private static float modifySfsEfficiency(float par5) {
        return 6f; }


}