package com.szqd.framework.util;

/**
 * Created by like on 11/26/15.
 */
public class BezierCurveUtil {

    private static final double CUBIC_BEZIER_COEFFICIENT = 0.55191502449;

//    public static final List<Point> getCirclePointsForCubicBezier(Point startPoint,double radius){
//
//        double p0_x = startPoint.getX();
//        double p0_y = startPoint.getY();
//
//        double p1_x = p0_x + radius * CUBIC_BEZIER_COEFFICIENT;
//        double p1_y = p0_y;
//
//        double p2_x = p0_x + radius;
//        double p2_y = p0_y + (radius -
//
//        List<Point> points = new ArrayList<>();
//        points.add(startPoint);
//    }

}

class Point{
    private double x = 0;
    private double y = 0;

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
