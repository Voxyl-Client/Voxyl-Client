package bwp.mods.impl.togglesprint;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;

public class ToggleSprintSneak extends ModDraggable{
    


    private String textToRender = "";

    //settings
    public boolean flyBoost = true;
    public float flyBoostFactor = 4;
    public int keyHoldTicks = 7;
    private boolean chroma = true;





    @Override
    public int getWidth() {
        return font.getStringWidth(textToRender);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }
    public void renderDummy(ScreenPosition pos) {
        if (chroma) {
            textToRender = "[Sprinting] (Toggled)";

            Render.drawChromaString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), true);

        }else{
            textToRender = "[Sprinting] (Toggled)";
            font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);
        }
    }



    @Override
    public void render(ScreenPosition pos) {
        textToRender = mc.thePlayer.movementInput.getDisplayText();
        font.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), -1);

    }
}
