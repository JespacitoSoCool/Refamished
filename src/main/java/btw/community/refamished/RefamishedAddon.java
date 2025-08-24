package btw.community.refamished;

import btw.AddonHandler;
import btw.BTWAddon;
import btw.achievement.BTWAchievements;
import btw.world.biome.BiomeDecoratorBase;
import btw.world.util.difficulty.Difficulty;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.refamished.RefamishedBlocks;
import net.fabricmc.refamished.RefamishedItems;
import net.fabricmc.refamished.RefamishedMod;
import net.fabricmc.refamished.RefamishedModClient;
import net.fabricmc.refamished.entities.*;
import net.fabricmc.refamished.entities.Particles.ParticleFireDot;
import net.fabricmc.refamished.entities.Particles.ParticleThickCloud;
import net.fabricmc.refamished.entities.render.*;
import net.fabricmc.refamished.entities.tiles.*;
import net.fabricmc.refamished.misc.DifficultyCruel;
import net.fabricmc.refamished.misc.RefAchievements;
import net.fabricmc.refamished.misc.RefamishedConfig;
import net.fabricmc.refamished.misc.RefamishedSoundManager;
import net.fabricmc.refamished.world.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

import java.util.Random;
import java.util.UUID;

public class RefamishedAddon extends BTWAddon {
    private static RefamishedAddon instance;
    public static final UUID toolRangeUUID = UUID.fromString("cfde7e5c-9ccf-4aef-abd4-152fde3d0c1a");
    public static final Difficulty CRUEL = new DifficultyCruel("cruel");

    public static final RefamishedSoundManager soundManageer = new RefamishedSoundManager();
    public RefamishedAddon() {
        super();
        RefamishedMod.addMaterialOverride();
        RefamishedBlocks.registerBlocks();
        RefamishedItems.registerItems();
        registerEntities();

        //If you are reading this, I didn't put this on initialize because it just crashes thanks to the recipes thing
    }
    @Override
    public void initialize() {
        AddonHandler.logMessage(this.getName() + " + SBTW - Version " + this.getVersionString() + " Initializing...");
    }

    @Override
    public void postInitialize() {
        RefamishedItems.addForkedItems();
    }

    @Override
    @Environment(value= EnvType.SERVER)
    public void registerAddonCommandServerOnly(ICommand command) {
        super.registerAddonCommandServerOnly(command);
        //System.out.println("EXECUTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        if (MinecraftServer.getIsServer()) {
            //MinecraftServer.getServer().commandManager.registerCommand(new SkillCmd());
            //System.out.println("EXECUTEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEED");
        }
    }

    @Override
    @Environment(value=EnvType.CLIENT)
    public EntityFX spawnCustomParticle(World world, String particleType, double x, double y, double z, double velX, double velY, double velZ) {
        EntityFX var21 = null;
        if (particleType.equals("firedot") && RefamishedConfig.embersEnabled) {
            var21 = new ParticleFireDot(world, x, y, z);
        }
        else if (particleType.equals("thicksmoke") && RefamishedConfig.smokeEnabled) {
            var21 = new ParticleThickCloud(world, x, y, z);
        }
        return var21;
    }

    @Override
    public void decorateWorld(BiomeDecoratorBase decorator, World currentWorld, Random random, int chunkX, int chunkZ, BiomeGenBase biome) {
        int yThing = random.nextInt(100) + 6;
        int var4;
        int var3;
        int var2;
        int var1;
        int var7;
        for (var1 = 0; var1 < 5; ++var1) {
            var2 = chunkX + random.nextInt(16)+8;
            var3 = chunkZ + random.nextInt(16)+8;
            new ScorchedStoneGen(10).generate(currentWorld, random, var2, yThing, var3);
        }
        for (var1 = 0; var1 < 3; ++var1) {
            var2 = chunkX + random.nextInt(16)+8;
            var3 = chunkZ + random.nextInt(16)+8;
            int y = chunkZ + random.nextInt(16)+8;
            if (y >= 7) {
                new ScorchedStoneGen(7).generate(currentWorld, random, var2, y, var3);
            }
        }
        for (var2 = 0; var2 < 17; ++var2)
        {
            var3 = chunkX + random.nextInt(16)+8;
            var4 = random.nextInt(88)+40;
            var7 = chunkZ + random.nextInt(16)+8;
            new BranchGen(RefamishedBlocks.branch.blockID).generate(currentWorld, random, var3, var4, var7);
        }
        for (var2 = 0; var2 < 20; ++var2)
        {
            var3 = chunkX + random.nextInt(16)+8;
            var4 = random.nextInt(88)+40;
            var7 = chunkZ + random.nextInt(16)+8;
            new BushGen().generate(currentWorld, random, var3, var4, var7);
        }
        for (var2 = 0; var2 < 20; ++var2)
        {
            var3 = chunkX + random.nextInt(16)+8;
            var4 = random.nextInt(88)+40;
            var7 = chunkZ + random.nextInt(16)+8;
            new SpurgeGen(RefamishedBlocks.wildSprurge.blockID).generate(currentWorld, random, var3, var4, var7);
        }

        if (random.nextInt(35) == 0) {
            for (int i = 0; i < 1; i++) {
                int x = chunkX + random.nextInt(16)+8;
                int z = chunkZ + random.nextInt(16)+8;
                int y = currentWorld.getTopSolidOrLiquidBlock(x,z);

                new BoulderGen().generate(currentWorld, random, x, y, z);
            }
        }
    }

    private static final int
            id_entityCopperArrow= 3103,
            id_entityBoltBone= 3104,
            id_entityBoltCopper= 3105,
            id_entityBoltCobalt= 3106,
            id_entityArrowFrost= 3107,
            id_entityArrowFlint= 3108,
            id_entityArrowGold= 3109,
            id_entityRottedWeb= 3334,
            id_entityGhoul= 3333,
            id_savageCow= 3335,
            id_savageSheep=3336,
            id_savagePig=3337,
            id_savageChicken=3338,
            id_savageWolf=3339,
            id_husk=3370,
            id_stray=3371,
            id_skillOrb=3372,
            id_entityArrowIncendiary=3373,
            id_molotov=3374,
    id_molotovHellfire=3375;

    public static void registerEntities() {
        System.out.println("STARTING ENTITIES");
        //EntityList.addMapping(EntitySKOrb.class, "SkillOrb", id_skillOrb);
        System.out.println("ENTITY");
        //EntityList.entityEggs.put(Integer.valueOf(id_skillOrb), new EntityEggInfo(id_skillOrb, 0x617677, 0xDDEAEA));
        TileEntity.addMapping(cokeovenTile.class, "cokeOvenTile");
        TileEntity.addMapping(tarTankTile.class, "tarTank");
        TileEntity.addMapping(copperConductTile.class, "copperConduct");
        TileEntity.addMapping(steamKilnTile.class, "steamKiln");
        System.out.println("TILE");
        //TileEntityRenderer.instance.addSpecialRendererForClass(tanningHorse.class, new TanningHorseRenderer());
        TileEntity.addMapping(placedSoftClayBrickTile.class, "softClay");
        TileEntity.addMapping(softBrickTile.class, "softBrick");
        TileEntity.addMapping(tanning.class, "tannin");
        TileEntity.addMapping(tanningHorse.class, "tanninHorse");
        TileEntity.addMapping(stoneAnvilTile.class, "stoneAnvil");
        TileEntity.addMapping(copperAnvilTile.class, "copperAnvil");
        TileEntity.addMapping(steelAnvilTile.class, "steelAnvil");

        EntityList.addMapping(EntityIncendiaryArrow.class, "arrowIncendiary", id_entityArrowIncendiary);
        EntityList.addMapping(EntityFlintArrow.class, "arrowFlint", id_entityArrowFlint);
        EntityList.addMapping(EntityGoldArrow.class, "arrowGold", id_entityArrowGold);
        EntityList.addMapping(EntityFrostArrow.class, "arrowFrost", id_entityArrowFrost);
        EntityList.addMapping(EntityBoneBolt.class, "boltBone", id_entityBoltBone);
        EntityList.addMapping(EntityCopperBolt.class, "boltIron", id_entityBoltCopper);

        EntityList.addMapping(EntityMolotov.class, "molotov", id_molotov);
        EntityList.addMapping(EntityMolotovHellfire.class, "hellfire", id_molotovHellfire);
        System.out.println("AAAAAAAAAAAAAAAAAAAA");

        if (!MinecraftServer.getIsServer()) {
            RefamishedModClient.registerEntitiesRenderer();
        }
    }
}