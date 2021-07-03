package bwp.gui.hud;


import bwp.gui.hud.snapping.SnappingArea;
import bwp.gui.hud.snapping.SnappingDirection;
import bwp.gui.hud.snapping.SnappingZone;
import bwp.mods.HUDMod;
import bwp.mods.Mod;
import bwp.mods.ModAPI;
import bwp.utils.*;

import bwp.utils.Rectangle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;



import java.io.IOException;
import java.awt.Color;
import java.util.*;
import java.util.function.Predicate;

public class HUDConfigScreen extends GuiScreen {
	private ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
	int screenWidth = res.getScaledWidth();
	int screenHeight = res.getScaledHeight();

	private final List<HUDMod> mods = new ArrayList<>();

	private Optional<HUDMod> selectedMod = Optional.empty();
	private Optional<HUDMod> hoveredMod = Optional.empty();

	int snappingLineColor = 0xFFFF0200;

	// Deal with area of shape too.
	private SnappingZone[] snappingZones = new SnappingZone[] {
			new SnappingZone(0F, SnappingDirection.HORIZONTAL, SnappingArea.SIDES, snappingLineColor),
			new SnappingZone(0.5F, SnappingDirection.HORIZONTAL, SnappingArea.CENTER, snappingLineColor),
			new SnappingZone(1F, SnappingDirection.HORIZONTAL, SnappingArea.SIDES, snappingLineColor),
			new SnappingZone(0F, SnappingDirection.VERTICAL, SnappingArea.T_SIDES, snappingLineColor),
			new SnappingZone(0.5F, SnappingDirection.VERTICAL, SnappingArea.CENTER, snappingLineColor),
			new SnappingZone(1F, SnappingDirection.VERTICAL, SnappingArea.T_SIDES, snappingLineColor),
	};

	private int prevX, prevY;

	private final int nodeSize = 8;

	private final int padding = 5;

	private final int halfSnapzoneWidth = 3;

	private int displacementX = 0;
	private int displacementY = 0;

	public HUDConfigScreen() {

		ModAPI api = ModAPI.getInstance();

		Collection<Mod> registeredMods = api.getRegisteredMods();

		for(Mod mod : registeredMods) {
			if (mod instanceof HUDMod) {
				HUDMod hudMod = (HUDMod) mod;
				if (!hudMod.getSettings().getEnabled()) {
					continue;
				}
				hudMod.loadDataFromFile();
				adjustBounds(hudMod);
				this.mods.add(hudMod);
			}
		}
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		super.drawDefaultBackground();

		final float zBackup = this.zLevel;
		this.zLevel = 200;

		int x = Mouse.getEventX() * this.width / screenWidth;
		int y = this.height - Mouse.getEventY() * this.height / screenHeight - 1;

		this.hoveredMod = mods.stream().filter(new MouseOverFinder(x, y)).findFirst();

		this.drawHollowRect(0, 0, this.width - 1, this.height - 1, 0xFFFF0200);

		// Draw snapping lines
		for (SnappingZone zone : snappingZones) {
			if (zone.getModToSnap() != null) {
				zone.updatePixelLoc();
				if (zone.getDirection() == SnappingDirection.VERTICAL) {
					this.drawHorizontalLine(0, screenWidth, zone.getPixelLoc(), zone.getColor());
				} else if (zone.getDirection() == SnappingDirection.HORIZONTAL) {
					this.drawVerticalLine(zone.getPixelLoc(), 0, screenHeight, zone.getColor());
				}
			}
		}

		for(HUDMod mod : mods) {
			ScreenPosition pos = mod.getPos();

			int absX = pos.getAbsoluteX();
			int absY = pos.getAbsoluteY();

			int color;

			if (pos.getScale() > 0.5) color = 0xFF00FFFF;
			else color = 0xFFFF0000;

			Color backgroundColor = ColorUtils.fromHex("#2400FFFF");
			if (this.hoveredMod.isPresent()) {
				if (mod == this.hoveredMod.get()) {
					backgroundColor = ColorUtils.fromHex("#3D00FFFF");
				}
			}

			if (mod.shouldUsePadding()) {
				this.drawHollowRect(pos.getAbsoluteX() - padding, pos.getAbsoluteY() - padding, (int) (mod.getWidth() * pos.getScale()) + padding * 2, (int) (mod.getHeight() * pos.getScale()) + padding * 2, 0xFF00FFFF);

				// Background
				Rectangle.render(pos.getAbsoluteX() - padding, pos.getAbsoluteY() - padding, pos.getAbsoluteX() + (int) (mod.getWidth() * pos.getScale()) + padding, pos.getAbsoluteY() + (int) (mod.getHeight() * pos.getScale()) + padding, backgroundColor);

				// Scale down
				this.drawHollowRect(absX - padding, absY - (nodeSize + 4) - padding, nodeSize, nodeSize, color);

				// Scale up
				if (pos.getScale() < 2) color = 0xFF00FFFF;
				else color = 0xFFFF0000;
				this.drawHollowRect(absX - padding + nodeSize + 4, absY - (nodeSize + 4) - padding, nodeSize, nodeSize, color);
			} else {
				this.drawHollowRect(pos.getAbsoluteX(), pos.getAbsoluteY(), (int) (mod.getWidth() * pos.getScale()), (int) (mod.getHeight() * pos.getScale()), 0xFF00FFFF);

				// Background
				Rectangle.render(pos.getAbsoluteX(), pos.getAbsoluteY(), pos.getAbsoluteX() + (int) (mod.getWidth() * pos.getScale()), pos.getAbsoluteY() + (int) (mod.getHeight() * pos.getScale()), backgroundColor);

				// Scale down
				this.drawHollowRect(absX, absY - (nodeSize + 4), nodeSize, nodeSize, color);

				// Scale up
				if (pos.getScale() < 2) color = 0xFF00FFFF;
				else color = 0xFFFF0000;
				this.drawHollowRect(absX + nodeSize + 4, absY - (nodeSize + 4), nodeSize, nodeSize, color);
			}

			mod.renderDummy();
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
			mods.forEach(HUDMod::saveDataToFile);
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			for (SnappingZone zone : snappingZones) {
				if (this.hoveredMod.isPresent() && zone.getModToSnap() != null)
					if (zone.getModToSnap().equals(this.hoveredMod.get())) { zone.removeRendererToSnap(); }
			}

			displacementX = 0;
			displacementY = 0;
		}
	}

	@Override
	protected void mouseClickMove(int x, int y, int button, long time) {
		int offsetX = x - prevX;
		int offsetY = y - prevY;

		if(selectedMod.isPresent()) {
			moveSelectedModBy(offsetX, offsetY);
		}

		displacementX += offsetX;
		displacementY += offsetY;

		this.prevX = x;
		this.prevY = y;
	}

	private void moveSelectedModBy(int offsetX, int offsetY) {
		HUDMod mod = selectedMod.get();
		ScreenPosition pos = mod.getPos();

		int newX = pos.getAbsoluteX() + offsetX;
		int newY = pos.getAbsoluteY() + offsetY;

		int adjX = newX;
		int adjY = newY;

		boolean didSnap = false;

		SnappingZone snappedZone = null;

		adjustBounds(mod);

		int adjPadding = padding;

		if (!mod.shouldUsePadding()) adjPadding = 0;

		for (SnappingZone zone : snappingZones) {
			if (zone.getSnappingArea() == SnappingArea.CENTER) {
				adjX = newX + (int) (mod.getWidth() * pos.getScale()) / 2;
				adjY = newY + (int) (mod.getHeight() * pos.getScale()) / 2;
			} else if (zone.getSnappingArea() == SnappingArea.SIDES) {
				if (pos.getAbsoluteX() > screenWidth / 2) {
					adjX = newX + (int) (mod.getWidth() * pos.getScale()) + adjPadding;
				} else {
					adjX = newX - adjPadding;
				}
				adjY = newY;
			} else if (zone.getSnappingArea() == SnappingArea.T_SIDES) {
				if (pos.getAbsoluteY() > screenHeight / halfSnapzoneWidth) {
					adjY = newY + (int) (mod.getHeight() * pos.getScale()) + adjPadding;
				} else {
					adjY = newY - adjPadding;
				}
				adjX = newX;
			}

			boolean shouldBind = false;
			if (zone.getDirection() == SnappingDirection.VERTICAL) {
				if (Math.abs(adjY - zone.getPixelLoc()) <= halfSnapzoneWidth) shouldBind = true;
				if (shouldBind) {
					int snappedY = zone.getPixelLoc();

					if (zone.getSnappingArea() == SnappingArea.CENTER) {
						snappedY = zone.getPixelLoc() - (int) (mod.getHeight() * pos.getScale()) / 2;
					} else if (zone.getSnappingArea() == SnappingArea.T_SIDES) {
						if (pos.getAbsoluteX() > screenHeight / 2) {
							snappedY = zone.getPixelLoc() - (int) (mod.getHeight() * pos.getScale()) - adjPadding;
						} else {
							snappedY = zone.getPixelLoc() + adjPadding;
						}
					}

					if (zone.getModSnapped() != mod) {
						pos.setAbsolute(pos.getAbsoluteX(), snappedY, pos.getScale());
						zone.setSnappedRenderer(mod);
						didSnap = true;
					}
					snappedZone = zone;
				} else {
					if (zone.getModSnapped() == mod) {
						zone.setSnappedRenderer(null);
					}
				}
			} else if (zone.getDirection() == SnappingDirection.HORIZONTAL) {
				if (Math.abs(adjX - zone.getPixelLoc()) <= halfSnapzoneWidth) shouldBind = true;
				if (shouldBind) {
					int snappedX = zone.getPixelLoc();

					if (zone.getSnappingArea() == SnappingArea.CENTER) {
						snappedX = zone.getPixelLoc() - (int) (mod.getWidth() * pos.getScale()) / 2;
					} else if (zone.getSnappingArea() == SnappingArea.SIDES) {
						if (pos.getAbsoluteX() > screenWidth / 2) {
							snappedX = zone.getPixelLoc() - (int) (mod.getWidth() * pos.getScale()) - adjPadding;
						} else {
							snappedX = zone.getPixelLoc() + adjPadding;
						}
					}
					if (zone.getModSnapped() != mod) {
						pos.setAbsolute(snappedX, pos.getAbsoluteY(), pos.getScale());
						zone.setSnappedRenderer(mod);
						didSnap = true;
					}
					snappedZone = zone;
				} else {
					if (zone.getModSnapped() == mod) {
						zone.setSnappedRenderer(null);
					}
				}
			}
			if (shouldBind) {
				zone.setModToSnap(selectedMod.get());
			}
			else if (zone.getModToSnap() != null) {
				if (zone.getModToSnap().equals(selectedMod.get()))
					zone.removeRendererToSnap();
			}
		}

		if (!didSnap) {
			if (snappedZone != null) {
				if (Math.abs(displacementY) >= halfSnapzoneWidth * 2 && snappedZone.getDirection() == SnappingDirection.VERTICAL) {
					int movementOffset = 0;
					if (displacementY > 0) {
						movementOffset = halfSnapzoneWidth * 2;
					} else if (displacementY < 0) {
						movementOffset = - (halfSnapzoneWidth * 2);
					}

					pos.setAbsolute(newX, newY + movementOffset, pos.getScale());
				} else if (Math.abs(displacementX) >= halfSnapzoneWidth * 2 && snappedZone.getDirection() == SnappingDirection.HORIZONTAL) {
					int movementOffset = 0;
					if (displacementX > 0) {
						movementOffset = halfSnapzoneWidth * 2;
					} else if (displacementX < 0) {
						movementOffset = - (halfSnapzoneWidth * 2);
					}
					pos.setAbsolute(newX + movementOffset, newY, pos.getScale());
				} else if (snappedZone.getDirection() == SnappingDirection.HORIZONTAL) {
					pos.setAbsolute(pos.getAbsoluteX(), newY, pos.getScale());
				} else {
					pos.setAbsolute(newX, pos.getAbsoluteY(), pos.getScale());
				}
			} else {
				pos.setAbsolute(newX, newY, pos.getScale());
			}
		} else {
			displacementX = 0;
			displacementY = 0;
		}
	}

	@Override
	public void onGuiClosed() {
		for(HUDMod mod : mods) {
			mod.saveDataToFile();
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return true;
	}

	private void adjustBounds(HUDMod mod) {
		ScreenPosition pos = mod.getPos();

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();

		int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - (int) (mod.getWidth() * pos.getScale()), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - (int) (mod.getHeight() * pos.getScale()), 0)));

		pos.setAbsolute(absoluteX, absoluteY, pos.getScale());
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		this.prevX = x;
		this.prevY = y;

		loadMouseOver(x, y);

		if (selectedMod.isPresent()) {
			ScreenPosition pos = selectedMod.get().getPos();

			if (selectedMod.get().shouldUsePadding()) {
				if (x >= pos.getAbsoluteX() - padding && x <= pos.getAbsoluteX() - padding + nodeSize && y >= pos.getAbsoluteY() - padding - (nodeSize + 4) && y <= pos.getAbsoluteY() - padding - 4) {
					if (pos.getScale() > 0.5) pos.setScale((float) (pos.getScale() - 0.1));
				} else if (x >= pos.getAbsoluteX() - padding + nodeSize + 4 && x <= pos.getAbsoluteX() - padding + 2 * nodeSize + 4 && y >= pos.getAbsoluteY() - padding - (nodeSize + 4) && y <= pos.getAbsoluteY() - padding - 4) {
					if (pos.getScale() < 2) pos.setScale((float) (pos.getScale() + 0.1));
				}
			} else {
				if (x >= pos.getAbsoluteX() && x <= pos.getAbsoluteX() + nodeSize && y >= pos.getAbsoluteY() - (nodeSize + 4) && y <= pos.getAbsoluteY() - 4) {
					if (pos.getScale() > 0.5) pos.setScale((float) (pos.getScale() - 0.1));
				} else if (x >= pos.getAbsoluteX() + nodeSize + 4 && x <= pos.getAbsoluteX() + 2 * nodeSize + 4 && y >= pos.getAbsoluteY() - (nodeSize + 4) && y <= pos.getAbsoluteY() - 4) {
					if (pos.getScale() < 2) pos.setScale((float) (pos.getScale() + 0.1));
				}
			}
		}
	}

	private void loadMouseOver(int x, int y) {
		this.selectedMod = mods.stream().filter(new MouseOverFinder(x, y)).findFirst();
	}

	private class MouseOverFinder implements Predicate<HUDMod> {

		private int mouseX, mouseY;

		public MouseOverFinder(int x, int y) {
			this.mouseX = x;
			this.mouseY = y;
		}

		@Override
		public boolean test(HUDMod mod) {
			ScreenPosition pos = mod.getPos();

			int absoluteX = pos.getAbsoluteX();
			int absoluteY = pos.getAbsoluteY();

			if (mod.shouldUsePadding()) {
				if (mouseX >= absoluteX - padding && mouseX <= (absoluteX + (pos.getScale() * mod.getWidth())) + padding) {
					return mouseY >= absoluteY - (nodeSize + 4) - padding && mouseY <= (absoluteY + (pos.getScale() * mod.getHeight())) + padding;
				}
			} else {
				if(mouseX >= absoluteX && mouseX <= (absoluteX + (pos.getScale() *mod.getWidth()))) {
					return mouseY >= absoluteY - (nodeSize + 4) && mouseY <= (absoluteY + (pos.getScale() * mod.getHeight()));
				}
			}

			return false;
		}
	}

}