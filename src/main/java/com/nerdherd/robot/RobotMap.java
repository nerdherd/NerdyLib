/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  
  public static final int kLeftMasterTalonID = 1;
  public static final int kRightMasterTalonID = 4;
  public static final int kLeftSlaveTalonID = 3;
  public static final int kRightSlaveTalonID = 5;

  public static final int kLeftSlaveTalon2ID = 2;
  public static final int kRightSlaveTalon2ID = 6;

}
