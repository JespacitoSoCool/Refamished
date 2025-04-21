package net.fabricmc.refamished;

import btw.block.tileentity.OvenTileEntity;
import btw.client.render.tileentity.OvenRenderer;
import net.fabricmc.refamished.entities.*;
import net.fabricmc.refamished.entities.render.*;
import net.fabricmc.refamished.entities.tiles.*;
import net.minecraft.src.EntityTracker;
import net.minecraft.src.*;

public class RefamishedEntities {

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
			id_molotov=3374
					;

	public static void registerEntities() {
		EntityList.addMapping(EntitySKOrb.class, "SkillOrb", id_skillOrb);
		//EntityList.entityEggs.put(Integer.valueOf(id_skillOrb), new EntityEggInfo(id_skillOrb, 0x617677, 0xDDEAEA));
		RenderManager.addEntityRenderer(EntitySKOrb.class, new RenderSKOrb());
		TileEntity.addMapping(cokeovenTile.class, "cokeOvenTile");
		TileEntity.addMapping(tarTankTile.class, "tarTank");
		TileEntityRenderer.instance.addSpecialRendererForClass(cokeovenTile.class, new CokeOvenRenderer());
		TileEntityRenderer.instance.addSpecialRendererForClass(tarTankTile.class, new TarTankRenderer());
		TileEntity.addMapping(placedSoftClayBrickTile.class, "softClay");
		TileEntity.addMapping(softBrickTile.class, "softBrick");
		TileEntity.addMapping(tanning.class, "tannin");

		EntityList.addMapping(EntityIncendiaryArrow.class, "arrowIncendiary", id_entityArrowIncendiary);
		RenderManager.addEntityRenderer(EntityIncendiaryArrow.class, new ArrowIncendiaryRender());
		EntityList.addMapping(EntityFlintArrow.class, "arrowFlint", id_entityArrowFlint);
		RenderManager.addEntityRenderer(EntityFlintArrow.class, new ArrowFlintRender());
		EntityList.addMapping(EntityGoldArrow.class, "arrowGold", id_entityArrowGold);
		RenderManager.addEntityRenderer(EntityGoldArrow.class, new ArrowGoldRender());
		EntityList.addMapping(EntityFrostArrow.class, "arrowFrost", id_entityArrowFrost);
		RenderManager.addEntityRenderer(EntityFrostArrow.class, new ArrowFrostRender());
		EntityList.addMapping(EntityBoneBolt.class, "boltBone", id_entityBoltBone);
		RenderManager.addEntityRenderer(EntityBoneBolt.class, new BoltBaseRender("bolt_bone"));
		EntityList.addMapping(EntityCopperBolt.class, "boltIron", id_entityBoltCopper);
		RenderManager.addEntityRenderer(EntityCopperBolt.class, new BoltBaseRender("bolt_iron"));

		EntityList.addMapping(EntityMolotov.class, "molotov", id_molotov);
		RenderManager.addEntityRenderer(EntityMolotov.class, new RenderSnowball(RefamishedItems.tar_molotov_lit));
		System.out.println("AAAAAAAAAAAAAAAAAAAA");
	}
}