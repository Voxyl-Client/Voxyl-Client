package bwp.gui;

import java.io.File;
import java.io.IOException;

import bwp.gui.elements.template.ClientButton;
import org.lwjgl.input.Keyboard;

import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;

import bwp.Client;
import bwp.FileManager;
import bwp.SessionChanger;
import bwp.gui.hud.HUDManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.resources.I18n;

public class GuiErrorLogin extends GuiScreen{
	private GuiMainMenu guimainmenu;
	private GuiTextField loginNameField;
	private GuiTextField loginPassField;
	private String loginName;
	private String loginPass;


    public GuiErrorLogin(GuiMainMenu guiMainMenu) {
		this.guimainmenu = guiMainMenu;
	}

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.loginNameField.updateCursorCounter();
        this.loginPassField.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
    	

    	Client.getInstance().getDiscordRP().update("Signing In", "Login Menu", "large");
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new ClientButton(0, this.width / 2 - 100, this.height / 4 + 96 + 18, 98, 20, I18n.format("Login", new Object[0])));
        this.buttonList.add(new ClientButton(1, this.width / 2 - 100, this.height / 4 + 120 + 18, 98, 20, I18n.format("Cancel", new Object[0])));
        this.loginNameField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.loginNameField.setMaxStringLength(128);
        this.loginNameField.setFocused(true);
        this.loginNameField.setText("Email");
        this.loginPassField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);
        this.loginPassField.setMaxStringLength(128);
        this.loginPassField.setText("Password");
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
            	this.mc.displayGuiScreen(new GuiMainMenu());
            }
            else if (button.id == 0)
            {
                this.loginName = this.loginNameField.getText();
                this.loginPass = this.loginPassField.getText();
                try {
                SessionChanger.getInstance().setUser(this.loginName, this.loginPass);
                }catch(InvalidCredentialsException e) {
                	HUDManager.getInstance().openLoginScreen();

				} catch (AuthenticationException e) {
					// TODO Auto-generated catch block
					System.out.println("fuck me ");
				}
                if(!FileManager.doesLoginFileExist()) {
        		FileManager.writeJsonToFile(new File(FileManager.getCacheDirectory(), "temp_name.json"), this.loginName);
        		FileManager.writeJsonToFile(new File(FileManager.getCacheDirectory(), "temp_pass.json"), this.loginPass);
                }

                
                

                this.mc.displayGuiScreen(new GuiMainMenu());
            }
            }
        
    }

    /**
     * Fired when a key is typed (except F11 who toggle full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        this.loginNameField.textboxKeyTyped(typedChar, keyCode);
        this.loginPassField.textboxKeyTyped(typedChar, keyCode);

        if (keyCode == 15)
        {
            this.loginNameField.setFocused(!this.loginNameField.isFocused());
            this.loginPassField.setFocused(!this.loginPassField.isFocused());
        }

        if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }

        ((GuiButton)this.buttonList.get(0)).enabled = this.loginPassField.getText().length() > 0 && this.loginPassField.getText().split(":").length > 0 && this.loginNameField.getText().length() > 0;
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginPassField.mouseClicked(mouseX, mouseY, mouseButton);
        this.loginNameField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        ScaledResolution sr = new ScaledResolution(mc);
        
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Login", this.width / 2, 17, 16777215);
        this.drawString(this.fontRendererObj, "Username", this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRendererObj, "Password", this.width / 2 - 100, 94, 10526880);
        this.loginNameField.drawTextBox();
        this.loginPassField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

}
