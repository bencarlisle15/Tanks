package com.carlisle.ben.tanks;

import android.util.Log;

class Tank extends Entity implements Runnable {

	private boolean canFire;
	private int speed;
	private int lives;
	private final int width;
	private double xPercentage, yPercentage;
	private final boolean isPlayer1;
	private boolean isDead = false;


	public Tank(int xPos, int yPos, int width, boolean isPlayer1) {
		super(xPos, yPos);
		canFire = true;
		lives = 3;
		boolean isShootBottomPressed = false;
		this.width = width;
		this.isPlayer1 = isPlayer1;
	}

	private int get_new_xPos(double xPercentage){
		return (int) (getXpos() + speed*xPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage) + 1/2 * (Math.pow(xPercentage,2)));
	}

	private int get_new_yPos(double yPercentage){
		return (int)(getYpos() + speed*yPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage) + 1/2 * (Math.pow(yPercentage,2)));
	}

	public Bullet fire(Map map){
		if (canFire) {
			int xpos = (int) (getXpos() + Bullet.SPEED * xPercentage / Math.sqrt(xPercentage * xPercentage + yPercentage * yPercentage));
			int ypos = (int) (getYpos() + Bullet.SPEED * yPercentage / Math.sqrt(xPercentage * xPercentage + yPercentage * yPercentage));
			if (xpos < 0 || ypos < 0 || xpos >= map.getWidth() || ypos >= map.getHeight()) {
				return null;
			} else {
				canFire = false;
				new Thread(this).start();
				return new Bullet(xpos, ypos, xPercentage, yPercentage, isPlayer1);
			}
		}
		return null;
	}

	public void move(float xPercentage, float yPercentage, Map map){
		this.xPercentage = xPercentage;
		this.yPercentage = yPercentage;
		this.xPercentage = Math.min(1, Math.max(this.xPercentage, -1));
		this.yPercentage = Math.min(1, Math.max(this.yPercentage, -1));
		speed = (int) (Math.sqrt(this.xPercentage*this.xPercentage + this.yPercentage*this.yPercentage)*10);
		int new_xPos = get_new_xPos(this.xPercentage);
		int new_yPos = get_new_yPos(this.yPercentage);
		checkCollision(new_xPos, new_yPos, map);
	}

	private void checkCollision(int nextXPos, int nextYPos, Map map) {
		int newX;
		int newY;
		for (int r = 0; r < getRadius(); r++) {
			for (int c = 0; c < getRadius(); c++) {
				newX = nextXPos - r;
				newY = nextYPos - c;
				if (checkPos(newX, newY, map) == 1) {
					return;
				}
				newX = nextXPos + r;
				newY = nextYPos - c;
				if (checkPos(newX, newY, map) == 1) {
					return;
				}
				newX = nextXPos - r;
				newY = nextYPos + c;
				if (checkPos(newX, newY, map) == 1) {
					return;
				}
				newX = nextXPos + r;
				newY = nextYPos + c;
				if (checkPos(newX, newY, map) == 1) {
					return;
				}
			}
		}
		map.moveEntity(getXpos(), getYpos(), nextXPos, nextYPos);
		setPosition(nextXPos, nextYPos);
	}

	private int checkPos(int newX, int newY, Map map) {
		if (newX < 0 || newY < 0 || newX >= map.getWidth() || newY >= map.getHeight()) {
			return 1;
		} else if (map.getEntity(newX, newY) == null) {
			return 0;
		} else if ((map.getEntity(newX, newY)) instanceof Bullet && newX != 0 && newY != 0) {
			if (((Bullet) map.getEntity(newX, newY)).isPlayer1() != isPlayer1) {
				Log.e(String.valueOf(newX), String.valueOf(newY));
				Log.e(String.valueOf(getXpos()), String.valueOf(getYpos()));
				map.setEntity(newX, newY, null);
				lives--;
				if (lives == 0) {
					destoryTank(map);
				}
				return 1;
			}
		} else if (map.getEntity(newX, newY) == this) {
			return 0;
		}
		return 1;
	}

	public int getLives() {
		return lives;
	}

	private void destoryTank(Map map) {
		map.setEntity(getXpos(), getYpos(), null);
		Log.e("AM", "ded");
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	public double getRadius() {
		return width;
	}

	public double getXPercentage() {
		return xPercentage;
	}

	public double getYPercentage() {
		return yPercentage;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (InterruptedException ignored) {
		}
		canFire = true;

	}
}
