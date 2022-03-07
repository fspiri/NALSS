package org.experiment.hull;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.experiment.Logic;

import java.util.ArrayList;
import java.util.List;

import static org.experiment.ui.Color.getColor;
import static org.experiment.ui.Color.updateColor;

public class Cube implements ConvexHull {
    double angle = 0;
    double stroke_width = 3;
    double growth = 100;
    double displace = 350;
    double displaceX = 350;
    double displaceY  = 350;
    double poly_length = 1;
    public  double[][] proj_vertices = null;
    List<Line> edges = new ArrayList<>();

    int projectionA = 0;
    int projectionB = -1;
    int projectionC = -1;

    //vertices of the cube
    double[][] vertices = {
            { -poly_length, -poly_length, poly_length },
            { poly_length, -poly_length, poly_length },
            { poly_length, poly_length, poly_length },
            { -poly_length, poly_length, poly_length },
            { -poly_length, -poly_length, -poly_length },
            { poly_length, -poly_length, -poly_length },
            { poly_length, poly_length, -poly_length },
            { -poly_length, poly_length, -poly_length }
    };
    //method that calls all the updates needed to give the updated rotate cube
    public void updateAll(double deg){
        setAngle(deg);
        updateVertices();
        updateEdges();
    }
    // Updates the proj_vertices array containing the coordinates of the rotated cube projected in 2D
    @Override
    public void updateVertices() {
        /*
        to count the number of active rotation the app signs with -1 the ones not active.
        C cannot be != -1 if B = -1 ,we call them active:
        saying that A is active means that is different from -1

        this setup allows for complete modularity,
        how many rotations and in which directions you want
         */
        if(projectionA == -1 && projectionB == -1 && projectionC == -1)
            return;
        if(projectionB!=-1) {
            if (projectionC != -1)
                proj_vertices = Logic.vertices2D(vertices, angle, projectionA, projectionB, projectionC);
            else
                proj_vertices = Logic.vertices2D(vertices, angle, projectionA, projectionB);
        }
        else
            proj_vertices = Logic.vertices2D(vertices, angle, projectionA);
    }

    public void updateEdges(){
        if(projectionA == -1 && projectionB == -1 && projectionC == -1)
            return;
        edges.clear();
        //combinations          01 03 04 12 15 23 26 37 45 47 56 67
        //with zeroed coords    01 03 04 01 04 01 04 04 01 03 01 01
        for (int i = 0; i<=12; i++){

        }

        edges.add(new Line(proj_vertices[0][0] * growth + displaceX , proj_vertices[0][1] * growth + displaceY, proj_vertices[1][0] * growth + displaceX, proj_vertices[1][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[0][0] * growth + displaceX , proj_vertices[0][1] * growth + displaceY, proj_vertices[3][0] * growth + displaceX, proj_vertices[3][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[0][0] * growth + displaceX , proj_vertices[0][1] * growth + displaceY, proj_vertices[4][0] * growth + displaceX, proj_vertices[4][1] * growth + displaceY));

        edges.add(new Line(proj_vertices[1][0] * growth + displaceX , proj_vertices[1][1] * growth + displaceY, proj_vertices[2][0] * growth + displaceX, proj_vertices[2][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[1][0] * growth + displaceX , proj_vertices[1][1] * growth + displaceY, proj_vertices[5][0] * growth + displaceX, proj_vertices[5][1] * growth + displaceY));

        edges.add(new Line(proj_vertices[2][0] * growth + displaceX , proj_vertices[2][1] * growth + displaceY, proj_vertices[3][0] * growth + displaceX, proj_vertices[3][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[2][0] * growth + displaceX , proj_vertices[2][1] * growth + displaceY, proj_vertices[6][0] * growth + displaceX, proj_vertices[6][1] * growth + displaceY));

        edges.add(new Line(proj_vertices[3][0] * growth + displaceX , proj_vertices[3][1] * growth + displaceY, proj_vertices[7][0] * growth + displaceX, proj_vertices[7][1] * growth + displaceY));

        edges.add(new Line(proj_vertices[4][0] * growth + displaceX , proj_vertices[4][1] * growth + displaceY, proj_vertices[5][0] * growth + displaceX, proj_vertices[5][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[4][0] * growth + displaceX , proj_vertices[4][1] * growth + displaceY, proj_vertices[7][0] * growth + displaceX, proj_vertices[7][1] * growth + displaceY));

        edges.add(new Line(proj_vertices[5][0] * growth + displaceX , proj_vertices[5][1] * growth + displaceY, proj_vertices[6][0] * growth + displaceX, proj_vertices[6][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[6][0] * growth + displaceX , proj_vertices[6][1] * growth + displaceY, proj_vertices[7][0] * growth + displaceX, proj_vertices[7][1] * growth + displaceY));
    }

    //adds the style selected to the edges
    //TODO add a way to change these values globally by an input that can be from the GUI
    public void stylizeEdges(boolean colorFlag, boolean darkMode){
        if(colorFlag==false){
            if(darkMode) {
                for (Line elle : edges) {
                    elle.setStroke(Color.WHITE);
                    elle.setStrokeWidth(stroke_width);
                }
            }
                else {
                for (Line elle : edges) {
                    elle.setStroke(Color.BLACK);
                    elle.setStrokeWidth(stroke_width);
                }
            }
        }
        else {
            updateColor();
            int[] color = getColor();
            for (Line elle : edges) {
                elle.setStroke(Color.rgb(color[0], color[1], color[2]));
                elle.setStrokeWidth(stroke_width);
            }
        }
    }

    public List<Line> getEdges(){
        return edges;
    }

    public double[][] getVertices(){ return proj_vertices; }

    //sets the angle based on the one given by the callling method (GUI)
    public void setAngle(double deg){
        this.angle = deg;
    }

    public void printVertices(){
        for(double[] a : proj_vertices){
            for(double b : a)
                System.out.print(b);
            System.out.println();
        }
    }
    public void setGrowth(double growth){
        this.growth = growth;
    }
    public void setDisplacement(double displaceX, double displaceY){
        this.displaceX = displaceX;
        this.displaceY = displaceY;
    }
    public void setRotations(int x, int y, int z){
        projectionA = x;
        projectionB = y;
        projectionC = z;
    }
    public double getDisplaceX(){
        return displaceX;
    }
    public double getDisplaceY(){
        return displaceY;
    }
    public int getShapeValue(){
        return 0;
    }
}
