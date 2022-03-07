package org.experiment.ui.transitions;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import org.experiment.ui.General;

public class TransitionOne {
    Pane pane;
    static long speed = 250L;
    double initialPos;
    double height;
    double width;
    final double OFFSET = 20;
    String val;

    public TransitionOne(Pane pane, String val){
        this.pane = pane;
        height = pane.getPrefHeight();
        width = pane.getPrefWidth();
        initialPos = pane.getTranslateY();
        this.val = val;
    }

        final  Animation settingsEnterAnimation = new Transition() {

            {
                setCycleDuration(Duration.millis(speed));
            }

            @Override
            protected void interpolate(double progress) {
                if(progress==0)
                    update();
                if(progress==1)
                    shootTrigger();
                pane.setTranslateY(initialPos - (progress * (height-OFFSET)));
            }
        };

        final Animation settingsExitAnimation = new Transition() {  //animation for the Pane exiting

            {
                setCycleDuration(Duration.millis(speed));
            }

            @Override
            protected void interpolate(double progress) {
                if(progress==0)
                    update();
                if(progress==1)
                    shootTrigger();
                pane.setTranslateY(initialPos + (progress * (height-OFFSET)));
            }
        };
    public void open(){
        settingsEnterAnimation.playFromStart();
    }
    public void close(){
        settingsExitAnimation.playFromStart();
    }
    public void update(){
        initialPos = pane.getTranslateY();
    }
    public void shootTrigger(){
        General.trigger(val);
    }


}
