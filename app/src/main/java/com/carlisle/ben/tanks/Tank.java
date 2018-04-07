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
    private Map map;


    public Tank(int xPos, int yPos, int width) {
        super(xPos, yPos);
        canFire = true;
        lives = 3;
        isShootBottomPressed = false;
        this.width = width;
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

    public Bullet fire(){
        return new Bullet((int)(getXpos() + BULLETCONST * xPercentage),(int)(getYpos() + BULLETCONST * yPercentage), xPercentage, yPercentage);
    }

    public void move(float xPercentage, float yPercentage, Map map){
        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;
        speed = (int) (Math.sqrt(xPercentage*xPercentage + yPercentage*yPercentage)*10);
        int new_xPos = get_new_xPos(xPercentage);
        int new_yPos = get_new_yPos(yPercentage);
		map.moveEntity(getXpos(), getYpos(), new_xPos, new_yPos);
		setPosition(new_xPos, new_yPos);
		Log.e("beg", "beg");
		Log.e(String.valueOf(getXpos()), String.valueOf(getYpos()));
		Log.e(String.valueOf(new_xPos), String.valueOf(new_yPos));
		Log.e("fin", "fin");


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

	private ArrayList<Entity> getObjectsWithinRadius(int new_xPos, int new_yPos) {
    	//TODO check all objects within a 1.3*width radius of the new pos of the tank
		return new ArrayList<>();
	}


	public int [][]  getVertices(int newXPos, int newYPos){

        int [][] vertices = {{(int) (newXPos + width),(int) newYPos},
                { (int) (newXPos + 0.3 * width), (int) (newYPos - 0.5 * width)},
                {(int) (newXPos - 0.3 * width), (int) (newYPos + 0.5 * width)}};
        return vertices;
    }
}
