package bonsai.dev.ggj2017;

import bonsai.dev.ggj2017.menu.MenuScreen;
import com.badlogic.gdx.Game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class WavesGame extends Game {
    public SpriteBatch batch;

    @Override
    public void create () {
        batch = new SpriteBatch();
        this.setScreen(new MenuScreen(this));
    }

    @Override
    public void render () {
        super.render();
    }

    @Override
    public void dispose () {
        batch.dispose();
    }
}
