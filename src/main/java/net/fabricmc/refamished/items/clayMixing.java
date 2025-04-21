package net.fabricmc.refamished.items;

import btw.BTWAddon;
import btw.block.BTWBlocks;
import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.interfaces.ProgressiveCraftingInterface;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class clayMixing extends ProgressiveCraftingItem {
    public clayMixing(int iItemID) {
        super(iItemID);
        setUnlocalizedName( "clay_mixing" );
    }

    @Override
    protected int getProgressiveCraftingMaxDamage() {
        return 60*10/4;
    }

    @Override
    protected void playCraftingFX( ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "mob.slime.attack",
                0.25F + 0.25F * (float)world.rand.nextInt( 2 ),
                ( world.rand.nextFloat() - world.rand.nextFloat() ) * 0.25F + 0.25F );
    }

    @Override
    public ItemStack onEaten( ItemStack stack, World world, EntityPlayer player )
    {
        world.playSoundAtEntity( player, BTWBlocks.squishStepSound.stepSoundName, 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        return new ItemStack( RefamishedItems.clay_mud, 1, 0 );
    }

    @Override
    public void onCreated(ItemStack stack, World world, EntityPlayer player )
    {
        player.playSound( "mob.slime.attack", 1.0F, world.rand.nextFloat() * 0.1F + 0.9F );

        super.onCreated( stack, world, player );
    }

    public Icon[] m_recipientIcon = new Icon[6];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/clay_mixing_" + i);
        }

    }

    public void updateUsingItem(ItemStack stack, World world, EntityPlayer player)
    {
        super.updateUsingItem(stack,world,player);
        int iUseCount = player.getItemInUseCount();
        if (this.getMaxItemUseDuration(stack) - iUseCount > this.getItemUseWarmupDuration()) {
            if (!world.isRemote && iUseCount % 4 == 0) {
                int iDamage = stack.getItemDamage();
                PlayerSkillData data = SkillManager.getSkillData(player);
                int tx = (int)Math.floor(player.posX);
                int ty = (int)Math.floor(player.posY);
                int tz = (int)Math.floor(player.posZ);
                if (player.worldObj.getBlockMaterial(tx,ty,tz) == Material.water)
                {
                    stack.setItemDamage(iDamage-1);
                }
            }
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
