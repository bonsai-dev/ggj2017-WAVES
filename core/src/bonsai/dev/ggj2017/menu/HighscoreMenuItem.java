package bonsai.dev.ggj2017.menu;

/**
 * Created by Matthias on 20.01.17.
 */
public class HighscoreMenuItem implements MenuItem {

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
