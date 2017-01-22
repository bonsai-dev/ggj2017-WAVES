package bonsai.dev.ggj2017;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public interface Wave {

    void setSpeed(float speed);

    float getSize();
    void render(ShapeRenderer shapeRenderer, float deltaTime);
    boolean coversPoint(float xCoord, float yCoord);

}
