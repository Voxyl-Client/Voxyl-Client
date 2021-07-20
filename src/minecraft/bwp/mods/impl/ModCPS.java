package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;

import java.util.ArrayList;
import java.util.List;

public class ModCPS extends HUDMod {

    private final List<Long> primaryClicks = new ArrayList<>();
    private final List<Long> secondaryClicks = new ArrayList<>();
    private boolean wasPrimaryPressed;
    private boolean wasSecondaryPressed;

    public ModCPS() {
        super("CPS");
    }

    @Override
    protected void init() {
        settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("00 | 00 CPS") + 5;
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render() {
        final boolean pressed = Mouse.isButtonDown(0);
        if(pressed != this.wasPrimaryPressed){
            long lastPressedPrimary = System.currentTimeMillis();
            this.wasPrimaryPressed = pressed;
            if(pressed){
                this.primaryClicks.add(lastPressedPrimary);
            }
        }
        final boolean pressedR = Mouse.isButtonDown(1);
        if(pressedR != this.wasSecondaryPressed){
            long lastPressedSecondary = System.currentTimeMillis();
            this.wasSecondaryPressed = pressedR;
            if(pressedR){
                this.secondaryClicks.add(lastPressedSecondary);
            }
        }

        Render.drawHUDString(getPrimaryCPS() + " | " + getSecondaryCPS() + " CPS", renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());
    }

    @Override
    public void onSettingChange(int settingId, GuiIntractable intractable) {
        if (settingId == 0) {
            settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
        }
    }

    private int getPrimaryCPS(){
        final long time = System.currentTimeMillis();
        this.primaryClicks.removeIf(aLong -> aLong + 1000 < time);
        return this.primaryClicks.size();
    }

    private int getSecondaryCPS(){
        final long time = System.currentTimeMillis();
        this.secondaryClicks.removeIf(aLong -> aLong + 1000 < time);
        return this.secondaryClicks.size();
    }
}
