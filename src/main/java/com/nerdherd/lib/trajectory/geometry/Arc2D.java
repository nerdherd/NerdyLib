/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.trajectory.geometry;

import com.nerdherd.lib.misc.NerdyMath;

/**
 * Add your docs here.
 * A 2 dimensional "arc" with non constant curvature, made from 3 points along a spline and the curvature at each point
 */
public class Arc2D {

    public Pose2DWithCurvature start, mid, end;
    public Point2D center;
    public double deltaTheta, length, deltaCurvature1, deltaCurvature2, deltaCurvature3, averageCurvature;
    public Arc2D(Pose2DWithCurvature start, Pose2DWithCurvature mid, Pose2DWithCurvature end) {
        this.start = start;
        this.end = end;
        this.mid = mid;
        averageCurvature = (start.curvature + end.curvature + mid.curvature)/3;
        deltaTheta = start.theta - end.theta;
        deltaCurvature1 = start.curvature - mid.curvature;
        deltaCurvature2 = mid.curvature - end.curvature;
        deltaCurvature3 = start.curvature - end.curvature;
        // use distance formula for length if it's a straight line (curvature = 0)
        if (averageCurvature == 0) {
            length = NerdyMath.distanceFormula(start.x, start.y, end.x, end.y);
        } else {
        length = Math.abs(deltaTheta/averageCurvature);
        }
        calculateCenter();
    }

    public boolean fitsConstraints(double maxDeltaCurvature, double  maxLength) {
        return deltaCurvature3 <= maxDeltaCurvature && length <= maxLength;
    }

    public SimpleArc2D getSimpleArc() {
        return new SimpleArc2D(averageCurvature, length, deltaTheta);
    }

    public void calculateCenter() {
        double centerX1 = 0;
        double centerY1 = 0;
        double centerX2 = 1;
        double centerY2 = 1;
        double i = -180;
        while (!(NerdyMath.errorTolerance(centerX1 - centerX2, 0.000001) && NerdyMath.errorTolerance(centerY1 - centerY2, 0.000001))) {
            centerX1 = start.x + (1 / averageCurvature) * Math.cos(Math.toRadians(start.thetaDegrees + i));
            centerY1 = start.y + (1 / averageCurvature) * Math.sin(Math.toRadians(start.thetaDegrees + i));
            centerX2 = end.x + (1 / averageCurvature) * Math.cos(Math.toRadians(end.thetaDegrees + i));
            centerY2 = end.y + (1 / averageCurvature) * Math.sin(Math.toRadians(end.thetaDegrees + i));
            i += 90;
        }
        center = new Point2D(centerX1, centerY1);
    }

    public Pose2DWithCurvature advanceAlongArc(Pose2D startPos, double angle) {
        double translatedStartX = startPos.x - center.x;
        double translatedStartY = startPos.y - center.y;
        double x = translatedStartX * Math.cos(angle) - translatedStartY * Math.sin(angle) + center.x;
        double y = translatedStartX * Math.sin(angle) + translatedStartY * Math.cos(angle) + center.y;
        return new Pose2DWithCurvature(x, y, startPos.theta + angle, averageCurvature);
    }

    public Pose2DWithCurvature advanceByDistance(Pose2D startPos, double dist) {
        return advanceAlongArc(startPos, dist * averageCurvature);
    }
}
