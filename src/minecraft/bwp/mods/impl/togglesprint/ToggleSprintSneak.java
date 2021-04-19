package bwp.mods.impl.togglesprint;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;

public class ToggleSprintSneak extends ModDraggable {
    
    private ScreenPosition pos;

    private String textToRender = "";

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
        return font.getStringWidth(textToRender);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }
    public void renderDummy(ScreenPosition pos){
        textToRender = "[Sprinting] (Toggled)";
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);


    }
    @Override
    public void render(ScreenPosition pos) {
        textToRender = mc.thePlayer.movementInput.getDisplayText();
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);

    }
}
