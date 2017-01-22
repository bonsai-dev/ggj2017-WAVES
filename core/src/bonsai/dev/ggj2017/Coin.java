package bonsai.dev.ggj2017;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Coin {

    float posX;
    float posY;
    Texture texture;
    Sprite sprite;

    public Coin(float posX, float posY, Texture texture) {
        this.posX = posX;
        this.posY = posY;
        this.texture = texture;
        this.sprite = new Sprite(texture);
    }


    public void render(SpriteBatch batch) {
        batch.begin();
        float width = 30;
        float height = 30;
        sprite.setSize(30, 30);
        sprite.setOriginCenter();
        sprite.setPosition(posX, posY);
        sprite.setCenter(posX, posY);

        sprite.setRotation(0);
        sprite.draw(batch);
        batch.end();
    }

    public void dispose() {
        texture.dispose();
    }
}
