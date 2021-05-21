package bwp.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;


public class RenderInfo {
    private static final Minecraft mc = Minecraft.getMinecraft();

    private int x, y;
    private float scale;

    public RenderInfo(double x, double y, float scale) {
        setRelative(x, y, scale);
    }

    public RenderInfo(int x, int y, float scale) {
        setAbsolute(x, y, scale);
    }

    public static RenderInfo fromRelative(double x, double y, float scale) {
        return new RenderInfo(x, y, scale);
    }

    public static RenderInfo fromAbsolute(int x, int y, float scale) {
        return new RenderInfo(x, y, scale);
    }

    public int getAbsoluteX() {
        return x;
    }

    public int getAbsoluteY() {
        return y;
    }

    public double getRelativeX() {
        ScaledResolution sr = new ScaledResolution(mc);
        return x / sr.getScaledWidth_double();
    }

    public double getRelativeY() {
        ScaledResolution sr = new ScaledResolution(mc);
        return y / sr.getScaledHeight_double();
    }

    public float getScale() {
        return scale;
    }

    public void setAbsolute(int x, int y, float scale) {
        this.x = x;
        this.y = y;
        this.scale = scale;
    }

    public void setRelative(double x, double y, float scale) {
        ScaledResolution sr = new ScaledResolution(mc);
        this.x = (int) (sr.getScaledWidth() / x);
        this.y = (int) (sr.getScaledHeight() / y);
        this.scale = scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

}