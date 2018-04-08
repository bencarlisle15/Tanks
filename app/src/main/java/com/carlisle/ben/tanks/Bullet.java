package com.carlisle.ben.tanks;

public class Bullet extends Entity {

    private double angle;
    private boolean alive = true;
    private double xPercentage;
    private double yPercentage;
    public final static int SPEED = 4;
    private boolean isPlayer1;

    // xpos and ypos are of the tank
    public Bullet(int xpos, int ypos, double xPercent, double yPercent, boolean isPlayer1) {
        super(xpos, ypos);
        this.xPercentage = xPercent;
        this.yPercentage = yPercent;
        this.isPlayer1 = isPlayer1;
    }

    public boolean isPlayer1() {
    	return isPlayer1;
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

    public boolean isAlive() {
    	return alive;
	}

	public void killBullet() {
    	alive = false;
	}

	private int get_new_xPos(double xPercentage){
		return (int) (getXpos() + SPEED*xPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage));
	}

	private int get_new_yPos(double yPercentage){
		return (int)(getYpos() + SPEED*yPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage));
	}

    public void updatePosition(Map map) {
		int new_xPos = get_new_xPos(xPercentage);
		int new_yPos = get_new_yPos(yPercentage);
		if (new_xPos >= 0 && new_yPos >= 0 && new_xPos < map.getWidth() && new_yPos < map.getHeight() && !collisionDetected(map, new_xPos, new_yPos)) {
			map.moveEntity(getXpos(), getYpos(), new_xPos, new_yPos);
			setPosition(new_xPos, new_yPos);
		} else {
			alive = false;
		}
//        if (!collisionDetected(theMap, nextXPos, nextYPos)) {
//        	theMap.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*xPercentage), (int) (getYpos() + speed*yPercentage));
//            setPosition((int)(getXpos() + speed*xPercentage), (int)(getYpos() + speed*yPercentage));
//        } else {
//			nextXPos -= 2*xPercentage;
//			nextYPos -= 2*yPercentage;
//        	for (int i = speed - 4; i >= 2 && collisionDetected(theMap, nextXPos, nextYPos); i -= 2) {
//        		nextXPos -= i*xPercentage;
//        		nextYPos -= i*yPercentage;
//			}
//			//find the slope from the first value that is not the wall
//			int slopeX1 = 0;
//        	int slopeY1 = 0;
//        	int slopeX2 = 0;
//        	int slopeY2 = 0;
//        	boolean isLeft1 = false, isLeft2 = false, isRight1 = false, isRight2 = false;
//			for (int i  = 0; i < 5; i++) {
//        		if (!collisionDetected(theMap, nextXPos + i, nextYPos + 1)) {
//        			if (isRight1) {
//						slopeX1 = nextXPos + i;
//						slopeY1 = nextYPos + 1;
//						break;
//					}
//				} else if (!isRight1){
//        			isRight1 = true;
//				}
//				if (!collisionDetected(theMap, nextXPos - i, nextYPos + 1)) {
//					if (isLeft1) {
//						slopeX1 = nextXPos - i;
//						slopeY1 = nextYPos + 1;
//						break;
//					}
//				} else if (!isLeft1){
//					isLeft1 = true;
//				}
//				if (!collisionDetected(theMap, nextXPos + i, nextYPos - 1)) {
//					if (isRight2) {
//						slopeX2 = nextXPos + i;
//						slopeY2 = nextYPos - 1;
//						break;
//					}
//				} else if (!isRight2){
//					isRight2 = true;
//				}
//				if (!collisionDetected(theMap, nextXPos - i, nextYPos - 1)) {
//					if (isLeft2) {
//						slopeX2 = nextXPos - i;
//						slopeY2 = nextYPos - 1;
//						break;
//					}
//				} else if (!isLeft2){
//					isLeft2 = true;
//				}
//			}
//			double slope1;
//			double newAngle;
//			if (isRight1 && isLeft2 && slopeY1 != 0 && slopeY1 != 0 && slopeX1 != 0 && slopeX2 != 0 || isRight2 && isLeft1 && slopeY1 != 0 && slopeY1 != 0 && slopeX1 != 0 && slopeX2 != 0) {
//				slope1 = (slopeY1 - slopeY2) / (slopeX1 - slopeX2);
//				newAngle = Math.atan(slope1);
//				angle = newAngle - Math.abs(angle - newAngle);
//				theMap.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*xPercentage), (int) (getYpos() + speed*yPercentage));
//				setPosition((int)(getXpos() + speed*xPercentage), (int)(getYpos() + speed*yPercentage));
//				numBounces--;
//			} else {
//				angle *= -1;
//				theMap.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*xPercentage), (int) (getYpos() + speed*yPercentage));
//				setPosition((int)(getXpos() + speed*xPercentage), (int)(getYpos() + speed*yPercentage));
//				numBounces--;
//			}
//        }
    }
}
