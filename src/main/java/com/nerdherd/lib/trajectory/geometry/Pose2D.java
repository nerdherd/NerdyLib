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
public class Pose2D {

    public double x, y, theta, thetaDegrees;

    public Pose2D(double x, double y, double theta) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.thetaDegrees = Math.toDegrees(theta);
    }
}
