package bonsai.dev.ggj2017.menu.items;

import bonsai.dev.ggj2017.menu.MenuItem;
import bonsai.dev.ggj2017.menu.MenuScreen;

/**
 * Created by Matthias on 20.01.17.
 */
public class PlayerTestMenuItem extends MenuItem {

    private MenuScreen screen;

    public PlayerTestMenuItem(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.startPlayerTest();
    }

    @Override
    public String getName() {
        return "Player Test";
    }
}
