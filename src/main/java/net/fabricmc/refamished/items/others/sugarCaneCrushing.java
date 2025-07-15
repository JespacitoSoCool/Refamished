package net.fabricmc.refamished.items.others;

import btw.block.BTWBlocks;
import btw.item.BTWItems;
import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class sugarCaneCrushing extends ProgressiveCraftingItem {
    public sugarCaneCrushing(int iItemID) {
        super(iItemID);
        setUnlocalizedName( "sugar_cane_crushing" );
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 60*5/4;
    }

    @Override
    protected void playCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "step.wood",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1F );
    }

    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "step.wood",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 1F );

        int internalBladeDamage = stack.getTagCompound().getInteger("damage") + 1;

        UtilItem.GivePlayerStackOrEject( player, new ItemStack( RefamishedItems.mortar_pestle, 1, 0 ));
        if (internalBladeDamage >= 8)
        {

        }
        else
        {
            ItemStack res = new ItemStack(BTWItems.stone, 1, internalBladeDamage);
            UtilItem.GivePlayerStackOrEject( player, res);
        }
        return new ItemStack( RefamishedItems.crude_sugar, 1, 0 );
    }

    public Icon[] m_recipientIcon = new Icon[6];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        itemIcon = register.registerIcon("refamished:progressive/crushing_stone");
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/sugar_crushing_" + i);;
        }

    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            if (damage == 0) {
                long time = System.currentTimeMillis();
                int frameIndex = (int) ((time / 1000) % m_recipientIcon.length);
                return m_recipientIcon[frameIndex];
            } else {
                int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
                return m_recipientIcon[5 - progressIndex];
            }
        } else {
            return itemIcon;
        }
    }

}
