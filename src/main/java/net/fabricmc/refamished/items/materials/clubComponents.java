package net.fabricmc.refamished.items.materials;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.src.*;

import java.util.List;

public class clubComponents extends Item
{
    @Environment(value=EnvType.CLIENT)
    private Icon[] iconBySubtype;
    @Environment(value=EnvType.CLIENT)
    private String[] iconNamesBySubtype;
    public clubComponents(int iItemID )
    {
        super( iItemID );

        setMaxDamage( 0 );
        setHasSubtypes( true );
        this.setFilterableProperties(8);
        setBuoyant();
        setBellowsBlowDistance( 2 );
        setUnlocalizedName( "clubComponent" );
        setCreativeTab( CreativeTabs.tabMaterials );
    }

    @Override
    public int getFurnaceBurnTime(int iItemDamage) {
        return iItemDamage == 4 ? 0 : iItemDamage == 5 ? 100 : 50;
    }

    private String[] nameExtensionsBySubtype = new String[]{"oak", "spruce", "birch", "jungle", "bone", "handle"};
    @Override
    public String getUnlocalizedName(ItemStack stack) {
        int iSubtype = MathHelper.clamp_int(stack.getItemDamage(), 0, 5);
        return super.getUnlocalizedName() + "." + this.nameExtensionsBySubtype[iSubtype];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public Icon getIconFromDamage(int iDamage) {
        int iIconIndex = MathHelper.clamp_int(iDamage, 0, 5);
        return this.iconBySubtype[iIconIndex];
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.iconBySubtype = new Icon[6];
        this.iconNamesBySubtype = new String[]{"refamished:pieces/oak_club", "refamished:pieces/spruce_club", "refamished:pieces/birch_club",
                "refamished:pieces/jungle_club", "refamished:pieces/bone_club", "refamished:pieces/handle_item"};
        for (int iTempSubtype = 0; iTempSubtype < this.iconNamesBySubtype.length; ++iTempSubtype) {
            this.iconBySubtype[iTempSubtype] = par1IconRegister.registerIcon(this.iconNamesBySubtype[iTempSubtype]);
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public void getSubItems(int iItemID, CreativeTabs creativeTabs, List list) {
        for (int iSubtype = 0; iSubtype < 6; ++iSubtype) {
            list.add(new ItemStack(iItemID, 1, iSubtype));
        }
    }
}
