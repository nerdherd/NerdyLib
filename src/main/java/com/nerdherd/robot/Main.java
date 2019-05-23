/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.trajectory.geometry.SimpleArc2D;

/**
 * Do NOT add any static variables to this class, or any initialization at all.
 * Unless you know what you are doing, do not modify this file except to
 * change the parameter class to the startRobot call.
 */
public final class Main {
  private Main() {
    
  }

  /**
   * Main initialization function. Do not perform any initialization here.
   *
   * <p>If you change your main robot class, change the parameter type.
   */
  public static void main(String... args) {
    // RobotBase.startRobot(Robot::new);
    // QuinticHermiteSpline spline = new QuinticHermiteSpline(new Pose2D(0, 0, 0), new Pose2D(10, 5, 0));
    // spline.arcParameterize(0, 0.01, 0.005);
    // double len = 0;
    // System.out.println(spline.arcParameterizedSpline.size());
    // for (Arc2D arc : spline.arcParameterizedSpline) {
    //   //len += arc.length;
    //   System.out.println(arc.length);
    // }
    // System.out.println(len);

    SimpleArc2D arc = new SimpleArc2D(1, Math.PI, Math.PI);
    SimpleArc2D arc2 = arc.split(Math.PI/2);
    System.out.println(arc2.)
   }
}
