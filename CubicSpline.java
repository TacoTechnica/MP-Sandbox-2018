
public class CubicSpline extends Spline {

    // Remember, the actual spline starts at p1 and ends at p2.
    public CubicSpline(Vector2f p0, Vector2f p1, Vector2f p2, Vector2f p3) {
        super (
                new HermiteFunction(p0.x, p1.x, p2.x, p3.x),
                new HermiteFunction(p0.y, p1.y, p2.y, p3.y)
            );
    }
}
