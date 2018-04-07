package com.carlisle.ben.tanks;

public class CollisionDetectorWithTank {

    private int[] entityVertices = new int[2];
    private int[][] tankVertices = new int[3][2];
    private double aDet;
    private double bDet;

    public CollisionDetectorWithTank(int[][] tankVertices, int[] entityVertices) {
        this.tankVertices = tankVertices;
        this.entityVertices = entityVertices;
    }

    public boolean isCollision() {

        setCalculatedDeterminants();

        if ((aDet > 0 && aDet < 1) && (bDet > 0 && bDet < 1)) {
            return true;
        }

        return false;
    }

    public void setCalculatedDeterminants() {

        int[] coordinates = {entityVertices[0], entityVertices[1]};

        aDet = findDeterminant(coordinates, tankVertices[2]) - findDeterminant(tankVertices[0], tankVertices[2]);
        aDet /= findDeterminant(tankVertices[1], tankVertices[2]);

        bDet = findDeterminant(coordinates, tankVertices[1]) - findDeterminant(tankVertices[0], tankVertices[1]);
        bDet /= findDeterminant(tankVertices[1], tankVertices[2]);
    }

    public double findDeterminant(int[] first, int[] second) {
        return (first[0] * second[1]) - (first[1] * second[0]);
    }
}