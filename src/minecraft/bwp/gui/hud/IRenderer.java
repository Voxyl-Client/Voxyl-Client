package bwp.gui.hud;

public interface IRenderer extends IRenderConfig {
	int getWidth();
	int getHeight();
	
	void render(RenderInfo pos);
	
	default void renderDummy(RenderInfo pos) {
		render(pos);
	}

	default boolean isEnabled() {
		return true;
	}

	default boolean shouldRender() { return true; }

	default boolean shouldUsePadding() { return true; }
}
