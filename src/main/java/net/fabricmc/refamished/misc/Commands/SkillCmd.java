package net.fabricmc.refamished.misc.Commands;

import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.WrongUsageException;

public class SkillCmd extends CommandBase {
    @Override
    public String getCommandName() {
        return "skill"; // The command players will type
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/skill <player> <skill> <amount>";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args.length < 2) {
            throw new WrongUsageException(getCommandUsage(sender));
        }

        if (sender instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) sender;
            String skillName = args[0];

            int amount;
            try {
                amount = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                player.addChatMessage("Invalid number amount");
                return;
            }

            modifyPlayerSkill(player, skillName, amount);
            player.addChatMessage("Given " + amount + " experience to for " + skillName);
        }
    }

    private void modifyPlayerSkill(EntityPlayer player, String skillName, int amount) {
        //player.addChatMessage("🎮 " + amount + " points added to " + skillName);
        SkillManager.addExperience(player,skillName, amount);
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 1; // OP-only command
    }
}