package org.usfirst.frc.team694.trajectory;

/**
 * This holds all of the data needed from a drivetrain in Motion Profiling.
 * 
 * It's kind of like a "PhysicalConstants" class specific to just the drivetrain.
 *
 */
public class DrivetrainData {

    private double width;
    private double maxVelocity;
    private double maxAcceleration;

    public DrivetrainData(double width, double maxVelocity, double maxAcceleration) {
        this.width = width;
        this.maxVelocity = maxVelocity;
        this.maxAcceleration = maxAcceleration;
    }
    
    public double getRobotWidth() {
        return width;
    }
    
    public double getMaxVelocity() {
        return maxVelocity;
    }
    
    public double getMaxAcceleration() {
        return maxAcceleration;
    }

}
