package com.carlisle.ben.tanks;

public class Tank extends Entity {

    private boolean canFire;
    private int xSpeed, ySpeed;
    private int xPos, yPos;
    private int lives;
    private static double timer = 0.1;
    private int height;
    private static double LEFTVALUEDEC  = 0.3;
    private static double RIGHTVALUEINC = 0.5;
    private static double UPVALUEINC = 1.0;
    private boolean isShootBottomPressed;
    private double acceleration;
    private double xPercentage, yPercentage;


    public Tank(int xPos, int yPos, int width) {
        super(xPos, yPos);
        canFire = true;
        lives = 3;
        isShootBottomPressed = false;

    }


    // returns the CenterPosition
    public double[] nextCenterPosition(){

        double tankPos[] = {xPos, yPos};
        double angle = getJoyAngle();
        double new_xPos = (xSpeed * 1 + acceleration);
        double new_yPos = (ySpeed * 1 + acceleration);
        double newPos[] = {new_xPos, new_yPos};
        CollisionDetectorWithTank detector = new CollisionDetectorWithTank(tankPos, newPos);

        if(!detector.isCollision()){
            return newPos;
        }

        return tankPos;
    }


    public double getTopTriangleXposition(){
        return xPos - LEFTVALUEDEC*height;
    }

    public double getTopYTriangleposition(){
        return yPos + RIGHTVALUEINC*height;
    }

    public double getRightXTrianglePosition(){
        return xPos + UPVALUEINC*height;
    }
    public double getRightYTrianglePosition(){
        return yPos;
    }

    public double getBottomXTrianglePosition(){
        return xPos - LEFTVALUEDEC*height;
    }

    public double getBottomYTrianglePosition(){
        return yPos - RIGHTVALUEINC*height;
    }

    private boolean validAngle(int checkAngle) {

        if(checkAngle < 0 || checkAngle > 360){
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

    private double getJoyAngle(){

        return Math.atan(yPercentage/xPercentage);
    }

    public void fire(){

    }

    public void move(float xPercentage, float yPercentage){

    }

}
