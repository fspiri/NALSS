package org.experiment;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.experiment.ui.General;

import java.util.ArrayList;
import java.util.List;

public class IO {
    static final int X_DIMENSION = 81;
    static final int Y_DIMENSION = 81;
    static String X = "x.png";
    static String Y = "y.png";
    static String Z = "z.png";
    static String XYZ = "xyz.png";
    static String PLAY = "play.png";
    static String PAUSE = "pause.png";
    static String REVERSE = "reverse.png";
    static String MINIMUM  ="min.png";
    static String MEDIUM = "med.png";
    static String HIGH = "high.png";
    static String BOUNCE = "bounce.png";
    static String CHILL = "chill.png";

    static ImageView x_imgView;
    static ImageView y_imgView;
    static ImageView z_imgView;
    static ImageView xyz_imgView;
    static ImageView pause_imgView;
    static ImageView play_imgView;
    static ImageView reverse_imgView;
    static ImageView min_imgView;
    static ImageView med_imgView;
    static ImageView high_imgView;
    static ImageView bounce_imgView;
    static ImageView chill_imgView;

    static List<ImageView> icons = new ArrayList<>();

    public IO(){
        loadFiles();
    }
    public static void loadFiles(){
       loadIcons();
       sizeIcons();
       centerIcons();
    }

    private static void loadIcons(){
        x_imgView     = new ImageView(new Image(X));
        icons.add(x_imgView);
        y_imgView     = new ImageView(new Image(Y));
        icons.add(y_imgView);
        z_imgView     = new ImageView(new Image(Z));
        icons.add(z_imgView);
        xyz_imgView   = new ImageView(new Image(XYZ));
        icons.add(xyz_imgView);

        pause_imgView = new ImageView(new Image(PAUSE));
        icons.add(pause_imgView);
        play_imgView = new ImageView(new Image(PLAY));
        icons.add(play_imgView);
        reverse_imgView = new ImageView(new Image(REVERSE));
        icons.add(reverse_imgView);

        min_imgView = new ImageView(new Image(MINIMUM));
        icons.add(min_imgView);
        med_imgView = new ImageView(new Image(MEDIUM));
        icons.add(med_imgView);
        high_imgView = new ImageView(new Image(HIGH));
        icons.add(high_imgView);

        bounce_imgView = new ImageView(new Image(BOUNCE));
        icons.add(bounce_imgView);

        chill_imgView = new ImageView(new Image(CHILL));
        icons.add(chill_imgView);
    }

    public static ImageView getIcon(char val){
        switch (val){
            case 'x': return x_imgView;
            case 'y': return y_imgView;
            case 'z': return z_imgView;
            case 'n': return xyz_imgView;
            case 'p': return play_imgView;
            case 'r': return reverse_imgView;
            case 's': return pause_imgView;

            case 'u': return min_imgView;
            case 'e': return med_imgView;
            case 't': return high_imgView;

            case 'b': return bounce_imgView;
            case 'c': return chill_imgView;
        }
        throw new IllegalStateException();
    }

    private static void  sizeIcons(){
        for (int i = 0; i<icons.size(); i++){
            icons.get(i).setFitWidth(X_DIMENSION);
            icons.get(i).setFitWidth(Y_DIMENSION);
        }
    }
    private static void  centerIcons(){
        for (int i = 0; i<icons.size(); i++){
            icons.get(i).setTranslateX(General.SETTINGS_BUTTON_WIDTH/2 - X_DIMENSION/2);
            icons.get(i).setTranslateY(General.SETTINGS_BUTTON_HEIGHT/2 - Y_DIMENSION/2);
        }
    }

}
