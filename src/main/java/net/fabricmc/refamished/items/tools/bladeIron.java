package net.fabricmc.refamished.items.tools;

import btw.block.BTWBlocks;
import net.fabricmc.refamished.itemsbase.blade;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;

public class bladeIron extends blade
{
    static int durability = 80;

    public bladeIron(int iItemID)
    {
        super(iItemID);
        setUnlocalizedName( "flintBlade" );
        this.setCreativeTab(CreativeTabs.tabTools);
        m_iWeaponDamage = 3;
        this.setMaxDamage(durability);

    }

    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k )
    {
        ToolQuality quality = ToolQualityHelper.getToolQuality(stack);

        float modifiedSpeed = ToolQualityHelper.getDigSpeedMultiplier(quality);
        float fStrength = super.getStrVsBlock( stack, world, block, i, j, k );

        if ( block.blockID == Block.web.blockID
                || block.blockID == BTWBlocks.web.blockID)
        {
            return fStrength *= 30*modifiedSpeed;
        }

        Material material = block.blockMaterial;

        if ( material == Material.wood || material == Material.plants || material == Material.vine || material == Material.coral || material == Material.leaves || material == Material.pumpkin)
        {
            return 4.5F*modifiedSpeed;
        }

//        if (block.blockID == Block.wood.blockID)
//        {
//        	return 3.0F;
//        }

        return fStrength;
    }

    public int getDamageVsEntity( Entity entity )
    {
        return m_iWeaponDamage;
    }



}

