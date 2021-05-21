package bwp.gui;

import bwp.gui.hud.HUDManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

public class GuiReportScreen extends GuiScreen{


        private final GuiScreen previousScreen;
        private GuiTextField username;
        private Minecraft mc = Minecraft.getMinecraft();
        private HUDManager hudManager = HUDManager.getInstance();


        public GuiReportScreen(GuiScreen previousScreen) {
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

            ScaledResolution sr = new ScaledResolution(mc);
            this.username.drawTextBox();
            Gui.drawCenteredString(mc.fontRendererObj, "Gui Settings", (int) (this.width / 2F), sr.getScaledHeight() / 2 - 65, -1);
            super.drawScreen(x2, y2, z2);
        }

        @Override
        public void initGui() {
            //Enable Minecrafts blur shader
            Minecraft.getMinecraft().entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
            ScaledResolution sr = new ScaledResolution(mc);
            this.buttonList.clear();
            //TODO - ADD
            this.buttonList.add(new GuiButton(0, this.width / 2 - 50, this.height / 2 - 20,  98, 20, I18n.format("Adjust Positions", new Object[0])));
            this.buttonList.add(new GuiButton(1, this.width / 2 - 50, this.height / 2 + 5,  98, 20, I18n.format("Adjust Sizes", new Object[0])));
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
            Minecraft.getMinecraft().entityRenderer.loadEntityShader(null);
            super.onGuiClosed();

            Keyboard.enableRepeatEvents(false);
            System.out.println("");
        }

        @Override
        public void updateScreen() {
            this.username.updateCursorCounter();
        }


}


