package org.experiment.ui.advanced;

import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.experiment.IO;
import org.experiment.ui.AdvancedSettings;
import org.experiment.ui.General;

public class RotationSettings {
    static HBox rotationHBox;
    double angleIncreaseBuffer;

    int rotationX = 0;
    int rotationY = 1;
    int rotationZ = 2;
    TextField textField_X = new TextField(String.valueOf(rotationX));     //
    TextField textField_Y = new TextField(String.valueOf(rotationY));    //
    TextField textField_Z = new TextField(String.valueOf(rotationZ));    //


    public RotationSettings() {
        setRotationSettings();
    }

    private void setRotationSettings(){
        textField_X.setOnAction(e -> rotationX = Integer.parseInt(textField_X.getText()));
        textField_Y.setOnAction(e -> rotationY = Integer.parseInt(textField_Y.getText()));
        textField_Z.setOnAction(e -> rotationZ = Integer.parseInt(textField_Z.getText()));

        //TODO instead of texts the buttons should have an icon
        //Button xyzButton = new Button("XYZ");  //standard XYZ rotation for the shape
        Pane xyzButton = new Pane();
        Pane playButton = new Pane();
        Pane xButton = new Pane();
        Pane yButton = new Pane();
        Pane zButton = new Pane();
        Pane pauseButton = new Pane();
        Pane rotationVerseButton = new Pane();

        xButton.getChildren().add(IO.getIcon('x'));
        yButton.getChildren().add(IO.getIcon('y'));
        zButton.getChildren().add(IO.getIcon('z'));
        xyzButton.getChildren().add(IO.getIcon('n'));
        playButton.getChildren().add(IO.getIcon('p'));
        pauseButton.getChildren().add(IO.getIcon('s'));
        rotationVerseButton.getChildren().add(IO.getIcon('r'));


        xyzButton.setOnMouseClicked(e-> {
            rotationX = 0;
            rotationY = 1;
            rotationZ = 2;
            textField_X.setText("0");
            textField_Y.setText("1");
            textField_Z.setText("2");
            AdvancedSettings.updateRotation(
                    rotationX,
                    rotationY,
                    rotationZ
            );});

        playButton.setOnMouseClicked(e -> AdvancedSettings.processPlayButton(angleIncreaseBuffer));
        pauseButton.setOnMouseClicked(e->{
            angleIncreaseBuffer = AdvancedSettings.getAngleIncrease();
            AdvancedSettings.processStopButton();
                }
        );

        xButton.setOnMouseClicked(e -> {
            setRotations(0, -1, -1);
            AdvancedSettings.updateRotation(
                    rotationX,
                    rotationY,
                    rotationZ
            );
        });
        yButton.setOnMouseClicked(e -> {
            setRotations(1, -1, -1);
            AdvancedSettings.updateRotation(
                    rotationX,
                    rotationY,
                    rotationZ
            );
        });
        zButton.setOnMouseClicked(e -> {
            setRotations(2, -1, -1);
            AdvancedSettings.updateRotation(
                    rotationX,
                    rotationY,
                    rotationZ
            );
        });
        rotationVerseButton.setOnMouseClicked(e->{
            AdvancedSettings.changeRotationVerse();
        });

        HBox buttonsHBox = new HBox(xyzButton,
                xButton,
                yButton,
                zButton,
                playButton,
                pauseButton,
                rotationVerseButton
        );
        for(Node e : buttonsHBox.getChildren()) {
            ((Pane) e).setPrefWidth(General.SETTINGS_BUTTON_WIDTH);
            ((Pane) e).setPrefHeight(General.SETTINGS_BUTTON_HEIGHT);
            e.setId("settings-button");
        }
        buttonsHBox.setTranslateX(General.SETTINGS_LATERAL_OFFSET);
        buttonsHBox.setSpacing(General.calculateXSpacing(buttonsHBox));
        rotationHBox = new HBox(buttonsHBox);   //hbox containing all the rotation settings
    }

    public static HBox getRotationSettingsBox(){
        return rotationHBox;
    }
    private void setRotations(int a, int b, int c){
        rotationX = a;
        rotationY = b;
        rotationZ = c;
    }
}
