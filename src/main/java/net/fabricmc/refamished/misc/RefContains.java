package net.fabricmc.refamished.misc;

import btw.block.tileentity.*;
import btw.block.tileentity.dispenser.BlockDispenserTileEntity;
import btw.client.gui.*;
import btw.client.network.packet.handler.CustomEntityPacketHandler;
import btw.entity.CanvasEntity;
import btw.network.packet.HardcoreSpawnPacket;
import btw.network.packet.PlayerSyncPacket;
import btw.network.packet.StartBlockHarvestPacket;
import btw.network.packet.TimerSpeedPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.entities.tiles.copperAnvilTile;
import net.fabricmc.refamished.entities.tiles.steelAnvilTile;
import net.fabricmc.refamished.entities.tiles.stoneAnvilTile;
import net.fabricmc.refamished.gui.StoneAnvilGui;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class RefContains {
    public static int stoneAnvil = 600;
    public static int copperAnvil = 601;
    public static int steelAnvil = 602;

    @Environment(value=EnvType.CLIENT)
    public static GuiContainer getAssociatedGui(EntityClientPlayerMP entityclientplayermp, int containerID) {
        if (containerID == stoneAnvil) {
            stoneAnvilTile cauldronEntity = new stoneAnvilTile();
            return new StoneAnvilGui(entityclientplayermp.inventory, cauldronEntity);
        }
        else if (containerID == copperAnvil) {
            copperAnvilTile cauldronEntity = new copperAnvilTile();
            return new StoneAnvilGui(entityclientplayermp.inventory, cauldronEntity);
        }
        else if (containerID == steelAnvil) {
            steelAnvilTile cauldronEntity = new steelAnvilTile();
            return new StoneAnvilGui(entityclientplayermp.inventory, cauldronEntity);
        }
        return null;
    }
}
