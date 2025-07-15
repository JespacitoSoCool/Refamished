package net.fabricmc.refamished.misc;

import btw.BTWMod;
import btw.client.network.packet.handler.CustomEntityPacketHandler;
import btw.client.network.packet.handler.GuiPacketHandler;
import btw.entity.CanvasEntity;
import btw.inventory.BTWContainers;
import btw.network.packet.HardcoreSpawnPacket;
import btw.network.packet.PlayerSyncPacket;
import btw.network.packet.StartBlockHarvestPacket;
import btw.network.packet.TimerSpeedPacket;
import btw.network.packet.handler.CustomPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class RefamishedGuiPacketHandler implements CustomPacketHandler {
    @Override
    public void handleCustomPacket(Packet250CustomPayload packet, EntityPlayer player) throws IOException {
        Minecraft mcInstance = Minecraft.getMinecraft();
        WorldClient world = mcInstance.theWorld;
        DataInputStream dataStream = new DataInputStream(new ByteArrayInputStream(packet.data));
        int windowID = dataStream.readInt();
        int containerID = dataStream.readInt();
        GuiContainer gui = RefContains.getAssociatedGui((EntityClientPlayerMP)player, containerID);
        if (gui != null) {
            mcInstance.displayGuiScreen(gui);
            player.openContainer.windowId = windowID;
        }
    }
}
