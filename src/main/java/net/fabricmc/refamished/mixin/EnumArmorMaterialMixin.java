package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.interfaces.EnumArmorMaterialInterface;
import net.minecraft.src.EnumArmorMaterial;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(EnumArmorMaterial.class)
public class EnumArmorMaterialMixin implements EnumArmorMaterialInterface {
    @Shadow
    @Final
    @Mutable
    private static EnumArmorMaterial[] $VALUES;

    // Define the custom material
    @Unique
    private static EnumArmorMaterial COPPER = addCustomMaterial("COPPER", 10, new int[]{2, 5, 4, 2}, 10);
    @Unique
    private static EnumArmorMaterial RUSTIRON = addCustomMaterial("RUSTIRON", 9, new int[]{1, 4, 3, 1}, 2);
    @Unique
    private static EnumArmorMaterial DULLEDGOLD = addCustomMaterial("DULLEDGOLD", 6, new int[]{1, 4, 3, 1}, 1);
    @Unique
    private static EnumArmorMaterial STEEL = addCustomMaterial("STEEL", 25, new int[]{2, 7, 6, 2}, 10);

    @Invoker("<init>")
    public static EnumArmorMaterial theExpansionInit(String internalName, int internalId, int par3, int[] par4ArrayOfInteger, int par5) {
        throw new AssertionError();
    }

    private static EnumArmorMaterial addCustomMaterial(String internalName, int par3, int[] par4ArrayOfInteger, int par5) {
        ArrayList<EnumArmorMaterial> variants = new ArrayList<>(Arrays.asList(EnumArmorMaterialMixin.$VALUES));
        EnumArmorMaterial instrument = theExpansionInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, par3,par4ArrayOfInteger,par5);
        variants.add(instrument);
        EnumArmorMaterialMixin.$VALUES = variants.toArray(new EnumArmorMaterial[0]);
        return instrument;
    }

    @Override
    public EnumArmorMaterial getCopperMaterial() {
        return COPPER;
    }
    @Override
    public EnumArmorMaterial getRustIronMaterial() {
        return RUSTIRON;
    }
    @Override
    public EnumArmorMaterial getDulledGoldMaterial() {
        return DULLEDGOLD;
    }
    @Override
    public EnumArmorMaterial getSteelMaterial() {
        return STEEL;
    }
}