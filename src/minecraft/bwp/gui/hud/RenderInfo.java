package bwp.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class RenderInfo {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private double x, y;

    private float scale;

    public RenderInfo(double x, double y, float scale) {
        setRelativePos(x, y, scale);
    }

    public int getX() {
        ScaledResolution res = new ScaledResolution(mc);
        return (int) (x * res.getScaledWidth());
    }

    public int getY() {
        ScaledResolution res = new ScaledResolution(mc);
        return (int) (y * res.getScaledHeight());
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setPos(int x, int y, float scale) {
        ScaledResolution res = new ScaledResolution(mc);
        if (x == 0) this.x = 0;
        else this.x = (double) x / (double) res.getScaledWidth();
        if (y == 0) this.y = 0;
        else this.y = (double) y / (double) res.getScaledHeight();
        this.scale = scale;
    }

    public void setRelativePos(double x, double y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }
}