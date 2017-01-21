package bonsai.dev.ggj2017.menu;

abstract public class MenuItem {

    private boolean selected = false;

    abstract public void action();
    abstract public String getName();

    public void select() {
        selected = true;
    }

    public void deselect() {
        selected = false;
    }

    public boolean isSelected() {
        return selected;
    }

}
