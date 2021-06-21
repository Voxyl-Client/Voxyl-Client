package bwp.mods.impl.togglemotion;


import bwp.mods.ModInstances;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiShareToLan;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerLoginClient;
import net.minecraft.client.renderer.InventoryEffectRenderer;
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
    private boolean sneakingToggled = false;
    private Minecraft mc;
    float f = 0.8F;

    public BWPMovementInput(GameSettings gameSettings){
        this.gameSettings = gameSettings;
        this.mc = Minecraft.getMinecraft();

    }

    public void disableSprint() {
        sprintingToggled = false;
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

        if(ModInstances.getToggleSneak().getSettings().getEnabled()){
            if(gameSettings.keyBindSneak.isKeyDown()){
                if(sneakWasPressed == 0){
                    if(sneakingToggled){
                        sneakWasPressed = -1;
                    }
                    else if(player.isRiding() || player.capabilities.isFlying){
                        sneakWasPressed = ModInstances.getToggleSneak().keyHoldTicks + 1;
                    }
                    else{
                        sneakWasPressed = 1;
                    }
                    sneakingToggled = !sneakingToggled;
                }
                else if(sneakWasPressed > 0){
                    sneakWasPressed++;
                }
            }
            else{
                if((ModInstances.getToggleSneak().keyHoldTicks > 0) && (sneakWasPressed > ModInstances.getToggleSneak().keyHoldTicks)){
                    sneakingToggled = false;
                }
                sneakWasPressed = 0;
            }
            boolean sneakingFlags = mc.currentScreen instanceof InventoryEffectRenderer ||
                    mc.currentScreen instanceof GuiContainer;
            if (sneakingFlags && sneakingToggled) {
                sneak = false;
            } else {
                sneak = sneakingToggled;
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
        if(ModInstances.getToggleSprint().getSettings().getEnabled()){
            if(gameSettings.keyBindSprint.isKeyDown()){
                if(sprintWasPressed == 0){
                    if(sprint){
                        sprintWasPressed = 1;
                    }
                    else if(player.capabilities.isFlying){
                        sprintWasPressed = ModInstances.getToggleSprint().keyHoldTicks + 1;

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
                if((ModInstances.getToggleSprint().keyHoldTicks > 0) && (sprintWasPressed > ModInstances.getToggleSprint().keyHoldTicks)){
                    sprint = false;
                }
                sprintWasPressed = 0;
            }

        }
        else{
            sprint = false;
        }
        if(mc.gameSettings.keyBindSprint.isPressed() && ModInstances.getToggleSprint().getSettings().getEnabled()) {
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

        if((boolean) ModInstances.getToggleSprint().getSettings().getSetting(0).getValue() && player.capabilities.isCreativeMode && player.capabilities.isFlying && (mc.getRenderViewEntity() == player) == sprint){
            if(originalFlySpeed < 0.0F || this.player.capabilities.getFlySpeed() != boostedFlySpeed){
                originalFlySpeed = this.player.capabilities.getFlySpeed();

            }
            boostedFlySpeed = originalFlySpeed * (float) (double) ModInstances.getToggleSprint().getSettings().getSetting(1).getValue();
            player.capabilities.setFlySpeed(boostedFlySpeed);

            if(sneak){
                player.motionY -= 0.15D * (double) ModInstances.getToggleSprint().getSettings().getSetting(1).getValue() - 1.0F;
            }
            if(jump){
                player.motionY += 0.15D * (double) ModInstances.getToggleSprint().getSettings().getSetting(1).getValue() - 1.0F;
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
    public String getSprintText(){
        String displayText = "";

        boolean isFlying = mc.thePlayer.capabilities.isFlying;
        boolean isRiding = mc.thePlayer.isRiding();
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

        else if(sprint && !isFlying && !isRiding){
            if(isHoldingSprint){

                displayText += "[Sprinting (Held)]  ";
            }
            else{
                displayText += "[Sprinting (Toggled)]  ";
            }
        }

        return displayText.trim();
    }

    public String getSneakText(){
        String displayText = "";

        boolean isFlying = mc.thePlayer.capabilities.isFlying;
        boolean isRiding = mc.thePlayer.isRiding();
        boolean isHoldingSneak = gameSettings.keyBindSneak.isKeyDown();

        if(sneak){
            if(isFlying){
                displayText += "[Descending]  ";
            }else if(isRiding){
                displayText += "[Dismounting]  ";
            }
            else if(isHoldingSneak){
                displayText += "[Sneaking (Held)]  ";
            }else{
                displayText += "[Sneaking (Toggled)]  ";
            }
        }

        return displayText.trim();
    }
}
