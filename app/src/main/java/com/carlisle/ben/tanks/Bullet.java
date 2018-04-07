package com.carlisle.ben.tanks;

public class Bullet extends Entity {

    private double angle;
    private int numBounces;
    boolean doesExist;
    private double speedX;
    private double speedY;

    // xpos and ypos are of the tank
    public Bullet(int xpos, int ypos, double angle) {
        super(xpos, ypos);
        this.angle = angle;
        numBounces = 2;
        doesExist = true;
        speedX = 10.0*(Math.cos(angle));
        speedY = 10.0*(Math.sin(angle));
    }

    public boolean collisionDetected(Map theMap) {

        CollisionDetectorNoTank collisionDetector = new CollisionDetectorNoTank(this,
                                                                                theMap.getEntity(getXpos(), getYpos()));

        return collisionDetector.isCollisionWithWall();
    }

    public void updatePosition(Map theMap) {

        if (!collisionDetected(theMap)) {
            setPosition((int) (getXpos() + speedX), (int) (getYpos() + speedY));
        }
    }

    public void changeExist() {
        doesExist = !doesExist;
    }
}
