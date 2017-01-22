package bonsai.dev.ggj2017;


import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Zone {

    private float anchorX;
    private float anchorY;

    private float width;
    private float height;

    private Color color;

    public Zone(float anchorX, float anchorY, float width, float height, Color color) {
        this.anchorX = anchorX;
        this.anchorY = anchorY;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public float getAnchorX() {
        return anchorX;
    }

    public float getAnchorY() {
        return anchorY;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Color getColor() {
        return color;
    }

    public boolean isInZone(float posX, float posY) {
        return posX > anchorX && posX < (anchorX + width) &&
                posY > anchorY && posY < (anchorY + height);
    }

    public void render(ShapeRenderer shapeRenderer) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(color);
        shapeRenderer.rect(anchorX, anchorY, width, height);
        shapeRenderer.end();
    }
}
