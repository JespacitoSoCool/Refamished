   package net.fabricmc.refamished.mixin;

   import btw.item.BTWItems;
   import net.fabricmc.refamished.RefamishedItems;
   import net.fabricmc.refamished.RefamishedMod;
   import net.fabricmc.refamished.skill.SkillRecipeStarter;
   import net.minecraft.src.Minecraft;
   import org.spongepowered.asm.mixin.Mixin;
   import org.spongepowered.asm.mixin.Shadow;
   import org.spongepowered.asm.mixin.injection.At;
   import org.spongepowered.asm.mixin.injection.Inject;
   import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

   @Mixin(Minecraft.class)
   public class MinecraftMixin {

       @Shadow
       private net.minecraft.src.GameSettings gameSettings;

       /**
        * Inject into Minecraft's startGame method to initialize key bindings
        */
       @Inject(method = "startGame", at = @At("RETURN"))
       private void startGameInject(CallbackInfo ci) {
           // Call the method in your main mod class to initialize key bindings.
           RefamishedMod.registerKeyBindings(gameSettings);
           System.out.println("RefamishedMod: Keybindings initialized in startGame method!");
           SkillRecipeStarter the = new SkillRecipeStarter();
           the.begin();
       }

       /**
        * Inject into the runTick method to handle custom key presses during game ticks.
        */
       @Inject(method = "runTick", at = @At("HEAD"))
       private void runTickInject(CallbackInfo ci) {
           RefamishedMod.handleKeyPress(); // Run key press logic for each tick
       }
   }