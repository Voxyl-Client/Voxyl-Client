package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.util.IChatComponent;

import java.util.Arrays;

public class FullBright extends ModDraggable {
    public static final FullBright INSTANCE = new FullBright();

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

    @Override
    public void onToggle() {
        if (INSTANCE.isEnabled()) {
            mc.gameSettings.gammaSetting = (((mc.gameSettings.gammaSetting+13)%28) + 1);
        } else {
            mc.gameSettings.gammaSetting = 1;
        }
    }
}

