package org.usfirst.frc.team694.trajectory;

public class CubicFunction implements Function {

    // ax^3 + bx^2 + cx^1 + d
    private double a, b, c, d;

    public CubicFunction(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    public double getValue(double t) {
        return a*t*t*t + b*t*t + c*t + d;
    }

    public double getDerivative(double t) {
        return 3*a*t*t + 2*b*t + c;
    }

}
