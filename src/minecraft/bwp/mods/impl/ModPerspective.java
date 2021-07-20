package bwp.mods.impl;

import bwp.event.EventManager;
import bwp.event.EventTarget;
import bwp.event.impl.KeyPressEvent;
import bwp.mods.Mod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

public class ModPerspective extends Mod {

    private boolean perspectiveToggled = false;

    private float cameraYaw = 0F;

    private float cameraPitch = 0F;

    private int previousPerspective = 0; //previous f5 state

    public ModPerspective() {
        super("Freelook");
        EventManager.register(this);
    }

    @Override
    protected void init() {
        settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
    }

    @EventTarget
    public void keyBoardEvent(KeyPressEvent e) {
        if (e.getKey() == mc.gameSettings.CLIENT_PERSPECTIVE.getKeyCode() && settings.getEnabled()) {
            //this means you have to hold down key
            boolean returnOnRelease = true;
            if (Keyboard.getEventKeyState()) {
                perspectiveToggled = !perspectiveToggled;

                cameraYaw = mc.thePlayer.rotationYaw;
                cameraPitch = mc.thePlayer.rotationPitch;

                if (perspectiveToggled) {
                    previousPerspective = mc.gameSettings.thirdPersonView;
                    mc.gameSettings.thirdPersonView = 1;
                } else {
                    mc.gameSettings.thirdPersonView = previousPerspective;
                }
            } else if (returnOnRelease) {
                perspectiveToggled = false;
                mc.gameSettings.thirdPersonView = previousPerspective;
            }
        }
        if (Keyboard.getEventKey() == mc.gameSettings.keyBindTogglePerspective.getKeyCode()) {
            perspectiveToggled = false;
        }
    }

    public float getCameraYaw() {
        return perspectiveToggled ? cameraYaw : mc.thePlayer.rotationYaw;
    }

    public float getCameraPitch() {
        return perspectiveToggled ? cameraPitch : mc.thePlayer.rotationPitch;
    }

    public boolean overrideMouse() {
        if (mc.inGameHasFocus && Display.isActive()) {
            if (!perspectiveToggled) {
                return true;
            }
            mc.mouseHelper.mouseXYChange();
            float f1 = mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
            float f2 = f1 * f1 * f1 * 8.0F;
            float f3 = (float) mc.mouseHelper.deltaX * f2;
            float f4 = (float) -mc.mouseHelper.deltaY * f2;

            cameraYaw += f3 * 0.15F;
            cameraPitch += f4 * 0.15F;

            if (cameraPitch > 90) cameraPitch = 90;
            if (cameraPitch < -90) cameraPitch = -90;

        }
        return false;
    }
}