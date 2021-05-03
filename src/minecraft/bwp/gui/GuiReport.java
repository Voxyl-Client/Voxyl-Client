package bwp.gui;

import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiReport extends GuiScreen {
    private final GuiScreen previousScreen;
    private GuiTextField username;

    public GuiReport(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {

        if (button.id == 0)
        {
            mc.thePlayer.sendChatMessage("/" + "wdr" + " " + this.username.getText() + " " + "Speed");
        }
        if (button.id == 1)
        {
            mc.thePlayer.sendChatMessage("/" + "wdr" + " " + this.username.getText() + " " + "KillAura");
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {

        ScaledResolution sr = new ScaledResolution(mc);
        this.username.drawTextBox();
        Gui.drawCenteredString(mc.fontRendererObj, "Watchdog Report!", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.buttonList.clear();
        //TODO - ADD
        //this.buttonList.add(new GuiTutorialButton(0, this.width / 2 - 50, this.height / 2 - 20,  98, 20, I18n.format("Speed", new Object[0])));
        //this.buttonList.add(new GuiTutorialButton(1, this.width / 2 - 50, this.height / 2 + 5,  98, 20, I18n.format("KillAura", new Object[0])));
        this.username = new GuiTextField(100, this.fontRendererObj, this.width / 2 - 50, sr.getScaledHeight() / 2 - 50, 100, 20);
        this.username.setFocused(true);
        Keyboard.enableRepeatEvents(true);

        
        //TODO  -ADAPT THIS IS FUCKING USEFUL BOIS
    }

    @Override
    protected void keyTyped(char character, int key) {
        try {
            super.keyTyped(character, key);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        if (character == '\t') {
            if (!this.username.isFocused()) {
                this.username.setFocused(true);
            } else {

            }
        }
        if (character == '\r') {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
        this.username.textboxKeyTyped(character, key);
    }

    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.username.mouseClicked(x2, y2, button);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void updateScreen() {
        this.username.updateCursorCounter();
    }
}

