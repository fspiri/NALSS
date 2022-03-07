package org.experiment.hull;

import javafx.scene.shape.Line;

import java.util.List;

public interface ConvexHull {

    //updater
    void updateAll(double deg);
    void updateVertices();
    void updateEdges();
    void stylizeEdges(boolean colorFlag, boolean darkMode);

    //setter
    void setAngle(double deg);
    void setGrowth(double growth);
    void setDisplacement(double displaceX, double displaceY);
    void setRotations(int x, int y, int z);

    //getter
    List<Line> getEdges();
    double[][] getVertices();
    double getDisplaceX();
    double getDisplaceY();
    int getShapeValue();
}
