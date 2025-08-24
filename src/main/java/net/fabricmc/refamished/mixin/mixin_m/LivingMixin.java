package net.fabricmc.refamished.mixin.mixin_m;

import btw.community.refamished.RefamishedAddon;
import btw.item.BTWItems;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.quality.ArmorQuality;
import net.fabricmc.refamished.quality.ToolQuality;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(EntityLiving.class)
public class LivingMixin {
    @Unique
    Item[] undeadHelmetOptions = {
            RefamishedItems.helmetRustIron,
            RefamishedItems.helmetDulledGold,
    };
    @Unique
    Item[] undeadChestplateOptions = {
            RefamishedItems.plateRustIron,
            RefamishedItems.plateDulledGold,
    };
    @Unique
    Item[] undeadLeggingsOptions = {
            RefamishedItems.legsRustIron,
            RefamishedItems.legsDulledGold,
    };
    @Unique
    Item[] undeadBootsOptions = {
            RefamishedItems.bootsRustIron,
            RefamishedItems.bootsDulledGold,
    };

    @Unique
    Item[] helmetOptions = {
            RefamishedItems.helmetCopper,
            Item.helmetChain,
    };
    @Unique
    Item[] chestplateOptions = {
            RefamishedItems.plateCopper,
            Item.plateChain,
    };
    @Unique
    Item[] leggingsOptions = {
            RefamishedItems.legsCopper,
            Item.legsChain,
    };
    @Unique
    Item[] bootsOptions = {
            RefamishedItems.bootsCopper,
            Item.bootsChain,
    };

    @Unique
    private Item getRandomStack(Item[] this_)
    {
        Random random = new Random();
        int randomIndex = random.nextInt(this_.length);
        return this_[randomIndex];
    }
    @Unique
    public Item getArmorForUh(int par0, int par1) {
        switch (par0) {
            case 4: {
                if (par1 == 20) {
                    return getRandomStack(helmetOptions);
                }
                return getRandomStack(undeadHelmetOptions);
            }
            case 3: {
                if (par1 == 20) {
                    return getRandomStack(chestplateOptions);
                }
                return getRandomStack(undeadChestplateOptions);
            }
            case 2: {
                if (par1 == 20) {
                    return getRandomStack(leggingsOptions);
                }
                return getRandomStack(undeadLeggingsOptions);
            }
            case 1: {
                if (par1 == 20) {
                    return getRandomStack(bootsOptions);
                }
                return getRandomStack(undeadBootsOptions);
            }
        }
        return null;
    }
    @Inject(method = "entityLivingAddRandomArmor",at = @At("HEAD"), cancellable = true)
    private void givearmor(CallbackInfo ci) {
        EntityLiving the = (EntityLiving)(Object)this;
        LivingInterface inter = (LivingInterface)the;
        if (the.worldObj.getDifficulty() == RefamishedAddon.CRUEL) {
            if (the.rand.nextFloat() < 0.6) {
                int var1 = the.rand.nextInt(2);
                float var2 = 0.25f;
                if (the.rand.nextFloat() < 0.095f) {
                    ++var1;
                }
                if (the.rand.nextFloat() < 0.095f) {
                    ++var1;
                }
                if (the.rand.nextFloat() < 0.095f) {
                    ++var1;
                }
                for (int var3 = 3; var3 >= 0; --var3) {
                    Item var5;
                    ItemStack var4 = the.func_130225_q(var3);
                    if (var3 < 3 && the.rand.nextFloat() < var2) break;
                    if (var4 != null || (var5 = getArmorForUh(var3 + 1, the.rand.nextInt(25))) == null) continue;
                    ItemStack what = new ItemStack(var5);
                    if (!what.hasTagCompound()) {
                        what.setTagCompound(new NBTTagCompound());
                    }
                    ArmorQuality quality = ArmorQuality.getRandomNegativeQuality();
                    what.getTagCompound().setString("ToolQuality", quality.getName());
                    the.setCurrentItemOrArmor(var3 + 1, what);
                    inter.getEquipmentDropChances()[var3 + 1] = 0.05f;
                }
            }
            else if (the.rand.nextFloat() < 0.7) {
                the.setCurrentItemOrArmor(4, new ItemStack(Item.helmetLeather));
                inter.getEquipmentDropChances()[4] = 0.025f;
            }
            ci.cancel();
        }
    }

    @Inject(method = "attackEntityAsMob",at = @At("HEAD"))
    private void dropItems(Entity attackedEntity, CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof EntitySpider) {
            EntitySpider the = (EntitySpider)(Object)this;
            LivingInterface Liver = (LivingInterface)(Object)this;
            if (the.worldObj.getDifficulty() == RefamishedAddon.CRUEL) {
                int hunger = the.worldObj.rand.nextInt(5)+15;
                int poison = the.worldObj.rand.nextInt(3)+3;

                ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( Potion.hunger.id, hunger * 20, 0 ) );
                ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( Potion.poison.id, poison * 20, 0 ) );
                ((EntityLivingBase)attackedEntity).addPotionEffect( new PotionEffect( RefamishedMod.INFESTEDWOUND.id, 120 * 20, 0 ) );
            }
        }
    }
}
