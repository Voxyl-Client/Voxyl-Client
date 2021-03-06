package bwp.gui;


import bwp.gui.hud.HUDManager;
import bwp.utils.BWPUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.Color;
import java.io.IOException;

import javax.vecmath.Vector3d;
public class GuiModMain extends GuiScreen{

    private final GuiScreen previousScreen;
    private Minecraft mc = Minecraft.getMinecraft();
    private HUDManager hudManager = HUDManager.getInstance();
    BWPUtils bwpUtils = new BWPUtils();

    




	    public GuiModMain(GuiScreen previousScreen) {
	        this.previousScreen = previousScreen;
	    }


	    @Override
	    public void drawScreen(int x2, int y2, float z2) {
	        Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));

	        ScaledResolution sr = new ScaledResolution(mc);
	        this.buttonList.clear();

			Render.drawRoundedRectFromV3D(new Vector3d(5,5,0), new Vector3d(50,50,0), 10, Color.WHITE);

	        Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
	        super.drawScreen(x2, y2, z2);

	        bwpUtils.drawEntityOnScreen(100,100, 100, 1, mc.thePlayer);
	    }

	    @Override
	    public void initGui() {

	        ScaledResolution sr = new ScaledResolution(mc);
	        this.buttonList.clear();
	        bwpUtils.drawEntityOnScreen(100,100, 100, 1, mc.thePlayer);


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


