package bonsai.dev.ggj2017.impl;

import bonsai.dev.ggj2017.Wave;
import bonsai.dev.ggj2017.WaveGenerator;
import com.badlogic.gdx.graphics.Color;

import java.util.ArrayList;
import java.util.List;

public class WaveGeneratorImpl implements WaveGenerator {

    private float xPos;
    private float yPos;
    private boolean isActive;
    private float timeBetweenEmissions;
    private float lastEmission;
    private Color color;

    public WaveGeneratorImpl(float xPos, float yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.lastEmission = 0f;
        this.timeBetweenEmissions = 10; //10 seconds
        this.color = Color.RED;
    }

    @Override
    public void turnOn() {
        isActive = true;
    }

    @Override
    public void turnOff() {
        isActive = false;
    }

    @Override
    public void setEmissionSpeed(float timeBetweenEmissions) {
        this.timeBetweenEmissions = timeBetweenEmissions;
    }

    @Override
    public List<Wave> processTime(float deltaTime) {
        List<Wave> waves = null;
        if(!isActive) {
            return waves;
        }

        lastEmission += deltaTime;

        if(lastEmission >= timeBetweenEmissions) {
            waves = new ArrayList<Wave>();
            while(lastEmission > timeBetweenEmissions) {
                WaveImpl wave = new WaveImpl(xPos, yPos, color);
                waves.add(wave);
                lastEmission -= timeBetweenEmissions;
            }
        }

        return waves;
    }

    @Override
    public void setColor(Color color) {
        this.color = color;
    }
}
