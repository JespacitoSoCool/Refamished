package net.fabricmc.refamished.mixin;

import btw.BTWMod;
import btw.world.util.difficulty.Difficulties;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.refamished.mixin.interfaces.GuiScreenInterface;
import net.fabricmc.refamished.mixin.interfaces.GuiCreateWorldInterface;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.minecraft.src.*;
import org.lwjgl.Sys;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(GuiCreateWorld.class)
public abstract class GuiCreateWorldMixin {
    @Shadow protected abstract void updateButtonText();

    @Unique
    String oldSeed = "";
    Boolean chestEnabled = false;
    Boolean cheatsEnabled = false;
    Difficulty oldDif = null;

    @Inject(method = "drawScreen", at = @At("TAIL"))
    private void onDrawScreen(int par1, int par2, float par3, CallbackInfo ci) {
        GuiCreateWorld idk = (GuiCreateWorld) (Object) this;
        int id = ((GuiCreateWorldInterface) this).getId();
        boolean options = ((GuiCreateWorldInterface) this).getOptions();
        GuiCreateWorldInterface inter = ((GuiCreateWorldInterface) this);
        FontRenderer font = ((GuiScreenInterface) this).getFontRenderer();
        Difficulty selectedDifficulty = Difficulties.DIFFICULTY_LIST.get(id);
        boolean changedDifficulty = oldDif != selectedDifficulty;

        if (selectedDifficulty instanceof DifficultyCruel) {
            if (changedDifficulty)
            {
                if (!MathHelper.stringNullOrLengthZero(inter.getTextboxSeed().getText())) {
                    oldSeed = inter.getTextboxSeed().getText();
                }
                chestEnabled = inter.bonusItems();
                cheatsEnabled = inter.commandsAllowed();
            }
            long forcedSeed = new Random().nextLong();
            inter.getTextboxSeed().setText(String.valueOf(forcedSeed));
            inter.setBonusItems(false);
            inter.setCommandValue(false);
        }
        else
        {
            if (changedDifficulty) {
                inter.getTextboxSeed().setText(String.valueOf(oldSeed));
                oldSeed = "";
                inter.setBonusItems(chestEnabled);
                inter.setCommandValue(cheatsEnabled);
            }
        }
        if (options) {
            if (selectedDifficulty instanceof DifficultyCruel) {
                font.drawString(I18n.getString("difficulty.cruel.seed"), idk.width / 2 - 100, 35, 0xA0A0A0);
                font.drawString(I18n.getString("difficulty.cruel.command"), idk.width / 2 - 150, 140, 0xA0A0A0);
                font.drawString(I18n.getString("difficulty.cruel.chest"), idk.width / 2 + 10, 172, 0xA0A0A0);
            }
        }
        else {
            if (selectedDifficulty instanceof DifficultyCruel) {
                font.drawString(I18n.getString("difficulty.cruel.extra1"), idk.width / 2 + 7, 161, 0xA0A0A0);
                font.drawString(I18n.getString("difficulty.cruel.extra2"), idk.width / 2 + 7, 173, 0xA0A0A0);
            }
        }
        updateButtonText();
        oldDif = selectedDifficulty;
    }

    @Inject(method = "actionPerformed", at = @At("HEAD"), cancellable = true)
    private void actionPerformed(GuiButton par1GuiButton, CallbackInfo ci) {
        GuiCreateWorld idk = (GuiCreateWorld) (Object) this;
        GuiCreateWorldInterface inter = ((GuiCreateWorldInterface) this);
        int id = ((GuiCreateWorldInterface) this).getId();
        Difficulty selectedDifficulty = Difficulties.DIFFICULTY_LIST.get(id);
        if (selectedDifficulty instanceof DifficultyCruel) {
            if (par1GuiButton.enabled) {
                if (par1GuiButton.id == 1) {
                } else if (par1GuiButton.id == 0) {
                    long forcedSeed = new Random().nextLong();
                    inter.getTextboxSeed().setText(String.valueOf(forcedSeed));
                    inter.setBonusItems(false);
                    inter.setCommandValue(false);
                } else if (par1GuiButton.id == 9) {
                    ci.cancel();
                    inter.setId(inter.getId()+1);
                    if (!(inter.getId() >= Difficulties.DIFFICULTY_LIST.size()) && Difficulties.DIFFICULTY_LIST.get(inter.getId()).isRestricted()) {
                        inter.setId(inter.getId()+1);;
                        System.out.println("Skipped");
                    }
                    if (inter.getId() >= Difficulties.DIFFICULTY_LIST.size()) {
                        inter.setId(0);
                    }
                    this.updateButtonText();
                }
            }
        }
    }
}
