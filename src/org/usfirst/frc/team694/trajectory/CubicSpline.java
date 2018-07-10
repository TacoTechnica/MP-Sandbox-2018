package org.usfirst.frc.team694.trajectory;

public class CubicSpline extends Spline {

    // Remember, the actual spline starts at p1 and ends at p2.
    public CubicSpline(Vector2d p0, Vector2d p1, Vector2d p2, Vector2d p3) {
        super (
                new HermiteFunction(p0.x, p1.x, p2.x, p3.x),
                new HermiteFunction(p0.y, p1.y, p2.y, p3.y)
            );
    }
}
