package bwp.mods.impl.togglemotion;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;

public class ToggleSprint extends HUDMod {
    private String textToRender = "";

    //settings
    public int keyHoldTicks = 7;
    private boolean chroma = false;
    private int color = -1;

    public ToggleSprint() {
        super("Toggle Sprint");
    }

    @Override
    protected void init() {
        settings.addSetting(new ModSetting(0, "Fly Boost", ModSettingType.CHECKBOX, this, true));
        settings.addSetting(new ModSetting(1, "Fly Boost Factor", ModSettingType.SLIDER, this, 4.0));
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
    public void renderDummy() {
        if (chroma) {
            textToRender = "[Sprinting] (Toggled)";

            Render.drawChromaString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true);

        }else{
            textToRender = "[Sprinting] (Toggled)";
            Render.drawString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
        }
    }

    @Override
    public void render() {
        textToRender = mc.thePlayer.movementInput.getSprintText();
        Render.drawString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), true, color);
    }

    @Override
    public void onSettingChange(int settingId, GuiIntractable intractable) {
        if (settingId == 0) {
            settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
        }
    }
}
