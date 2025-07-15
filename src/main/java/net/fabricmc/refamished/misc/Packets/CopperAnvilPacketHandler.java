package net.fabricmc.refamished.misc.Packets;

import btw.network.packet.handler.CustomPacketHandler;
import net.fabricmc.refamished.entities.tiles.copperAnvilContainer;
import net.fabricmc.refamished.entities.tiles.copperAnvilTile;
import net.fabricmc.refamished.entities.tiles.stoneAnvilContainer;
import net.fabricmc.refamished.entities.tiles.stoneAnvilTile;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Packet250CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

public class CopperAnvilPacketHandler implements CustomPacketHandler {

    @Override
    public void handleCustomPacket(Packet250CustomPayload packet, EntityPlayer player) {
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(packet.data));
        try {
            byte packetId = input.readByte();

            if (packetId == 0) {
                boolean success = input.readBoolean();

                if (player.openContainer instanceof copperAnvilContainer) {
                    copperAnvilContainer container = (copperAnvilContainer) player.openContainer;
                    copperAnvilTile anvil = container.tile;

                    ItemStack hammer = container.getSlot(0).getStack();
                    if (hammer != null) {
                        //hammer.setItemDamage(hammer.getItemDamage()+1);
                        //hammer.damageItem(hammer.getItemDamage()+1,player);
                        if (hammer.getItemDamage() >= hammer.getMaxDamage() || hammer.stackSize <= 0) {
                            container.putStackInSlot(0, null);
                        }
                    }

                    anvil.applyMinigameResult(success,player,container, container, anvil);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
