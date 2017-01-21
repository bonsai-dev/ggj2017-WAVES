package bonsai.dev.ggj2017.menu.items;

import bonsai.dev.ggj2017.menu.MenuItem;
import bonsai.dev.ggj2017.menu.MenuScreen;

/**
 * Created by Matthias on 20.01.17.
 */
public class HighscoreMenuItem extends MenuItem {

    private MenuScreen screen;

    public HighscoreMenuItem(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.startGame();
    }

    @Override
    public String getName() {
        return "Highscore";
    }
}
