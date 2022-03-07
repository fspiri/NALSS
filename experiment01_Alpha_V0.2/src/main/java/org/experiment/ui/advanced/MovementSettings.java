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

public class MovementSettings {
    static HBox moveSettings;
    boolean moveFlag = false;
    double movementMagnitude = 7.5;


    public MovementSettings(){
        setMovementSettings();
    }
    private void setMovementSettings(){
        Pane moveButton = new Pane();
        moveButton.getChildren().add(IO.getIcon('b'));
        moveButton.setOnMouseClicked(e->{
            moveFlag = !moveFlag;
            AdvancedSettings.setMovementFlag(moveFlag);
            if(moveFlag) {
                //moveButton.setText("stop");
                DimensionSettings.ignoreMouse(true);
            }else {
                //moveButton.setText("bounce");
                DimensionSettings.ignoreMouse(false);
            }
        });
        Slider movementSpeedSlider = new Slider(0, 100, movementMagnitude);
        movementSpeedSlider.setPrefWidth(General.SETTINGS_BUTTON_WIDTH*3);
        movementSpeedSlider.setTranslateY((General.SETTINGS_BUTTON_HEIGHT/2)-15);
        movementSpeedSlider.valueProperty().addListener((observableValue, oldValue, newValue) -> {
            movementMagnitude = newValue.doubleValue();
            App.updateMovementSpeed(movementMagnitude, newValue.doubleValue() > oldValue.doubleValue());
        });
        Pane noRandomnessButton = new Pane();
        noRandomnessButton.getChildren().add(IO.getIcon('c'));
        noRandomnessButton.setOnMouseClicked(e->App.changeRandomness());
        moveSettings = new HBox(moveButton, movementSpeedSlider, noRandomnessButton);

        for(Node e : moveSettings.getChildren()) {
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
        moveSettings.setSpacing(General.calculateXSpacing(moveSettings));
        moveSettings.setTranslateX(General.SETTINGS_LATERAL_OFFSET);
    }
    public static HBox getMovementSettings(){
        return moveSettings;
    }
}
