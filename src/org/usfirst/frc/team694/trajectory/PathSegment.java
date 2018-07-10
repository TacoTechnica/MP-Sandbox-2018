package org.usfirst.frc.team694.trajectory;

/** PathSegment
 *
 *  Holds data for a single spline.
 *
 *  To grab spline information, instead of passing a "progress" value, we pass the distance traveled through this spline.
 *
 *  This includes the spline itself, and our target velocities.
 */
public class PathSegment {

    /** How far ahead to sample our spline for things like angular velocity */
    private static final double SAMPLE_DELTA = 0.01;

    private Spline spline;
    private double velInitial, velFinal; // Velocity initial and final
    private double totalDistance; // cached thing

    public PathSegment(Spline spline, double velInitial, double velFinal) {
        this.spline = spline;
        this.velInitial = velInitial;
        this.velFinal = velFinal;

        totalDistance = spline.getSplineLength(0, 1);
    }

    /**
     * Converts a given distance from the START of the spline to the spline's "progress" (0 to 1)
     * @param distance
     * @return
     */
    private double getProgress(double distance) {
        return distance / totalDistance;
    }

    /**
     *  Gets the magnitude of the velocity given the distance from the start of the segment
     */
    public double getSpeed(double distance) {
        if (distance > totalDistance) {
            // Cap it off at the total distance 
            distance = totalDistance;
        }
        return (totalDistance - distance) * velFinal + velInitial;
    }

    /**
     *  Gets the angular velocity at a particular distance from the start of the segment
     */
    public double getAngularVelocity(double distance) {
        double currentSpeed = getSpeed(distance);
        double nextSpeed =    getSpeed(distance + SAMPLE_DELTA);

        double currentHeading = getHeading(distance);//currentVelocityDirection.getAngle();
        double nextHeading =    getHeading(distance + SAMPLE_DELTA);//nextVelocityDirection.getAngle();

        // Theoretical time it takes to go from our current point to our next point.
        // This assumes constant acceleration! ( resulting equation: t = 2d / (vf + vi) )
        double theoreticalDeltaTime = 2*SAMPLE_DELTA / (currentSpeed + nextSpeed);

        // d Theta / dt
        return (nextHeading - currentHeading) / theoreticalDeltaTime;
    }

    /**
     *  Just in case, gets the target velocity given the distance from the start of the segment
     */
    public double getHeading(double distance) {
        double currentProgress = getProgress(distance);
        // Remember, splines don't actually tell you the velocity, just the direction of the velocity.
        Vector2d currentVelocityDirection = spline.getDerivative(currentProgress);
        return currentVelocityDirection.getAngle();
    }
    
    /**
     * @return how long the spline is in real units
     */
    public double getTotalDistance() {
        return totalDistance;
    }

}
