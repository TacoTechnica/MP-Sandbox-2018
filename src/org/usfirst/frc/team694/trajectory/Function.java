package org.usfirst.frc.team694.trajectory;

public interface Function {
    public double getValue(double t);
    public double getDerivative(double t);
}
