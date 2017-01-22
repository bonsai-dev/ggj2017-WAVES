package bonsai.dev.ggj2017;


import com.badlogic.gdx.graphics.Color;

public class HealthZone extends Zone {

    private float healRate = 10;

    public HealthZone(float anchorX, float anchorY, float width, float height, Color color) {
        super(anchorX, anchorY, width, height, color);
    }

    float applyHeal(Player player, float deltaTime) {
        return player.applyHeal(healRate * deltaTime);
    }

}
