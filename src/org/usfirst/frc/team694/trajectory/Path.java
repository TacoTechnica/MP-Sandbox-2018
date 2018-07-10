package org.usfirst.frc.team694.trajectory;

import java.util.ArrayList;

/** 
 *
 * Holds a path and the splines in said path
 *
 */
public class Path {

    /** How expressive the start and end angles are.
     * Basically, how far back to start the first and last point to account for the angle.
     */
//    private static final int ANGLE_EXPRESSIVENESS = 500;

    // TODO: Stitch these together.
    private ArrayList<PathSegment> segments;

    // The total distance of the entire path
    private double totalDistance;

    /**
     * 
     * @param angleExpressiveness: How expressive should start/end angles be?
     * @param startAngle: What angle to start the path with
     * @param endAngle: What angle to end the path with
     * @param waypoints
     */
    public Path(double angleExpressiveness, double startAngle, double endAngle, Waypoint... waypoints ) {
        segments = new ArrayList<>(waypoints.length + 2); // + 2 because we need the extra start and ending waypoints (from the angle)

        // Handle the extra waypoints at the start and end of the segmented spline curve.

        Vector2d start = waypoints[0].position;
        Vector2d end = waypoints[waypoints.length - 1].position;

        Vector2d bonusAngleBeforeStart = new Vector2d(angleExpressiveness * Math.cos(startAngle),
                                                      angleExpressiveness * Math.sin(startAngle));
        Vector2d bonusAngleAfterEnd = new Vector2d(angleExpressiveness * Math.cos(endAngle),
                                                   angleExpressiveness * Math.sin(endAngle));


        Vector2d positionBeforeStart = new Vector2d();
        Vector2d positionAfterEnd = new Vector2d();
        start.sub(bonusAngleBeforeStart, positionBeforeStart);
        end.add(bonusAngleAfterEnd, positionAfterEnd);
        
//        System.out.println(positionBeforeStart + ", " + positionAfterEnd);
        
        // Construct our spline segments!
        for(int i = 0; i < waypoints.length - 1; i++) {
            Waypoint prev, now, next, nextnext;
            if (i == 0) {
                prev = new Waypoint(positionBeforeStart, waypoints[i].speed);
            } else {
                prev = waypoints[i - 1];
            }
            now = waypoints[i];
            next = waypoints[i + 1];

            if (i == waypoints.length - 2) {
                nextnext = new Waypoint(positionAfterEnd, waypoints[i + 1].speed); // i + 1 because that's the last one
            } else {
                nextnext = waypoints[i + 2];
            }

            Spline current = new CubicSpline(prev.position, now.position, next.position, nextnext.position);
            PathSegment currentSegment = new PathSegment(current, now.speed, next.speed);
            segments.add(currentSegment);

            totalDistance += currentSegment.getTotalDistance();
        }
    }

    /**
     * @param distanceFromStart: How far from the start we are
     * @return: The current spline that we're grabbing our values from AND the distance within that spline
     */
    public PathSegmentDistancePair getSegmentAtDistance(double distanceFromStart) {
        double d = distanceFromStart;

        int i;
        for(i = 0; d >= segments.get(i).getTotalDistance(); i++) {
            d -= segments.get(i).getTotalDistance();
            if (i + 1 >= segments.size()) {
                System.err.println("[Path.java] Distance sent to getSegmentAtDistance() is out of bounds! : " + distanceFromStart);
            }
        }

        return new PathSegmentDistancePair(segments.get(i), d);
    }

    /**
     * @param distanceFromStart: How far from the start we are
     * @return: The speed we're traveling at
     */
    public double getSpeedAtDistance(double distanceFromStart) {
        PathSegmentDistancePair pair = getSegmentAtDistance(distanceFromStart);
        return pair.segment.getSpeed(pair.distance);
    }

    /**
     * @param distanceFromStart: How far from the start we are
     * @return: The angular velocity we're traveling at
     */
    public double getAngularVelocityAtDistance(double distanceFromStart) {
        PathSegmentDistancePair pair = getSegmentAtDistance(distanceFromStart);
        return pair.segment.getAngularVelocity(pair.distance);
    }

    /**
     * @param distanceFromStart: How far from the start we are
     * @return: The heading / angle of the robot at that point
     */
    public double getHeadingAtDistance(double distanceFromStart) {
        PathSegmentDistancePair pair = getSegmentAtDistance(distanceFromStart);
        return pair.segment.getHeading(pair.distance);
    }

    // Only here for visual testing
    public Vector2d getPositionAtDistance(double distanceFromStart) {
        PathSegmentDistancePair pair = getSegmentAtDistance(distanceFromStart);
        return pair.segment.getPosition(pair.distance);
    }

    /**
     * @return The total distance of the path
     */
    public double getTotalDistance() {
        return totalDistance;
    }

    /**
     * Holds a path segment and a distance within that segment only.
     * @author adris
     *
     */
    private static class PathSegmentDistancePair {
        PathSegment segment;
        double distance;
        public PathSegmentDistancePair(PathSegment segment, double distance) {
            this.segment = segment;
            this.distance = distance;
        }
    }
    
//    public static void main(String[] args) {
//        Waypoint[] waypoints = new Waypoint[] {
//                new Waypoint(20, 20, 1),
//                new Waypoint(30, 30, 1),
//                new Waypoint(30, 20, 1),
//                new Waypoint(10, 10, 1)    
//             };
//             Path path = new Path(Math.PI/2, 0, waypoints);
//
//             System.out.println(path.getTotalDistance());
//             //Vector2d lastPos = path.getPositionAtDistance(0);
//
//    }

}
