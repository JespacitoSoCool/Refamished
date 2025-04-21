package net.fabricmc.refamished.items.decorative;
import net.minecraft.src.*;

public class bowStringing extends Item {
    public bowStringing(int iItemID) {
        super(iItemID);
        setUnlocalizedName( "bow_stringing" );
        maxStackSize = 1;
        setTextureName("refamished:stringing");
    }
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 72000;
    }

    public ItemStack onItemRightClick( ItemStack stack, World world, EntityPlayer player )
    {

        player.setItemInUse( stack, getMaxItemUseDuration( stack ) );

        return stack;
    }

    //may need to look into this to make animation work
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {

        int var6 = this.getMaxItemUseDuration(itemStack) - iTicksInUseRemaining;
        System.out.println("Var 6 = " + var6);

        if (var6 < 15)
        {
            return;
        }

        if (var6 > 23 && var6 < 35)
        {
            player.playSound( "random.bow", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
            player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.bow, 1, 0);

        }
        else if (var6 > 29)
        {
            itemStack.damageItem( 11, player );
            player.playSound( "mob.zombie.woodbreak", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
            player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( Item.silk, 2);
        }


    }
    public Icon[] m_pullIcon = new Icon[3];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );
        for (int i = 0; i < m_pullIcon.length; i++) {
            m_pullIcon[i] = register.registerIcon("refamished:progressive/stringing_pull_" + i);
        }

    }

    @Override
    public boolean requiresMultipleRenderPasses()
    {
        return true;
    }

    @Override
    public Icon getIconFromDamage(int damage) {
        return calculatePullingIcon();
    }

    private Icon calculatePullingIcon() {
        EntityPlayer player = Minecraft.getMinecraft().thePlayer;
        if (player != null)
        {
            ItemStack itemInUse = player.getItemInUse();

            if (itemInUse != null && itemInUse.itemID == this.itemID) {
                int pullingDuration = getMaxItemUseDuration(itemInUse) - player.getItemInUseCount();
                int pullingIndex = Math.min(pullingDuration / 9, m_pullIcon.length - 1);
                return m_pullIcon[pullingIndex];
            }
            return m_pullIcon[0];
        }
        return m_pullIcon[0];
    }
}