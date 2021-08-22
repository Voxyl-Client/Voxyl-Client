package net.minecraft.client.gui;

import bwp.Client;
import bwp.gui.elements.template.ClientButton;
import bwp.gui.hud.HUDManager;
import bwp.login.Accounts;
import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.ISaveFormat;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GLContext;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {

    private static final FontRenderer fontRenderer = Minecraft.getMinecraft().fontRendererObj;

    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();


    private float updateCounter;

    /**
     * The splash message.
     */
    private String splashText;
    private GuiButton buttonResetDemo;


    /**
     * Timer used to rotate the panorama, increases every tick.
     */
    private int panoramaTimer;

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;

    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;

    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    /**
     * An array of all the paths to the panorama pictures.
     */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;

    /**
     * Minecraft Realms button.
     */
    private GuiButton realmsButton;

    public GuiMainMenu() {
        this.openGLWarning2 = field_96138_a;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = (String) list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var12) {
            ;
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException var11) {
                    ;
                }
            }
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    @Override
    public void initGui() {
        this.buttonList.add(new ClientButton(1, this.width / 2 - 50, height / 2 - 17, 98, 16, "Singleplayer"));
        this.buttonList.add(new ClientButton(2, this.width / 2 - 50, height / 2, 98, 16, "Multiplayer"));
        this.buttonList.add(new ClientButton(3, this.width / 2 - 50, height / 2 + 17, 98, 16, "Settings"));
        this.buttonList.add(new ClientButton(4, this.width - 21, 4, 16, 16, EnumChatFormatting.BOLD + "✗"));

        int accountsButtonWidth;

        String accountsButtonText = "Account";

        if (Accounts.getSelectedAccount() != null) {
            accountsButtonText = Accounts.getSelectedAccount().getUsername();
        }

        accountsButtonWidth = fontRenderer.getStringWidth(accountsButtonText) + 10;

        this.buttonList.add(new ClientButton(5, 5, 4, accountsButtonWidth, 16, accountsButtonText));

        this.buttonList.add(new ClientButton(6, 10 + accountsButtonWidth, 4, fontRenderer.getStringWidth("Add account") + 10, 16, "Add account"));
        super.initGui();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 1:
                if (Accounts.getSelectedAccount() != null) this.mc.displayGuiScreen(new GuiSelectWorld(this));
                else if (Accounts.getAccounts().size() == 0) HUDManager.getInstance().openLoginScreen();
                else HUDManager.getInstance().openAccountsScreen();
                break;
            case 2:
                if (Accounts.getSelectedAccount() != null) this.mc.displayGuiScreen(new GuiMultiplayer(this));
                else if (Accounts.getAccounts().size() == 0) HUDManager.getInstance().openLoginScreen();
                else HUDManager.getInstance().openAccountsScreen();
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                this.mc.shutdown();
                break;
            case 5:
                HUDManager.getInstance().openAccountsScreen();
                break;
            case 6:
                HUDManager.getInstance().openLoginScreen();
                break;
        }
        super.actionPerformed(button);
    }

    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(this.openGLWarningLink)});
                } catch (Throwable throwable) {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.mc.getTextureManager().bindTexture(new ResourceLocation("bwp/bg.png"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);


        this.drawString(this.fontRendererObj, "Copyright " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "Mojang AB", this.width - this.fontRendererObj.getStringWidth("Copyright " + EnumChatFormatting.RED + EnumChatFormatting.BOLD + "M" + EnumChatFormatting.RESET + "ojang AB") - 2, this.height - 10, -1);
        this.drawCenteredString(mc.fontRendererObj, EnumChatFormatting.BOLD + "Voxyl " + EnumChatFormatting.RESET + "Client", width / 2 - 2, height / 2 - 30, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);

    }
}
