package org.experiment.ui;

import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.Effect;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.experiment.App;
import org.experiment.ui.advanced.*;

public class AdvancedSettings {
    Pane settingsBox;


    static double angle_increase = App.getAngleIncrease();  //value added to the angle, in settings can be increased to increase rotation speed


    public AdvancedSettings(boolean darkMode){
        setMidSettings();
    }



    private void setMidSettings(){
        settingsBox = new Pane();      //Pane containing all the settings - stylized in acqua.CSS
        settingsBox.setPrefWidth(General.SETTINGS_WIDTH);
        settingsBox.setPrefHeight(900);
        final double BLUR_AMOUNT = 3;
        final Effect frostEffect =
                new BoxBlur(BLUR_AMOUNT, BLUR_AMOUNT, 1);
        HBox rotationSettingsRow = new RotationSettings().getRotationSettingsBox();
        HBox rotationSpeedSettingsRow = new RotationSpeedSettings().getSpeedSettings();
        HBox dimensionSettingsRow = new DimensionSettings().getDimensionSettings();
        HBox vertAndEdgesSettingsRow = new VertAndEdgesSettings().getVertAndEdges();
        HBox movementSettingsRow = new MovementSettings().getMovementSettings();



        //settingsBox.setTranslateX(-850);


        VBox settingsVertical = new VBox(               //vertical box containing all the settings
                rotationSettingsRow,
                rotationSpeedSettingsRow,
                dimensionSettingsRow,
                vertAndEdgesSettingsRow,
                movementSettingsRow);
        settingsVertical.setSpacing(General.SETTINGS_VERTICAL_OFFSET);
        rotationSettingsRow.setId("rotation");
        rotationSpeedSettingsRow.setId("slider");
        dimensionSettingsRow.setId("dimension");
        vertAndEdgesSettingsRow.setId("verts");
        settingsVertical.setTranslateY(General.SETTINGS_VERTICAL_OFFSET);
        Pane background = new Pane();
        background.setId("advanced-settings-background");
        background.setEffect(frostEffect);
        settingsBox.getChildren().addAll(background, settingsVertical);
    }

    public Pane getAdvancedSettings(){
        return settingsBox;
    }

    public static void changeRotationSpeed(double val){App.setAngleIncrease(val);}
    public static void processStopButton(){
        angle_increase = 0;
        RotationSpeedSettings.updateSpeedSlider(angle_increase);
        App.stopRotation();
    }
    public static void processPlayButton(double angle){
        angle_increase = angle;
        RotationSpeedSettings.updateSpeedSlider(angle_increase);
        App.startRotation(angle);
    }
    public static double getAngleIncrease(){
        return App.getAngleIncrease();
    }
    public static void updateRotation(int a, int b, int c){
        App.updateRotation(a, b, c);
    }
    public static void setEdges(boolean val) {App.setEdges(val);}
    public static void setVertices(boolean val) {App.setVertices(val);}
    public static void setMovementFlag(boolean val){App.setMovementFlag(val);}
    public static double getDisplaceX(){return DimensionSettings.getDisplacementX();}
    public static double getDisplaceY(){return DimensionSettings.getDisplacementY();}
    public static void setDisplacementSlidersValue(double x, double y){
        DimensionSettings.setDisplacementSliders(x, y);
    }
    public static void updateDisplacementSlidersSettingsMax(double x, double y){DimensionSettings.setDisplacementSlidersMax(x, y);}
    public static void updateColor(boolean flag){
        App.updateColor(flag);
    }
    public static void changeRotationVerse() {App.changeRotationVerse();}





}
