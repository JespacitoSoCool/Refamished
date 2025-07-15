package net.fabricmc.refamished.items.others;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class sugarCaneCuttingFlint extends leatherWorkFlint implements ProgressiveCraftingInterface {
    public sugarCaneCuttingFlint(int iItemID) {
        super(iItemID);
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 200;
    }

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );
        m_recipientIcon = new Icon[6];
        m_itemIcon = register.registerIcon("refamished:progressive/rib_carve_flint");

        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/sugar_cutting_" + i);
        }

    }

    @Override
    protected void playCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "random.eat", 0.25F + 0.5F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.25F ) + 1.75F );

        if ( player.worldObj.isRemote )
        {
            for (int var3 = 0; var3 < 3; ++var3)
            {
                Vec3 var4 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D );

                var4.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F );
                var4.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F );

                Vec3 var5 = player.worldObj.getWorldVec3Pool().getVecFromPool( ( (double)player.rand.nextFloat() - 0.5D) * 0.3D, (double)(-player.rand.nextFloat()) * 0.6D - 0.3D, 0.6D );

                var5.rotateAroundX( -player.rotationPitch * (float)Math.PI / 180.0F);
                var5.rotateAroundY( -player.rotationYaw * (float)Math.PI / 180.0F);

                var5 = var5.addVector( player.posX, player.posY + (double)player.getEyeHeight(), player.posZ);

                player.worldObj.spawnParticle( "iconcrack_" + Item.reed.itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05D, var4.zCoord);
            }
        }
    }

    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {

        player.playSound( "random.eat", 0.5F + 0.5F * (float)player.rand.nextInt(2), ( player.rand.nextFloat() * 0.25F ) + 1.75F );

        //FCUtilsItem.GivePlayerStackOrEject( player, leatherWorking );

        int internalBladeDamage;

        internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;
        String quality = stack.getTagCompound().getString("quality");

        if (internalBladeDamage >= 12)
        {

        }
        else
        {
            ItemStack res = new ItemStack(RefamishedItems.flintBlade, 1, internalBladeDamage);
            if (!world.isRemote)
            {
                if (!res.hasTagCompound()) {
                    res.setTagCompound(new NBTTagCompound());
                }
                res.getTagCompound().setString("ToolQuality", quality);
            }
            UtilItem.GivePlayerStackOrEject( player, res);
        }

        SkillManager.addExperience(player,"Artisanry", 7);
        SkillManager.addExperience(player,"Chipping", 5);

        return new ItemStack( RefamishedItems.cut_sugar_cane, 1);
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            if (damage == 0) {
                long time = System.currentTimeMillis();
                int frameIndex = (int) ((time / 1000) % m_recipientIcon.length); // Changes every second
                return m_recipientIcon[frameIndex];
            } else {
                int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
                return m_recipientIcon[5 - progressIndex];
            }
        } else {
            return m_itemIcon;
        }
    }
}
