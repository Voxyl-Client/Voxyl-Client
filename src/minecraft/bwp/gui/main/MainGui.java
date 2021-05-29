package bwp.gui.main;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import bwp.gui.hud.HUDManager;
import bwp.utils.ClientButtons;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class MainGui extends GuiScreen {
	
	    private final GuiScreen previousScreen;
	    private Minecraft mc = Minecraft.getMinecraft();
	    private HUDManager hudManager = HUDManager.getInstance();
	    private int prevPosX;
	    private int prevPosY;
	    
	    
	    
	    


        //Setting a parent GuiScreen
	    public MainGui(GuiScreen previousScreen) {
	        this.previousScreen = previousScreen;
	    }
	    

	    @Override
	    //Casting button actions 
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
	    
	    //All text, and boxes are drawn here, but not buttons

	    @Override
	    public void drawScreen(int x2, int y2, float z2) {


	        ScaledResolution sr = new ScaledResolution(mc);


	        Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
	        int newX = (this.width / 2) - (this.width / 4) - (this.width / 5);
	        int newY = (int) (this.height / 2 -  this.height / 3) - this.height /14;
	        int newHeight = (int) ((int)this.height - this.height / 5);
	        int newWidth = (int) ((int) this.width - (this.width / 9.8));
	        
	        
	        
	        //roundedRectangle(newX , newY, newWidth, newHeight, 5, Color.decode("#3A3636"));
	        super.drawScreen(x2, y2, z2);
	    }
	    
	    //Called to initialise the Gui with buttons etc

	    @Override
	    public void initGui() {

	        ScaledResolution sr = new ScaledResolution(mc);
	        this.buttonList.clear();
	        this.buttonList.add(new ClientButtons(0, this.width / 2 - 50, this.height / 2 - 20,  98, 20, I18n.format("Adjust Positions", new Object[0])));
	        this.buttonList.add(new ClientButtons(1, this.width / 2 - 50, this.height / 2 + 5,  98, 20, I18n.format("Toggle Mods", new Object[0])));

	        if(!(mc.gameSettings.ofFastRender) && OpenGlHelper.shadersSupported && this.mc.getRenderViewEntity() instanceof EntityPlayer) {
	            if (this.mc.entityRenderer.theShaderGroup != null)
	                this.mc.entityRenderer.theShaderGroup.deleteShaderGroup();
	        
	        	this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
	        }
	        Keyboard.enableRepeatEvents(true);
	    }

        //Getting mouse clicks 
	    @Override
	    protected void mouseClicked(int x2, int y2, int button) {
	        try {
	            super.mouseClicked(x2, y2, button);
	        }
	        catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    
	    //Unloading shaders etc

	    @Override
	    public void onGuiClosed() {
	        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
	        super.onGuiClosed();
	        Keyboard.enableRepeatEvents(false);
	    }
	    //Maths to draw arcs for roudning
	    private void drawArc(int x, int y, int radius, int startAngle, int endAngle, Color color) {
	        
	        GL11.glPushMatrix();
	        GL11.glEnable(3042);
	        GL11.glDisable(3553);
	        GL11.glBlendFunc(770, 771);
	        GL11.glColor4f((float) color.getRed() / 255, (float) color.getGreen() / 255, (float) color.getBlue() / 255, (float) color.getAlpha() / 255);
	        
	        WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();

	        worldRenderer.begin(6, DefaultVertexFormats.POSITION);
	        worldRenderer.pos(x, y, 0).endVertex();

	        for (int i = (int) (startAngle / 360.0 * 100); i <= (int) (endAngle / 360.0 * 100); i++) {
	            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180); // Aka Pi
	            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
	        }
	        
	        Tessellator.getInstance().draw();
	        
	        GL11.glEnable(3553);
	        GL11.glDisable(3042);
	        GL11.glPopMatrix();
	    }
	    //The drawing method

		public void roundedRectangle(int x, int y, int width, int height, int cornerRadius, Color color) {
	        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
	        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
	        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());
	        
	        this.drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
	        this.drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
	        this.drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
	        this.drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
		}
		
		//Checking on a rescale, so Gui Screen can adjust.
		public void updateScreen() {

			if(prevPosX != 0 && prevPosY != 0) {

            if(prevPosY != mc.displayHeight || prevPosX != mc.displayWidth) {
				mc.displayGuiScreen(null);
				HUDManager.getInstance().openMainScreen();

			}

			
			}
			prevPosX = mc.displayWidth;
			prevPosY = mc.displayHeight;


			
		}


	
}

  
