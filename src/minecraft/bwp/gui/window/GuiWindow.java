package bwp.gui.window;

import bwp.gui.elements.ModButton;
import bwp.gui.elements.template.CustomButton;
import bwp.gui.hud.HUDManager;
import bwp.utils.ColorUtils;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GuiWindow extends GuiScreen {
    protected final String title;
    protected int x;
    protected int y;
    protected int windowHeight;
    protected int windowWidth;
    protected final GuiWindow previous;
    protected final List<CustomButton> buttons = new ArrayList<>();

    protected int mouseX = 0;
    protected int mouseY = 0;

    public GuiWindow(String title, GuiWindow previous) {
        this.title = title;
        this.previous = previous;
    }

    public GuiWindow(String title) {
        this.title = title;
        this.previous = null;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);

        ScaledResolution sr = new ScaledResolution(mc);

        x = sr.getScaledWidth() / 8;
        y = sr.getScaledHeight() / 8;
        this.windowWidth = (sr.getScaledWidth() / 8) * 6;
        this.windowHeight = (sr.getScaledHeight() / 8) * 6;

        Render.drawRoundedRectangle(x, y, windowWidth, windowHeight, 10, ColorUtils.fromHex("#9F292a2b"));

        Render.drawString(title, x + (windowWidth / 2) - (fontRendererObj.getStringWidth(title) / 2), y + 10, 1F, true);

        // TODO: Add back button to go to previous GuiWindow

        drawWindowParts();
        // Pop the matrix made in drawWindowParts()
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public void drawWindowParts() {
        GL11.glPushMatrix();

        ScaledResolution sr = new ScaledResolution(mc);

        x = sr.getScaledWidth() / 8;
        y = sr.getScaledHeight() / 8;

        GL11.glScissor(x * sr.getScaleFactor(), (height * sr.getScaleFactor()) - (y * sr.getScaleFactor() + windowHeight * sr.getScaleFactor()) + 10 * sr.getScaleFactor(), windowWidth * sr.getScaleFactor(), windowHeight * sr.getScaleFactor() - 20 * sr.getScaleFactor() - fontRendererObj.FONT_HEIGHT * sr.getScaleFactor() - 5 * sr.getScaleFactor());
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
    }

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();

        mouseX = Mouse.getEventX() * this.width / this.mc.displayWidth;
        mouseY = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

        if (mouseX < x || mouseX > x + windowWidth || mouseY < y || mouseY > y + windowHeight) {
            if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState()) {
                HUDManager.getInstance().openConfigScreen();
            }
            return;
        }
    }

    @Override
    public void initGui() {
        if(!(Minecraft.getMinecraft().gameSettings.ofFastRender) && OpenGlHelper.shadersSupported && Minecraft.getMinecraft().getRenderViewEntity() instanceof EntityPlayer) {
            if (Minecraft.getMinecraft().entityRenderer.theShaderGroup != null)
                Minecraft.getMinecraft().entityRenderer.theShaderGroup.deleteShaderGroup();

            Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        }
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    public void onGuiClosed() {
        Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
        super.onGuiClosed();
        Keyboard.enableRepeatEvents(false);
    }
}
