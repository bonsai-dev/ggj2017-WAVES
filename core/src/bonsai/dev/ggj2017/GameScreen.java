package bonsai.dev.ggj2017;

import bonsai.dev.ggj2017.impl.WaveGeneratorImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    ShapeRenderer shapeRenderer;
    int width;
    int height;
    WaveGenerator waveGenerator1;
    List<Wave> waves;
    List<Zone> zones;
    boolean lastStateCovered;
    Color dotColor;
    Player player;

    WaveGenerator waveGenerator2;
    WaveGenerator waveGenerator3;

    private Viewport viewport;
    private Camera camera;


    float damageTick;


    public GameScreen() {
        shapeRenderer = new ShapeRenderer();
        //width = Gdx.app.getGraphics().getWidth();
        //height = Gdx.app.getGraphics().getHeight();
        width = 1280;
        height = 720;

        // viewport
        camera = new OrthographicCamera();
        viewport = new FitViewport(width, height, camera);
        viewport.apply();

        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight /  2, 0);

        waves = new ArrayList<Wave>();
        waveGenerator1 = new WaveGeneratorImpl(20, height);
        waveGenerator1.setEmissionSpeed(10);
        waveGenerator1.turnOn();
        waveGenerator1.setColor(Color.RED);
        waveGenerator2 = new WaveGeneratorImpl(width/2, height/2);
        waveGenerator2.setEmissionSpeed(8);
        waveGenerator2.turnOn();
        waveGenerator2.setColor(Color.BLUE);
        waveGenerator3 = new WaveGeneratorImpl(width-20, 20);
        waveGenerator3.setEmissionSpeed(15);
        waveGenerator3.turnOn();
        waveGenerator3.setColor(Color.YELLOW);

        dotColor = Color.BLUE;

        lastStateCovered = false;

        player = new Player(100, 100);

        damageTick = 50;

        Zone healZone = new HealthZone(15, 15, 50, 50, Color.GREEN);

        zones = new ArrayList<Zone>();
        zones.add(healZone);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        float newPlayerX = player.getPosX();
        float newPlayerY = player.getPosY();
        boolean playerInSafeZone = false;
        if(Gdx.input.isKeyPressed(Input.Keys.W)) {
            newPlayerY += delta * player.getSpeed();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A)) {
            newPlayerX -= delta * player.getSpeed();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)) {
            newPlayerY -= delta * player.getSpeed();
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)) {
            newPlayerX += delta * player.getSpeed();
        }

        player.moveToPosition(newPlayerX, newPlayerY);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //render zones
        for(Zone zone : zones) {
            zone.render(shapeRenderer);
            if(zone.isInZone(player.getPosX(), player.getPosY())
                    && HealthZone.class.isAssignableFrom(zone.getClass())) {
                playerInSafeZone = true;
                ((HealthZone) zone).applyHeal(player, delta);
            }
        }

        List<Wave> newWaves = waveGenerator1.processTime(delta);
        if(newWaves != null) {
            waves.addAll(newWaves);
        }
        newWaves = waveGenerator2.processTime(delta);
        if(newWaves != null) {
            waves.addAll(newWaves);
        }
        newWaves = waveGenerator3.processTime(delta);
        if(newWaves != null) {
            waves.addAll(newWaves);
        }

        for(Wave wave : waves) {
            wave.render(shapeRenderer, delta);
        }

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(dotColor);
        shapeRenderer.circle(player.getPosX(), player.getPosY(), 3);
        shapeRenderer.end();

        wavesCleanup(waves);
        final boolean isCovered;
        if(playerInSafeZone) {
            isCovered = true;
        } else {
            isCovered = checkIsCoveredByAnyWave(player.getPosX(), player.getPosY(), waves);
        }
        if(isCovered != lastStateCovered) {
            lastStateCovered = isCovered;
            if(lastStateCovered) {
                dotColor = Color.GREEN;
            } else {
                dotColor = Color.RED;
            }
        }

        if(dotColor.equals(Color.RED) && player.isAlive()) {
            player.applyDamage(damageTick * delta);
        }
        if(!player.isAlive()) {
            Gdx.app.log("HEALTH", "Player is dead.");
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
        viewport.update(width, height);
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
