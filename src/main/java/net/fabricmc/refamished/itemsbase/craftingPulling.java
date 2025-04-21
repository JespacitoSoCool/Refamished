package net.fabricmc.refamished.itemsbase;
import btw.item.BTWItems;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.PlayerSkillData;
import net.fabricmc.refamished.skill.SkillData;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class craftingPulling extends Item {
    //NOTE: this icon array is worthless and does nothing!!!
//    public static final String[] bowPullIconNameArray = new String[] {"bow_pull_0", "bow_pull_1", "bow_pull_2"};
//    public Icon[] iconArray;

    public float iProgression = 0;

    public craftingPulling(int iItemID) {
        super(iItemID);

        setUnlocalizedName( "pullingCrafting" );

        maxStackSize = 1;

        //setMaxDamage( GetProgressiveCraftingMaxDamage() );
        this.setMaxDamage(10);
    }

//	 protected int GetProgressiveCraftingMaxDamage()
//	 {
//	    return 10;
//	 }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        //same as bow, not sure what this does...

        return 72000;
    }

    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player )
    {

        player.setItemInUse( stack, getMaxItemUseDuration( stack ) );

        return stack;
    }

    //may need to look into this to make animation work
    public EnumAction getItemUseAction(ItemStack par1ItemStack)
    {
        return EnumAction.bow;
    }

    //when bad hits == 3, break item and return stone to player
    public void onPlayerStoppedUsing( ItemStack itemStack, World world, EntityPlayer player, int iTicksInUseRemaining )
    {
        //a little animation :F
        player.swingItem();

        if (itemStack.getTagCompound() == null)
        {
            NBTTagCompound newTag = new NBTTagCompound();
            itemStack.setTagCompound(newTag);
            itemStack.getTagCompound().setInteger("badHits", 0);
        }

        int badHits = itemStack.getTagCompound().getInteger("badHits");

        int var6 = this.getMaxItemUseDuration(itemStack) - iTicksInUseRemaining;
        //TESTER VVV
//    	System.out.println("Var 6 = " + var6);
//        float var7 = (float)var6 / 20.0F;
//        var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

        if (var6 < 5 )
        {
            return;
        }

        player.addExhaustion( 0.5F );

        if (var6 < 20 || var6 > 35)
        {
            badHits++;
//        	System.out.println("Uh oh! bad hits: " + badHits);

            player.playSound( "random.bow", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );

            itemStack.getTagCompound().setInteger("badHits", badHits);

            if (badHits > badHitsBreak())
            {
                itemStack.damageItem( 11, player );
                shatter(itemStack,world,player);
            }
        }
        else if (var6 >= 21)
        {
            PlayerSkillData data = SkillManager.getSkillData(player);
            int skillLevel = data.getSkillProgress(skillName()).getLevel();
            int levelCap = SkillData.getLevelCap(skillName());

            if (skillLevel == 0)
            {
                itemStack.damageItem( -1, player);
            }
            else
            {
                float getExtraProgression = (((float)skillLevel/(float)levelCap)*0.5F)+1;
                //SetProgression(stack,GetProgression(stack)+getExtraProgression);
                iProgression = iProgression+getExtraProgression;
                //int getDamaging = (int) Math.floor(GetProgression(stack));
                int getDamaging = (int) Math.floor(iProgression);
                itemStack.damageItem( -getDamaging, player);
                iProgression = iProgression-getDamaging;
                //SetProgression(stack,GetProgression(stack)-getDamaging);
            }
            //System.out.println("Item damage = " + itemStack.getItemDamage());
            player.playSound( "fire.ignite", 1.0F, itemRand.nextFloat() * 0.4F + 0.8F );
            //var7 = 1.0F;

            if (itemStack.getItemDamage() <= 0)
            {

                SkillManager.addExperience(player,skillName(), expAmount());
                craft(itemStack,world,player);
            }
        }
    }

    public static void GivePlayerStackOrEject(EntityPlayer player, ItemStack stack) {
        if (!player.inventory.addItemStackToInventory(stack)) {
            // If the player's inventory is full, eject the item into the world
            player.dropPlayerItemWithRandomChoice(stack, false);
        }
    }

    public int badHitsBreak() {
        return 4;
    }

    public int expAmount() {
        return 5;
    }

    public String skillName() {
        return "Chipping";
    }

    public void craft(ItemStack itemStack, World world, EntityPlayer player) {
        GivePlayerStackOrEject( player, new ItemStack(BTWItems.stone, 1));
        //itemStack.damageItem( 11, player );
        //to remove item from inv VVV
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( BTWItems.sharpStone, 1);
    }

    public void shatter(ItemStack itemStack, World world, EntityPlayer player) {
        //FCUtilsItem.GivePlayerStackOrEject( player, new ItemStack(FCBetterThanWolves.fcItemStone, 1));
        player.inventory.mainInventory[player.inventory.currentItem] = null;
        player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack( BTWItems.stone, 1);
    }

    //LOCAL AAAAAAAAAA

    public Icon[] m_pullIcon = new Icon[3];
    public Icon[] m_recipientIcon = new Icon[4];

    @Override
    public void registerIcons( IconRegister register )
    {
        super.registerIcons( register );

        for (int i = 0; i < m_pullIcon.length; i++) {
            m_pullIcon[i] = register.registerIcon("refamished:progressive/pull_stone_" + i);
        }

        // Register recipient icons
        for (int i = 0; i < m_recipientIcon.length; i++) {
            m_recipientIcon[i] = register.registerIcon("refamished:progressive/stone_sharpening_" + i);
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
                // Calculate animation frame based on time
                long time = System.currentTimeMillis();
                int frameIndex = (int) ((time / 1000) % m_recipientIcon.length); // Changes every second
                return m_recipientIcon[frameIndex];
            } else {
                // Calculate progress-based icon
                int progressIndex = Math.min(damage * (m_recipientIcon.length) / this.getMaxDamage(), m_recipientIcon.length - 1);
                return m_recipientIcon[3 - progressIndex];
            }
        } else {
            return calculatePullingIcon();
        }
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

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
        par1ItemStack.setItemDamage(par1ItemStack.getMaxDamage());
    }
}