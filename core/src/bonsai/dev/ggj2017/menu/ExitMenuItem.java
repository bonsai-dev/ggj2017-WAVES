package bonsai.dev.ggj2017.menu;

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
