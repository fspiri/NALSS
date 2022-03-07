package org.experiment.ui;

public class Color {
    public static int[] color = {255 , 255, 22};
    public static int COL_MIN = 22;
    public static int COL_MAX = 255;

    public static void updateColor(){
        if(color[0]<=COL_MAX && color[1]>=COL_MAX){
            if (color[0] > COL_MIN) {
                if(color[2]==COL_MIN)
                    color[0]--;
            } else
                color[2]++;
        }
        if(color[1]<=COL_MAX && color[2]>=COL_MAX){
            if(color[1]>COL_MIN){
                if (color[0] == COL_MIN)
                    color[1]--;
            } else
                color[0]++;
        }
        if(color[2]<=COL_MAX && color[0]>=COL_MAX){
            if (color[2]>COL_MIN){
                if (color[1] == COL_MIN)
                    color[2]--;
            } else
                color[1]++;
        }
    }

    public static int[] getColor(){
        return color;
    }
}
