package net.fabricmc.refamished.items.tools;

import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.misc.Utils.UtilItem;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class WoodenClubAssembling extends ProgressiveCraftingItem {

    static public final int m_assemblingDamage = ( 60 * 3 );
    protected int var;
    protected Item result;
    public WoodenClubAssembling(int par1, int Var, Item Result) {
        super(par1);
        var = Var;
        result = Result;
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
        player.playSound( "mob.zombie.woodbreak", 0.1F, 1.25F + ( world.rand.nextFloat() * 0.25F ) );
        ItemStack the = new ItemStack( result, 1, 0 );
        SkillManager.addExperience(player,"Artisanry", 8);
        if (!world.isRemote)
        {
            if (!the.hasTagCompound()) {
                the.setTagCompound(new NBTTagCompound());
            }
            ToolQuality quality = ToolQuality.getRandomQuality();
            the.getTagCompound().setString("ToolQuality", quality.getName());
        }
        return the;
    }

    @Override
    public void onCreated( ItemStack stack, World world, EntityPlayer player )
    {
        if ( player.timesCraftedThisTick == 0 && world.isRemote )
        {
            player.playSound( "mob.zombie.woodbreak", 0.1F, 1.25F + ( world.rand.nextFloat() * 0.25F ) );
        }

        super.onCreated( stack, world, player );
    }

    @Override
    public boolean getCanBeFedDirectlyIntoCampfire( int iItemDamage )
    {
        return false;
    }

    @Override
    public boolean getCanBeFedDirectlyIntoBrickOven( int iItemDamage )
    {
        return false;
    }

    @Override
    protected int getProgressiveCraftingMaxDamage()
    {
        return m_assemblingDamage;
    }
}
