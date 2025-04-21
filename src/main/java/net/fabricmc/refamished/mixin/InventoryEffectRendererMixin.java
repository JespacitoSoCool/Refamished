package net.fabricmc.refamished.mixin;

import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.Potion.RePotion;
import net.fabricmc.refamished.mixin.interfaces.GuiScreenRendererInterface;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(InventoryEffectRenderer.class)
public class InventoryEffectRendererMixin {
    @Unique
    private static final ResourceLocation mcField = new ResourceLocation("textures/gui/container/inventory.png");
    @Unique
    private static final ResourceLocation reField = new ResourceLocation("refamished:textures/gui/reffects.png");

    @Inject(method = "displayDebuffEffects",at = @At("RETURN"), cancellable = true)
    private void SpeedThing(CallbackInfo ci) {
        InventoryEffectRenderer renderer = (InventoryEffectRenderer)(Object)this;
        GuiScreenRendererInterface uh = (GuiScreenRendererInterface)renderer;
        Minecraft mc = uh.getMc();
        int n = renderer.getGuiLeft() - 124;
        int n2 = renderer.getGuiTop();
        int n3 = 166;
        Collection collection = mc.thePlayer.getActivePotionEffects();
        if (collection.isEmpty()) {
            return;
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glDisable(2896);
        int n4 = 33;
        if (collection.size() > 5) {
            n4 = 132 / (collection.size() - 1);
        }
        for (Object o : mc.thePlayer.getActivePotionEffects()) {
            PotionEffect potionEffect = (PotionEffect) o;
            Potion potion = Potion.potionTypes[potionEffect.getPotionID()];
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

            if (potionEffect.getPotionID() >= 100) //Above 100 is modded effects
            {
                mc.getTextureManager().bindTexture(reField);
                renderer.drawTexturedModalRect(n, n2, 0, 0, 140, 32);
                    RePotion repot = (RePotion)potion;
                    int n5 = potion.getStatusIconIndex();
                    int xp = repot.getGridX() * 18;
                    int yp = (repot.getGridY()* 18) + 32;

                    renderer.drawTexturedModalRect(n + 6, n2 + 7, xp, yp, 18, 18);  // Draw the icon
            }
            else //Vanilla Effects
            {
                mc.getTextureManager().bindTexture(mcField);
                renderer.drawTexturedModalRect(n, n2, 0, 166, 140, 32);
                if (potion.hasStatusIcon()) {
                    int n5 = potion.getStatusIconIndex();
                    renderer.drawTexturedModalRect(n + 6, n2 + 7, 0 + n5 % 8 * 18, 198 + n5 / 8 * 18, 18, 18);
                }
            }

            String string = I18n.getString(potion.getName());
            int amp = potionEffect.getAmplifier();
            if (amp == 1) {
                string = string + " II";
            } else if (amp == 2) {
                string = string + " III";
            } else if (amp == 3) {
                string = string + " IV";
            } else if (amp == 4) {
                string = string + " V";
            } else if (amp == 5) {
                string = string + " VI";
            } else if (amp == 6) {
                string = string + " VII";
            } else if (amp == 7) {
                string = string + " VIII";
            } else if (amp == 8) {
                string = string + " IX";
            } else if (amp == 9) {
                string = string + " X";
            }
            mc.fontRenderer.drawStringWithShadow(string, n + 10 + 18, n2 + 6, 0xFFFFFF);
            String duration = Potion.getDurationString(potionEffect);
            mc.fontRenderer.drawStringWithShadow(duration, n + 10 + 18, n2 + 6 + 10, 0x7F7F7F);
            n2 += n4;
        }
        ci.cancel();
    }
}
