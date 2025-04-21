package net.fabricmc.refamished.mixin.mixin_m;

import btw.item.BTWItems;
import btw.world.util.difficulty.Difficulty;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityZombie.class)
public class ZombieMixin {
    @Unique
    ItemStack[] itemOptions = {
            new ItemStack(RefamishedItems.rustIronSword),
            new ItemStack(RefamishedItems.rustIronPickaxe),
            new ItemStack(RefamishedItems.rustIronAxe),
            new ItemStack(RefamishedItems.rustIronShovel),
            new ItemStack(RefamishedItems.rustIronHoe),
            new ItemStack(RefamishedItems.rustIronMachete),
            new ItemStack(RefamishedItems.corrodedCopperSword),
            new ItemStack(RefamishedItems.corrodedCopperPickaxe),
            new ItemStack(RefamishedItems.corrodedCopperAxe),
            new ItemStack(RefamishedItems.corrodedCopperShovel),
            new ItemStack(RefamishedItems.corrodedCopperHoe),
            new ItemStack(RefamishedItems.copperSword),
            new ItemStack(RefamishedItems.dulledGoldSword),
            new ItemStack(RefamishedItems.dulledGoldPickaxe),
            new ItemStack(RefamishedItems.dulledGoldAxe),
            new ItemStack(RefamishedItems.dulledGoldShovel),
            new ItemStack(RefamishedItems.dulledGoldHoe),
            new ItemStack(Item.axeStone),
            new ItemStack(RefamishedItems.ironBlade),
            new ItemStack(RefamishedItems.death_club[0]),
            new ItemStack(RefamishedItems.death_club[1]),
            new ItemStack(RefamishedItems.death_club[2]),
            new ItemStack(RefamishedItems.death_club[3]),
            new ItemStack(RefamishedItems.heavy_club),
    };

    @Unique
    private ItemStack cruelTool()
    {
        Random random = new Random();
        int randomIndex = random.nextInt(itemOptions.length);
        return itemOptions[randomIndex];
    }
    @Inject(method = "getSpeedModifier",at = @At("RETURN"),cancellable = true)
    private void SpeedThing(CallbackInfoReturnable<Float> cir) {
        EntityZombie the = (EntityZombie)(Object)this;
        if (the.worldObj.getDifficulty() == RefamishedMod.CRUEL) {
            cir.setReturnValue(cir.getReturnValue()*1.4F);
        }
    }
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
                ToolQuality quality = ToolQuality.getRandomNegativeQuality();
                select.getTagCompound().setString("ToolQuality", quality.getName());
                ((LivingInterface) the).getEquipmentDropChances()[0] = 0.05f;
            }
            Liver.setArmor();
            ci.cancel();
        }
    }
    @Inject(method = "attackEntityAsMob",at = @At("HEAD"),cancellable = true)
    private void dropItems(Entity attackedEntity, CallbackInfoReturnable<Boolean> cir) {
        EntityZombie the = (EntityZombie)(Object)this;
        LivingInterface Liver = (LivingInterface)(Object)this;
        if (the.worldObj.getDifficulty() == RefamishedMod.CRUEL) {
            int hunger = the.worldObj.rand.nextInt(5)+10;
            int poison = the.worldObj.rand.nextInt(5)+5;

            ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( Potion.hunger.id, hunger * 20, 0 ) );
            ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( Potion.poison.id, poison * 20, 0 ) );
            ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( RefamishedMod.INFESTEDWOUND.id, 180 * 20, 0 ) );
        }
    }

    @Inject(method = "attackEntityAsMob", at = @At("HEAD"), cancellable = true)
    private void injectToolDamage(Entity target, CallbackInfoReturnable<Boolean> cir) {
        EntityZombie zombie = (EntityZombie)(Object)this;

        double baseDamage = zombie.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
        double toolDamage = 0.0;

        // Get held item attack damage
        ItemStack heldItem = zombie.getHeldItem();
        if (heldItem != null) {
            Multimap<String, AttributeModifier> modifiers = heldItem.getAttributeModifiers();

            if (modifiers.containsKey(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                for (AttributeModifier mod : modifiers.get(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName())) {
                    toolDamage += mod.getAmount();
                }
            }
        }

        float totalDamage = (float)(baseDamage + toolDamage);

        boolean attacked = target.attackEntityFrom(DamageSource.causeMobDamage(zombie), totalDamage);
        cir.setReturnValue(attacked);
    }


}
