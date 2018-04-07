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
        	theMap.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*Math.cos(angle)), (int) (getYpos() + speed*Math.sin(angle)));
            setPosition((int)(getXpos() + speed*Math.cos(angle)), (int)(getYpos() + speed*Math.sin(angle)));
        } else {
			nextXPos -= 2*Math.cos(angle);
			nextYPos -= 2*Math.sin(angle);
        	for (int i = speed - 4; i >= 2 && collisionDetected(theMap, nextXPos, nextYPos); i -= 2) {
        		nextXPos -= i*Math.cos(angle);
        		nextYPos -= i*Math.sin(angle);
			}
			//find the slope from the first value that is not the wall
			int slopeX1;
        	int slopeY1;
        	int slopeX2;
        	int slopeY2;
        	boolean isLeft1 = false, isLeft2 = false, isRight1 = false, isRight2 = false;
			for (int i  = 0; i < 5; i++) {
        		if (!collisionDetected(theMap, nextXPos + i, nextYPos + 1)) {
        			if (isRight1) {
						slopeX1 = nextXPos + i;
						slopeY1 = nextYPos + 1;
						break;
					}
				} else if (!isRight1){
        			isRight1 = true;
				}
				if (!collisionDetected(theMap, nextXPos - i, nextYPos + 1)) {
					if (isLeft1) {
						slopeX1 = nextXPos - i;
						slopeY1 = nextYPos + 1;
						break;
					}
				} else if (!isLeft1){
					isLeft1 = true;
				}
				if (!collisionDetected(theMap, nextXPos + i, nextYPos - 1)) {
					if (isRight2) {
						slopeX2 = nextXPos + i;
						slopeY2 = nextYPos - 1;
						break;
					}
				} else if (!isRight2){
					isRight2 = true;
				}
				if (!collisionDetected(theMap, nextXPos - i, nextYPos - 1)) {
					if (isLeft2) {
						slopeX2 = nextXPos - i;
						slopeY2 = nextYPos - 1;
						break;
					}
				} else if (!isLeft2){
					isLeft2 = true;
				}
			}
			if (isRight1 && isLeft2) {
				//TODO
			} else if (isRight2 && isLeft1) {
				//TODO
			} else {
				angle *= -1;
				theMap.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*Math.cos(angle)), (int) (getYpos() + speed*Math.sin(angle)));
				setPosition((int)(getXpos() + speed*Math.cos(angle)), (int)(getYpos() + speed*Math.sin(angle)));
			}
        }
    }

    public void changeExist() {
        doesExist = !doesExist;
    }
}
