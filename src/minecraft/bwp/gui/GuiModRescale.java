package bwp.gui;

import bwp.gui.elements.template.ClientButton;
import bwp.gui.hud.HUDManager;
import bwp.utils.RoundedRectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;

public class GuiModRescale extends GuiScreen{
    private final GuiScreen previousScreen;
    private Minecraft mc = Minecraft.getMinecraft();
    private HUDManager hudManager = HUDManager.getInstance();


    public GuiModRescale(GuiScreen previousScreen) {
        this.previousScreen = previousScreen;
    }

    @Override
    protected void actionPerformed(GuiButton button) {


        if (button.id == 0)
        {
            hudManager.openConfigScreen();
        }
        if (button.id == 1)
        {
            hudManager.openToggleScreen();
        }
    }

    @Override
    public void drawScreen(int x2, int y2, float z2) {
        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

        ScaledResolution sr = new ScaledResolution(mc);
        Gui.drawRect(100, 100, 100, 100, 100);

        Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        //TODO - ADD
        this.buttonList.add(new ClientButton(0, this.width / 2 - 50, this.height / 2 - 20,  98, 20, I18n.format("Adjust Positions", new Object[0])));
        this.buttonList.add(new ClientButton(1, this.width / 2 - 50, this.height / 2 + 5,  98, 20, I18n.format("Toggle Mods", new Object[0])));
        this.buttonList.add(new ClientButton(2, this.width / 2 - 50, this.height / 2 - 45,  98, 20, I18n.format("Main Menu", new Object[0])));
        Gui.drawRect(100, 100, 100, 100, 100);
        RoundedRectangle roundedRect = new RoundedRectangle();
        roundedRect.roundedRectangle(100, 100, 100,100, 100, Color.WHITE);

        Keyboard.enableRepeatEvents(true);



    }


    @Override
    protected void mouseClicked(int x2, int y2, int button) {
        try {
            super.mouseClicked(x2, y2, button);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }



}
