package net.fabricmc.refamished.items.tools;

import btw.item.items.HoeItem;
import btw.item.items.ShovelItem;
import btw.item.items.ShovelItemStone;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.minecraft.src.*;

public class shovelDecorated extends ShovelItemStone {
    public shovelDecorated(int iItemID) {
        super(iItemID);
    }
    private Icon m_iconWool[] = new Icon[3];

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public int getColorFromItemStack(ItemStack stack, int iRenderPass )
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

        tag.setInteger( "binding", iColor );

    }

    static public int GetColor( ItemStack stack )
    {
        NBTTagCompound tag = stack.getTagCompound();

        if ( tag != null )
        {
            if ( tag.hasKey( "binding" ) )
            {
                return tag.getInteger( "binding" );
            }
        }

        return 0xffffff;
    }


    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        if (RefamishedConfig.refamishedTextures) {
            itemIcon = register.registerIcon( "refamished:override/stone_shovel");
        }
        else {
            itemIcon = register.registerIcon( "stone_shovel");
        }
        m_iconWool[2] = register.registerIcon( "refamished:bindings/shovel_binding" );
    }

    @Override
    public Icon getIconFromDamageForRenderPass( int iDamage, int iRenderPass )
    {
        if ( iRenderPass == 0 )
        {
            return itemIcon;
        }

        return m_iconWool[2];
    }
}
