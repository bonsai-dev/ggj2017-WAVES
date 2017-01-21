package bonsai.dev.ggj2017;

import java.util.List;

public interface WaveGenerator {

    void turnOn();
    void turnOff();
    void setEmissionSpeed(float timeBetweenEmissions);
    List<Wave> processTime(float deltaTime);
}
