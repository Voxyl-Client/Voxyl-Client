package bwp.gui;


import bwp.gui.hud.HUDManager;
import bwp.utils.ClientButtons;
import bwp.utils.RoundedRect;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;

import javax.vecmath.Vector3d;
public class GuiModMain extends GuiScreen{

    private final GuiScreen previousScreen;
    private Minecraft mc = Minecraft.getMinecraft();
    private HUDManager hudManager = HUDManager.getInstance();

    




	    public GuiModMain(GuiScreen previousScreen) {
	        this.previousScreen = previousScreen;
	    }


	    @Override
	    public void drawScreen(int x2, int y2, float z2) {
	        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

	        ScaledResolution sr = new ScaledResolution(mc);
	        this.buttonList.clear();
	        RoundedRect roundedRect = new RoundedRect();
	        roundedRect.renderRoundedQuad(new Vector3d(5,5,0), new Vector3d(50,50,0), 10, Color.WHITE);

	        Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
	        super.drawScreen(x2, y2, z2);
	    }

	    @Override
	    public void initGui() {

	        ScaledResolution sr = new ScaledResolution(mc);
	        this.buttonList.clear();


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


