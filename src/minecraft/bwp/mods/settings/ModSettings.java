package bwp.mods.settings;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModSettings {
    protected final List<ModSetting> settings;

    protected boolean enabled = false;

    public ModSettings(ModSetting...settings) {
        this.settings = Arrays.stream(settings).collect(Collectors.toList());
    }

    public List<ModSetting> getSettings() {
        return settings;
    }

    public void addSetting(ModSetting setting) {
        settings.add(setting);
    }

    public ModSetting getSetting(int id) {
        for (ModSetting setting : settings) {
            if (setting.getId() == id) {
                return setting;
            }
        }
        return null;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean getEnabled() {
        return enabled;
    }
}
