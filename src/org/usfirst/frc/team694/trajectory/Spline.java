package org.usfirst.frc.team694.trajectory;


public abstract class Spline {

    private static final int SAMPLE_SIZE = 500;

    protected Function interpolationX,
                       interpolationY;

    public Spline(Function interpolationX, Function interpolationY) {
        this.interpolationX = interpolationX;
        this.interpolationY = interpolationY;
    }

    /**
     * Gets the position of this spline,
     * given a progress between 0 and 1
     */
    public Vector2d getPosition(double progress) {
        double x = interpolationX.getValue(progress);
        double y = interpolationY.getValue(progress);
        return new Vector2d(x, y);
    }

    /**
     * Gets the derivative of this spline (NOTE: Not velocity. Expressed in terms of progress),
     * given a progress between 0 and 1
     */
    public Vector2d getDerivative(double progress) {
        double dx = interpolationX.getDerivative(progress);
        double dy = interpolationY.getDerivative(progress);
        return new Vector2d(dx, dy);
    }

    /**
     * Gets the angle/direction/heading of the spline
     * given a progress between 0 and 1
     */
    public double getAngle(double progress) {
        return getDerivative(progress).getAngle();
    }

    /**
     * Gets the length of the spline, from start progress to end progress.
     */
    public double getSplineLength(double startProgress, double endProgress) {
        double resultLength = 0;
        double deltaProgress = (endProgress - startProgress) / (double)SAMPLE_SIZE;
        for(int i = 0; i < SAMPLE_SIZE - 1; i++) {
            double nowProgress = startProgress + i * deltaProgress;
            Vector2d nowPosition = getPosition(nowProgress);
            Vector2d nextPosition = getPosition(nowProgress + deltaProgress);
            Vector2d delta = nextPosition.sub(nowPosition);
            double deltaDistance = delta.getMagnitude();

            resultLength += deltaDistance;
        }

        return resultLength;
    }

}
