package org.experiment;

import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import org.experiment.hull.ConvexHull;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class Movement {
    Pane shapePane;
    Scene scene;
    Bounds sceneBounds;
    Bounds screenBounds;

    double[] downRight_corner;
    double[] upRight_corner;
    double[] downLeft_corner;
    double[] upLeft_corner;

    double angle_deg;
    double angle_rad;

    double shapePosX;
    double shapePosY;
    double[] shapeCenter;

    ConvexHull shape;

    Vector movementVector;

    Vector u;
    Vector w;

    final double[] leftBorderNormal   = new double[]{1, 0};
    final double[] botBorderNormal    = new double[]{0, 1};
    final double[] rightBorderNormal  = new double[]{-1,0};
    final double[] topBorderNormal    = new double[]{0,-1};

    double sceneHeight;
    double sceneWidth;

    double ray;
    int lastBorder;

    double magnitude;
    //TODO
    //  BUG: when changing the magnitude, the angle is still the same as the initial one
    Movement(@NotNull Scene scene, @NotNull Pane shapePane, @NotNull ConvexHull shape, double magnitude){
        this.magnitude = magnitude;
        angle_deg = new Random().nextInt(360 + 1);
        angle_rad = Math.toRadians(angle_deg + 90);

        movementVector = new Vector();
        movementVector.setByAngle(angle_rad, magnitude);

        this.shape = shape;
        shapeCenter = new double[]{
                shape.getDisplaceX(),
                shape.getDisplaceY()
        };
        this.shapePane = shapePane;
        this.scene = scene;
        screenBounds = scene.getRoot().localToScreen(scene.getRoot().getLayoutBounds());
        sceneBounds = shapePane.localToScene(shapePane.getBoundsInLocal());

        downRight_corner = new double[]{screenBounds.getMaxX(), screenBounds.getMaxY()};
        upRight_corner = new double[] {screenBounds.getMaxX(), sceneBounds.getMinY() };
        upLeft_corner = new double[]  {screenBounds.getMinX(), screenBounds.getMinY()};
        downLeft_corner = new double[]{screenBounds.getMinX(), sceneBounds.getMaxY() };

        shapePosX = screenBounds.getMinX() + sceneBounds.getMinX();
        shapePosY = screenBounds.getMinY() + sceneBounds.getMinY();

        u = new Vector();
        w = new Vector();
    }

    public void bounce(){
        updateSceneBounds();
        updateScreenBounds();
        updateShapeRay();
        updateShapeCenter();
        processMovementVector(hitABorderTrigger());
        shape.setDisplacement(
                shape.getDisplaceX() + movementVector.getX(),
                shape.getDisplaceY() + movementVector.getY());
    }
    private int hitABorderTrigger(){
        if  (shapeCenter[0] + ray <= sceneWidth
                && shapeCenter[0] - ray >= 0
                && shapeCenter [1] + ray <= sceneHeight
                && shapeCenter [1] - ray >= 0
        )   return 0;
        else if  (shapeCenter[0]+ray >= sceneWidth       //right border
                && shapeCenter[1]+ray >= 0) {
            return 1;
        }
        else if  (shapeCenter[0]-ray <= 0           //left border
                && shapeCenter[1]+ray >= 0)
            return 3;
        else if  (shapeCenter[1]-ray <= 0          //top border
                && shapeCenter[0]-ray >=0)
            return 2;
        else                                        //bot border
            return 4;
    }
    private void processRectangularComponents(int val){
        double[] borderNormal = null;
        if(val>4 || val<1){
            return;
        }
        switch (val) {
            case 1: borderNormal = rightBorderNormal; break;
            case 2: borderNormal = topBorderNormal; break;
            case 3: borderNormal = leftBorderNormal; break;
            case 4: borderNormal = botBorderNormal; break;
        }
        try {
            assert borderNormal != null;
            u.setY((movementVector.getY()) * borderNormal[1] * borderNormal[1]);
            u.setX((movementVector.getX() * borderNormal[0]) * borderNormal[0]);
        } catch (NullPointerException e){
            System.err.println("borderNormal in App.Movement.getU()," +
                    "error in array initialization");
        }
        //  This updates the U Vector
        w.setX((movementVector.getX() - u.getX()));
        w.setY((movementVector.getY() - u.getY()));
        //  This updates the W vector
    }
    private void processMovementVector(int val){
        //TODO add a sprinkle of randomness to avoid the shape looping
        if(lastBorder != val)
        {
            if (App.randomness) {
                if(Math.random() >= 0.55)
                    App.changeRotationVerse();
                if(Math.random() > 0.5)
                    App.changeRotationSpeed();
            }


            lastBorder = val;
            processRectangularComponents(val);
            movementVector.setXY(w.getX() - u.getX(), w.getY()-u.getY());


        }
    }
    void updateScreenBounds(){
        screenBounds = scene.getRoot().localToScreen(scene.getRoot().getLayoutBounds());
        sceneHeight = screenBounds.getMaxY() - screenBounds.getMinY();
        sceneWidth = screenBounds.getMaxX() - screenBounds.getMinX();
    }
    private void updateShapeCenter(){
        shapeCenter = new double[]{shape.getDisplaceX(), shape.getDisplaceY()};
    }
    void updateSceneBounds(){
        sceneBounds = shapePane.localToScene(shapePane.getBoundsInLocal());
    }
    private void updateShapeRay(){
        ray = sceneBounds.getMaxX() - shapeCenter[0];
    }

    public double[] getMoveVectorValues(){
        return new double[]{movementVector.getX(), movementVector.getY()};
    }
    public void updateMagnitude(double newMagnitude, boolean increase){
        this.magnitude = newMagnitude;
        movementVector.increaseMagnitude(newMagnitude, increase);
    }
}
