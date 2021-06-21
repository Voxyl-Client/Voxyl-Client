package bwp.gui.elements;

public interface GuiIntractable {

    boolean handleInteract(int mouseX, int mouseY);

    void onLeftClick(int mouseX, int mouseY);
}
