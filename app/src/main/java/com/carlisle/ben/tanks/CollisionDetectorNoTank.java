package com.carlisle.ben.tanks;

public class CollisionDetectorNoTank {

    private Bullet bullet;
    private Entity otherObj;

    public CollisionDetectorNoTank(Bullet bullet, Entity otherObj) {
        this.bullet = bullet;
        this.otherObj = otherObj;
    }

    public boolean isCollisionWithWall() {

        if (otherObj instanceof Wall) {

            // ricochet

            return true;
        }

        else if (otherObj instanceof Tank) {
            bullet.changeExist();
            return true;
        }

        else {
            return false;
        }
    }
}
