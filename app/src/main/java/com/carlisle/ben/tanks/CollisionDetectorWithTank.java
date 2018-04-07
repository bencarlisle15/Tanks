package com.carlisle.ben.tanks;

public class CollisionDetectorWithTank {

    private double[] tankPos;
    private double[] otherPos;
    private double[][] vertices = new double[3][2];
    private double aDet;
    private double bDet;
    private double cDet;

    public CollisionDetectorWithTank(double[] tankPos, double[] otherPos) {
        this.tankPos = tankPos;
        this.otherPos = otherPos;
    }

    public boolean isCollision() {

        double[] topVertex = {tankPos[0] + 1.0, tankPos[1]};
        double[] botRightVertex = {tankPos[0] + 0.3, tankPos[1] - 0.5};
        double[] botLeftVertex = {tankPos[0] - 0.3, tankPos[1] + 0.5};

        vertices[0] = topVertex;
        vertices[1] = botRightVertex;
        vertices[2] = botLeftVertex;

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

    public double findDeterminant(double[] first, double[] second) {
        return (first[0] * second[1]) - (first[1] * second[0]);
    }
}
