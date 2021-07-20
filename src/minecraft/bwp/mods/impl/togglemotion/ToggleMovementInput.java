package bwp.mods.impl.togglemotion;

import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

public class ToggleMovementInput extends MovementInput {
    private boolean sprintingToggled = false;
    private boolean sneakingToggled = false;

    private final Minecraft mc = Minecraft.getMinecraft();

    // For detecting on button release
    private boolean isSprintKeyDown = false;
    private boolean isSneakKeyDown = false;

    float f = 0.8F;

    @Override
    public void updatePlayerMoveState() {
        moveStrafe = 0.0F;
        moveForward = 0.0F;

        if(mc.gameSettings.keyBindForward.isKeyDown()){
            moveForward++;

        }
        if(mc.gameSettings.keyBindBack.isKeyDown()){
            moveForward--;

        }
        if(mc.gameSettings.keyBindLeft.isKeyDown()){
            moveStrafe++;

        }
        if(mc.gameSettings.keyBindRight.isKeyDown()){
            moveStrafe--;

        }
        jump = mc.gameSettings.keyBindJump.isKeyDown();

        // Sprint
        if (ModInstances.getToggleSprint().getSettings().getEnabled()) {
            if (mc.gameSettings.keyBindSprint.isKeyDown() && mc.gameSettings.keyBindSprint.isKeyDown() != isSprintKeyDown) {
                sprintingToggled = !sprintingToggled;
            }

            isSprintKeyDown = mc.gameSettings.keyBindSprint.isKeyDown();

            boolean flags = sprintingToggled &&
                    (mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying) &&
                    !mc.thePlayer.isPotionActive(Potion.blindness) &&
                    mc.thePlayer.movementInput.moveForward >= f &&
                    !mc.thePlayer.isUsingItem() &&
                    !mc.thePlayer.isCollidedHorizontally;

            mc.thePlayer.setSprinting(flags);
        } else {
            boolean flags = (mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying) &&
                    !mc.thePlayer.isPotionActive(Potion.blindness) &&
                    mc.thePlayer.movementInput.moveForward >= f &&
                    !mc.thePlayer.isUsingItem() &&
                    !mc.thePlayer.isCollidedHorizontally &&
                    (mc.gameSettings.keyBindSprint.isKeyDown() || mc.thePlayer.isSprinting());

            mc.thePlayer.setSprinting(flags);
        }

        // Sneak
        if (ModInstances.getToggleSprint().getSettings().getEnabled() && (boolean) ModInstances.getToggleSprint().getSettings().getSetting(1).getValue()) {
            if (mc.gameSettings.keyBindSneak.isKeyDown() && mc.gameSettings.keyBindSneak.isKeyDown() != isSneakKeyDown) {
                sneakingToggled = !sneakingToggled;

                boolean flags = sneakingToggled &&
                        !(mc.currentScreen instanceof InventoryEffectRenderer || mc.currentScreen instanceof GuiContainer);

                mc.thePlayer.setSneaking(flags);
                sneak = flags;
            }

            isSneakKeyDown = mc.gameSettings.keyBindSneak.isKeyDown();
        } else {
            boolean flags = mc.gameSettings.keyBindSneak.isKeyDown() &&
                    !(mc.currentScreen instanceof InventoryEffectRenderer || mc.currentScreen instanceof GuiContainer);

            mc.thePlayer.setSneaking(flags);
            sneak = flags;
        }

        if (mc.thePlayer.isSneaking()) {
            moveStrafe *= 0.3F;
            moveForward *= 0.3F;
        }
    }

    public String getText() {
        if (sprintingToggled && !mc.thePlayer.capabilities.isFlying) return "[Sprinting Toggled]";
        else if (sprintingToggled) {
            return "[Flying]";
        }
        else return "";
    }
}
