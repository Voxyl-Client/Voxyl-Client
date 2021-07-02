package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.HUDMod;
import bwp.mods.Mod;

public class FullBright extends Mod {

    public FullBright() {
        super("Full Bright");
    }

    @Override
    public void onToggle() {
        System.out.println("uwu my owo");
        System.out.println(settings.getEnabled());
        if (settings.getEnabled()) {
            mc.gameSettings.gammaSetting = (((mc.gameSettings.gammaSetting+13)%28) + 1);
        } else {
            mc.gameSettings.gammaSetting = 1;
        }
    }
}

