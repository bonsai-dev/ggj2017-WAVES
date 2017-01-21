package bonsai.dev.ggj2017.menu;

import bonsai.dev.ggj2017.GameScreen;
import bonsai.dev.ggj2017.WavesGame;
import com.badlogic.gdx.*;
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

    private MenuItem selectedItem;

    public MenuScreen(WavesGame game) {
        this.game = game;

        font = new BitmapFont();

        logoTexture = new Texture(Gdx.files.internal("Logo.png"));

        menuItems = new LinkedList<MenuItem>();

        StartMenuItem startMenuItem = new StartMenuItem(this);
        startMenuItem.select();
        selectedItem = startMenuItem;

        menuItems.add(startMenuItem);
        menuItems.add(new HighscoreMenuItem(this));
        menuItems.add(new OptionsMenuItem(this));
        menuItems.add(new ExitMenuItem(this));

        
        Gdx.input.setInputProcessor(new InputAdapter() {

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
        });

        Controllers.addListener(new ControllerAdapter() {
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
        });
    }

    @Override
    public void show() {
        Gdx.app.log("MENU", "Show");
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
                selectedItem = item;
                selectNext = false;
            }
        }

        Gdx.app.log("MENU", "Down");
    }

    public void startGame() {
        Gdx.app.log("MENU", "StartGame");
        game.setScreen(new GameScreen());
    }

    
    public void exit() {
        Gdx.app.log("MENU", "Exit");
        Gdx.app.exit();
    }

}
