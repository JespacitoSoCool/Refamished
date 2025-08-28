package net.fabricmc.refamished.misc.Packets;

import btw.network.packet.handler.CustomPacketHandler;
import net.fabricmc.refamished.misc.RefContains;
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
