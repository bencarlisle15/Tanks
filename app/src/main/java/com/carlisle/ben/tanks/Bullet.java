package com.carlisle.ben.tanks;

public class Bullet extends Entity {

    private int direction;
    private int speedX;
    private int speedY;

    // xpos and ypos are of the tank
    public Bullet(int xpos, int ypos) {
        super(xpos, ypos);
    }

    public boolean collisionDetected(int[] posTank, int[] pos) {



    }
}
