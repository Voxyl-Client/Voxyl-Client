package bwp.mods.impl;

import bwp.gui.elements.CheckBoxButton;
import bwp.gui.elements.GuiIntractable;
import bwp.gui.hud.RenderInfo;
import bwp.mods.HUDMod;
import bwp.mods.settings.ModSetting;
import bwp.mods.settings.ModSettingType;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

import java.awt.*;

public class ModKeystrokes extends HUDMod {

	public ModKeystrokes() {
		super("Keystrokes");
	}

	@Override
	protected void init() {
		settings.addSetting(new ModSetting(0, "Chroma", ModSettingType.CHECKBOX, this, false));
	}

	public enum KeystrokesMode {
		
		WASD(Key.W, Key.A, Key.S, Key.D),
		WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB);
		
		private final Key[] keys;
		private int width;
		private int height;
		 
		KeystrokesMode(Key... keysIn) {
			this.keys = keysIn;
			
			for(Key key : keys) {
				this.width = Math.max(this.width, key.getX() + key.getWidth());
				this.height = Math.max(this.height, key.getY() + key.getHeight());
			}
			
		}
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}
		public Key[] getKeys() {
			return keys;
		}
	}

	@Override
	public boolean shouldUsePadding() {
		return false;
	}

	private KeystrokesMode mode = KeystrokesMode.WASD_MOUSE;

	
	private static class Key {
		
		private static final Key W = new Key("W", Minecraft.getMinecraft().gameSettings.keyBindForward, 21, 1, 18, 18);
		private static final Key A = new Key("A", Minecraft.getMinecraft().gameSettings.keyBindLeft, 1, 21, 18, 18);
		private static final Key S = new Key("S", Minecraft.getMinecraft().gameSettings.keyBindBack, 21, 21, 18, 18);
		private static final Key D = new Key("D", Minecraft.getMinecraft().gameSettings.keyBindRight, 41, 21, 18, 18);
		
		private static final Key LMB = new Key("LMB", Minecraft.getMinecraft().gameSettings.keyBindAttack, 1, 41, 28, 18);
		private static final Key RMB = new Key("RMB", Minecraft.getMinecraft().gameSettings.keyBindUseItem, 31, 41, 28, 18);
		
		
		private final String name;
		private final KeyBinding keyBind;
		private final int x;
		private final int y;
		private final int width;
		private final int height;
		
		public Key(String name, KeyBinding keyBind, int x ,int y, int width, int height) {
			this.name = name;
			this.keyBind = keyBind;
			this.x = x;
			this.y = y;
			this.width = width;
			this.height = height;
			
		}
		
		public boolean isDown() {
			return keyBind.isKeyDown();
		}
		public int getHeight() {
			return height;
		}
		public int getWidth() {
			return width;
		}
		public int getX() {
			return x;
		}
		public int getY() {
			return y;
		}
		public String getName() {
			return name;
		}
	}

	@Override
	public int getWidth() {
		return mode.getWidth();
	}

	@Override
	public int getHeight() {
		return mode.getHeight();
	}

	@Override
	public void render() {

		for(Key key : mode.getKeys()) {
			int textWidth = font.getStringWidth(key.getName());


			Gui.drawRect(renderInfo.getX() + (int) ((key.getX()) * renderInfo.getScale()), renderInfo.getY()  + (int) ((key.getY()) * renderInfo.getScale()) ,
				renderInfo.getX()  + (int) ((key.getX()) * renderInfo.getScale())  + (int) ((key.getWidth()) * renderInfo.getScale()),
				renderInfo.getY()+ (int) ((key.getY()) * renderInfo.getScale())+ (int) ((key.getHeight()) * renderInfo.getScale()),
				key.isDown() ? new Color(255, 255, 255).getRGB() : new Color(0, 0,0, 102).getRGB());

			int adjX = (int) (renderInfo.getX() + ((key.getX()) * renderInfo.getScale()) + ((key.getWidth()) * renderInfo.getScale()) / 2 - (textWidth * renderInfo.getScale()) / 2);
			int adjY = (int) (renderInfo.getY() + ((key.getY()) * renderInfo.getScale()) + ((key.getHeight()) * renderInfo.getScale()) / 2 - (4 * renderInfo.getScale()));

			if ((boolean) settings.getSetting(0).getValue()) {
				Render.drawChromaString(key.getName(), adjX, adjY, renderInfo.getScale(), true);
			} else {
				Render.drawString(key.getName(), adjX, adjY, renderInfo.getScale(), key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB(), true);
			}
		}
	}

	@Override
	public void onSettingChange(int settingId, GuiIntractable intractable) {
		if (settingId == 0) {
			settings.getSetting(0).setValue(((CheckBoxButton) intractable).isChecked());
		}
	}
}
