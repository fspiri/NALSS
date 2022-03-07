package org.experiment.ui.advanced;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.experiment.App;
import org.experiment.IO;
import org.experiment.ui.AdvancedSettings;
import org.experiment.ui.General;

public class RotationSpeedSettings {
    double angle_increase;
    final double SLOW_SPEED =   0.0025;
    final double MEDIUM_SPEED = 0.025;
    final double FAST_SPEED =   0.0625;

    static HBox speedBox;
    static Slider speedSlider;             //slider controlling the speed

    public RotationSpeedSettings(){
        angle_increase = App.getAngleIncrease();
        setSpeedSettings();
    }

    private void setSpeedSettings(){
        final Pane slowButton = new Pane();
        slowButton.getChildren().add(IO.getIcon('u'));
        final Pane mediumButton = new Pane();
        mediumButton.getChildren().add(IO.getIcon('e'));
        final Pane fastButton = new Pane();
        fastButton.getChildren().add(IO.getIcon('t'));

        final Label angleLabel = new Label(String.valueOf(angle_increase));     //label showing the angle increase -> speed
        angleLabel.setPrefWidth(General.SETTINGS_BUTTON_WIDTH/2);

        speedSlider = new Slider(0, 0.1, angle_increase);  //slider setting the speed
        speedSlider.setPrefWidth(General.SETTINGS_BUTTON_WIDTH * 3);

        speedSlider.setTranslateY((General.SETTINGS_BUTTON_HEIGHT/2)-15);
        speedSlider.setMajorTickUnit(0.0025); speedSlider.setMinorTickCount(1);
        speedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            try {
                angleLabel.setText(String.valueOf(newValue.doubleValue()*1000).substring(0, 6));
            } catch (StringIndexOutOfBoundsException e) {
                int anselmo = Integer.parseInt(String.valueOf(e.toString().charAt(e.toString().length()-1))) -1;
                angleLabel.setText(String.valueOf(newValue.doubleValue()*1000).substring(0, anselmo));
            }
            angle_increase = newValue.doubleValue();
            AdvancedSettings.changeRotationSpeed(angle_increase);
        });

        slowButton.setOnMouseClicked( e -> processButtonPress(0) );
        mediumButton.setOnMouseClicked( e -> processButtonPress(1) );
        fastButton.setOnMouseClicked( e -> processButtonPress(2) );


        speedBox = new HBox(slowButton, mediumButton, fastButton, speedSlider
                , angleLabel
        );   //all speed options
        for(Node e : speedBox.getChildren()) {
            if (e.getClass() == Pane.class) {
                ((Pane) e).setPrefWidth(General.SETTINGS_BUTTON_WIDTH);
                ((Pane) e).setPrefHeight(General.SETTINGS_BUTTON_HEIGHT);
            }
            if (e.getClass() == Label.class)
                e.setId("settings-label");
            else if (e.getClass() == Pane.class)
                e.setId("settings-button");
            else
                e.setId("settings-slider");
        }
        speedBox.setSpacing(General.calculateXSpacing(speedBox));
        speedBox.setTranslateX(General.SETTINGS_LATERAL_OFFSET);

    }
    public static HBox getSpeedSettings(){
        return speedBox;
    }
    public static void updateSpeedSlider(double angle_increase){
        speedSlider.setValue(angle_increase);
    }
    private void processButtonPress(int val){
        switch(val){
            case 0: angle_increase = SLOW_SPEED; break;
            case 1: angle_increase = MEDIUM_SPEED; break;
            case 2: angle_increase = FAST_SPEED; break;
        }
        speedSlider.setValue(angle_increase);
        AdvancedSettings.changeRotationSpeed(angle_increase);
    }
}
