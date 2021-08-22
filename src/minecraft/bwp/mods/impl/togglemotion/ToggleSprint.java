package bwp.mods.impl.togglemotion;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;

import java.text.DecimalFormat;

public class ToggleSprint extends HUDMod {
    public ToggleSprint() {
        super("Toggle Sprint");
    }

    @Override
    protected void init() {
        settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
        settings.addSetting(new ModSetting(1, "Toggle Sneak", ModSettingType.CHECKBOX, this, false));
        settings.addSetting(new ModSetting(2, "Show Text", ModSettingType.CHECKBOX, this, true));
    }

    @Override
    public int getWidth() {
        return font.getStringWidth("[Sprinting Toggled]" + 5);
    }

    @Override
    public int getHeight() {
        return font.FONT_HEIGHT;
    }

    @Override
    public void render() {
        if ((boolean) settings.getSetting(2).getValue()) {
            String textToRender = mc.thePlayer.movementInput.getText();

            Render.drawHUDString(textToRender, renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());
        }
    }

    @Override
    public void renderDummy() {
        Render.drawHUDString("[Sprinting Toggled]", renderInfo.getX(), renderInfo.getY(), renderInfo.getScale(), (boolean) settings.getSetting(0).getValue());
    }

    @Override
    public void onSettingChange(int settingId, GuiIntractable intractable) {
        if (settingId == 0) settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
        else if (settingId == 1) settings.getSetting(1).setValue(((CheckBoxButton) intractable).isChecked());
        else if (settingId == 2) settings.getSetting(2).setValue(((CheckBoxButton) intractable).isChecked());
    }
}
