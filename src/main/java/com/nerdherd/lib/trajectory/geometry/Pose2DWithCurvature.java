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
public class Pose2DWithCurvature extends Pose2D {

    public double curvature;
    public Pose2DWithCurvature(double x, double y, double theta, double curvature) {
        super(x, y, theta);
        this.curvature = curvature;
    }

}
