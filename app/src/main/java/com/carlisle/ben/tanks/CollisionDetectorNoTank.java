package com.carlisle.ben.tanks;

public class CollisionDetectorNoTank {

    private int[] firstObj;
    private int[] secondObj;

    public CollisionDetectorNoTank (int firstXpos, int firstYpos, int secondXpos, int secondYpos) {

        firstObj = new int[2];
        secondObj = new int[2];

        firstObj[0] = firstXpos;
        firstObj[1] = firstYpos;

        secondObj[0] = secondXpos;
        secondObj[1] = secondYpos;
    }

    public boolean isCollision() {

        if (firstObj[0] == secondObj[0] || firstObj[1] == secondObj[1]) {
            return true;
        }

        return false;
    }
}
