package org.experiment.ui;

import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import org.experiment.App;
import org.experiment.hull.*;
import org.experiment.ui.transitions.TransitionOne;

import java.util.concurrent.atomic.AtomicBoolean;

public class TopSettings {
    static int activeShape = 0;
    double polygonLength = 75;
    private boolean darkModeFlag = true;
    static Polygon chosenShape = null;
    static Pane shapeChooseBox = new GridPane();
    static HBox shapeBox;
    final double BLUR_AMOUNT = 3;
    final Effect frostEffect =
            new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 1);

    public TopSettings(boolean darkMode){
        setTopSettings();
    }

    private void setTopSettings(){
        double width = 900;
        double height = 212;


        //shapeChooseBox.setTranslateX(-850);
        shapeChooseBox.setId("choiceBox");
        shapeChooseBox.setPrefWidth(width);
        shapeChooseBox.setPrefHeight(height);

        shapeBox = new HBox(); //expandable rectangle containing all the possible shapes
        Polygon[] shapes = getShapes();
        for (int i = 0; i<shapes.length; i++){
            int finalI = i;
            shapes[i].setOnMouseClicked(e -> updateShape(finalI));
            shapes[i].setOnMouseEntered(e -> handleShapeMouseHover(shapes[finalI]));
            shapes[i].setOnMouseExited(e -> handleShapeMouseExit(shapes[finalI]));
            shapes[i].setId("inner-shape");
            shapes[i].setTranslateX(27.5);
            shapes[i].setTranslateY(27.5);
            shapeBox.getChildren().add(shapes[i]);
        }

        shapeBox.setSpacing(20);
        shapeBox.setTranslateX(20);
        chosenShape = shapes[0];     //first selected shape is the cube

        Pane background = new Pane();
        background.setId("top-settings-background");
        background.setEffect(frostEffect);

        Pane rec_1 =   new Pane(shapes[0]);
        Pane rec_2 =   new Pane(shapes[1]);
        Pane rec_3 = new Pane(shapes[2]);
        Pane rec_4 =  new Pane(shapes[3]);
        Pane rec_5 =  new Pane(shapes[4]);

        HBox containersBox = new HBox(rec_1, rec_2, rec_3, rec_4, rec_5);

        for(int i = 0; i<containersBox.getChildren().size(); i++){
            int finalI = i;
            containersBox.getChildren().get(i).setId("recOne");
            containersBox.getChildren().get(i).setOnMouseClicked(e->updateShape(finalI));
        }
        containersBox.setSpacing(25);
        containersBox.setTranslateY(40);
        containersBox.setTranslateX(70);

        Rectangle highRec = new Rectangle(550,10);
        highRec.setArcHeight(10);
        highRec.setArcWidth(10);
        highRec.setTranslateY(-95);
        highRec.setTranslateX(width/2 - highRec.getWidth()/2);
        highRec.setOnMouseEntered(e->{
            General.processHighTrigger();
        });

        Rectangle lowRec = new Rectangle(150,10);
        lowRec.setArcHeight(10);
        lowRec.setArcWidth(10);
        lowRec.setTranslateY(90);
        lowRec.setTranslateX(width/2 - lowRec.getWidth()/2);
        lowRec.setOnMouseEntered(e->{
            General.processLowTrigger();
        });

        shapeChooseBox.getChildren().addAll(background, highRec, containersBox, lowRec
        );
    }

    private Polygon[] getShapes(){
        //shapes declaration
        Polygon shapeOne = new Polygon(0.0, 0.0, polygonLength, 0.0, polygonLength, polygonLength, 0.0, polygonLength);   //first shape
        Polygon shapeTwo = new Polygon(polygonLength/2,0.0, 0.0, polygonLength, polygonLength, polygonLength);   //second shape
        Polygon shapeThree = new Polygon(0.0,polygonLength/2, polygonLength/2,0.0,  polygonLength, polygonLength/2, polygonLength/2, polygonLength);   //third shape
        Polygon shapeFour = new Polygon(0.0, polygonLength/2, polygonLength/3, 0.0, (polygonLength/3)*2, 0.0, polygonLength, polygonLength/2, (polygonLength/3)*2, polygonLength, polygonLength/2, polygonLength);   //forth shape
        Polygon shapeFive = new Polygon(0.0,polygonLength/2, polygonLength/2,0.0, polygonLength, polygonLength/2, (polygonLength/3)*2, polygonLength, polygonLength/3, polygonLength);   //fifth shape

        return new Polygon[]{shapeOne, shapeTwo, shapeThree, shapeFour, shapeFive};
    }

    public static void updateShape(int shapeVal){
        if(shapeVal == activeShape)
            return;
        activeShape = shapeVal;
        ConvexHull shape3D = null;
        switch(shapeVal){
            case 0: shape3D = new Cube(); break;
            case 1: shape3D = new Tetrahedron(); break;
            case 2: shape3D = new Octahedron(); break;
            case 3: shape3D = new Icosahedron(); break;
            case 4: shape3D = new Dodecahedron(); break;
        }
        App.updateShape(shape3D);
    }
    //handles the mouse when hovers on top of the shape
    public void handleShapeMouseHover(Polygon shape){
        shape.setOpacity(0.6);
    }
    //handles the mouse exiting from the shape
    public void handleShapeMouseExit(Polygon shape){
        shape.setOpacity(1);
    }

    public Pane getTopSettings(){
        return shapeChooseBox;
    }


}
