package org.experiment.ui;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.experiment.App;
import org.experiment.ui.transitions.TransitionOne;

import java.util.concurrent.atomic.AtomicBoolean;

public class General {
    TopSettings topSettings;
    AdvancedSettings advancedSettings;

    static Pane topSettingsPane;
    static Pane advancedSettingsPane;
    static TransitionOne topTransition;
    static TransitionOne advTransition;
    static AtomicBoolean topOpen;
    static AtomicBoolean advOpen;

    public static final double SETTINGS_WIDTH = 900;
    public static final double SETTINGS_LATERAL_OFFSET = 25;
    public static final double SETTINGS_VERTICAL_OFFSET = 20;
    public static final double SETTINGS_BUTTON_WIDTH = 107.5;
    public static final double SETTINGS_BUTTON_HEIGHT = 107.5;

    Pane generalSettingsPane;
    double[] UIDimensions;
    static double[] sceneDimensions;





    public General(boolean darkMode){
        topSettings  = new TopSettings(darkMode);
        advancedSettings = new AdvancedSettings(darkMode);
        setGeneralSettings();
    }

    private void setGeneralSettings(){
        topOpen = new AtomicBoolean(false);
        advOpen = new AtomicBoolean(false);

        topSettingsPane = topSettings.getTopSettings();

        topTransition = new TransitionOne(topSettingsPane, "top");


        topSettingsPane.setOnMouseExited( e->{
            if(topOpen.get()){
                topTransition.update();
                topTransition.close();
                //topOpen.set(false);
            }
        });

        advancedSettingsPane = advancedSettings.getAdvancedSettings();
        advancedSettingsPane.setOnMouseExited( e->{
            if(advOpen.get()){
                advTransition.update();
                advTransition.close();
                //advOpen.set(false);
            }
        });




        advTransition = new TransitionOne(advancedSettingsPane, "adv");


        sceneDimensions = App.getSceneDimensions();
        UIDimensions = new double[]{topSettingsPane.getPrefWidth(), (advancedSettingsPane.getPrefHeight() + topSettingsPane.getPrefHeight())};


        VBox settings = new VBox();
        settings.getChildren().addAll(topSettingsPane
                ,advancedSettingsPane
        );

        generalSettingsPane = new Pane(settings);
        generalSettingsPane.setId("settings");
        generalSettingsPane.setTranslateX( (sceneDimensions[0]/2) - UIDimensions[0]/2 );
        generalSettingsPane.setTranslateY( (sceneDimensions[1] - SETTINGS_VERTICAL_OFFSET) );
    }
    public void updateUIPlacement(double sceneWidth, double sceneHeight){
        generalSettingsPane.setTranslateX( (sceneWidth/2) - (UIDimensions[0]/2) );
        generalSettingsPane.setTranslateY( (sceneHeight) - SETTINGS_VERTICAL_OFFSET);
    }
    public static void processHighTrigger(){
        if(!topOpen.get()){

            topTransition.update();
            topTransition.open();
            //topOpen.set(true);
        }
    }
    public static void processLowTrigger(){
        if(!advOpen.get()){

            advTransition.update();
            advTransition.open();
            //advOpen.set(true);
        }
    }
    public static void trigger(String val){
        if(val.equals("top"))
            topOpen.set(!topOpen.get());
        else
            advOpen.set(!advOpen.get());
    }
    public Pane getGeneralSettings(){   return generalSettingsPane;}

    public static double calculateXSpacing(HBox container){


        double realLength = SETTINGS_WIDTH - 2*SETTINGS_LATERAL_OFFSET; //a
        double numberOfElements = 0;
        double totalElementsWidth = 0;
        double remainingSpace = 0;
        double spacing = 0;

        for(Node e : container.getChildren()){
            if(e.getClass()== Pane.class && ((Pane)e).getPrefWidth() != 0) {
                totalElementsWidth += ((Pane) e).getPrefWidth();
                numberOfElements ++ ;
            }
            else if(e.getClass()==Slider.class && ((Slider)e).getPrefWidth()!= 0) {
                totalElementsWidth += ((Slider) e).getPrefWidth();
                numberOfElements ++ ;
            }
            else if (e.getClass() == Label.class && ((Label)e).getPrefWidth() != 0) {
                totalElementsWidth += ((Label) e).getPrefWidth();
                numberOfElements++;
            }
            else if(e.getClass() == RadioButton.class && ((RadioButton)e).getPrefWidth()!=0) {
                totalElementsWidth += ((RadioButton) e).getPrefWidth();
                numberOfElements++;
            }


        }

        remainingSpace = realLength - totalElementsWidth;

        spacing = remainingSpace/ ( numberOfElements - 1 );
        return spacing;
    }

    public static void setIcon(Pane pane, ImageView image){

    }


}
