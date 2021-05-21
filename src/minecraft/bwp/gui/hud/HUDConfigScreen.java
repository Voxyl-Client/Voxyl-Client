package bwp.gui.hud;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {
	
	private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<IRenderer, ScreenPosition>();
	
	private Optional<IRenderer> selectedRenderer = Optional.empty();
	
	private int prevX, prevY;

	private int nodeSize = 8;


	public HUDConfigScreen(HUDManager api) {
		
		Collection<IRenderer> registeredRenderers = api.getRegisteredRenderers();
		
		for(IRenderer ren : registeredRenderers) {
			if(!ren.isEnabled()) {
				continue;
			}
			ScreenPosition pos = ren.load();
			
			if (pos == null) {
				pos = ScreenPosition.fromRelativePosition(0.5, 0.5, 1F);
			}
			adjustBounds(ren, pos);
			this.renderers.put(ren, pos);
		}
	}
		
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		super.drawDefaultBackground();
			
		final float zBackup = this.zLevel;
		this.zLevel = 200;
			
		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0200);
			
		for(IRenderer renderer : renderers.keySet()) {
			ScreenPosition pos = renderers.get(renderer);
				
			renderer.renderDummy(pos);
				
			this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), (int) (renderer.getWidth() * pos.getScale()), (int) (renderer.getHeight() * pos.getScale()), 0xFF00FFFF);

			int absX = pos.getAbsoluteX();
			int absY = pos.getAbsoluteY();

			int color;
			if (pos.getScale() > 0.5) color = 0xFF00FFFF;
			else color = 0xFFFF0000;
			this.drawHollowRect(absX, absY - (nodeSize + 4), nodeSize, nodeSize, color);

			if (pos.getScale() < 2) color = 0xFF00FFFF;
			else color = 0xFFFF0000;
			this.drawHollowRect(absX + nodeSize + 4, absY - (nodeSize + 4), nodeSize, nodeSize, color);
		}
		
		this.zLevel = zBackup;
			
	}

	private void drawHollowRect(int x, int y, int w, int h, int color) {
		this.drawHorizontalLine(x, x + w, y, color);
		this.drawHorizontalLine(x, x + w, y + h, color);
		this.drawVerticalLine(x, y + h, y, color);
		this.drawVerticalLine(x + w, y + h, y, color);
	}
		
	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(keyCode == Keyboard.KEY_ESCAPE) {
			renderers.entrySet().forEach((entry) -> {
				entry.getKey().save(entry.getValue());
			});
			this.mc.displayGuiScreen(null);
		}
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		if(selectedRenderer.isPresent()) {
			moveSelectedRenderBy(x - prevX, y - prevY);
		}
		
		this.prevX = x;
		this.prevY = y;
	}

	private void moveSelectedRenderBy(int offsetX, int offsetY) {
		IRenderer renderer = selectedRenderer.get();
		ScreenPosition pos = renderers.get(renderer);
		
		pos.setAbsolute(pos.getAbsoluteX() + offsetX, pos.getAbsoluteY() + offsetY, pos.getScale());
		
		adjustBounds(renderer, pos);
	}
	
	@Override
	public void onGuiClosed() {
		for(IRenderer renderer: renderers.keySet()) {
			renderer.save(renderers.get(renderer));
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}
	
	private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
		
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();
		
		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - (int) (renderer.getWidth() * pos.getScale()), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - (int) (renderer.getHeight() * pos.getScale()), 0)));
		
		pos.setAbsolute(absoluteX, absoluteY, pos.getScale());
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		this.prevX = x;
		this.prevY = y;
		
		loadMouseOver(x, y);

		if (selectedRenderer.isPresent()) {
			IRenderer renderer = selectedRenderer.get();
			ScreenPosition pos = renderers.get(renderer);

			if (x >= pos.getAbsoluteX() && x <= pos.getAbsoluteX() + nodeSize && y >= pos.getAbsoluteY() - (nodeSize + 4) && y <= pos.getAbsoluteY() - 4) {
				if (pos.getScale() > 0.5) pos.setScale((float) (pos.getScale() - 0.1));
			} else if (x >= pos.getAbsoluteX() + nodeSize + 4 && x <= pos.getAbsoluteX() + 2 * nodeSize + 4 && y >= pos.getAbsoluteY() - (nodeSize + 4) && y <= pos.getAbsoluteY() - 4) {
				if (pos.getScale() < 2) pos.setScale((float) (pos.getScale() + 0.1));
			}
		}
	}

	private void loadMouseOver(int x, int y) {
		this.selectedRenderer = renderers.keySet().stream().filter(new MouseOverFinder(x, y)).findFirst();
	}
	
	private class MouseOverFinder implements Predicate<IRenderer> {
		
		private int mouseX, mouseY;
		
		public MouseOverFinder(int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}

		@Override
		public boolean test(IRenderer renderer) {


			ScreenPosition pos = renderers.get(renderer);

			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();

			if(mouseX >= absoluteX && mouseX <= (absoluteX + (pos.getScale() *renderer.getWidth()))) {

				if(mouseY >= absoluteY - (nodeSize + 4) && mouseY <= (absoluteY + (pos.getScale() * renderer.getHeight()))) {
					return true;
				}

			}

			return false;
		}
	}
	
}
