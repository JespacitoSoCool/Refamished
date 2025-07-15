package net.fabricmc.refamished.mixin;

import btw.entity.CorpseEyeEntity;
import net.fabricmc.refamished.entities.*;
import net.fabricmc.refamished.mixin.interfaces.NetClientHandlerInterface;
import net.minecraft.src.*;
import net.fabricmc.refamished.skill.SkillPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetClientHandler.class)
public abstract class NetClientHandlerMixin {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onHandleCustomPayload(Packet250CustomPayload packet, CallbackInfo ci) {
        if ("SkillUpdateChannel".equals(packet.channel)) {
            SkillPacketHandler.handleClientPacket(packet);
            ci.cancel();
        }
    }
    @Inject(method = "handleVehicleSpawn", at = @At("HEAD"), cancellable = true)
    private void what(Packet23VehicleSpawn par1Packet23VehicleSpawn, CallbackInfo ci) {
        double var2 = (double)par1Packet23VehicleSpawn.xPosition / 32.0;
        double var4 = (double)par1Packet23VehicleSpawn.yPosition / 32.0;
        double var6 = (double)par1Packet23VehicleSpawn.zPosition / 32.0;
        Entity var8 = null;
        NetClientHandler theReal = (NetClientHandler)(Object)this;
        NetClientHandlerInterface interballs = (NetClientHandlerInterface)theReal;
        if (par1Packet23VehicleSpawn.type == EntityIncendiaryArrow.getVehicleSpawnPacketType()) {
            var8 = new EntityIncendiaryArrow(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == EntityFlintArrow.getVehicleSpawnPacketType()) {
            var8 = new EntityFlintArrow(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == EntityGoldArrow.getVehicleSpawnPacketType()) {
            var8 = new EntityGoldArrow(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == EntityFrostArrow.getVehicleSpawnPacketType()) {
            var8 = new EntityFrostArrow(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == EntityBoneBolt.getVehicleSpawnPacketType()) {
            var8 = new EntityBoneBolt(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == EntityCopperBolt.getVehicleSpawnPacketType()) {
            var8 = new EntityCopperBolt(interballs.getClient(), var2, var4, var6);
        }
        else if (par1Packet23VehicleSpawn.type == 3374) {
            var8 = new EntityMolotov(interballs.getClient(), var2, var4, var6);
        }
//        else if (par1Packet23VehicleSpawn.type == EntitySKOrb.getVehicleSpawnPacketType()) {
//            var8 = new EntitySKOrb(interballs.getClient(), var2, var4, var6);
//        }
        if (var8 != null) {
            ((Entity)var8).serverPosX = par1Packet23VehicleSpawn.xPosition;
            ((Entity)var8).serverPosY = par1Packet23VehicleSpawn.yPosition;
            ((Entity)var8).serverPosZ = par1Packet23VehicleSpawn.zPosition;
            ((Entity)var8).rotationPitch = (float)(par1Packet23VehicleSpawn.pitch * 360) / 256.0f;
            ((Entity)var8).rotationYaw = (float)(par1Packet23VehicleSpawn.yaw * 360) / 256.0f;
            Entity[] var12 = ((Entity)var8).getParts();
            if (var12 != null) {
                int var10 = par1Packet23VehicleSpawn.entityId - ((Entity)var8).entityId;
                for (int var11 = 0; var11 < var12.length; ++var11) {
                    var12[var11].entityId += var10;
                }
            }
            ((Entity)var8).entityId = par1Packet23VehicleSpawn.entityId;
            interballs.getClient().addEntityToWorld(par1Packet23VehicleSpawn.entityId, var8);
            if (par1Packet23VehicleSpawn.throwerEntityId > 0) {
                Entity var13;
                if (var8 instanceof EntityArrow && (var13 = getEntityByID(par1Packet23VehicleSpawn.throwerEntityId)) instanceof EntityLivingBase) {
                    EntityArrow var14 = (EntityArrow)var8;
                    var14.shootingEntity = var13;
                }
                ((Entity)var8).setVelocity((double)par1Packet23VehicleSpawn.speedX / 8000.0, (double)par1Packet23VehicleSpawn.speedY / 8000.0, (double)par1Packet23VehicleSpawn.speedZ / 8000.0);
            }
        }
    }

    private Entity getEntityByID(int par1) {
        NetClientHandler theReal = (NetClientHandler)(Object)this;
        NetClientHandlerInterface interballs = (NetClientHandlerInterface)theReal;
        return par1 == interballs.getMc().thePlayer.entityId ? interballs.getMc().thePlayer : interballs.getClient().getEntityByID(par1);
    }
}