package net.fabricmc.refamished.items.tools;

import com.google.common.collect.Multimap;
import net.minecraft.src.*;

public class flintSharpener extends Item {

    public flintSharpener(int par1) {
        super(par1);
        setUnlocalizedName( "flintSharpener" );
        this.setMaxStackSize(1);
        setMaxDamage(15);
        setTextureName("refamished:flintSharpener");
        setCreativeTab(CreativeTabs.tabTools);
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", 3, 0));
        return var1;
    }
    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0) {
            par1ItemStack.damageItem(1, par7EntityLivingBase);
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        if (par2EntityLivingBase.worldObj.rand.nextInt(3) == 0)
        {
            par1ItemStack.damageItem(1, par3EntityLivingBase);
        }
        return true;
    }
}
