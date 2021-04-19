package bwp.mods.impl.togglesprint;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;

public class ToggleSprintSneak extends ModDraggable {
    
    private ScreenPosition pos;

    //settings
    public boolean flyBoost = true;
    public float flyBoostFactor = 4;
    public int keyHoldTicks = 7;///



    @Override
    public void save(ScreenPosition pos) {
        this.pos = pos;
    }

    @Override
    public ScreenPosition load() {
        return pos;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {

    }
}
