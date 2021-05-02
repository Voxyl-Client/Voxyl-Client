package bwp.mods.impl.togglesprint;

import bwp.mods.ModDraggable;
import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.MovementInput;

public class ToggleSneak extends MovementInput {
    private boolean sprint = false;
    private GameSettings gameSettings;
    private int sneakWasPressed = 0;
    private int sprintWasPressed = 0;
    private EntityPlayerSP player;
    private float originalFlySpeed = -1.0F;
    private float boostedFlySpeed = 0;
    private Minecraft mc;
    float f = 0.8F;

    public ToggleSneak(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
        this.mc = Minecraft.getMinecraft();

    }

    //Sneak
    @Override
    public void updatePlayerMoveState() {
        player = mc.thePlayer;
        moveStrafe = 0.0F;
        moveForward = 0.0F;

        if (gameSettings.keyBindForward.isKeyDown()) {
            moveForward++;

        }
        if (gameSettings.keyBindBack.isKeyDown()) {
            moveForward--;

        }
        if (gameSettings.keyBindLeft.isKeyDown()) {
            moveStrafe++;

        }
        if (gameSettings.keyBindRight.isKeyDown()) {
            moveStrafe--;

        }
        jump = gameSettings.keyBindJump.isKeyDown();

        //Sneak

        if (ModInstances.getToggleSprintSneak().isEnabled()) {
            if (gameSettings.keyBindSneak.isKeyDown()) {
                if (sneakWasPressed == 0) {
                    if (sneak) {
                        sneakWasPressed = -1;
                    } else if (player.isRiding() || player.capabilities.isFlying) {
                        sneakWasPressed = ModInstances.getToggleSprintSneak().keyHoldTicks + 1;
                    } else {
                        sneakWasPressed = 1;
                    }
                    sneak = !sneak;
                } else if (sneakWasPressed > 0) {
                    sneakWasPressed++;
                }
            } else {
                if ((ModInstances.getToggleSprintSneak().keyHoldTicks > 0) && (sneakWasPressed > ModInstances.getToggleSprintSneak().keyHoldTicks)) {
                    sneak = false;

                }
                sneakWasPressed = 0;
            }

        } else {
            sneak = gameSettings.keyBindSneak.isKeyDown();
        }
        if (sneak) {
            moveStrafe *= 0.3F;
            moveForward *= 0.3F;
        }
    }
}
