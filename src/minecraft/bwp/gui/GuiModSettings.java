package bwp.gui;

import bwp.gui.elements.template.ClientButton;
import bwp.gui.hud.HUDManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.Color;
import java.io.IOException;

public class GuiModSettings extends GuiScreen {
    private final GuiScreen previousScreen;
    private Minecraft mc = Minecraft.getMinecraft();
    private HUDManager hudManager = HUDManager.getInstance();



    public GuiModSettings(GuiScreen previousScreen) {
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

        roundedRectangle(100, 100, 100,100, 100, Color.WHITE);

        Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
        super.drawScreen(x2, y2, z2);
    }

    @Override
    public void initGui() {

        ScaledResolution sr = new ScaledResolution(mc);
        this.buttonList.clear();
        //TODO - ADD
        this.buttonList.add(new ClientButton(0, this.width / 2 - 50, this.height / 2 - 20,  98, 20, I18n.format("Adjust Positions", new Object[0])));
        this.buttonList.add(new ClientButton(1, this.width / 2 - 50, this.height / 2 + 5,  98, 20, I18n.format("Toggle Mods", new Object[0])));
        System.out.println("hello this is working now");

        roundedRectangle(100, 100, 100,100, 100, Color.WHITE);
        


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
            double angle = (Math.PI * 2 * i / 100) + Math.toRadians(180);
            worldRenderer.pos(x + Math.sin(angle) * radius, y + Math.cos(angle) * radius, 0).endVertex();
        }
        
        Tessellator.getInstance().draw();
        
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }

	public void roundedRectangle(int x, int y, int width, int height, int cornerRadius, Color color) {
		// TODO Auto-generated method stub
        Gui.drawRect(x, y + cornerRadius, x + cornerRadius, y + height - cornerRadius, color.getRGB());
        Gui.drawRect(x + cornerRadius, y, x + width - cornerRadius, y + height, color.getRGB());
        Gui.drawRect(x + width - cornerRadius, y + cornerRadius, x + width, y + height - cornerRadius, color.getRGB());
        
        this.drawArc(x + cornerRadius, y + cornerRadius, cornerRadius, 0, 90, color);
        this.drawArc(x + width - cornerRadius, y + cornerRadius, cornerRadius, 270, 360, color);
        this.drawArc(x + width - cornerRadius, y + height - cornerRadius, cornerRadius, 180, 270, color);
        this.drawArc(x + cornerRadius, y + height - cornerRadius, cornerRadius, 90, 180, color);
	}


}
