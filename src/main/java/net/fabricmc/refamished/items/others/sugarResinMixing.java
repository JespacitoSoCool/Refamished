package net.fabricmc.refamished.items.others;

import btw.block.BTWBlocks;
import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class sugarResinMixing extends ProgressiveCraftingItem {
    public sugarResinMixing(int iItemID) {
        super(iItemID);
        setUnlocalizedName( "sugar_cane_crushing" );
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 60*7/4;
    }

    @Override
    protected void playCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "mob.slime.attack",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 0.25F );
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "mob.slime.attack", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        super.onCreated( stack, world, player );
    }

    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, BTWBlocks.squishStepSound.stepSoundName, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        SkillManager.addExperience(player,"Weaving", 3);

        return new ItemStack( RefamishedItems.sugar_resin );
    }

    public Icon[] m_recipientIcon = new Icon[6];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/sugar_resin_" + i);
        }

    }

    @Override
    @Environment(value= EnvType.CLIENT)
    public Icon getIconFromDamage(int damage) {
        if (damage == 0) {
            long time = System.currentTimeMillis();
            int frameIndex = (int) ((time / 1000) % m_recipientIcon.length);
            return m_recipientIcon[frameIndex];
        } else {
            int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
            return m_recipientIcon[5 - progressIndex];
        }
    }

}
