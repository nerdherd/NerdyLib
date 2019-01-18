/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.trajectory.falconlib;

/**
 * Add your docs here.
 */
public class TrajectoryPoint {

    double x, y, theta, acceleration, velocity;
    
    public TrajectoryPoint(double x, double y, double theta, double acceleration, double velocity) {
        this.x = x;
        this.y = y;
        this.theta = theta;
        this.acceleration = acceleration;
        this.velocity = velocity;
    }
}
