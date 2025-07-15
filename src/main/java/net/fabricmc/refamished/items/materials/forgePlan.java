package net.fabricmc.refamished.items.materials;

import emi.shims.java.net.minecraft.text.Text;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Recipes.ForgingPlansRecipes;
import net.minecraft.src.*;

import java.util.List;

public class forgePlan extends Item {
    public forgePlan(int iItemID) {
        super(iItemID);
        this.setUnlocalizedName("forge_plan");
        this.setCreativeTab(CreativeTabs.tabMaterials);
        setMaxDamage(0);
        setHasSubtypes(true);
    }
    private static final String[] ingot = new String[]{"copper","iron","gold","cobalt","gilded","diamond","steel"};
    private Icon[] iconByMetadataArray = new Icon[9];

    /*
        @Override
    public String getUnlocalizedName(ItemStack stack) {
        int meta = MathHelper.clamp_int(stack.getItemDamage(), 0, ingot.length - 1);
        return super.getUnlocalizedName() + "." + ingot[meta];
    }
     */

    @Override
    public Icon getIconFromDamage(int metadata) {
        int var2 = MathHelper.clamp_int(metadata, 0, 8);
        return this.iconByMetadataArray[var2];
    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public void registerIcons(IconRegister register) {
        for (int i = 0; i < ingot.length; i++) {
            this.iconByMetadataArray[i] = register.registerIcon("refamished:metallurgy/metal_pieces/" + ingot[i]+"_plate");
        }
    }

    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean advanced) {
        super.addInformation(stack, player, info, advanced);
        if (stack.getTagCompound() != null) {
            NBTTagCompound tags = stack.getTagCompound();
            String plan = tags.hasKey("PlanOutput") ? tags.getString("PlanOutput") : "Not Found";
            int shatterMax = tags.hasKey("ShatterMax") ? tags.getInteger("ShatterMax") : 0;
            int shatter = tags.hasKey("Shatter") ? tags.getInteger("Shatter"): 0;
            int meta = tags.hasKey("Metadata") ? tags.getInteger("Metadata"): 0;
            ForgingPlansRecipes.RecipeEntry getResult = ForgingPlansRecipes.getInstance().getMatchingRecipes(plan);
            if (getResult != null) {
                int getOutput = getResult.output.itemID;
                ItemStack tempOutput = new ItemStack(getOutput,1,meta);
                String getUnlocalizedName = Item.itemsList[getOutput].getUnlocalizedName(tempOutput)+".name";
                info.add(StatCollector.translateToLocalFormatted("description.output", StatCollector.translateToLocalFormatted(getUnlocalizedName)));
                info.add(StatCollector.translateToLocalFormatted("description.shatter", shatter+"/"+shatterMax));
            }
        }
    }


    @Override
    public void getSubItems(int par1, CreativeTabs tab, List list) {
        for (int i = 0; i < ingot.length; i++) {
            list.add(new ItemStack(par1, 1, i));
        }
    }
}
