package bwp.gui.hud.snapping;

import bwp.mods.HUDMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;

public class SnappingZone {
    private ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
    int screenWidth = res.getScaledWidth();
    int screenHeight = res.getScaledHeight();

    public final float relativeLoc;
    private final SnappingDirection direction;
    private final SnappingArea snappingArea;
    private HUDMod modToSnap = null;
    private HUDMod modSnapped = null;
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

    public HUDMod getModToSnap() {
        return modToSnap;
    }

    public void setModToSnap(HUDMod modToSnap) {
        this.modToSnap = modToSnap;
    }

    public void setSnappedRenderer(HUDMod renderer) { this.modSnapped = renderer; }

    public void setColor(int color) {
        this.color = color;
    }

    public void removeRendererToSnap() {
        this.modToSnap = null;
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

    public HUDMod getModSnapped() {
        return modSnapped;
    }
}
