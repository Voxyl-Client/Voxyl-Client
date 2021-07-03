package bwp.mods.impl;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.HUDMod;
import bwp.utils.Render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

import java.awt.*;

public class ModKeystrokes extends HUDMod {

	public ModKeystrokes() {
		super("Keystrokes");
	}

	public static enum KeystrokesMode {
		
		WASD(Key.W, Key.A, Key.S, Key.D),
		WASD_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB),
		WASD_SPRINT(Key.W, Key.A, Key.S, Key.D, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 41, 58, 18)),
		WASD_SPRINT_MOUSE(Key.W, Key.A, Key.S, Key.D, Key.LMB, Key.RMB, new Key("Sprint", Minecraft.getMinecraft().gameSettings.keyBindSprint, 1, 61, 58, 18));
		
		
		
		private final Key[] keys;
		private int width;
		private int height;
		 
		private KeystrokesMode(Key... keysIn) {
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

	private KeystrokesMode mode = KeystrokesMode.WASD_SPRINT_MOUSE;
			
	
	
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
	
	private ScreenPosition pos;

	@Override
	public int getWidth() {
		// TODO Auto-generated method stub
		return mode.getWidth();
	}

	@Override
	public int getHeight() {
		// TODO Auto-generated method stub
		return mode.getHeight();
	}

	@Override
	public void render() {

		for(Key key : mode.getKeys()) {
			int textWidth = font.getStringWidth(key.getName());


			Gui.drawRect(pos.getAbsoluteX() + (int) ((key.getX()) * pos.getScale()), pos.getAbsoluteY()  + (int) ((key.getY()) * pos.getScale()) ,
				pos.getAbsoluteX()  + (int) ((key.getX()) * pos.getScale())  + (int) ((key.getWidth()) * pos.getScale()),
				pos.getAbsoluteY()+ (int) ((key.getY()) * pos.getScale())+ (int) ((key.getHeight()) * pos.getScale()),
				key.isDown() ? new Color(255, 255, 255).getRGB() : new Color(0, 0,0, 102).getRGB());

			int adjX = (int) (pos.getAbsoluteX() + ((key.getX()) * pos.getScale()) + ((key.getWidth()) * pos.getScale()) / 2 - (textWidth * pos.getScale()) / 2);
			int adjY = (int) (pos.getAbsoluteY() + ((key.getY()) * pos.getScale()) + ((key.getHeight()) * pos.getScale()) / 2 - (4 * pos.getScale()));

			if (chroma) {
				Render.drawChromaString(
						key.getName(),
						adjX,
						adjY,
						pos.getScale(),
						true);
			} else {
				Render.drawString(
						key.getName(),
						adjX,
						adjY,
						pos.getScale(),
						key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB(),
						true
				);
			}
		}
	}
}
