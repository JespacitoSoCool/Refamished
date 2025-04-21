package net.fabricmc.refamished.models.block;

import btw.block.model.BlockModel;

public class CokeOvenModel extends BlockModel {
    public static final float BASE_HEIGHT = 0.375f;
    public static final float SIDE_WIDTH = 0.25f;
    public static final float HALF_SIDE_WIDTH = 0.125f;
    public static final float TOP_THICKNESS = 0.25f;
    public static final float HALF_TOP_THICKNESS = 0.125f;
    public static final float MIND_THE_GAP = 0.001f;

    @Override
    protected void initModel() {
        add16Box(0,12,0,16,16,16);
        add16Box(0,0,0,16,4,16);
        add16Box(14,4,0,16,12,16);
        add16Box(0,4,0,2,12,16);
    }
    private void add16Box(int dMinX,int dMinY,int dMinZ,int dMaxX,int dMaxY,int dMaxZ)
    {
        this.addBox(dMinX/16f, dMinY/16f,dMinZ/16f,dMaxX/16f,dMaxY/16f,dMaxZ/16f);
    }
}
