package net.fabricmc.refamished.items.tools;

import btw.item.items.ProgressiveCraftingItem;
import net.fabricmc.refamished.quality.ToolQuality;
import net.fabricmc.refamished.skill.SkillManager;
import net.minecraft.src.*;

public class deathClubAssembling extends ProgressiveCraftingItem {

    static public final int m_assemblingDamage = ( 60 * 3 );
    protected int var;
    protected Item result;
    public deathClubAssembling(int par1, int Var, Item Result) {
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
        ItemStack result_ = new ItemStack( result, 1, 0 );
        SkillManager.addExperience(player,"Artisanry", 10);
        if (!world.isRemote)
        {
            if (!result_.hasTagCompound()) {
                result_.setTagCompound(new NBTTagCompound());
            }
            ToolQuality quality = ToolQuality.getRandomQuality();
            result_.getTagCompound().setString("ToolQuality", quality.getName());
        }
        return result_;
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
    protected int getProgressiveCraftingMaxDamage()
    {
        return m_assemblingDamage;
    }

    private Icon defaultHandle;
    private Icon[] handleTextures = new Icon[2];
    private Icon[] headTextures = new Icon[4];

    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }

    @Override
    public void registerIcons(IconRegister register) {
        super.registerIcons(register);

        handleTextures[0] = register.registerIcon("refamished:pieces/assembling_ribs");
    }

    @Override
    public Icon getIconFromDamageForRenderPass(int damage, int renderPass) {
        if (renderPass == 0) {
            return itemIcon;
        }
        return handleTextures[0];
    }
}
