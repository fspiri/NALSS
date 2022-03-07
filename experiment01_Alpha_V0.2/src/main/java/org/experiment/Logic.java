package org.experiment;

public class Logic {
    public static double  angle = 0;
    public static double[][] proj = {{1, 0, 0}, {0, 1, 0}, {0, 0, 0}};




    public static double[][] vertices2D(double[][] A, double deg, int projectionA){
        angle = deg;
        double[][] rotA = new Logic().rot_mat(projectionA);
        double[][] rotated2d = matrixMult(A, rotA);
        return matrixMult(rotated2d, proj);
    }

    public static double[][] vertices2D(double[][] A, double deg, int projectionA, int projectionB){
        Logic logic = new Logic();
        angle = deg;
        double[][] rotA = logic.rot_mat(projectionA);
        double[][] rotB = logic.rot_mat(projectionB);
        return matrixMult(matrixMult(matrixMult(A, rotA), rotB), proj);
    }

    public static double[][] vertices2D(double[][] A, double deg, int projectionA, int projectionB, int projectionC) {
        Logic logic = new Logic();
        angle = deg;
        double[][] rotA = logic.rot_mat(projectionA);
        double[][] rotB = logic.rot_mat(projectionB);
        double[][] rotC = logic.rot_mat(projectionC);
        return matrixMult(matrixMult(matrixMult(matrixMult(A, rotA), rotB), rotC), proj);
    }

    public double[][] rot_mat(int value){
        switch (value){
            case 0: return rotationX;
            case 1: return rotationY;
            case 2: return rotationZ;
        }
        throw new RuntimeException();
    }

    public static double[][] matrixMult(double[][] A, double[][] B) {

        int aRows = A.length;
        int aColumns = A[0].length;
        int bRows = B.length;
        int bColumns = B[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] C = new double[aRows][bColumns];
        for (int i = 0; i < aRows; i++) {
            for (int j = 0; j < bColumns; j++) {
                C[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }
    double[][] rotationX = {
            {1, 0, 0},
            {0, Math.cos(angle), -Math.sin(angle)},
            {0, Math.sin(angle), Math.cos(angle)},
    };
    double[][] rotationY = {
            {Math.cos(angle), 0, Math.sin(angle)},
            {0, 1, 0},
            {-Math.sin(angle), 0, Math.cos(angle)},
    };
    double[][] rotationZ = {
            {Math.cos(angle), -Math.sin(angle),0},
            {Math.sin(angle), Math.cos(angle), 0},
            {0, 0, 1},
    };



}
