package com.carlisle.ben.tanks;

public class Bullet extends Entity {

	private boolean alive = true;
	private final double xPercentage;
	private final double yPercentage;
	public final static int SPEED = 4;
	private final boolean isPlayer1;

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
	private boolean collisionDetected(Map theMap, int nextXPos, int nextYPos) {
		return theMap.getEntity(nextXPos, nextYPos) == null && (theMap.getEntity(nextXPos, nextYPos)) instanceof Wall;
	}

	public boolean isAlive() {
		return alive;
	}

	private int get_new_xPos(double xPercentage) {
		return (int) (getXpos() + SPEED * xPercentage / Math.sqrt(xPercentage * xPercentage + yPercentage * yPercentage));
	}

	private int get_new_yPos(double yPercentage) {
		return (int) (getYpos() + SPEED * yPercentage / Math.sqrt(xPercentage * xPercentage + yPercentage * yPercentage));
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
	}
}