package bwp.mods.impl;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import bwp.gui.hud.ScreenPosition;
import bwp.mods.ModDraggable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.settings.KeyBinding;

public class ModKeystrokes extends ModDraggable {
	
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
	public void render(ScreenPosition pos) {
		GL11.glPushMatrix();
		
		boolean blend = GL11.glIsEnabled(GL11.GL_BLEND);
	

		
		for(Key key : mode.getKeys()) {
			int textWidth = font.getStringWidth(key.getName());
			
			
			Gui.drawRect(pos.getAbsoluteX() + key.getX(), pos.getAbsoluteY() + key.getY(), 
					pos.getAbsoluteX() + key.getX() + key.getWidth(), 
					pos.getAbsoluteY() + key.getY() + key.getHeight(), 
					key.isDown() ? new Color(255, 255, 255).getRGB() : new Color(0, 0,0, 102).getRGB());
			
			font.drawString(
					key.getName(), 
					pos.getAbsoluteX() + key.getX() + key.getWidth() / 2  - textWidth / 2, 
					pos.getAbsoluteY() + key.getY() + key.getHeight() / 2 - 4, 
					key.isDown() ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
		}
		

		GL11.glPopMatrix();
		
	}

	@Override
	public void save(ScreenPosition pos) {
		this.pos = pos;
		
	}

	@Override
	public ScreenPosition load() {
		// TODO Auto-generated method stub
		return pos;
	}
	
}
