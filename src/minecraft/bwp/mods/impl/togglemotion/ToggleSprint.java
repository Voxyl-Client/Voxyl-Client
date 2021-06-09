package bwp.mods.impl.togglemotion;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;

public class ToggleSprint extends ModDraggable{
    private String textToRender = "";

    //settings
    public boolean flyBoost = true;
    public float flyBoostFactor = 4;
    public int keyHoldTicks = 7;
    private boolean chroma = false;
    private int color = -1;

    public ToggleSprint(String name) {
        super(name);
    }

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

            Render.drawChromaString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);

        }else{
            textToRender = "[Sprinting] (Toggled)";
            Render.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
        }
    }

    //TODO - GETTERS AND SETTERS
    @Override
    public void render(ScreenPosition pos) {
        textToRender = mc.thePlayer.movementInput.getSprintText();
        Render.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
    }
}
