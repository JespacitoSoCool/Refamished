package net.fabricmc.refamished.misc.Commands;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.mixin.interfaces.MinecraftInterface;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.lwjgl.opengl.DisplayMode;

@Environment(value= EnvType.CLIENT)
public class PanoramaHandler {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final float[][] rotations = {
            {0f, 0f}, {90f, 0f}, {180f, 0f}, {-90f, 0f}, {0f, -90f}, {0f, 90f}
    };

    private static int index = -1;
    private static int delay = 0;
    private static Boolean isScreenShoot = false;
    private static int oldWidth = 0;
    private static int oldHeight = 0;
    private static float oldFov = 0;

    public static void start() {
        index = 0;
        delay = 10;
        System.out.println("Panorama capture started.");
        oldWidth = mc.displayWidth;
        oldHeight = mc.displayHeight;
        int squareSize = Math.min(oldWidth, oldHeight);
        mc.displayWidth = squareSize;
        mc.displayHeight = squareSize;
        Minecraft mc = Minecraft.getMinecraft();
        oldFov = mc.gameSettings.fovSetting;
        mc.gameSettings.fovSetting = 90.0F;
        ((MinecraftInterface)(Object)mc).re(mc.displayWidth, mc.displayHeight);
    }

    public static void tick() throws InterruptedException {
        if (index < 0) return;

        if (delay > 0) {
            delay--;
            return;
        }

        if (index < rotations.length) {
            if (isScreenShoot) {
                String fileName = "panorama_" + index + ".png";
                ScreenShotHelper.func_74292_a(mc.mcDataDir, fileName, mc.displayWidth, mc.displayHeight);
                System.out.println("Saved " + fileName);
                delay = 10;
                index++;
                isScreenShoot = false;
            }
            else {
                mc.thePlayer.rotationYaw = rotations[index][0];
                mc.thePlayer.rotationPitch = rotations[index][1];
                isScreenShoot = true;
                delay = 5;
            }
        } else {
            System.out.println("Panorama complete!");
            index = -1;
            ((MinecraftInterface)(Object)mc).re(oldWidth, oldHeight);
            mc.gameSettings.fovSetting = oldFov;
        }
    }
}
