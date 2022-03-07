package org.experiment;

public class MovFlag {
    int counter;
    final int THRESHOLD = 45;

    public MovFlag(){
        counter = THRESHOLD;
    }

    public boolean isMovFlag() {
        return counter < THRESHOLD;
    }

    public void reset(){
        counter = 0;
    }

    public void increment(){
        System.out.println("aaa");
        counter++;
    }

}
