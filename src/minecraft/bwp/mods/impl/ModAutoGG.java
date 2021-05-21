package bwp.mods.impl;

import bwp.gui.hud.HUDConfigScreen;
import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;

public class ModAutoGG extends ModDraggable {
    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public void render(ScreenPosition pos) {
    }

    @Override
    public boolean shouldRender() {
        return false;
    }
}
