package bonsai.dev.ggj2017.impl;

import bonsai.dev.ggj2017.Wave;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

/**
 * Created by kaso on 1/20/17.
 */
public class WaveImpl implements Wave {

    private float speed;
    private float size;
    private float waveSpread;
    private float widthPos;
    private float heightPos;

    public WaveImpl(float widthPos, float heightPos) {
        this.size = 0f;
        this.waveSpread = 20f;
        this.speed = 10f;
        this.heightPos = heightPos;
        this.widthPos = widthPos;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public void render(ShapeRenderer shapeRenderer, float deltaTime) {
        size += deltaTime * speed;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.circle(widthPos, heightPos, size);
        if(size > waveSpread) {
            shapeRenderer.circle(widthPos, heightPos, size-waveSpread);
        }
        shapeRenderer.end();
    }
}
