package bwp.gui.hud.snapping;

import bwp.gui.hud.IRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SnappingZone {
    private ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
    int screenWidth = res.getScaledWidth();
    int screenHeight = res.getScaledHeight();

    public final float relativeLoc;
    private final SnappingDirection direction;
    private final SnappingArea snappingArea;
    private IRenderer rendererToSnap = null;
    private IRenderer rendererSnapped = null;
    private int pixelLoc;
    private int color;

    public SnappingZone(float relativeLoc, SnappingDirection direction, SnappingArea snappingArea, int color) {
        this.relativeLoc = relativeLoc;
        this.direction = direction;
        this.snappingArea = snappingArea;
        this.color = color;
        updatePixelLoc();
    }

    public void updatePixelLoc() {
        if (direction == SnappingDirection.HORIZONTAL) {
            pixelLoc = (int) (relativeLoc * screenWidth);
        } else if (direction == SnappingDirection.VERTICAL) {
            pixelLoc = (int) (relativeLoc * screenHeight);
        }
    }

    public IRenderer getRendererToSnap() {
        return rendererToSnap;
    }

    public void setRendererToSnap(IRenderer rendererToSnap) {
        this.rendererToSnap = rendererToSnap;
    }

    public void setSnappedRenderer(IRenderer renderer) { this.rendererSnapped = renderer; }

    public void setColor(int color) {
        this.color = color;
    }

    public void removeRendererToSnap() {
        this.rendererToSnap = null;
    }

    public int getPixelLoc() {
        return pixelLoc;
    }

    public SnappingDirection getDirection() {
        return direction;
    }

    public SnappingArea getSnappingArea() {
        return snappingArea;
    }

    public int getColor() {
        return color;
    }

    public IRenderer getRendererSnapped() {
        return rendererSnapped;
    }
}
