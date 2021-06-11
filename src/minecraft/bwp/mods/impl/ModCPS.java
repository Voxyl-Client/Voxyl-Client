package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import bwp.utils.Render;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends ModDraggable {

    private List<Long> clicks = new ArrayList<Long>();
    private List<Long> clicksRight = new ArrayList<Long>();
    private boolean wasPressed;
    private boolean wasPressedR;
    private long lastPressed;
    private long lastPressedR;

    public ModCPS(String name) {
        super(name);
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("CPS: 00 | 00") + 5;
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render(ScreenPosition pos) {
        final boolean pressed = Mouse.isButtonDown(0);
        if(pressed != this.wasPressed){
            this.lastPressed = System.currentTimeMillis();
            this.wasPressed = pressed;
            if(pressed){
                this.clicks.add(this.lastPressed);
            }
        }
        final boolean pressedR = Mouse.isButtonDown(1);
        if(pressedR != this.wasPressedR){
            this.lastPressedR = System.currentTimeMillis();
            this.wasPressedR = pressedR;
            if(pressedR){
                this.clicksRight.add(this.lastPressedR);
            }
        }
        if(chroma){
            Render.drawChromaString("CPS : " + getCPS() + " | " + getRightCPS(), pos.getAbsoluteX() , pos.getAbsoluteY(), pos.getScale(), true);
        }else {
            Render.drawString("CPS : " + getCPS() + " | " + getRightCPS(), pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);
        }

    }

    @Override
    public void renderDummy(ScreenPosition pos) {
        if(chroma){
            Render.drawChromaString("CPS : 10 | 10" , pos.getAbsoluteX() , pos.getAbsoluteY(), pos.getScale(), true);
        }
        else {
            Render.drawString("CPS : 10 | 10", pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getScale(), true);
        }
    }
    private int getCPS(){
        final long time = System.currentTimeMillis();
        this.clicks.removeIf(aLong -> aLong + 1000 < time);
        return this.clicks.size();
    }
    private int getRightCPS(){
        final long time = System.currentTimeMillis();
        this.clicksRight.removeIf(aLong -> aLong + 1000 < time);
        return this.clicksRight.size();
    }
}
