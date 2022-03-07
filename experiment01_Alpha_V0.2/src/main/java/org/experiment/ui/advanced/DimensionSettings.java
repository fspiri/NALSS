package org.experiment.ui.advanced;

import javafx.scene.Node;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.experiment.ui.General;

public class DimensionSettings {
    static HBox dimensionHBox;
    int GROWTH = 100;
    static double displaceX = 350;
    static double displaceY = 350;
    static Slider displacementXSlider;
    static Slider displacementYSlider;

    public DimensionSettings(){
        setDimensionSettings();
    }
    private void setDimensionSettings(){
        Pane transparentPane = new Pane(); //Pane needed for correct spacing
        transparentPane.setPrefHeight(General.SETTINGS_BUTTON_HEIGHT);
        transparentPane.setPrefWidth(0);
        Slider  growthSlider;
        growthSlider = new Slider(0, 300, 100);
        growthSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            GROWTH = newValue.intValue();
            org.experiment.App.changeGrowth(newValue.intValue());
        });


        displacementXSlider = new Slider(0, 1500, displaceX);
        displacementYSlider = new Slider(0,1500, displaceY);

        displacementXSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            displaceX = newValue.doubleValue();
            org.experiment.App.changeDisplacement(displacementXSlider.getValue(), displacementYSlider.getValue());
        });
        displacementYSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            displaceY = newValue.doubleValue();
            org.experiment.App.changeDisplacement(displacementXSlider.getValue(), displacementYSlider.getValue());
        });

        dimensionHBox = new HBox(transparentPane, growthSlider, displacementXSlider, displacementYSlider);

        for(Node e : dimensionHBox.getChildren()){
            if(e.getClass().equals(Slider.class))
                ((Slider)e).setPrefWidth(General.SETTINGS_BUTTON_WIDTH * 2.6);
            e.setTranslateY((General.SETTINGS_BUTTON_HEIGHT/2)-15);
            e.setId("settings-slider");
        }
        dimensionHBox.setTranslateX(General.SETTINGS_LATERAL_OFFSET);
        dimensionHBox.setSpacing(General.calculateXSpacing(dimensionHBox));
    }
    public static HBox getDimensionSettings(){
        return dimensionHBox;
    }
    public static void ignoreMouse(boolean flag){
        displacementXSlider.setMouseTransparent(flag);
        displacementYSlider.setMouseTransparent(flag);
    }
    public static double getDisplacementX(){ return displaceX;}
    public static double getDisplacementY(){ return displaceY;}
    public static void setDisplacementSliders(double x, double y){
        displacementXSlider.setValue(x);
        displacementYSlider.setValue(y);
    }
    public static void setDisplacementSlidersMax(double x, double y){
            displacementXSlider.setMax(x);
            displacementYSlider.setMax(y);
    }
}
