/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.trajectory.falconlib;

import org.ghrobotics.lib.mathematics.twodim.geometry.Pose2d;
import org.ghrobotics.lib.mathematics.units.LengthKt;
import org.ghrobotics.lib.mathematics.units.Rotation2dKt;

/**
 * Add your docs here.
 */
public class Pose2D {

    public Pose2d pose;
    public Pose2D(double x, double y, double theta) {
        pose = new Pose2d(LengthKt.getFeet(x), LengthKt.getFeet(y), Rotation2dKt.getDegree(theta));
    }
}
