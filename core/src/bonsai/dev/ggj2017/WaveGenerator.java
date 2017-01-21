package bonsai.dev.ggj2017;

import com.badlogic.gdx.graphics.Color;

import java.util.List;

public interface WaveGenerator {

    void turnOn();
    void turnOff();
    void setEmissionSpeed(float timeBetweenEmissions);
    List<Wave> processTime(float deltaTime);
    void setColor(Color color);
}
