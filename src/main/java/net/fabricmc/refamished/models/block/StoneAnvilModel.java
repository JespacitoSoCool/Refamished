package net.fabricmc.refamished.models.block;

import btw.block.model.BlockModel;
import net.minecraft.src.AxisAlignedBB;

public class StoneAnvilModel extends BlockModel {
    public AxisAlignedBB boxSelection;
    @Override
    protected void initModel() {
        add16Box(0,0,0,16,3,16);
        add16Box(0,10,0,16,16,16);
        add16Box(1,3,2,15,10,14);
        add16Box(1,3,14,15,5,15);
        add16Box(1,3,1,15,5,2);
        this.boxSelection = new AxisAlignedBB(0, 0, 0, 1, 1, 1);
    }
    private void add16Box(int dMinX,int dMinY,int dMinZ,int dMaxX,int dMaxY,int dMaxZ)
    {
        this.addBox(dMinX/16f, dMinY/16f,dMinZ/16f,dMaxX/16f,dMaxY/16f,dMaxZ/16f);
    }
}
