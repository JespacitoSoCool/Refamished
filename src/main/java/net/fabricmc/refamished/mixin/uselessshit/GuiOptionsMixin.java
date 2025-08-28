package net.fabricmc.refamished.mixin.uselessshit;

import btw.BTWMod;
import net.fabricmc.refamished.gui.GuiRefamishedOptions;
import net.fabricmc.refamished.mixin.interfaces.GuiScreenInterface;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiOptions.class)
public class GuiOptionsMixin {

	@Inject(at = @At("HEAD"), method = "initGui", remap = false)
	private void AAAAA(CallbackInfo info) {
		GuiOptions ts = (GuiOptions)(Object)this;
		GuiScreenInterface screen = (GuiScreenInterface)(Object)this;
		screen.getButtonList().add(new GuiButton(201, 2, ts.height -22, I18n.getString("gui.refamished.options")));
	}

	@Inject(at = @At("HEAD"), method = "actionPerformed", remap = false)
	private void CALL(GuiButton par1GuiButton, CallbackInfo ci) {
		GuiOptions ts = (GuiOptions)(Object)this;
		GuiScreenInterface screen = (GuiScreenInterface)(Object)this;
		Minecraft mc = screen.getMc();
		if (par1GuiButton.id == 201) {
			mc.gameSettings.saveOptions();
			mc.displayGuiScreen(new GuiRefamishedOptions(ts));
		}
	}
}
