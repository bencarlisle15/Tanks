package com.carlisle.ben.tanks;

import static java.lang.Math.sin;

public class Bullet extends Entity {

    private double angle;
    private int numBounces;
    boolean doesExist;
    private final int speed = 10;

    // xpos and ypos are of the tank
    public Bullet(int xpos, int ypos, double angle) {
        super(xpos, ypos);
        this.angle = angle;
        numBounces = 2;
        doesExist = true;
    }

    //returns false if no collission true if wall collision
    public boolean collisionDetected(Map theMap, int nextXPos, int nextYPos) {
		if (theMap.getEntity(nextXPos, nextYPos) == null) {
			return false;
		} else if ((theMap.getEntity(nextXPos, nextYPos)) instanceof Wall) {
			return true;
		}
		return false;
    }

    public void updatePosition(Map theMap) {
		int nextXPos = (int) (getXpos() + speed*Math.cos(angle));
		int nextYPos = (int) (getYpos() + speed*Math.sin(angle));
        if (!collisionDetected(theMap, nextXPos, nextYPos)) {
            setPosition((int) (getXpos() + speed*Math.cos(angle)), (int) (getYpos() + angle* sin(angle)));
        } else {
			nextXPos -= 2*Math.cos(angle);
			nextYPos -= 2*Math.sin(angle);
        	for (int i = speed - 4; i >= 2 && collisionDetected(theMap, nextXPos, nextYPos); i -= 2) {
        		nextXPos -= i*Math.cos(angle);
        		nextYPos -= i*Math.sin(angle);
			}
			//find the slope from the first value that is not the wall
        }
    }

    public void changeExist() {
        doesExist = !doesExist;
    }
}
