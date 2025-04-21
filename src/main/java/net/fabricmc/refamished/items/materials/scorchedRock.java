package net.fabricmc.refamished.items.materials;

import net.minecraft.src.*;

import java.util.List;

public class scorchedRock extends Item {
    public scorchedRock(int par1) {
        super(par1);
        this.setTextureName("refamished:scorched_stone_1_strata_1");
        setUnlocalizedName("scorched_rock");
        setHasSubtypes(true);
        setMaxDamage(0);
    }
    private Icon[] iconByMetadataArray = new Icon[6];

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String base = super.getUnlocalizedName();
        return base + "." + (switch (stack.getItemDamage()%3) {
            default -> "stone";
            case 1 -> "deepslate";
            case 2 -> "blackstone";
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
        this.iconByMetadataArray[0] = register.registerIcon("refamished:scorched_stone_1_strata_1");
        this.iconByMetadataArray[1] = register.registerIcon("refamished:scorched_stone_1_strata_2");
        this.iconByMetadataArray[2] = register.registerIcon("refamished:scorched_stone_1_strata_3");
        this.iconByMetadataArray[3] = register.registerIcon("refamished:scorched_stone_2_strata_1");
        this.iconByMetadataArray[4] = register.registerIcon("refamished:scorched_stone_2_strata_2");
        this.iconByMetadataArray[5] = register.registerIcon("refamished:scorched_stone_2_strata_3");
    }

    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        list.add(new ItemStack(par1, 1, 0));
        list.add(new ItemStack(par1, 1, 1));
        list.add(new ItemStack(par1, 1, 2));
        list.add(new ItemStack(par1, 1, 3));
        list.add(new ItemStack(par1, 1, 4));
        list.add(new ItemStack(par1, 1, 5));
    }
}
