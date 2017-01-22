package bonsai.dev.ggj2017;

public class Player {

    float health;
    float maxHealth;
    float posX;
    float posY;
    float speed;

    public Player(float xPos, float yPos) {
        posX = xPos;
        posY = yPos;
        speed = 75;
        health = 100;
        maxHealth= 100;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getHealth() {
        return health;
    }

    public float getMaxHealth() {
        return maxHealth;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }

    public float getSpeed() {
        return speed;
    }

    public float applyDamage(float damage) {
        health -= damage;
        if(health < 0) {
            health = 0;
        }
        return health;
    }

    public float applyHeal(float healAmount) {
        health += healAmount;
        if(health > maxHealth) {
            health = maxHealth;
        }
        return health;
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void moveToPosition(float posX, float posY) {
        this.posX = posX;
        this.posY = posY;
    }

}