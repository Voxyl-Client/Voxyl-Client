package bwp.mods.settings;

import bwp.mods.Mod;

public class ModSetting {
    final int id;
    final String name;
    final ModSettingType type;
    final Mod mod;
    final Object baseValue;

    public ModSetting(int id, String name, ModSettingType type, Mod mod, Object baseValue) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.mod = mod;
        this.baseValue = baseValue;
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

    public Object getBaseValue() {
        return baseValue;
    }
}
