package org.usfirst.frc.team694.trajectory;


/** HermiteFunction
 *
 * Interpolates between 4 y values.
 *
 * A neat explanation can be found here: https://www.paulinternet.nl/?page=bicubic
 * Desmos test here: https://www.desmos.com/calculator/sfpr5ponhe
 *
 */
public class HermiteFunction extends CubicFunction {

    // We form a curve of 4 y values.
    // The actual curve is interpolated between y1 and y2.
    //private double y0, y1, y2, y3;

    public HermiteFunction(double y0, double y1, double y2, double y3) {
        super(
            -0.5*y0 + 1.5*y1 - 1.5*y2 + 0.5*y3,
            y0 - 2.5*y1 + 2*y2 - 0.5*y3,
            -0.5*y0 + 0.5*y2,
            y1
        );
    }
}
