package net.fabricmc.refamished.mixin.mixin_m;

import net.minecraft.src.EntityAITasks;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityLiving.class)
public interface LivingInterface {
    @Accessor("equipmentDropChances")
    float[] getEquipmentDropChances();

    @Accessor("equipmentDropChances")
    void setEquipmentDropChances(float[] chances);

    @Invoker("entityLivingAddRandomArmor")
    void setArmor();

    @Invoker("entityInit")
    void entityInitBruh();

    @Accessor("tasks")
    EntityAITasks getTasks();

    @Accessor("targetTasks")
    EntityAITasks getTargetTasks();

    @Accessor("equipment")
    ItemStack[] getEquipment();
}
