package net.fabricmc.refamished.mixin;

import emi.shims.java.net.minecraft.util.Formatting;
import net.fabricmc.refamished.interfaces.EnumFormattingInterface;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.Arrays;

@Mixin(Formatting.class)
public class EnumFormattingMixin implements EnumFormattingInterface {
    @Shadow
    @Final
    @Mutable
    private static Formatting[] $VALUES;

    // Define the custom material
    @Unique
    private static Formatting DIAMOND_STEEL = addCustomMaterial("DIAMOND_STEEL", 'j');

    @Invoker("<init>")
    public static Formatting theExpansionInit(String internalName, int internalId, char c) {
        throw new AssertionError();
    }

    private static Formatting addCustomMaterial(String internalName, char c) {
        ArrayList<Formatting> variants = new ArrayList<>(Arrays.asList(EnumFormattingMixin.$VALUES));
        Formatting instrument = theExpansionInit(internalName, variants.get(variants.size() - 1).ordinal() + 1, c);
        variants.add(instrument);
        EnumFormattingMixin.$VALUES = variants.toArray(new Formatting[0]);
        return instrument;
    }

    @Override
    public Formatting getDiamondSteel() {
        return DIAMOND_STEEL;
    }

    @Inject(method = "getColorValue", at=@At("TAIL"), cancellable = true, remap = false)
    private void theExpansionInit(CallbackInfoReturnable<Integer> cir) {
        Formatting this_ = (Formatting)(Object)this;
        System.out.println("Returning custom color for "+this_.toString());
        if (this_ == DIAMOND_STEEL) {
            System.out.println("Returning custom color for DIAMOND_STEEL");
            cir.setReturnValue(0x2E5B57);
        }
    }
}