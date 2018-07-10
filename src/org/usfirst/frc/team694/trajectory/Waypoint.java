package org.usfirst.frc.team694.trajectory;

/**
 * All data stored in an individual waypoint.
 * 
 * This includes position and target speed.
 *
 */
public class Waypoint {

    Vector2d position;
    double speed;
    
    public Waypoint(Vector2d position, double speed) {
        this.position = position;
        this.speed = speed;
    }

    // Easier to use constructor
    public Waypoint(double x, double y, double speed) {
        this(new Vector2d(x, y), speed);
    }
}
