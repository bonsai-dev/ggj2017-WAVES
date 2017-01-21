package bonsai.dev.ggj2017;

import java.util.List;

/**
 * Created by kaso on 1/20/17.
 */
public interface WaveGenerator {

    void turnOn();
    void turnOff();
    void setEmissionSpeed(float timeBetweenEmissions);
    List<Wave> processTime(float deltaTime);
}
