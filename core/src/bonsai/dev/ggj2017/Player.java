package bonsai.dev.ggj2017;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Player {

    float health;
    float maxHealth;
    float posX;
    float posY;
    float speed;
    Texture texture;
    Sprite sprite;
    Color headColor;
    float rotationDegrees;


    public Player(float xPos, float yPos) {
        posX = xPos;
        posY = yPos;
        speed = 75;
        health = 100;
        maxHealth= 100;
        headColor = Color.BLUE;
        rotationDegrees = 180;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getSpeed() {
        return speed;
    }

    public float applyDamage(float damage) {
        health -= damage;
        if(health < 0) {
            health = 0;
        }
        return health;
    }

    public float applyHeal(float healAmount) {
        health += healAmount;
        if(health > maxHealth) {
            health = maxHealth;
        }
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void moveToPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
        if(sprite == null) {
            sprite = new Sprite(this.texture);
        } else {
            sprite.setTexture(this.texture);
        }
    }

    public void render(SpriteBatch batch, ShapeRenderer shapeRenderer) {
        batch.begin();
        //batch.draw(sprite, posX-texture.getWidth()/2, posY-texture.getHeight()/2);
        float width = 50;
        float height = 50;
        sprite.setSize(50, 50);
        sprite.setOriginCenter();
        sprite.setPosition(posX, posY);
        sprite.setCenter(posX, posY);

        sprite.setRotation(rotationDegrees);
        sprite.draw(batch);

        batch.end();
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(headColor);
        shapeRenderer.circle(posX, posY, 3);
        shapeRenderer.end();
    }

    public void dispose() {
        this.texture.dispose();
    }

    public Color getHeadColor() {
        return headColor;
    }

    public void setHeadColor(Color headColor) {
        this.headColor = headColor;
    }

    public void setRotationDegrees(float rotationDegrees) {
        this.rotationDegrees = rotationDegrees;
    }
}