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
    }

    public boolean fitsConstraints(double maxDeltaCurvature, double  maxLength) {
        return deltaCurvature3 <= maxDeltaCurvature && length <= maxLength;
    }

    public SimpleArc2D getSimpleArc() {
        return new SimpleArc2D(averageCurvature, length, deltaTheta);
    }

}
