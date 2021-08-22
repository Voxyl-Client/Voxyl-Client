package bwp.gui.hud;


import bwp.gui.hud.snapping.SnappingArea;
import bwp.gui.hud.snapping.SnappingDirection;
import bwp.gui.hud.snapping.SnappingZone;
import bwp.mods.HUDMod;
import bwp.mods.Mod;
import bwp.mods.ModAPI;
import bwp.mods.impl.togglemotion.ToggleSprint;
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
			if (mod instanceof ToggleSprint) {
				// Checks for the should render text settings value
				if (!((boolean) mod.getSettings().getSetting(2).getValue())) continue;
			}

			RenderInfo pos = mod.getPos();

			int absX = pos.getX();
			int absY = pos.getY();

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
				this.drawHollowRect(pos.getX() - padding, pos.getY() - padding, (int) (mod.getWidth() * pos.getScale()) + padding * 2, (int) (mod.getHeight() * pos.getScale()) + padding * 2, 0xFF00FFFF);

				// Background
				Rectangle.render(pos.getX() - padding, pos.getY() - padding, pos.getX() + (int) (mod.getWidth() * pos.getScale()) + padding, pos.getY() + (int) (mod.getHeight() * pos.getScale()) + padding, backgroundColor);

				// Scale down
				this.drawHollowRect(absX - padding, absY - (nodeSize + 4) - padding, nodeSize, nodeSize, color);

				// Scale up
				if (pos.getScale() < 2) color = 0xFF00FFFF;
				else color = 0xFFFF0000;
				this.drawHollowRect(absX - padding + nodeSize + 4, absY - (nodeSize + 4) - padding, nodeSize, nodeSize, color);
			} else {
				this.drawHollowRect(pos.getX(), pos.getY(), (int) (mod.getWidth() * pos.getScale()), (int) (mod.getHeight() * pos.getScale()), 0xFF00FFFF);

				// Background
				Rectangle.render(pos.getX(), pos.getY(), pos.getX() + (int) (mod.getWidth() * pos.getScale()), pos.getY() + (int) (mod.getHeight() * pos.getScale()), backgroundColor);

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
			onGuiClosed();
			this.mc.displayGuiScreen(null);
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (state == 0) {
			/*for (SnappingZone zone : snappingZones) {
				if (this.hoveredMod.isPresent() && zone.getModToSnap() != null)
					if (zone.getModToSnap().equals(this.hoveredMod.get())) { zone.removeRendererToSnap(); }
			}*/

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
		RenderInfo pos = mod.getPos();

		int newX = pos.getX() + offsetX;
		int newY = pos.getY() + offsetY;

		int adjX = newX;
		int adjY = newY;

		boolean didSnap = false;

		SnappingZone snappedZone = null;

		adjustBounds(mod);

		int adjPadding = padding;

		if (!mod.shouldUsePadding()) adjPadding = 0;

		/*for (SnappingZone zone : snappingZones) {
			if (zone.getSnappingArea() == SnappingArea.CENTER) {
				adjX = newX + (int) (mod.getWidth() * pos.getScale()) / 2;
				adjY = newY + (int) (mod.getHeight() * pos.getScale()) / 2;
			} else if (zone.getSnappingArea() == SnappingArea.SIDES) {
				if (pos.getX() > screenWidth / 2) {
					adjX = newX + (int) (mod.getWidth() * pos.getScale()) + adjPadding;
				} else {
					adjX = newX - adjPadding;
				}
				adjY = newY;
			} else if (zone.getSnappingArea() == SnappingArea.T_SIDES) {
				if (pos.getY() > screenHeight / halfSnapzoneWidth) {
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
						if (pos.getX() > screenHeight / 2) {
							snappedY = zone.getPixelLoc() - (int) (mod.getHeight() * pos.getScale()) - adjPadding;
						} else {
							snappedY = zone.getPixelLoc() + adjPadding;
						}
					}

					if (zone.getModSnapped() != mod) {
						pos.setPos(pos.getX(), snappedY, pos.getScale());
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
						if (pos.getX() > screenWidth / 2) {
							snappedX = zone.getPixelLoc() - (int) (mod.getWidth() * pos.getScale()) - adjPadding;
						} else {
							snappedX = zone.getPixelLoc() + adjPadding;
						}
					}
					if (zone.getModSnapped() != mod) {
						pos.setPos(snappedX, pos.getY(), pos.getScale());
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

					pos.setPos(newX, newY + movementOffset, pos.getScale());
				} else if (Math.abs(displacementX) >= halfSnapzoneWidth * 2 && snappedZone.getDirection() == SnappingDirection.HORIZONTAL) {
					int movementOffset = 0;
					if (displacementX > 0) {
						movementOffset = halfSnapzoneWidth * 2;
					} else if (displacementX < 0) {
						movementOffset = - (halfSnapzoneWidth * 2);
					}
					pos.setPos(newX + movementOffset, newY, pos.getScale());
				} else if (snappedZone.getDirection() == SnappingDirection.HORIZONTAL) {
					pos.setPos(pos.getX(), newY, pos.getScale());
				} else {
					pos.setPos(newX, pos.getY(), pos.getScale());
				}
			} else {
				pos.setPos(newX, newY, pos.getScale());
			}
		} else {
			displacementX = 0;
			displacementY = 0;
		}*/
		pos.setPos(newX, newY, pos.getScale());
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
		RenderInfo pos = mod.getPos();

		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());

		int screenWidth = res.getScaledWidth();
		int screenHeight = res.getScaledHeight();

		int absoluteX = Math.max(0, Math.min(pos.getX(), Math.max(screenWidth - (int) (mod.getWidth() * pos.getScale()), 0)));
		int absoluteY = Math.max(0, Math.min(pos.getY(), Math.max(screenHeight - (int) (mod.getHeight() * pos.getScale()), 0)));

		pos.setPos(absoluteX, absoluteY, pos.getScale());
	}

	@Override
	protected void mouseClicked(int x, int y, int button) throws IOException {
		this.prevX = x;
		this.prevY = y;

		loadMouseOver(x, y);

		if (selectedMod.isPresent()) {
			RenderInfo pos = selectedMod.get().getPos();

			if (selectedMod.get().shouldUsePadding()) {
				if (x >= pos.getX() - padding && x <= pos.getX() - padding + nodeSize && y >= pos.getY() - padding - (nodeSize + 4) && y <= pos.getY() - padding - 4) {
					if (pos.getScale() > 0.5) pos.setScale((float) (pos.getScale() - 0.1));
				} else if (x >= pos.getX() - padding + nodeSize + 4 && x <= pos.getX() - padding + 2 * nodeSize + 4 && y >= pos.getY() - padding - (nodeSize + 4) && y <= pos.getY() - padding - 4) {
					if (pos.getScale() < 2) pos.setScale((float) (pos.getScale() + 0.1));
				}
			} else {
				if (x >= pos.getX() && x <= pos.getX() + nodeSize && y >= pos.getY() - (nodeSize + 4) && y <= pos.getY() - 4) {
					if (pos.getScale() > 0.5) pos.setScale((float) (pos.getScale() - 0.1));
				} else if (x >= pos.getX() + nodeSize + 4 && x <= pos.getX() + 2 * nodeSize + 4 && y >= pos.getY() - (nodeSize + 4) && y <= pos.getY() - 4) {
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
			RenderInfo pos = mod.getPos();

			int absoluteX = pos.getX();
			int absoluteY = pos.getY();

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