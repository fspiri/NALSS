package org.experiment;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.experiment.hull.ConvexHull;
import org.experiment.hull.Cube;
import org.experiment.ui.AdvancedSettings;
import org.experiment.ui.General;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/*
   TODO
    -> improve code structure even to enable better graphics
        DONE new class for top settings
        DONE new class for mid settings
        DONE new class for general settings (?)
        DONE fix the shape changing mechanism
        DONE fix change of speed bug
    -> Color has to be moved into experiment.ui
    -> upload on GIT
    -> running time
    ** UI DESIGN IDEAs **
        -> glassy and opaque
        -> ?? dark mode ??
 */
/*
    App class is the main class that generates the entire scene, it must be clean and easy to understand
 */
public class App extends Application {
    static int GROWTH = 100;       //growth coefficient of edges length

    double currentAngle = 0;       //shape's angle
    static double sceneWidth = 1920;
    static double sceneHeight = 1080;
    static double displaceX = sceneWidth/2;
    static double displaceY = sceneHeight/2;
    Stage stage;            //App main stage

    static boolean colorFlag= false;
    boolean darkMode = true;

    static boolean rotationVerseFlag = true;
    static int rotationX = 0;
    static int rotationY = 1;
    static int rotationZ = 2;

    static ConvexHull shape = new Cube();  //actual 3D shape, initialized as a Cube
    static double angle_increase = 0.020;  //value added to the angle, in settings can be increased to increase rotation speed

    static boolean moveFlag = false;
    double movementMagnitude = 7.5;

    static AtomicBoolean vertYes = new AtomicBoolean(true);    //vertices selection flag
    static AtomicBoolean edgeYes = new AtomicBoolean (true);   //edges selection flag

    static Movement mv;

    static boolean randomness = true;

    @Override
    public void start(Stage stage) {
        IO.loadFiles();
        Pane topSettings = new org.experiment.ui.TopSettings(true).getTopSettings();
        topSettings.setId("top-settings");

        Pane advancedSettings = new AdvancedSettings(true).getAdvancedSettings();
        advancedSettings.setId("advanced-settings");

        General generalSettings = new General(darkMode);
        Pane generalSettingsPane = new Pane(generalSettings.getGeneralSettings());

        Group points = new Group();     //group containing all the vertices of the shape
        Group lines = new Group();      //group containing all the edges

        var shapeScene = new Pane();            //background pane


        Label commands = new Label("press D to hide GUI");
        commands.setId("commands");
        commands.setMouseTransparent(true);

        VBox settingsScene = new VBox(//topSettings, advancedSettings, commands,
                generalSettingsPane);   //vertical scene containing all the boxes

        Pane finalBox = new Pane(shapeScene, settingsScene);
        finalBox.setId("background");

        Scene scene = new Scene(finalBox, sceneWidth, sceneHeight); //actual scene of the app
        scene.getStylesheets().add("/acqua.css");
        processRotButtonPress();
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.D) {
                if (finalBox.getChildren().contains(settingsScene))
                    finalBox.getChildren().remove(settingsScene);
                else
                    finalBox.getChildren().addAll(settingsScene);
            }
        });

        shapeScene.setMouseTransparent(true);


        this.stage = stage;
        this.stage.setScene(scene);
        //TODO set this ON after all changes
        // this.stage.setFullScreen(true);
        // sceneHeight = stage.getHeight();
        // sceneWidth = stage.getWidth();

        this.stage.show();
        AdvancedSettings.updateDisplacementSlidersSettingsMax(sceneWidth, sceneHeight);
        AdvancedSettings.setDisplacementSlidersValue(displaceX, displaceY);
        mv = new Movement(scene, shapeScene, shape, movementMagnitude);

        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            sceneWidth = stage.getWidth();
            AdvancedSettings.updateDisplacementSlidersSettingsMax(sceneWidth, sceneHeight);
            generalSettings.updateUIPlacement(sceneWidth, sceneHeight);
            //shape.setDisplacement(sceneWidth/2, sceneHeight/2);
            //displaceX = sceneWidth/2;
            //displaceY = sceneHeight/2;
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            sceneHeight = stage.getHeight();
            AdvancedSettings.updateDisplacementSlidersSettingsMax(sceneWidth, sceneHeight);
            generalSettings.updateUIPlacement(sceneWidth, sceneHeight);
            //shape.setDisplacement(sceneWidth/2, sceneHeight/2);
            //displaceX = sceneWidth/2;
            //displaceY = sceneHeight/2;
        });
        processRotButtonPress();

        //scene update/ frame update
        new AnimationTimer(){
            final int UPDATE_SPEED = 60; //how much time before frame update
            long lastUpdate = 0;      //used with update Speed to calculate how fast to update the scene
            @Override
            public void handle(long now){
                if(now - lastUpdate >= UPDATE_SPEED) { //update every 'updateSpeed' amount of time
                    if(moveFlag) {
                        mv.bounce();  //then updates the movement vector
                        displaceX += mv.getMoveVectorValues()[0];
                        displaceY += mv.getMoveVectorValues()[1];
                        AdvancedSettings.setDisplacementSlidersValue(displaceX, displaceY);
                    }
                    shapeScene.getChildren().clear();       //clear the background from the shape vertices and edges
                    points.getChildren().clear();   //clear vertices
                    lines.getChildren().clear();    //clear the edges

                    shape.updateAll(currentAngle);  //call the updateAll for the shape, updating all its values
                    shape.stylizeEdges(colorFlag, darkMode);           //add style to the edges

                    if(vertYes.get()) {             //if the vert option is true, add vertices to the scene to a list
                        double[][] t_vert = shape.getVertices();
                        for(double[] d : t_vert) {
                            if(d != null)
                                points.getChildren().add(new Circle(d[0] * GROWTH + displaceX, d[1] * GROWTH + displaceY, 3, Color.WHITE));
                        }
                    }

                    if(edgeYes.get()){              //if the edges option is true, add the edges to the scene to a list
                        List<Line>edges = shape.getEdges();
                        for(Line elle : edges)
                            lines.getChildren().add(elle);
                    }
                    shapeScene.getChildren().addAll(points, lines); //add the vertices and edges list to the background
                    if(rotationVerseFlag)
                        currentAngle += angle_increase;     //increase the angle by the speed coefficient
                    else
                        currentAngle -= angle_increase;
                }
                lastUpdate = now;
            }
        }.start();
    }


    public static void processRotButtonPress(){
            shape.setGrowth(GROWTH);
            shape.setDisplacement(displaceX, displaceY);
            if (checkValue(rotationX) && checkValue(rotationY) && checkValue(rotationZ))
                shape.setRotations(rotationX, rotationY, rotationZ);
    }
    //updates the rotating tridimensional shape when requested
    public static void updateShape(ConvexHull shape3D){
        if(shape3D!=null) {
            shape = shape3D;
            processRotButtonPress();
        }
    }
    //check if the value is in the list of all possible values
    private static boolean checkValue(int a){
        int[] validRot = {-1, 0, 1, 2};
        for(int j : validRot){
            if(j == a) return true;
        }
        return false;
    }

    public static void updateMovementSpeed(double movementMagnitude, boolean increase){
        mv.updateMagnitude(movementMagnitude, increase);
    }
    public static void changeGrowth(int val){
        GROWTH = val;
        shape.setGrowth(val);
    }
    public static void changeDisplacement(double x, double y){
        displaceX = x;
        displaceY = y;
        shape.setDisplacement(x, y);
    }
    //takes the command from rotation UI
    public static void updateRotation(int a, int b, int c){
        setRotation(a, b, c);
        shape.setRotations(a, b, c);
    }
    public static void stopRotation(){
        angle_increase = 0;
    }
    public static void startRotation(double angle){
        angle_increase = angle;
    }
    public static double getAngleIncrease(){
        return angle_increase;
    }
    public static void setAngleIncrease(double val) {angle_increase = val;}
    private static void setRotation(int a, int b, int c){
        rotationX = a;
        rotationY = b;
        rotationZ = c;
    }
    public static void setEdges(boolean val) { edgeYes.set(val);}
    public static void setVertices(boolean val) { vertYes.set(val);}
    public static void setMovementFlag(boolean val){
        displaceX = AdvancedSettings.getDisplaceX();
        displaceY = AdvancedSettings.getDisplaceY();
        moveFlag = val;
    }
    public static void updateColor(boolean flag){
        colorFlag = flag;
    }
    public static double[] getSceneDimensions(){
        return new double[] {sceneWidth, sceneHeight};
    }
    public static void changeRotationVerse() {
        rotationVerseFlag = !rotationVerseFlag;
    }
    public static void changeRotationSpeed(){

            if (Math.random() >= 0.55 && angle_increase < 0.035)
                angle_increase += Math.random() / 65;
            else {
                if (angle_increase > 0.001)
                    angle_increase -= Math.random() / 65;
            }

    }
    public static void changeRandomness(){
        randomness = !randomness;
    }


    public static void main(String[] args) {
        launch();
    }



}
