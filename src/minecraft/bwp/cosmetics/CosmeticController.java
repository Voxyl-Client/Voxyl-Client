package bwp.cosmetics;

import net.minecraft.client.entity.AbstractClientPlayer;

public class CosmeticController {

    public static boolean shouldRenderTopHat(AbstractClientPlayer player){
        //TODO - Redo With checking
        return false;

    }
    public static float[] getTopHatColor(AbstractClientPlayer player){
        //Using R G B Values 0 -1 Only

        return new float[] {1, 0 , 0};
    }

    public static boolean shouldRenderEyes(AbstractClientPlayer player){
        return false;
    }

}
