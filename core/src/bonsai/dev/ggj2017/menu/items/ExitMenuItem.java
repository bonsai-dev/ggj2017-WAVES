package bonsai.dev.ggj2017.menu.items;

import bonsai.dev.ggj2017.menu.MenuItem;
import bonsai.dev.ggj2017.menu.MenuScreen;

public class ExitMenuItem extends MenuItem {

    private MenuScreen screen;

    public ExitMenuItem(MenuScreen screen) {
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.exit();
    }

    @Override
    public String getName() {
        return "Exit";
    }
}
