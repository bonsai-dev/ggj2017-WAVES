package bonsai.dev.ggj2017.menu;

/**
 * Created by Matthias on 20.01.17.
 */
public class OptionsMenuItem implements MenuItem {

    private MenuScreen screen;

    public OptionsMenuItem(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.startGame();
    }

    @Override
    public String getName() {
        return "Options";
    }
}
