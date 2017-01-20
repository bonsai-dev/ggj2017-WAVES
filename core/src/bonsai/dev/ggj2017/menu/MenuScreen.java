package bonsai.dev.ggj2017.menu;

import bonsai.dev.ggj2017.WavesGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.controllers.mappings.Xbox;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MenuScreen implements Screen {

    private WavesGame game;
    private ArrayList<MenuItem> menuItems;
    private BitmapFont font;


    public MenuScreen(WavesGame game) {
        this.game = game;

        font = new BitmapFont();

        menuItems = new ArrayList<MenuItem>();
        menuItems.add(new StartMenuItem(this));
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
        Gdx.gl.glClearColor(1, 0, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int startPos = 400;

        game.batch.begin();

        Iterator<MenuItem> menuItemsIterator= menuItems.iterator();

        while (menuItemsIterator.hasNext()) {
            MenuItem item = menuItemsIterator.next();

            font.draw(game.batch, item.getName(), 40, startPos);
            startPos -= 30;
        }


        game.batch.end();
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
    public void dispose() {

    }

    private void menuUp() {
        Gdx.app.log("MENU", "Up");
    }

    private void menuDown() {
        Gdx.app.log("MENU", "Down");
    }

    public void startGame() {
        Gdx.app.log("MENU", "StartScreen");
    }
    
    public void exit() {
        Gdx.app.log("MENU", "Exit");
    }

}
