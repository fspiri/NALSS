package org.experiment.hull;

import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import org.experiment.Logic;

import java.util.ArrayList;
import java.util.List;

import static org.experiment.ui.Color.getColor;
import static org.experiment.ui.Color.updateColor;

public class Dodecahedron implements ConvexHull{
    double angle = 0;
    double stroke_width = 3;
    double growth = 100;
    double displace = 350;
    double displaceX = 350;
    double displaceY = 350;
    double poly_length = 1;
    public  double[][] proj_vertices = null;
    List<Line> edges = new ArrayList<>();

    int projectionA = 0;
    int projectionB = -1;
    int projectionC = -1;

    double[][] vertices = {
            { poly_length, poly_length, poly_length}, //+ + +
            { poly_length, poly_length, -poly_length }, // + + -
            { poly_length, -poly_length, poly_length }, // + - +
            { poly_length, -poly_length, -poly_length }, // + - -
            { -poly_length, poly_length, poly_length }, //- + +
            { -poly_length, poly_length, -poly_length}, // - + -
            { -poly_length, -poly_length, poly_length}, // - - +
            { -poly_length, -poly_length, -poly_length}, // - - -

            { 0, 1.618*poly_length, poly_length/(1.618*poly_length)},
            { 0, 1.618*poly_length, -poly_length/(1.618*poly_length)},
            { 0, -1.618*poly_length, poly_length/(1.618*poly_length)},
            { 0, -1.618*poly_length, -poly_length/(1.618*poly_length)},

            { poly_length/(1.618*poly_length), 0, 1.618*poly_length},
            { poly_length/(1.618*poly_length), 0, -1.618*poly_length},
            { -poly_length/(1.618*poly_length), 0, 1.618*poly_length},
            { -poly_length/(1.618*poly_length), 0, -1.618*poly_length},


            { 1.618*poly_length, poly_length/(1.618*poly_length), 0},
            { 1.618*poly_length, -poly_length/(1.618*poly_length), 0},
            { -1.618*poly_length, poly_length/(1.618*poly_length), 0},
            { -1.618*poly_length, -poly_length/(1.618*poly_length), 0}

    };
    @Override
    public void updateAll(double deg) {
        setAngle(deg);
        updateVertices();
        updateEdges();
    }

    @Override
    public void updateVertices() {
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

    @Override
    public void updateEdges() {
        if(projectionA == -1 && projectionB == -1 && projectionC == -1)
            return;
        edges.clear();
        edges.add(new Line(proj_vertices[7][0] *  growth + displaceX, proj_vertices[7][1] * growth +  displaceY, proj_vertices[15][0] * growth + displaceX, proj_vertices[15][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[7][0] *  growth + displaceX, proj_vertices[7][1] * growth +  displaceY, proj_vertices[19][0] * growth + displaceX, proj_vertices[19][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[5][0] *  growth + displaceX, proj_vertices[5][1] * growth +  displaceY, proj_vertices[15][0] * growth + displaceX, proj_vertices[15][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[19][0] * growth +displaceX , proj_vertices[19][1] * growth + displaceY, proj_vertices[6][0] * growth +  displaceX, proj_vertices[6][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[18][0] * growth +displaceX , proj_vertices[18][1] * growth + displaceY, proj_vertices[5][0] * growth +  displaceX, proj_vertices[5][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[18][0] * growth +displaceX , proj_vertices[18][1] * growth + displaceY, proj_vertices[19][0] * growth + displaceX, proj_vertices[19][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[6][0] *  growth + displaceX, proj_vertices[6][1] * growth +  displaceY, proj_vertices[19][0] * growth + displaceX, proj_vertices[19][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[4][0] *  growth + displaceX, proj_vertices[4][1] * growth +  displaceY, proj_vertices[18][0] * growth + displaceX, proj_vertices[18][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[14][0] * growth +displaceX , proj_vertices[14][1] * growth + displaceY, proj_vertices[6][0] * growth +  displaceX, proj_vertices[6][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[14][0] * growth +displaceX , proj_vertices[14][1] * growth + displaceY, proj_vertices[4][0] * growth +  displaceX, proj_vertices[4][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[11][0] * growth +displaceX , proj_vertices[11][1] * growth + displaceY, proj_vertices[7][0] * growth +  displaceX, proj_vertices[7][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[10][0] * growth +displaceX , proj_vertices[10][1] * growth + displaceY, proj_vertices[6][0] * growth +  displaceX, proj_vertices[6][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[10][0] * growth +displaceX , proj_vertices[10][1] * growth + displaceY, proj_vertices[11][0] * growth + displaceX, proj_vertices[11][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[9][0] *  growth + displaceX, proj_vertices[9][1] * growth +  displaceY, proj_vertices[5][0] * growth +  displaceX, proj_vertices[5][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[8][0] *  growth + displaceX, proj_vertices[8][1] * growth +  displaceY, proj_vertices[4][0] * growth +  displaceX, proj_vertices[4][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[8][0] *  growth + displaceX, proj_vertices[8][1] * growth +  displaceY, proj_vertices[9][0] * growth +  displaceX, proj_vertices[9][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[13][0] * growth +displaceX , proj_vertices[13][1] * growth + displaceY, proj_vertices[15][0] * growth + displaceX, proj_vertices[15][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[3][0] *  growth + displaceX, proj_vertices[3][1] * growth +  displaceY, proj_vertices[13][0] * growth + displaceX, proj_vertices[13][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[3][0] *  growth + displaceX, proj_vertices[3][1] * growth +  displaceY, proj_vertices[11][0] * growth + displaceX, proj_vertices[11][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[0][0] *  growth + displaceX, proj_vertices[0][1] * growth +  displaceY, proj_vertices[8][0] * growth +  displaceX, proj_vertices[8][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[0][0] *  growth + displaceX, proj_vertices[0][1] * growth +  displaceY, proj_vertices[12][0] * growth + displaceX, proj_vertices[12][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[0][0] *  growth + displaceX, proj_vertices[0][1] * growth +  displaceY, proj_vertices[16][0] * growth + displaceX, proj_vertices[16][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[1][0] *  growth + displaceX, proj_vertices[1][1] * growth +  displaceY, proj_vertices[9][0] * growth +  displaceX, proj_vertices[9][1] * growth +  displaceY));
        edges.add(new Line(proj_vertices[1][0] *  growth + displaceX, proj_vertices[1][1] * growth +  displaceY, proj_vertices[13][0] * growth + displaceX, proj_vertices[13][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[1][0] *  growth + displaceX, proj_vertices[1][1] * growth +  displaceY, proj_vertices[16][0] * growth + displaceX, proj_vertices[16][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[2][0] *  growth + displaceX, proj_vertices[2][1] * growth +  displaceY, proj_vertices[10][0] * growth + displaceX, proj_vertices[10][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[2][0] *  growth + displaceX, proj_vertices[2][1] * growth +  displaceY, proj_vertices[12][0] * growth + displaceX, proj_vertices[12][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[2][0] *  growth + displaceX, proj_vertices[2][1] * growth +  displaceY, proj_vertices[17][0] * growth + displaceX, proj_vertices[17][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[12][0] * growth +displaceX , proj_vertices[12][1] * growth + displaceY, proj_vertices[14][0] * growth + displaceX, proj_vertices[14][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[17][0] * growth +displaceX , proj_vertices[17][1] * growth + displaceY, proj_vertices[16][0] * growth + displaceX, proj_vertices[16][1] * growth + displaceY));
        edges.add(new Line(proj_vertices[17][0] * growth +displaceX , proj_vertices[17][1] * growth + displaceY, proj_vertices[3][0] * growth +  displaceX, proj_vertices[3][1] * growth +  displaceY));
    }

    @Override
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

    @Override
    public void setAngle(double deg) {
        this.angle = deg;
    }

    @Override
    public void setGrowth(double growth) {
        this.growth = growth;
    }

    @Override
    public void setDisplacement(double displaceX, double displaceY) {
        this.displaceX = displaceX;
        this.displaceY = displaceY;
    }

    @Override
    public void setRotations(int x, int y, int z) {
        projectionA = x;
        projectionB = y;
        projectionC = z;
    }

    @Override
    public List<Line> getEdges() {
        return edges;
    }

    @Override
    public double[][] getVertices() {
        return proj_vertices;
    }
    public double getDisplaceX(){
        return displaceX;
    }
    public double getDisplaceY(){
        return displaceY;
    }
    public int getShapeValue(){
        return 4;
    }
}
