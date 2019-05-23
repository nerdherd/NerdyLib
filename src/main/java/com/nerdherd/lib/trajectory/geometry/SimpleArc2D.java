/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.trajectory.geometry;

/**
 * Add your docs here.
 */
public class SimpleArc2D {

    public double curvature, length, deltaTheta;
    public SimpleArc2D (double curvature, double length, double deltaTheta) {
        this.curvature = curvature;
        this.length = length;
        this.deltaTheta = deltaTheta;
    }

    public SimpleArc2D split(double length) {
        return new SimpleArc2D(curvature, length - length, length * curvature);
    }
}

