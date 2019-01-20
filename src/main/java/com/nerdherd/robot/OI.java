/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.drivetrain.auto.DriveDistanceMotionMagic;
import com.nerdherd.lib.drivetrain.auto.ResetDriveEncoders;
import com.nerdherd.lib.drivetrain.auto.ResetGyro;
import com.nerdherd.lib.drivetrain.characterization.DriveCharacterizationTest;
import com.nerdherd.lib.drivetrain.characterization.OpenLoopDrive;
import com.nerdherd.lib.oi.DefaultOI;

import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends DefaultOI{
  
  
  JoystickButton trigger_1, button_2;
  
  public OI() {
    super();
    trigger_1 = new JoystickButton(super.driveJoyLeft, 1);
    trigger_1.whenPressed(new OpenLoopDrive(Robot.drive, 0.2));
    button_2 = new JoystickButton(super.driveJoyLeft, 2);
    button_2.whenPressed(new OpenLoopDrive(Robot.drive, 0));
    // SmartDashboard.putData("Set Climber Wheel Position", new SetMotorPositionPID(Robot.climberWheelLeft, 1024));
    // SmartDashboard.putData("Retract Climber Wheel", new SetMotorPower(Robot.climberWheelLeft, -0.1));
    SmartDashboard.putData("Test Drive", new OpenLoopDrive(Robot.drive, 0.2));
    SmartDashboard.putData("Reset Encoders", new ResetDriveEncoders(Robot.drive));
    SmartDashboard.putData("Reset Gyro", new ResetGyro(Robot.drive));
    SmartDashboard.putData("Drive Characterization Test", new DriveCharacterizationTest(Robot.drive, 0.75));
    SmartDashboard.putData("Motion Magic Test", new DriveDistanceMotionMagic(Robot.drive, 25000, 3000, 6000));

  }
}
