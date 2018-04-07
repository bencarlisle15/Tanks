package com.carlisle.ben.tanks;

public class Tank extends Entity {

    private boolean canFire;
    private int speed;
    private int lives;
    private static double timer = 0.1;
    private int width;
    private final static double LEFTVALUEDEC  = 0.3;
    private final static double RIGHTVALUEINC = 0.5;
    private final static double UPVALUEINC = 1.0;
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

    // returns the CenterPosition
    public int[] nextCenterPosition(){

        int tankPos[] = {super.getXpos(), super.getYpos()};
        double angle = getJoyAngle();
        int new_xPos = get_new_xPos(angle);
        int new_yPos = get_new_yPos(angle);
        int newPos[] = {new_xPos, new_yPos};
        CollisionDetectorWithTank detector = new CollisionDetectorWithTank(getVertices(), newPos,angle);

        if(!detector.isCollision()){
            return newPos;
        }

        return tankPos;
    }

    private double getTopTriangleXposition(){
        return getXpos() + UPVALUEINC * width;
    }

    private double getTopYTriangleposition(){
        return getYpos();
    }

    private double getBotRightXTrianglePosition(){
        return getXpos() + LEFTVALUEDEC * width;
    }

    private double getBotRightYTrianglePosition(){
        return getYpos() - RIGHTVALUEINC * width;
    }

    private double getBotLeftXTrianglePosition(){
        return getXpos() - LEFTVALUEDEC * width;
    }

    private double getBotLeftYTrianglePosition(){
        return getYpos() + RIGHTVALUEINC * width;
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

    private int get_new_xPos(double angle){
        return (int) (getXpos() + speed*Math.cos(angle) + 1/2 * (Math.pow(xPercentage,2)));
    }

    private int get_new_yPos(double angle){
        return (int)(getYpos() + speed*Math.sin(angle) + 1/2 * (Math.pow(yPercentage,2)));
    }

    public Bullet fire(){
        return new Bullet((int)(BULLETCONST * Math.cos (getJoyAngle())),(int)(BULLETCONST * Math.cos (getJoyAngle())), getJoyAngle());
    }

    public void move(float xPercentage, float yPercentage, Map map){

        this.xPercentage = xPercentage;
        this.yPercentage = yPercentage;

        int new_xPos;
        int new_yPos;


        for(int i = 0; i < 90; i+= 10) {


            new_xPos = get_new_xPos(i);
            new_yPos = get_new_yPos(i);

            if (!((map.getEntity(new_xPos, new_yPos)) instanceof Wall) && !(map.getEntity(new_xPos, new_yPos) instanceof Tank)) {
                super.setPosition(new_xPos, new_yPos);
            }


        }
    }



    public int [][]  getVertices(){

        int [][] vertices = {{(int)getTopTriangleXposition(),(int)getTopYTriangleposition()},
                { (int)getBotRightXTrianglePosition(), (int)getBotRightYTrianglePosition()},
                {(int)getBotLeftXTrianglePosition(), (int)getBotLeftYTrianglePosition()}};
        return vertices;
    }

}
