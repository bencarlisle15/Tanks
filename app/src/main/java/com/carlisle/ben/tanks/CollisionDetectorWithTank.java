package com.carlisle.ben.tanks;

public class CollisionDetectorWithTank {

    private int[] otherPos;
    private int[][] vertices = new int[3][2];
    private double angle;
    private double aDet;
    private double bDet;

    public CollisionDetectorWithTank(int[][] vertices, int[] otherPos, double angle) {
        this.otherPos = otherPos;
        this.angle = angle;
        this.vertices = vertices;
    }

    public boolean isCollision() {

        setCalculatedDeterminants();

        if ((aDet > 0 && aDet < 1) && (bDet > 0 && bDet < 1)) {
            return true;
        }

        return false;
    }

    public void setCalculatedDeterminants() {

        aDet = findDeterminant(otherPos, vertices[2]) - findDeterminant(vertices[0], vertices[2]);
        aDet /= findDeterminant(vertices[1], vertices[2]);

        bDet = findDeterminant(otherPos, vertices[1]) - findDeterminant(vertices[0], vertices[1]);
        bDet /= findDeterminant(vertices[1], vertices[2]);
    }

    public double findDeterminant(int[] first, int[] second) {
        return (first[0] * second[1]) - (first[1] * second[0]);
    }
}