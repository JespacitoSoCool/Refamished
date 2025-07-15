package net.fabricmc.refamished.mixin.mixin_m;

import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.UUID;

@Mixin(EntityPigZombie.class)
public class PigZombieMixin {
    @Unique
    ItemStack[] itemOptions = {
            new ItemStack(RefamishedItems.dulledGoldSword),
            new ItemStack(RefamishedItems.dulledGoldPickaxe),
            new ItemStack(RefamishedItems.dulledGoldAxe),
            new ItemStack(RefamishedItems.dulledGoldShovel),
            new ItemStack(RefamishedItems.dulledGoldHoe),
    };

    @Unique
    private ItemStack cruelTool()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(itemOptions.length);
        return itemOptions[randomIndex];
    }

    @Shadow
    private Entity field_110191_bu;
    @Shadow
    private static AttributeModifier field_110190_br;
    @Shadow
    private static UUID field_110189_bq;
    private static AttributeModifier oldField = null;

    @Inject(method = "addRandomArmor",at = @At("HEAD"),cancellable = true)
    private void dropItems(CallbackInfo ci) {
        EntityZombie the = (EntityZombie)(Object)this;
        LivingInterface Liver = (LivingInterface)(Object)this;
        if (the.worldObj.getDifficulty() == RefamishedMod.CRUEL) {
            if (the.rand.nextFloat() < 0.8f) {
                ItemStack select = cruelTool();
                the.setCurrentItemOrArmor(0, select);
                if (!select.hasTagCompound()) {
                    select.setTagCompound(new NBTTagCompound());
                }
                ArmorQuality quality = ArmorQuality.getRandomNegativeQuality();
                select.getTagCompound().setString("ToolQuality", quality.getName());
                ((LivingInterface) the).getEquipmentDropChances()[0] = 0.05f;
            }
            Liver.setArmor();
            ci.cancel();
        }
    }

    @Inject(method = "onUpdate",at = @At("TAIL"),cancellable = true)
    private void SpeedThing(CallbackInfo ci) {
        if ((Object)this instanceof EntityPigZombie zombie) {
            if (zombie.worldObj.getDifficulty() == RefamishedMod.CRUEL) {
                float maxHealth = zombie.getMaxHealth();
                float currentHealth = zombie.getHealth();

                float healthRatio = currentHealth / maxHealth;
                float speedMultiplier = 1f + ((1.0f - healthRatio) * 1);
                //System.out.println(speedMultiplier);
                AttributeInstance attributes = zombie.getEntityAttribute(SharedMonsterAttributes.movementSpeed);

                AttributeModifier newSpeed = new AttributeModifier(field_110189_bq, "Attacking speed boost", 0.55*speedMultiplier, 0).setSaved(false);
                if (oldField!= null) {attributes.removeModifier(oldField);}
                attributes.removeModifier(field_110190_br);
                attributes.applyModifier(newSpeed);
                oldField = newSpeed;
            }
        }
    }
}
