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
public class TrajectoryPoint2D extends Pose2DWithCurvature {

    public double acceleration, velocity;
    public TrajectoryPoint2D(double x, double y, double theta, double curvature, double acceleration, double velocity) {
        super(x, y, theta, curvature);
        this.acceleration = acceleration;
        this.velocity = velocity;
    }

    public TrajectoryPoint2D(Pose2DWithCurvature pose, double acceleration, double velocity) {
        super(pose.x, pose.y, pose.theta, pose.curvature);
        this.acceleration = acceleration;
        this.velocity = velocity;
    }
}
