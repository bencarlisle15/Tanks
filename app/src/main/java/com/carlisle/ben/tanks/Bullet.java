package com.carlisle.ben.tanks;

public class Bullet extends Entity {

    private double angle;
    private int numBounces;
    private double speedX;
    private double speedY;
    private boolean doesExist;

    // xpos and ypos are of the tank
    public Bullet(int xpos, int ypos, double angle) {
        super(xpos, ypos);
        this.angle = angle;
        numBounces = 2;
        doesExist = true
        speedX = 10.0*(Math.cos(angle));
        speedY = 10.0*(Math.sin(angle));
    }

    public boolean collisionDetected(int[] otherObj) {

        CollisionDetectorNoTank collisionDetector = new CollisionDetectorNoTank(getXpos(), getYpos(), otherObj[0], otherObj[1]);

        return collisionDetector.isCollision();
    }

    public void updatePosition(Map ) {

        if (collisionDetected()) {
            doesExist = false;
        }


    }
}
