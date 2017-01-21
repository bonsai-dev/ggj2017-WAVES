package bonsai.dev.ggj2017.menu;

import bonsai.dev.ggj2017.GameScreen;
import bonsai.dev.ggj2017.WavesGame;
import bonsai.dev.ggj2017.playertest.PlayerTestScreen;
import com.badlogic.gdx.*;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.PovDirection;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class MenuScreen extends ScreenAdapter {

    private WavesGame game;

    private LinkedList<MenuItem> menuItems;

    private BitmapFont font;
    private Texture logoTexture;
    private Sound switchAudio;
    private Sound bgAudio;

    private InputAdapter inputAdapter;
    private ControllerAdapter controllerAdapter;

    private MenuItem selectedItem;

    public MenuScreen(WavesGame game) {
        this.game = game;

        // assets
        font = new BitmapFont();
        logoTexture = new Texture(Gdx.files.internal("Logo.png"));
        switchAudio = Gdx.audio.newSound(Gdx.files.internal("key.ogg"));
        bgAudio = Gdx.audio.newSound(Gdx.files.internal("Ove Melaa - Heaven Sings.mp3"));

        // controls
        inputAdapter = new InputAdapter() {
            @Override
            public boolean keyUp(int keycode) {
                if (keycode == Input.Keys.DOWN) {
                    menuDown();
                }
                if (keycode == Input.Keys.UP) {
                    menuUp();
                }
                if (keycode == Input.Keys.ENTER) {
                    menuSelect();
                }
                return true;
            }
        };

        controllerAdapter = new ControllerAdapter() {
            @Override
            public boolean buttonDown(Controller controller, int buttonIndex) {
                if (buttonIndex == Xbox.DPAD_DOWN) {
                    menuDown();
                }

                if (buttonIndex == Xbox.DPAD_UP) {
                    menuUp();
                }

                if (buttonIndex == Xbox.A) {
                    menuSelect();
                }

                Gdx.app.log("CONTROLLER", Integer.toString(buttonIndex));
                return true;
            }
        };

        // menu
        menuItems = new LinkedList<MenuItem>();

        StartMenuItem startMenuItem = new StartMenuItem(this);
        startMenuItem.select();
        selectedItem = startMenuItem;

        menuItems.add(startMenuItem);
        menuItems.add(new HighscoreMenuItem(this));
        menuItems.add(new PlayerTestMenuItem(this));
        menuItems.add(new OptionsMenuItem(this));
        menuItems.add(new ExitMenuItem(this));
    }

    @Override
    public void show() {
        bgAudio.play(0.2f);

        Gdx.input.setInputProcessor(inputAdapter);
        Controllers.addListener(controllerAdapter);
    }

    @Override
    public void hide() {
        bgAudio.stop();

        Gdx.input.setInputProcessor(null);
        Controllers.removeListener(controllerAdapter);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int startPos = 400;

        game.batch.begin();

        float logoMulti = 0.4f;

        game.batch.draw(logoTexture, 150, 140, logoTexture.getWidth() * logoMulti, logoTexture.getHeight() * logoMulti);

        for (MenuItem item : menuItems) {
            font.setColor(0, 0, 0, 1);

            if (item.isSelected()) {
                font.setColor(1, 0, 0, 1);
            }

            font.draw(game.batch, item.getName(), 40, startPos);
            startPos -= 30;
        }


        game.batch.end();
    }

    private void menuSelect() {
        selectedItem.action();
    }

    private void menuUp() {
        MenuItem prevItem = null;

        for (MenuItem item : menuItems) {

            if (item.isSelected() && prevItem != null) {
                prevItem.select();
                switchAudio.play(1.0f);
                selectedItem = prevItem;
                item.deselect();
            }

            prevItem = item;
        }

        Gdx.app.log("MENU", "Up");
    }

    private void menuDown() {
        boolean selectNext = false;

        Iterator<MenuItem> menuItemsIterator = menuItems.iterator();
        while (menuItemsIterator.hasNext()) {
            MenuItem item = menuItemsIterator.next();

            if (item.isSelected() && menuItemsIterator.hasNext()) {
                item.deselect();
                selectNext = true;
            } else if (selectNext) {
                item.select();
                switchAudio.play(1.0f);
                selectedItem = item;
                selectNext = false;
            }
        }

        Gdx.app.log("MENU", "Down");
    }

    public void startGame() {
        game.setScreen(new GameScreen());
    }

    
    public void exit() {
        Gdx.app.exit();
    }

    public void startPlayerTest() {
        game.setScreen(new PlayerTestScreen(game));
    }

    @Override
    public void dispose() {
        bgAudio.dispose();
        switchAudio.dispose();
        logoTexture.dispose();
        font.dispose();
    }
}
