package bonsai.dev.ggj2017.menu;

public class StartMenuItem extends MenuItem {

    private MenuScreen screen;

    public StartMenuItem(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.startGame();
    }

    @Override
    public String getName() {
        return "Start Game";
    }
}
