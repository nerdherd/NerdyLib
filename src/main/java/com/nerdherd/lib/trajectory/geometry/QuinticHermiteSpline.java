/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.trajectory.geometry;

import java.util.ArrayList;

import com.nerdherd.lib.misc.NerdyMath;

/**
 * Derivations provided by FRC 254
 */
public class QuinticHermiteSpline {

    private double ax, bx, cx, dx, ex, fx, ay, by, cy, dy, ey, fy;
    private double x0, x1, dx0, dx1, ddx0, ddx1, y0, y1, dy0, dy1, ddy0, ddy1;
    private Pose2D start, end;
    public ArrayList<Arc2D> arcParameterizedSpline;

    public QuinticHermiteSpline(Pose2D start, Pose2D end) {
        double scale = NerdyMath.distanceFormula(start.x, start.y, end.x, end.y) * 1.2;
        x0 = start.x;
        x1 = end.x;
        dx0 = Math.cos(start.theta) * scale;
        dx1 = Math.cos(end.theta) * scale;
        ddx0 = 0;
        ddx1 = 0;

        y0 = start.y;
        y1 = end.y;
        dy0 = Math.sin(start.theta) * scale;
        dy1 = Math.sin(end.theta) * scale;
        ddy0 = 0;
        ddy1 = 0;

        ax = -6 * x0 - 3 * dx0 - 0.5 * ddx0 + 0.5 * ddx1 - 3 * dx1 + 6 * x1;
        bx = 15 * x0 + 8 * dx0 + 1.5 * ddx0 - ddx1 + 7 * dx1 - 15 * x1;
        cx = -10 * x0 - 6 * dx0 - 1.5 * ddx0 + 0.5 * ddx1 - 4 * dx1 + 10 * x1;
        dx = 0.5 * ddx0;
        ex = dx0;
        fx = x0;

        ay = -6 * y0 - 3 * dy0 - 0.5 * ddy0 + 0.5 * ddy1 - 3 * dy1 + 6 * y1;
        by = 15 * y0 + 8 * dy0 + 1.5 * ddy0 - ddy1 + 7 * dy1 - 15 * y1;
        cy = -10 * y0 - 6 * dy0 - 1.5 * ddy0 + 0.5 * ddy1 - 4 * dy1 + 10 * y1;
        dy = 0.5 * ddy0;
        ey = dy0;
        fy = y0;

        arcParameterizedSpline = new ArrayList<>();
    }

    public Pose2D getPose(double t) {
        double x = ax * t * t * t * t * t + bx * t * t * t * t + cx * t * t * t + dx * t * t + ex * t + fx;
        double y = ay * t * t * t * t * t + by * t * t * t * t + cy * t * t * t + dy * t * t + ey * t + fy;
        double theta = Math.atan2(dy(t), dx(t));
        return new Pose2D(x, y, theta);
    }
    private double dx(double t) {
        return 5 * ax * t * t * t * t + 4 * bx * t * t * t + 3 * cx * t * t + 2 * dx * t + ex;
    }

    private double dy(double t) {
        return 5 * ay * t * t * t * t + 4 * by * t * t * t + 3 * cy * t * t + 2 * dy * t + ey;
    }

    private double ddx(double t) {
        return 20 * ax * t * t * t + 12 * bx * t * t + 6 * cx * t + 2 * dx;
    }

    private double ddy(double t) {
        return 20 * ay * t * t * t + 12 * by * t * t + 6 * cy * t + 2 * dy;
    }

    private double dddx(double t) {
        return 60 * ax * t * t + 24 * bx * t + 6 * cx;
    }

    private double dddy(double t) {
        return 60 * ay * t * t + 24 * by * t + 6 * cy;
    }

    public double getCurvature(double t) {
        return (dx(t) * ddy(t) - ddx(t) * dy(t)) / ((dx(t) * dx(t) + dy(t) * dy(t)) * Math.sqrt((dx(t) * dx(t) + dy
                (t) * dy(t))));
    }

    public Pose2DWithCurvature getPose2DWithCurvature(double t) {
        Pose2D pose = getPose(t);
        return new Pose2DWithCurvature(pose.x, pose.y, pose.theta, getCurvature(t));
    }

    public void arcParameterize(double start, double end, double dt) {
        // Pose2DWithCurvature p0 = getPose2DWithCurvature(start);
        // Pose2DWithCurvature p1 = getPose2DWithCurvature((start + end)/2);
        // Pose2DWithCurvature p2 = getPose2DWithCurvature(end);
        Arc2D arc = new Arc2D(getPose2DWithCurvature(start), getPose2DWithCurvature((start + end)/2), getPose2DWithCurvature(end));
        // values in feet, ripped from pathfinder 2
        if (arc.fitsConstraints(0.3281, .3281)) {
            arcParameterizedSpline.add(arc);
            if (end + dt > 1){
                if (end != 1) {
                    arcParameterize(end, 1, dt);
                }
            } else {
                arcParameterize(end, end + dt, dt);
            }
        } else {
            arcParameterize(start, (start + end)/2, dt);
        }
    }

    
    
}
