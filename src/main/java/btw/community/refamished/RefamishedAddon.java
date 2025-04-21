package btw.community.refamished;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedEntities;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class RefamishedAddon extends BTWAddon {
    private static RefamishedAddon instance;
    public RefamishedAddon() {
        super();
        RefamishedMod.addMaterialOverride();
        RefamishedBlocks.registerBlocks();
        RefamishedItems.registerItems();
        RefamishedEntities.registerEntities();
        //If you are reading this, I didn't put this on initialize because it just crashes thanks to the recipes thing
    }
    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " + SBTW - Version " + this.getVersionString() + " Initializing...");
    }

    @Override
    @Environment(value= EnvType.SERVER)
    public void registerAddonCommandServerOnly(ICommand command) {
        super.registerAddonCommandServerOnly(command);
        //System.out.println("EXECUTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        if (MinecraftServer.getIsServer()) {
            //MinecraftServer.getServer().commandManager.registerCommand(new SkillCmd());
            //System.out.println("EXECUTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        }
    }
}