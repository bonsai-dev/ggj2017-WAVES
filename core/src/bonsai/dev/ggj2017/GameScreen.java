package bonsai.dev.ggj2017;

import bonsai.dev.ggj2017.impl.WaveGeneratorImpl;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GameScreen implements Screen {
    private ShapeRenderer shapeRenderer;
    private SpriteBatch batch;
    private int width;
    private int height;
    private WaveGenerator waveGenerator1;
    private List<Wave> waves;
    private List<Zone> zones;
    private boolean lastStateCovered;
    private Player player;

    private WaveGenerator waveGenerator2;
    private WaveGenerator waveGenerator3;

    private Viewport viewport;
    private Camera camera;

    private Texture damageOverlay;
    private Sprite damageSprite;
    private Color deathColor;
    private Texture deathTextTex;

    private float damageTick;

    private Coin coin;


    public GameScreen() {
        shapeRenderer = new ShapeRenderer();
        batch = new SpriteBatch();
        //width = Gdx.app.getGraphics().getWidth();
        //height = Gdx.app.getGraphics().getHeight();
        width = 1280;
        height = 720;

        damageOverlay = new Texture(Gdx.files.internal("DamageScreen.png"));
        damageSprite = new Sprite(damageOverlay);
        deathTextTex = new Texture(Gdx.files.internal("YoureDead.png"));

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

        deathColor = new Color(135f/256f, 1f/256f, 1f/256f, 0f);

        lastStateCovered = false;

        player = new Player(225, 225);
        player.setTexture(new Texture(Gdx.files.internal("Player.png")));

        damageTick = 50;

        Zone healZone = new HealthZone(200, 200, 50, 50, Color.GREEN);

        zones = new ArrayList<Zone>();
        zones.add(healZone);

        coin = new Coin(900, 500, new Texture(Gdx.files.internal("Item.png")));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);
        batch.setProjectionMatrix(camera.combined);
        batch.enableBlending();

        if(!player.isAlive()) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.rect(0, 0, width, height, deathColor, deathColor, deathColor, deathColor);
            shapeRenderer.end();
            batch.begin();
            batch.draw(deathTextTex, width/2 - deathTextTex.getWidth()/2,
                    height/2 - deathTextTex.getHeight()/2);
            batch.end();
            return;
        }

        float newPlayerX = player.getPosX();
        float newPlayerY = player.getPosY();
        boolean playerInSafeZone = false;
        if(Gdx.input.isKeyPressed(Input.Keys.W) || Gdx.input.isKeyPressed(Input.Keys.UP)) {
            newPlayerY += delta * player.getSpeed();
            player.setRotationDegrees(180f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            newPlayerX -= delta * player.getSpeed();
            player.setRotationDegrees(270f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S) || Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            newPlayerY -= delta * player.getSpeed();
            player.setRotationDegrees(0f);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            newPlayerX += delta * player.getSpeed();
            player.setRotationDegrees(90f);
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

        coin.render(batch);

        player.render(batch, shapeRenderer);


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
                player.setHeadColor(Color.GREEN);
            } else {
                player.setHeadColor(Color.RED);
            }
        }

        if(!isCovered && player.isAlive()) {
            player.applyDamage(damageTick * delta);
        }

        batch.begin();
        float alpha = 1f - 1f * (player.getHealth()/player.getMaxHealth());
        damageSprite.setColor(1, 1, 1, alpha);
        damageSprite.draw(batch, alpha);
        batch.end();
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
        damageOverlay.dispose();
        deathTextTex.dispose();
        player.dispose();
        coin.dispose();
    }
}
