package bwp.mods.impl.togglemotion;

import bwp.mods.HUDMod;
import bwp.utils.Render;

public class ToggleSneak extends HUDMod {
    private String textToRender = "";

    //settings
    public int keyHoldTicks = 7;
    private boolean chroma = false;
    private int color = -1;

    public ToggleSneak() {
        super("Toggle Sneak");
    }

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

    @Override
    public void renderDummy() {
        if (chroma) {
            textToRender = "[Sneaking] (Toggled)";

            Render.drawChromaString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true);

        }else{
            textToRender = "[Sneaking] (Toggled)";
            Render.drawString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
        }
    }

    @Override
    public void render() {
        textToRender = mc.thePlayer.movementInput.getSneakText();
        Render.drawString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
    }
}
