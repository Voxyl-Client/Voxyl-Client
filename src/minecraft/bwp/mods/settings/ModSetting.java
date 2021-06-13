package bwp.mods.settings;

import bwp.mods.Mod;

public class ModSetting {
    final int id;
    final String name;
    final ModSettingType type;
    final Mod mod;
    Object value;

    public ModSetting(int id, String name, ModSettingType type, Mod mod, Object value) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mod = mod;
        this.value = value;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ModSettingType getType() {
        return type;
    }

    public Mod getMod() {
        return mod;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
