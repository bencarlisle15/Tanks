package com.carlisle.ben.tanks;

import android.util.Log;

import java.util.ArrayList;

public class Tank extends Entity {

	private boolean canFire;
	private int speed;
	private int lives;
	private static double timer = 0.1;
	private int width;
	private final static double BULLETCONST = 10;
	private boolean isShootBottomPressed;
	private double xPercentage, yPercentage;
	private boolean isPlayer1;
	private boolean isDead = false;


	public Tank(int xPos, int yPos, int width, boolean isPlayer1) {
		super(xPos, yPos);
		canFire = true;
		lives = 3;
		isShootBottomPressed = false;
		this.width = width;
		this.isPlayer1 = isPlayer1;
	}

	private boolean validAngle(int checkAngle) {

		if(checkAngle < 0 || checkAngle > Math.PI){
			return false;
		}
		return true;
	}

	public void setXPercentage(double xPercentage){
		this.xPercentage = xPercentage;
	}

	public void setYPercentage(double yPercentage){
		this.yPercentage = yPercentage;
	}

	private int get_new_xPos(double xPercentage){
		return (int) (getXpos() + speed*xPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage) + 1/2 * (Math.pow(xPercentage,2)));
	}

	private int get_new_yPos(double yPercentage){
		return (int)(getYpos() + speed*yPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage) + 1/2 * (Math.pow(yPercentage,2)));
	}

	public Bullet fire(Map map){
		int xpos = (int)(getXpos() + Bullet.SPEED*xPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage));
		int ypos = (int)(getYpos() + Bullet.SPEED*yPercentage/Math.sqrt(xPercentage*xPercentage + yPercentage* yPercentage));
		if (xpos < 0 || ypos < 0 || xpos >= map.getWidth() || ypos >= map.getHeight()) {
			return null;
		}else {
			return new Bullet(xpos, ypos, xPercentage, yPercentage, isPlayer1);
		}
	}

	public void move(float xPercentage, float yPercentage, Map map){
		xPercentage = Math.min(1, Math.max(xPercentage, -1));
		yPercentage = Math.min(1, Math.max(yPercentage, -1));
		this.xPercentage = xPercentage;
		this.yPercentage = yPercentage;
		speed = (int) (Math.sqrt(xPercentage*xPercentage + yPercentage*yPercentage)*10);
		int new_xPos = get_new_xPos(xPercentage);
		int new_yPos = get_new_yPos(yPercentage);
		checkCollision(new_xPos, new_yPos, map);


//		if (true) {
//			return;
//		}
//        ArrayList<Entity> hittingEntities = new ArrayList<>();
//		int[] newVertex = new int[2];
//		double angle = 9;
//		for(int i = 0; i < Math.PI/2; i+= Math.PI/6) {
//            new_xPos = get_new_xPos(angle + i);
//            new_yPos = get_new_yPos(angle + i);
//            hittingEntities = getObjectsWithinRadius(new_xPos, new_yPos);
//            for (Entity entity: hittingEntities) {
//                if (entity != null) {
//					newVertex[0] = entity.getXpos();
//					newVertex[1] = entity.getYpos();
//                    if (!(new CollisionDetectorWithTank(getVertices(new_xPos, new_yPos, getJoyAngle() + i), newVertex).isCollision())) {
//                    	angle = angle + i;
//						map.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*Math.cos(angle)), (int) (getYpos() + speed*Math.sin(angle)));
//						setPosition((int)(getXpos() + speed*Math.cos(angle)), (int)(getYpos() + speed*Math.sin(angle)));
//					}
//                }
//            }
//			new_xPos = get_new_xPos(angle - i);
//			new_yPos = get_new_yPos(angle - i);
//			hittingEntities = getObjectsWithinRadius(new_xPos, new_yPos);
//			for (Entity entity: hittingEntities) {
//				if (entity != null) {
//					newVertex[0] = entity.getXpos();
//					newVertex[1] = entity.getYpos();
//					if (!(new CollisionDetectorWithTank(getVertices(new_xPos, new_yPos, getJoyAngle() - i), newVertex).isCollision())) {
//						angle = angle - i;
//						map.moveEntity(getXpos(), getYpos(), (int)(getXpos() + speed*Math.cos(angle)), (int) (getYpos() + speed*Math.sin(angle)));
//						setPosition((int)(getXpos() + speed*Math.cos(angle)), (int)(getYpos() + speed*Math.sin(angle)));
//					}
//				}
//			}
//        }
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

	public void destoryTank(Map map) {
		map.setEntity(getXpos(), getYpos(), null);
		Log.e("AM", "ded");
		isDead = true;
	}

	public boolean isDead() {
		return isDead;
	}

	private ArrayList<Entity> getObjectsWithinRadius(int new_xPos, int new_yPos) {
		//TODO check all objects within a 1.3*width radius of the new pos of the tank
		return new ArrayList<>();
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

	public int [][]  getVertices(int newXPos, int newYPos){

		int [][] vertices = {{(int) (newXPos + width),(int) newYPos},
				{ (int) (newXPos + 0.3 * width), (int) (newYPos - 0.5 * width)},
				{(int) (newXPos - 0.3 * width), (int) (newYPos + 0.5 * width)}};
		return vertices;
	}
}
