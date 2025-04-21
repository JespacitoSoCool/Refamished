package net.fabricmc.refamished.itemsbase;

import btw.block.BTWBlocks;
import com.google.common.collect.Multimap;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class blade extends Item
{
    public int m_iWeaponDamage = 1;

    protected blade( int iItemID)
    {
        super(iItemID);
        setFull3D();
        this.setMaxStackSize(1);
    }

    @Override
    public void onCreated(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        super.onCreated(par1ItemStack,par2World,par3EntityPlayer);
        int maxDurability = par1ItemStack.getMaxDamage();
        int experience = (int) (maxDurability/7);

        SkillManager.addExperience(par3EntityPlayer,"Artisanry", experience);
        if (!par2World.isRemote)
        {
            if (!par1ItemStack.hasTagCompound()) {
                par1ItemStack.setTagCompound(new NBTTagCompound());
            }
            ToolQuality quality = ToolQuality.getRandomQuality();
            par1ItemStack.getTagCompound().setString("ToolQuality", quality.getName());
        }
    }

    public float func_82803_g() {
        return m_iWeaponDamage;
    }

    @Override
    public Multimap getItemAttributeModifiers() {
        Multimap var1 = super.getItemAttributeModifiers();
        var1.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.m_iWeaponDamage, 0));
        return var1;
    }

    public int getMaterial()
    {
        return 1; //0 is flint, 1 is iron
    }

    public float getStrVsBlock(ItemStack stack, World world, Block block, int i, int j, int k )
    {
        return super.getStrVsBlock( stack, world, block, i, j, k );
    }

    @Override
    public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLivingBase par7EntityLivingBase) {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0) {
            par1ItemStack.damageItem(1, par7EntityLivingBase);
        }
        return true;
    }

    @Override
    public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
        par1ItemStack.damageItem(1, par3EntityLivingBase);
        return true;
    }

    static public void PlayBreakSoundOnPlayer( EntityPlayer player )
    {

        player.playSound( "random.break", 0.8F, 0.8F + player.worldObj.rand.nextFloat() * 0.4F );
    }

}
