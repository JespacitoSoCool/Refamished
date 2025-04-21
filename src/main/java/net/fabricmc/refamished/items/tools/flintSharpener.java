package net.fabricmc.refamished.items.tools;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Item;

public class flintSharpener extends Item {

    public flintSharpener(int par1) {
        super(par1);
        setUnlocalizedName( "flintSharpener" );
        this.setMaxStackSize(1);
        setMaxDamage(15);
        setTextureName("refamished:flintSharpener");
        setCreativeTab(CreativeTabs.tabTools);
    }
}
