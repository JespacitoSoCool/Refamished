package net.fabricmc.refamished.mixin;

import net.minecraft.src.NetServerHandler;
import net.minecraft.src.Packet250CustomPayload;
import net.fabricmc.refamished.skill.SkillPacketHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NetServerHandler.class)
public abstract class NetServerHandlerMixin {
    @Inject(method = "handleCustomPayload", at = @At("HEAD"), cancellable = true)
    private void onHandleCustomPayload(Packet250CustomPayload packet, CallbackInfo ci) {
        if ("SkillUpdateChannel".equals(packet.channel)) {
            SkillPacketHandler.handleServerPacket(packet);
            ci.cancel();
        }
    }
}