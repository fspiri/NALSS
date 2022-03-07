package org.experiment;

public class Vector {
    double X;
    double Y;
    double magnitude;
    double angle;

    Vector(){}
    Vector(double A, double B){
        this.X = A;
        this.Y = B;
        magnitude = Math.sqrt(A*B);
    }
    Vector(double A, double B, double magnitude){
        this.X = A;
        this.Y = B;
        this.magnitude = magnitude;
    }
    public void setByAngle(double angle, double magnitude){
        this.angle = angle;
        this.magnitude = magnitude;
        X = magnitude * Math.sin(angle);
        Y = magnitude * Math.cos(angle);
    }
    public void translateXY(double x, double y){
        X += x;
        Y += y;
    }
    public void setX(double x){
        this.X = x;
    }
    public void setY(double y){
        this.Y = y;
    }
    public void setXY(double x, double y){
        this.X=x;
        this.Y=y;
    }
    public void setAngle(double angle){
        this.angle = angle;
    }
    public double getAngle(){
        return angle;
    }
    public double getX(){
        return X;
    }
    public double getY(){
        return Y;
    }
    public double getMagnitude(){
        return magnitude;
    }
    public double[] getValue(){
        return new double[]{X, Y};
    }

    public void increaseMagnitude(double newMagnitude, boolean increase){
        if (increase) {
            double magnitudeDiff = newMagnitude - magnitude;
            magnitude += magnitudeDiff;
            if (X<0)
                X-=magnitudeDiff;
            else
                X+=magnitudeDiff;
            if(Y<0)
                Y-=magnitudeDiff;
            else
                Y+=magnitudeDiff;
        }
        else{
            double magnitudeDiff = magnitude - newMagnitude;
            magnitude -= magnitudeDiff;
            if (X<0)
                X+=magnitudeDiff;
            else
                X-=magnitudeDiff;
            if(Y<0)
                Y+=magnitudeDiff;
            else
                Y-=magnitudeDiff;
        }


    }

}
