package net.fabricmc.refamished.items.materials;

import emi.shims.java.net.minecraft.util.DyeColor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class PigmentItem extends ItemDye
{
    public PigmentItem(int iItemID )
    {
        super( iItemID );

        setMaxDamage( 0 );
        setHasSubtypes( true );
        this.setFilterableProperties(8);
        setBellowsBlowDistance( 2 );
        setUnlocalizedName( "pigment" );
        setCreativeTab( CreativeTabs.tabMaterials );
    }
    @Environment(value=EnvType.CLIENT)
    private Icon[] dyeIcons;

    @Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer player, World world, int i, int j, int k, int iFacing, float fClickX, float fClickY, float fClickZ) {
        return false;
    }

    @Override
    public int getFilterableProperties(ItemStack stack) {
        return 8;
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIconFromDamage(int damage) {
        int var2 = MathHelper.clamp_int(damage, 0, 15);
        return this.dyeIcons[var2];
    }

    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List) {
        for (int var4 = 0; var4 < 16; ++var4) {
            par3List.add(new ItemStack(par1, 1, var4));
        }
    }

    @Override
    public void registerIcons(IconRegister par1IconRegister) {
        this.dyeIcons = new Icon[dyeItemNames.length];
        for (int var2 = 0; var2 < dyeItemNames.length; ++var2) {
            this.dyeIcons[var2] = par1IconRegister.registerIcon("refamished:variant/pigment_" + dyeItemNames[var2]);
        }
    }
}
