package bonsai.dev.ggj2017.menu;

import bonsai.dev.ggj2017.WavesGame;
import com.badlogic.gdx.Screen;

/**
 * Created by Matthias on 20.01.17.
 */
public class StartMenuItem implements MenuItem {

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
