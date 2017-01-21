package bonsai.dev.ggj2017;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by kaso on 1/20/17.
 */
public interface Wave {

    void setSpeed(float speed);

    void render(ShapeRenderer shapeRenderer, float deltaTime);

}
