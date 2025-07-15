package net.fabricmc.refamished.items.decorative;

import btw.block.BTWBlocks;
import btw.item.items.PlaceAsBlockItem;
import net.fabricmc.refamished.RefamishedBlocks;
import net.minecraft.src.*;

import java.util.List;

public class berrySeeds extends PlaceAsBlockItem {
    public berrySeeds(int iItemID) {
        super(iItemID, 0);
        setUnlocalizedName("berry_seed");
        setHasSubtypes(true);
        setMaxDamage(0);
        this.setCreativeTab(CreativeTabs.tabFood);
        setTextureName("refamished:blueberry_seeds");
    }
    @Override
    public int getBlockIDToPlace(int iItemDamage, int iFacing, float fClickX, float fClickY, float fClickZ) {
        return (switch (iItemDamage) {
            default -> RefamishedBlocks.blueberryBush.blockID;
            case 1 -> RefamishedBlocks.sweetberryBush.blockID;
            case 2 -> RefamishedBlocks.cranberryBush.blockID;
            case 3 -> RefamishedBlocks.blackberryBush.blockID;
        });
    }
    private Icon[] iconByMetadataArray = new Icon[4];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String base = super.getUnlocalizedName();
        return base + "." + (switch (stack.getItemDamage()%4) {
            default -> "blueberry";
            case 1 -> "sweetberry";
            case 2 -> "cranberry";
            case 3 -> "blackberry";
        });
    }
    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 5);
        return this.iconByMetadataArray[var2];
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);
        this.iconByMetadataArray[0] = register.registerIcon("refamished:blueberry_seeds");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:sweetberry_seeds");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:sweetberry_seeds");
        this.iconByMetadataArray[3] = register.registerIcon("refamished:sweetberry_seeds");
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 3));
    }
}