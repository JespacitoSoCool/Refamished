package net.fabricmc.refamished.items.others;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.interfaces.IconByItemStack;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.quality.ToolQualityHelper;
import net.minecraft.src.*;

import javax.tools.Tool;
import java.util.List;

public class qualityBlueprints extends Item implements IconByItemStack {
    public qualityBlueprints(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("blueprint");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
        setTextureName("refamished:variant/blueprint/blueprint_default");
    }
    private static final String[] qualities = new String[]{"average","exceptional","masterwork","gold","yellow","red",
    "keen","short","long","etched","reinforced","tempered","heavy","light"};
    private Icon[] iconByMetadataArray = new Icon[qualities.length];

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        super.addInformation(stack, player, info, advanced);
        if (stack.getTagCompound() != null) {
            int shatter = stack.getTagCompound().getInteger("Shatter");
            info.add("Shatter: " + shatter);
        }
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < qualities.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:variant/blueprint/blueprint_"+qualities[i]);
        }
    }

    @Override
    public void getSubItems(int itemId, CreativeTabs tab, List list) {
        for (int i = 0; i < qualities.length; i++) {
            list.add(new ItemStack(itemId, 1, i));
        }

        for (ToolQuality quality : ToolQuality.values()) {
            ItemStack stack = new ItemStack(itemId, 1, 0);
            NBTTagCompound tag = new NBTTagCompound();
            tag.setString("ToolQuality", quality.name());
            stack.setTagCompound(tag);
            list.add(stack);
        }
    }

    @Override
    public Icon getIcon(ItemStack stack, EntityPlayer player) {
        int index = getIndex(stack);
        if (index >= 0 && index < iconByMetadataArray.length) {
            return iconByMetadataArray[index];
        }
        // Fallback icon (optional)
        return iconByMetadataArray[0];
    }

    public static int getIndex(String name) {
        for (int i = 0; i < qualities.length; i++) {
            if (qualities[i].equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    public static int getIndex(ItemStack stack) {
        ToolQuality quality = ToolQualityHelper.getToolQuality(stack);

        if (quality != null) {
            int index = getIndex(quality.getName());
            if (index != -1) return index;

            // Optional: fallback by color (EnumChatFormatting)
            for (int i = 0; i < qualities.length; i++) {
                try {
                    ToolQuality q = ToolQuality.valueOf(qualities[i].toUpperCase());
                    if (q.getColor() == quality.getColor()) {
                        return i;
                    }
                } catch (IllegalArgumentException ignored) {}
            }
        }

        // Last fallback to metadata
        int meta = stack.getItemDamage();
        return (meta >= 0 && meta < qualities.length) ? meta : 0;
    }

}
