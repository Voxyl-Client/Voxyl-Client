package bwp.mods.impl.togglesprint;


import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;

import java.text.DecimalFormat;

public class BWPMovementInput extends MovementInput {

    private boolean sprint = false;
    private GameSettings gameSettings;
    private int sneakWasPressed = 0;
    private int sprintWasPressed = 0;
    private EntityPlayerSP player;
    private float originalFlySpeed = -1.0F;
    private float boostedFlySpeed = 0;
    private boolean sprintingToggled = false;
    private Minecraft mc;
    float f = 0.8F;

    public BWPMovementInput(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        this.mc = Minecraft.getMinecraft();

    }
    //Sneak
    @Override
    public void updatePlayerMoveState(){
        player = mc.thePlayer;
        moveStrafe = 0.0F;
        moveForward = 0.0F;

        if(gameSettings.keyBindForward.isKeyDown()){
            moveForward++;

        }
        if(gameSettings.keyBindBack.isKeyDown()){
            moveForward--;

        }
        if(gameSettings.keyBindLeft.isKeyDown()){
            moveStrafe++;

        }
        if(gameSettings.keyBindRight.isKeyDown()){
            moveStrafe--;

        }
        jump = gameSettings.keyBindJump.isKeyDown();

        if(ModInstances.getToggleSprintSneak().isEnabled()){
            if(gameSettings.keyBindSneak.isKeyDown()){
                if(sneakWasPressed == 0){
                    if(sneak){
                        sneakWasPressed = -1;
                    }
                    else if(player.isRiding() || player.capabilities.isFlying){
                        sneakWasPressed = ModInstances.getToggleSprintSneak().keyHoldTicks + 1;
                    }
                    else{
                        sneakWasPressed = 1;
                    }
                    sneak = !sneak;
                }
                else if(sneakWasPressed > 0){
                    sneakWasPressed++;
                }
            }
            else{
                if((ModInstances.getToggleSprintSneak().keyHoldTicks > 0) && (sneakWasPressed > ModInstances.getToggleSprintSneak().keyHoldTicks)){
                    sneak = false;

                }
                sneakWasPressed = 0;
            }

        }
        else{
            sneak = gameSettings.keyBindSneak.isKeyDown();
        }
        if(sneak){
            moveStrafe *= 0.3F;
            moveForward *= 0.3F;
        }

        //Sprint module
        if(ModInstances.getToggleSprintSneak().isEnabled()){
            if(gameSettings.keyBindSprint.isKeyDown()){
                if(sprintWasPressed == 0){
                    if(sprint){
                        sprintWasPressed = 1;
                    }
                    else if(player.capabilities.isFlying){
                        sprintWasPressed = ModInstances.getToggleSprintSneak().keyHoldTicks + 1;

                    }
                    else{
                        sprintWasPressed = 1;
                    }
                    sprint = !sprint;
                }
                else if(sprintWasPressed > 0){
                    sprintWasPressed++;
                }
            }
            else{
                if((ModInstances.getToggleSprintSneak().keyHoldTicks > 0) && (sprintWasPressed > ModInstances.getToggleSprintSneak().keyHoldTicks)){
                    sprint = false;
                }
                sprintWasPressed = 0;
            }

        }
        else{
            sprint = false;
        }
        if(mc.gameSettings.keyBindSprint.isPressed()) {
            if(mc.thePlayer.isSprinting() && !sprintingToggled) sprintingToggled = true;
            else if(!mc.thePlayer.isSprinting()) sprintingToggled = !sprintingToggled;
        }

        boolean flags = !mc.thePlayer.movementInput.sneak &&
                (mc.thePlayer.getFoodStats().getFoodLevel() > 6.0F || mc.thePlayer.capabilities.allowFlying) &&
                !mc.thePlayer.isPotionActive(Potion.blindness) &&
                mc.thePlayer.movementInput.moveForward >= f &&
                !mc.thePlayer.isSprinting() &&
                !mc.thePlayer.isUsingItem() &&
                !mc.thePlayer.isCollidedHorizontally &&
                sprintingToggled;
        if (flags) { mc.thePlayer.setSprinting(true); }

        if(ModInstances.getToggleSprintSneak().flyBoost && player.capabilities.isCreativeMode && player.capabilities.isFlying && (mc.getRenderViewEntity() == player) == sprint){
            if(originalFlySpeed < 0.0F || this.player.capabilities.getFlySpeed() != boostedFlySpeed){
                originalFlySpeed = this.player.capabilities.getFlySpeed();

            }
            boostedFlySpeed = originalFlySpeed * ModInstances.getToggleSprintSneak().flyBoostFactor;
            player.capabilities.setFlySpeed(boostedFlySpeed);

            if(sneak){
                player.motionY -= 0.15D * (double)(ModInstances.getToggleSprintSneak().flyBoostFactor - 1.0F);
            }
            if(jump){
                player.motionY += 0.15D * (double)(ModInstances.getToggleSprintSneak().flyBoostFactor - 1.0F);
            }
        }
        else{
            if(player.capabilities.getFlySpeed() == boostedFlySpeed){
                this.player.capabilities.setFlySpeed(originalFlySpeed);
            }
            originalFlySpeed = -1.0F;
        }
    }
    private static final DecimalFormat df = new DecimalFormat("#.0");
    public String getDisplayText(){
        String displayText = "";

        boolean isFlying = mc.thePlayer.capabilities.isFlying;
        boolean isRiding = mc.thePlayer.isRiding();
        boolean isHoldingSneak = gameSettings.keyBindSneak.isKeyDown();
        boolean isHoldingSprint = gameSettings.keyBindSprint.isKeyDown();

        if(isFlying){
            if(originalFlySpeed > 0.0F){
                displayText = "[Flying (" + df.format(boostedFlySpeed / originalFlySpeed ) + "x Boost)]  ";

            }else{
                displayText = "[Flying]  ";
            }
        }
        if(isRiding){
            displayText += "[Riding  ";
        }
        if(sneak){
            if(isFlying){
                displayText += "[Descending]  ";
            }else if(isRiding){
                displayText += "[Dismounting]  ";
            }
            else if(isHoldingSneak){
                displayText += "[Sneaking (Key Held)]  ";
            }else{
                displayText += "[Sneaking (Key Toggled)]  ";
            }
        }

        else if(sprint && !isFlying && !isRiding){
            if(isHoldingSprint){

                displayText += "[Sprinting (Key Held)]  ";
            }
            else{
                displayText += "[Sprinting (Key Toggled)]  ";
            }
        }

        return displayText.trim();
    }
}
