package net.fabricmc.refamished.misc.Commands;

import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class PanoramaCmd extends CommandBase {
    @Override
    public String getCommandName() {
        return "panorama";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/panorama";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        Minecraft mc = Minecraft.getMinecraft();
        PanoramaHandler.start();
    }

}