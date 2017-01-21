package bonsai.dev.ggj2017;

import bonsai.dev.ggj2017.impl.WaveGeneratorImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    ShapeRenderer shapeRenderer;
    int width;
    int height;
    float dTime;
    WaveGenerator waveGenerator;
    List<Wave> waves;
    boolean lastStateCovered;
    Color dotColor;

    public GameScreen() {
        shapeRenderer = new ShapeRenderer();
        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        waves = new ArrayList<Wave>();
        waveGenerator = new WaveGeneratorImpl(width/2, height/2);
        waveGenerator.setEmissionSpeed(10);
        waveGenerator.turnOn();
        shapeRenderer = new ShapeRenderer();
        width = Gdx.app.getGraphics().getWidth();
        height = Gdx.app.getGraphics().getHeight();
        waves = new ArrayList<Wave>();
        waveGenerator = new WaveGeneratorImpl(width/2, height/2);
        waveGenerator.setEmissionSpeed(10);
        waveGenerator.turnOn();
        dotColor = Color.BLUE;

        lastStateCovered = false;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        dTime = Gdx.graphics.getDeltaTime();

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        List<Wave> newWaves = waveGenerator.processTime(dTime);
        if(newWaves != null) {
            waves.addAll(newWaves);
        }

        for(Wave wave : waves) {
            wave.render(shapeRenderer, dTime);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(dotColor);
        shapeRenderer.circle(100, 100, 3);
        shapeRenderer.end();

        wavesCleanup(waves);
        boolean isCovered = checkIsCoveredByAnyWave(100, 100, waves);
        if(isCovered != lastStateCovered) {
            lastStateCovered = isCovered;
            if(lastStateCovered) {
                dotColor = Color.GREEN;
            } else {
                dotColor = Color.RED;
            }
        }
    }

    private void wavesCleanup(List<Wave> waves) {
        float diag = (float) Math.sqrt(width*width+height*height);
        Iterator<Wave> it = waves.iterator();
        while(it.hasNext()) {
            Wave wave = it.next();
            if(wave.getSize() > diag) {
                it.remove();
            }
        }
    }

    private boolean checkIsCoveredByAnyWave(float xCoord, float yCoord, List<Wave> waves) {

        for (Wave wave : waves) {
            if(wave.coversPoint(xCoord, yCoord)) {
                return true;
            }
        }

        return false;
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
    }
}
