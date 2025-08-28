package net.fabricmc.refamished.misc.Packets;

import btw.AddonHandler;
import btw.client.network.packet.handler.*;
import btw.entity.*;
import btw.network.packet.handler.CustomPacketHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.MinecraftServer;

public class RefamishedPacketManager {

    public static final String SPAWN_CUSTOM_ENTITY_PACKET_CHANNEL = "re|SE";
    public static final String CUSTOM_ENTITY_EVENT_PACKET_CHANNEL = "re|EV";
    public static final String BTW_OPTIONS_PACKET_CHANNEL = "re|OP";
    public static final String BTW_DIFFICULTY_PACKET_CHANNEL = "re|DF";
    public static final String CUSTOM_INTERFACE_PACKET_CHANNEL = "re|OI";
    public static final String STONE_ANVIL_ = "re|LSS";
    public static final String COPPER_ANVIL_ = "re|LSC";
    public static final String STEEL_ANVIL_ = "re|LSST";
    public static final String FORGE_PLAN = "re|FP";

    public static void initPacketInfo() {
        if (!MinecraftServer.getIsServer()) {
            RefamishedPacketManager.initClientPacketInfo();
        }
        registerPacketHandler(STONE_ANVIL_, new StoneAnvilPacketHandler());
        registerPacketHandler(COPPER_ANVIL_, new CopperAnvilPacketHandler());
        registerPacketHandler(STEEL_ANVIL_, new SteelAnvilPacketHandler());
        registerPacketHandler(FORGE_PLAN, new ForgePlanPacketHandler());
        //Packet.addIdClassMapping(166, false, true, StartBlockHarvestPacket.class);
        //Packet.addIdClassMapping(21, true, true, PlayerSyncPacket.class);
        //Packet.addIdClassMapping(167, true, false, TimerSpeedPacket.class);
        //Packet.addIdClassMapping(168, true, false, HardcoreSpawnPacket.class);
    }

    @Environment(value= EnvType.CLIENT)
    private static void initClientPacketInfo() {
        //BTWMod.instance.registerPacketHandler(SPAWN_CUSTOM_ENTITY_PACKET_CHANNEL, new CustomEntityPacketHandler());
        //BTWMod.instance.registerPacketHandler(CUSTOM_ENTITY_EVENT_PACKET_CHANNEL, new EntityEventPacketHandler());
        //BTWMod.instance.registerPacketHandler(BTW_OPTIONS_PACKET_CHANNEL, new BTWOptionsPacketHandler());
        //BTWMod.instance.registerPacketHandler(BTW_DIFFICULTY_PACKET_CHANNEL, new BTWDifficultyPacketHandler());
        registerPacketHandler(CUSTOM_INTERFACE_PACKET_CHANNEL, new RefamishedGuiPacketHandler());
        //RefamishedPacketManager.initEntitySpawnEntries();
    }

    public static void registerPacketHandler(String channel, CustomPacketHandler handler) {
        AddonHandler.registerPacketHandler(channel, handler);
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
