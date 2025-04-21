package net.fabricmc.refamished.misc;

import btw.BTWMod;
import btw.client.network.packet.handler.*;
import btw.entity.*;
import btw.entity.mechanical.platform.BlockLiftedByPlatformEntity;
import btw.entity.mechanical.source.VerticalWindMillEntity;
import btw.entity.mechanical.source.WaterWheelEntity;
import btw.entity.mechanical.source.WindMillEntity;
import btw.network.packet.*;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.Packet;
import net.minecraft.src.Packet24MobSpawn;

import java.util.List;

public class RefamishedPacketManager {

    public static final String SPAWN_CUSTOM_ENTITY_PACKET_CHANNEL = "re|SE";
    public static final String CUSTOM_ENTITY_EVENT_PACKET_CHANNEL = "re|EV";
    public static final String BTW_OPTIONS_PACKET_CHANNEL = "re|OP";
    public static final String BTW_DIFFICULTY_PACKET_CHANNEL = "re|DF";
    public static final String CUSTOM_INTERFACE_PACKET_CHANNEL = "re|OI";

    public static void initPacketInfo() {
        if (!MinecraftServer.getIsServer()) {
            RefamishedPacketManager.initClientPacketInfo();
        }
        Packet.addIdClassMapping(166, false, true, StartBlockHarvestPacket.class);
        Packet.addIdClassMapping(21, true, true, PlayerSyncPacket.class);
        Packet.addIdClassMapping(167, true, false, TimerSpeedPacket.class);
        Packet.addIdClassMapping(168, true, false, HardcoreSpawnPacket.class);
    }

    @Environment(value= EnvType.CLIENT)
    private static void initClientPacketInfo() {
        //BTWMod.instance.registerPacketHandler(SPAWN_CUSTOM_ENTITY_PACKET_CHANNEL, new CustomEntityPacketHandler());
        //BTWMod.instance.registerPacketHandler(CUSTOM_ENTITY_EVENT_PACKET_CHANNEL, new EntityEventPacketHandler());
        //BTWMod.instance.registerPacketHandler(BTW_OPTIONS_PACKET_CHANNEL, new BTWOptionsPacketHandler());
        //BTWMod.instance.registerPacketHandler(BTW_DIFFICULTY_PACKET_CHANNEL, new BTWDifficultyPacketHandler());
        //BTWMod.instance.registerPacketHandler(CUSTOM_INTERFACE_PACKET_CHANNEL, new GuiPacketHandler());
        RefamishedPacketManager.initEntitySpawnEntries();
    }

    @Environment(value=EnvType.CLIENT)
    private static void initEntitySpawnEntries() {
        CustomEntityPacketHandler.entryMap.put(1, (world, dataStream, packet) -> {
            int x = dataStream.readInt();
            int y = dataStream.readInt();
            int z = dataStream.readInt();
            int direction = dataStream.readInt();
            int artNum = dataStream.readInt();
            return new CanvasEntity(world, x, y, z, direction, artNum);
        });

    }
}
