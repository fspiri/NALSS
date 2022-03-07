package org.experiment.ui.advanced;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import org.experiment.ui.AdvancedSettings;
import org.experiment.ui.General;

public class VertAndEdgesSettings {
    static HBox vertAndEdges;


    public VertAndEdgesSettings(){
        setVertAndEdgesSettings();
    }
    private void setVertAndEdgesSettings(){
        RadioButton vert = new RadioButton();     //button to show/hide vertices
        RadioButton edge = new RadioButton();     //button to show/hide edges
        RadioButton color = new RadioButton();
        vert.setSelected(true);
        edge.setSelected(true);

        Pane vertButton = new Pane();
        Pane edgeButton = new Pane();
        Pane colorButton = new Pane();



        vertButton.setOnMouseClicked(e-> {
            vert.setSelected(!vert.isSelected());
            AdvancedSettings.setEdges(vert.isSelected());
        });
        edgeButton.setOnMouseClicked(e-> {
            edge.setSelected(!edge.isSelected());
            AdvancedSettings.setVertices(edge.isSelected());
        });
        colorButton.setOnMouseClicked(e->{
            color.setSelected(!color.isSelected());
            updateColor(color.isSelected());
        });

        vertAndEdges = new HBox(vertButton, edgeButton, colorButton);
        for(Node e : vertAndEdges.getChildren()){
            ((Pane)e).setPrefWidth(General.SETTINGS_BUTTON_WIDTH);
            ((Pane)e).setPrefHeight(General.SETTINGS_BUTTON_HEIGHT);
            e.setId("settings-button");
        }
        vertAndEdges.setTranslateX(General.SETTINGS_LATERAL_OFFSET);
        vertAndEdges.setSpacing(General.calculateXSpacing(vertAndEdges));
    }
    public static HBox getVertAndEdges(){
        return vertAndEdges;
    }
    public static void updateColor(boolean flag){
        AdvancedSettings.updateColor(flag);
    }

}
