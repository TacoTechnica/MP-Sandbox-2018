
public Vector2d {

    public double x, y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitudeSquared() {
        return x*x + y*y;
    }

    public double getMagnitude() {
        return Math.sqrt( getMagnitudeSquared() );
    }

    /** add(Vector2d v)
     *
     * Adds v to this vector and returns this vector
     *
     */
    public Vector2d add(Vector2d v) {
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    /** add(Vector2d v, Vector2d result)
     *
     * Adds v to this vector without changing this vector and puts it in result
     *
     */
    public Vector2d add(Vector2d v, Vector2d result) {
        result.x = x;
        result.y = y;
        return result.add(v);
    }

    /** sub(Vector2d v)
     *
     * Subtracts v to this vector and returns this vector
     *
     */
    public Vector2d sub(Vector2d v) {
        this.x -= v.x;
        this.y -= v.y;
        return this; 
    }

    /** sub(Vector2d v, Vector2d result)
     *
     * Subtracts v to this vector without changing this vector and puts it in result
     *
     */
    public Vector2d add(Vector2d v, Vector2d result) {
        result.x = x;
        result.y = y;
        return result.sub(v);
    }

    /** mul(double c)
     *
     * Multiplies this vector by c
     *
     */
    public Vector2d mul(double c) {
        x *= c;
        y *= c;
        return this;
    }

    /** normalize()
     *
     * Normalizes this vector
     *
     */
    public Vector2d normalize() {
        double mag = getMagnitude();
        x /= mag;
        y /= mag;
        return this;
    }

    /** getAngle()
     *
     * Gets the angle of this vector from the origin
     *
     */
    public double getAngle() {
        return Math.atan2(y, x);
    }

}
