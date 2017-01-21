package bonsai.dev.ggj2017.menu.items;

import bonsai.dev.ggj2017.menu.MenuItem;
import bonsai.dev.ggj2017.menu.MenuScreen;

/**
 * Created by Matthias on 20.01.17.
 */
public class OptionsMenuItem extends MenuItem {

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
