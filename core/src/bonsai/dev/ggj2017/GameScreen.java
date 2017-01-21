package bonsai.dev.ggj2017;

import bonsai.dev.ggj2017.impl.WaveGeneratorImpl;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameScreen implements Screen {
    SpriteBatch batch;
    Texture img;
    ShapeRenderer shapeRenderer;
    int width;
    int height;
    float dTime;
    WaveGenerator waveGenerator;
    List<Wave> waves;

    public GameScreen() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        shapeRenderer = new ShapeRenderer();
        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        waves = new ArrayList<Wave>();
        waveGenerator = new WaveGeneratorImpl(width/2, height/2);
        waveGenerator.setEmissionSpeed(10);
        waveGenerator.turnOn();batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        shapeRenderer = new ShapeRenderer();
        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        waves = new ArrayList<Wave>();
        waveGenerator = new WaveGeneratorImpl(width/2, height/2);
        waveGenerator.setEmissionSpeed(10);
        waveGenerator.turnOn();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        dTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        List<Wave> newWaves = waveGenerator.processTime(dTime);
        if(newWaves != null) {
            waves.addAll(newWaves);
        }

        for(Wave wave : waves) {
            wave.render(shapeRenderer, dTime);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose () {
        batch.dispose();
        img.dispose();
    }
}
