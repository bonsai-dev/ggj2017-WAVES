package bonsai.dev.ggj2017.impl;

import bonsai.dev.ggj2017.Wave;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class WaveImpl implements Wave {

    private float speed;
    private float size;
    private float waveSpread;
    private float widthPos;
    private float heightPos;

    public WaveImpl(float widthPos, float heightPos) {
        this.size = 0f;
        this.waveSpread = 20f;
        this.speed = 20f;
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
        float innerSize = size-waveSpread;
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(widthPos, heightPos, size, calcSegments(size));
        if(size > waveSpread) {
            shapeRenderer.circle(widthPos, heightPos, innerSize, calcSegments(innerSize));
        }
        shapeRenderer.end();
    }

    private int calcSegments(float size) {
        return 20+(int)(Math.ceil(size) / 10);
    }

    @Override
    public boolean coversPoint(float xCoord, float yCoord) {
        float distance = (float) Math.sqrt(Math.pow(Math.abs(widthPos - xCoord), 2)
                + Math.pow(Math.abs(heightPos - yCoord), 2)) - size;
        return distance <= 0  && Math.abs(distance) < waveSpread;
    }

    @Override
    public float getSize() {
        return size;
    }
}
