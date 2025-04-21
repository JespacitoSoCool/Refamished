package net.fabricmc.refamished.items.tools;

import btw.item.items.ProgressiveCraftingItem;
import btw.item.items.WoolItem;
import net.fabricmc.refamished.RefamishedItems;
import net.minecraft.src.*;

public class boneClubAssembling extends ProgressiveCraftingItem {

    static public final int m_assemblingDamage = ( 60 * 4 );
    protected int var;
    protected Item result;
    public boneClubAssembling(int par1, int Var, Item Result) {
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
        BoneClub.SetColor(the,GetColor(stack));
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

    private Icon m_iconWool[] = new Icon[3];

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getColorFromItemStack( ItemStack stack, int iRenderPass )
    {
        if ( iRenderPass == 1 )
        {
            return GetColor( stack );
        }

        return super.getColorFromItemStack( stack, iRenderPass );
    }

    static public void SetColor( ItemStack stack, int iColor )
    {
        NBTTagCompound tag = stack.getTagCompound();

        if ( tag == null )
        {
            tag = new NBTTagCompound();
            stack.setTagCompound( tag );
        }

        tag.setInteger( "color", iColor );

    }

    static public int GetColor( ItemStack stack )
    {
        NBTTagCompound tag = stack.getTagCompound();

        if ( tag != null )
        {
            if ( tag.hasKey( "color" ) )
            {
                return tag.getInteger( "color" );
            }
        }

        return 0xffffff;
    }


    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        m_iconWool[1] = register.registerIcon( "refamished:bindings/bone_club_binding" );
    }

    @Override
    public Icon getIconFromDamageForRenderPass( int iDamage, int iRenderPass )
    {
        if ( iRenderPass == 0 )
        {
            return itemIcon;
        }

        return m_iconWool[1];
    }
}
