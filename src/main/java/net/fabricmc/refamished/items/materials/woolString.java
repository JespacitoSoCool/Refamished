package net.fabricmc.refamished.items.materials;

import btw.item.items.WoolItem;
import btw.util.color.ColorHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class woolString extends Item
{
    public woolString( int iItemID )
    {
        super( iItemID );

        setMaxDamage( 0 );
        setHasSubtypes( true );
        this.setFilterableProperties(8);
        setBuoyant();
        setBellowsBlowDistance( 2 );
        setUnlocalizedName( "woolString" );
        setCreativeTab( CreativeTabs.tabMaterials );
        this.setTextureName("refamished:woolString");
    }

    @Override
    public String getItemDisplayName( ItemStack stack )
    {
        int iColor = MathHelper.clamp_int( stack.getItemDamage(), 0, 15 );

        int itemDamage = stack.getItemDamage();
        return StringTranslate.getInstance().translateKey("woolString." + ColorHelper.colorOrder[itemDamage] + ".name").trim();
    }
    @Override
    @Environment(value= EnvType.CLIENT)
    public void getSubItems(int iItemID, CreativeTabs creativeTabs, List list) {
        for (int iColor = 0; iColor < 16; ++iColor) {
            list.add(new ItemStack(iItemID, 1, iColor));
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int iRenderPass) {
        return WoolItem.woolColors[stack.getItemDamage()];
    }
}
