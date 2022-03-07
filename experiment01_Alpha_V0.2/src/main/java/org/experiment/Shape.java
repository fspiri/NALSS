package org.experiment;
import javafx.geometry.Point3D;
import javafx.stage.Stage;
import org.experiment.App;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class Shape {
    double poly_length = 1;
    double[][] coords;
    double D_PLACE;
    double GROWTH;
    public static double angle;
    int vert_num;
    int shape_num;
    double[][] vertices;
    double[][] proj_matrix = {{1, 0, 0}, {0, 1, 0}, {0, 0, 0}};
        //the idea behind the shape_num var is that this class can retrieve
        // different shapes based on the shape_num
        //shape_num chart:
        //      1 - pyramid
        //      2 - cube
        Shape(double angle, int shape_num, int growth, double d_place) {
            Shape.angle = angle;
            this.shape_num = shape_num;
            this.GROWTH = growth;
            this.D_PLACE = d_place;
            if(shape_num == 1) vert_num = 8;
            else if(shape_num == 2) vert_num = 8;
            coords = new double[vert_num][2];

            if(shape_num == 1) vertices = pyramid_vertices;
            else if(shape_num ==2) vertices = cube_vertices;
            else throw new IllegalArgumentException();
        }



        //cube
        double[][] cube_vertices = {
                { -poly_length, -poly_length, poly_length },
                { poly_length, -poly_length, poly_length },
                { poly_length, poly_length, poly_length },
                { -poly_length, poly_length, poly_length },
                { -poly_length, -poly_length, -poly_length },
                { poly_length, -poly_length, -poly_length },
                { poly_length, poly_length, -poly_length },
                { -poly_length, poly_length, -poly_length }
        };

        Point3D[] cube_vert = {
                new Point3D(-poly_length, -poly_length, poly_length),
                new Point3D(poly_length, -poly_length, poly_length),
                new Point3D(poly_length, poly_length, poly_length),
                new Point3D(-poly_length, poly_length, poly_length),
                new Point3D(-poly_length, -poly_length, -poly_length),
                new Point3D(poly_length, -poly_length, -poly_length),
                new Point3D(poly_length, poly_length, -poly_length),
                new Point3D(-poly_length, poly_length, -poly_length)
        };

        //pyramid
        double[][] pyramid_vertices = {
                { -poly_length, 0, poly_length },
                { poly_length, 0, poly_length },
                { -poly_length, 0, -poly_length },
                { poly_length, 0, -poly_length },
                { 0, -poly_length, 0 }
        };


        double[][] projectedCube(){
            double[][] rotated2d = matrixMult(vertices, rotation_z);
            rotated2d = matrixMult(rotated2d, rotation_x);
            rotated2d = matrixMult(rotated2d, rotation_y);
            double[][] projection = matrixMult(rotated2d, proj_matrix);

            setCoords(projection);
            return projection;
        }
        public double[][] getCoords(){
            return coords;
        }
        void setCoords(double[][] A){
            this.coords = A;
        }

        List<Line> getEdges(){
            List<Line> edges = new ArrayList();
            //pyramid segments generator
            //all combinations are 01 02 04 13 14 23 24 34
            if(shape_num == 1) {
                for (int i = 0; i < 4; i++) {
                    for (int a = 1; a <= 4; a++) {
                        if (i + a != 3) {
                            if (a > i) {
                                edges.add(new Line(coords[i][0] * GROWTH + D_PLACE,
                                        coords[i][1] * GROWTH + D_PLACE,
                                        coords[a][0] * GROWTH + D_PLACE,
                                        coords[a][1] * GROWTH + D_PLACE));
                            }
                        }
                    }
                }
                return edges;
            }
            else if(shape_num == 2){
                //combinations 01 03 04 12 15 23 26 37 45 47 56 67
                edges.add(new Line(coords[0][0] * GROWTH + D_PLACE , coords[0][1] * GROWTH + D_PLACE, coords[1][0] * GROWTH + D_PLACE, coords[1][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[0][0] * GROWTH + D_PLACE , coords[0][1] * GROWTH + D_PLACE, coords[3][0] * GROWTH + D_PLACE, coords[3][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[0][0] * GROWTH + D_PLACE , coords[0][1] * GROWTH + D_PLACE, coords[4][0] * GROWTH + D_PLACE, coords[4][1] * GROWTH + D_PLACE));

                edges.add(new Line(coords[1][0] * GROWTH + D_PLACE , coords[1][1] * GROWTH + D_PLACE, coords[2][0] * GROWTH + D_PLACE, coords[2][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[1][0] * GROWTH + D_PLACE , coords[1][1] * GROWTH + D_PLACE, coords[5][0] * GROWTH + D_PLACE, coords[5][1] * GROWTH + D_PLACE));

                edges.add(new Line(coords[2][0] * GROWTH + D_PLACE , coords[2][1] * GROWTH + D_PLACE, coords[3][0] * GROWTH + D_PLACE, coords[3][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[2][0] * GROWTH + D_PLACE , coords[2][1] * GROWTH + D_PLACE, coords[6][0] * GROWTH + D_PLACE, coords[6][1] * GROWTH + D_PLACE));

                edges.add(new Line(coords[3][0] * GROWTH + D_PLACE , coords[3][1] * GROWTH + D_PLACE, coords[7][0] * GROWTH + D_PLACE, coords[7][1] * GROWTH + D_PLACE));

                edges.add(new Line(coords[4][0] * GROWTH + D_PLACE , coords[4][1] * GROWTH + D_PLACE, coords[5][0] * GROWTH + D_PLACE, coords[5][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[4][0] * GROWTH + D_PLACE , coords[4][1] * GROWTH + D_PLACE, coords[7][0] * GROWTH + D_PLACE, coords[7][1] * GROWTH + D_PLACE));

                edges.add(new Line(coords[5][0] * GROWTH + D_PLACE , coords[5][1] * GROWTH + D_PLACE, coords[6][0] * GROWTH + D_PLACE, coords[6][1] * GROWTH + D_PLACE));
                edges.add(new Line(coords[6][0] * GROWTH + D_PLACE , coords[6][1] * GROWTH + D_PLACE, coords[7][0] * GROWTH + D_PLACE, coords[7][1] * GROWTH + D_PLACE));

                return edges;
            }
            else throw new IllegalArgumentException();
        }

        Point3D[] getVert(){
            return cube_vert;
        }

        Circle[] getVertex(){
            Circle[] v_point = new Circle[vert_num];
            double[][] vertices = projectedCube();
            setCoords(vertices);

            for(int i = 0; i<vertices.length; i++){
                double x = (vertices[i][0] * GROWTH) + D_PLACE;
                double y = (vertices[i][1] * GROWTH) + D_PLACE;
                v_point[i] = new Circle( x, y, 3, Color.WHITE);
            }
            return v_point;
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

        double[][] rotation_x = {
                {1, 0, 0},
                {0, Math.cos(angle), -Math.sin(angle)},
                {0, Math.sin(angle), Math.cos(angle)},
        };
        double[][] rotation_y = {
                {Math.cos(angle), 0, Math.sin(angle)},
                {0, 1, 0},
                {-Math.sin(angle), 0, Math.cos(angle)},
        };
        double[][] rotation_z = {
                {Math.cos(angle), -Math.sin(angle),0},
                {Math.sin(angle), Math.cos(angle), 0},
                {0, 0, 1},
        };

}
