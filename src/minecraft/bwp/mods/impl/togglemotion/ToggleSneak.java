package bwp.mods.impl.togglemotion;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;

public class ToggleSneak extends ModDraggable{
    private String textToRender = "";

    //settings
    public boolean flyBoost = true;
    public float flyBoostFactor = 4;
    public int keyHoldTicks = 7;
    private boolean chroma = false;
    private int color = -1;

    @Override
    public int getWidth() {
        return font.getStringWidth(textToRender);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public int getColor() {
        return color;
    }

    @Override
    public void changeColor(int colorin) {
        color = colorin;
    }

    public void renderDummy(ScreenPosition pos) {
        if (chroma) {
            textToRender = "[Sneaking] (Toggled)";

            Render.drawChromaString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);

        }else{
            textToRender = "[Sneaking] (Toggled)";
            Render.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
        }
    }

    @Override
    public void render(ScreenPosition pos) {
        textToRender = mc.thePlayer.movementInput.getSneakText();
        Render.drawString(textToRender, pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true, color);
    }
}
