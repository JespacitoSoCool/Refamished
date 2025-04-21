package net.fabricmc.refamished.misc.Commands;

import btw.AddonHandler;
import btw.command.ServerLocCommand;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class CmdHandler extends CommandHandler implements IAdminCommand {

    public void ServerCommandManager() {
        this.registerCommand((ICommand) new CmdHandler());
        if (MinecraftServer.getServer().isDedicatedServer()) {

        } else {

        }
        if (!MinecraftServer.getIsServer()) {
            for (ICommand command : AddonHandler.commandList) {
                this.registerCommand(command);
            }
        }
        CommandBase.setAdminCommander(this);
    }
    @Override
    public void notifyAdmins(ICommandSender par1ICommandSender, int par2, String par3Str, Object ... par4ArrayOfObj) {
        boolean var5 = true;
        if (par1ICommandSender instanceof TileEntityCommandBlock && !MinecraftServer.getServer().worldServers[0].getGameRules().getGameRuleBooleanValue("commandBlockOutput")) {
            var5 = false;
        }
        ChatMessageComponent var6 = ChatMessageComponent.createFromTranslationWithSubstitutions("chat.type.admin", par1ICommandSender.getCommandSenderName(), ChatMessageComponent.createFromTranslationWithSubstitutions(par3Str, par4ArrayOfObj));
        var6.setColor(EnumChatFormatting.GRAY);
        var6.setItalic(true);
        if (var5) {
            for (Object var8 : MinecraftServer.getServer().getConfigurationManager().playerEntityList) {
                EntityPlayerMP plr = (EntityPlayerMP) var8;
                if (var8 == par1ICommandSender || !MinecraftServer.getServer().getConfigurationManager().isPlayerOpped(plr.getCommandSenderName())) continue;
                plr.sendChatToPlayer(var6);
            }
        }
        if (par1ICommandSender != MinecraftServer.getServer()) {
            MinecraftServer.getServer().sendChatToPlayer(var6);
        }
        if ((par2 & 1) != 1) {
            par1ICommandSender.sendChatToPlayer(ChatMessageComponent.createFromTranslationWithSubstitutions(par3Str, par4ArrayOfObj));
        }
    }
}
