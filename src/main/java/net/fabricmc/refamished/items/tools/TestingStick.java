package net.fabricmc.refamished.items.tools;

import btw.entity.attribute.AttributeOperation;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.entities.EntitySKOrb;
import net.fabricmc.refamished.entities.tiles.tarTankTile;
import net.fabricmc.refamished.misc.ResharedMonsterAttributes;
import net.minecraft.src.*;

public class TestingStick extends Item {
    public TestingStick(int par1) {
        super(par1);
    }

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ )
    {
        //world.spawnEntityInWorld(new EntitySKOrb(world, i, j, k, 10));
        //System.out.println("Spawned?");
        System.out.println("meta : "+world.getBlockMetadata(i, j, k));
        System.out.println("Facing : "+iFacing);
        return true;
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put(ResharedMonsterAttributes.toolRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedMod.toolRangeUUID, "Tool Range", 8, AttributeOperation.ADDITIVE.value));
        var1.put(ResharedMonsterAttributes.attackRange.getAttributeUnlocalizedName(), new AttributeModifier(RefamishedMod.toolRangeUUID, "Attack Range", 8, AttributeOperation.ADDITIVE.value));
        return var1;
    }
}