/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import java.util.ArrayList;
import java.util.Arrays;

import com.nerdherd.lib.drivetrain.auto.DriveDistanceMotionMagic;
import com.nerdherd.lib.drivetrain.auto.DriveFalconTrajectory;
import com.nerdherd.lib.drivetrain.auto.ResetDriveEncoders;
import com.nerdherd.lib.drivetrain.auto.ResetGyro;
import com.nerdherd.lib.drivetrain.characterization.DriveCharacterizationTest;
import com.nerdherd.lib.drivetrain.characterization.OpenLoopDrive;
import com.nerdherd.lib.drivetrain.characterization.VelocityTest;
import com.nerdherd.lib.drivetrain.trajectory.falconlib.Pose2D;
import com.nerdherd.lib.drivetrain.trajectory.falconlib.TrajectoryGen;
import com.nerdherd.lib.drivetrain.trajectory.falconlib.TrajectoryPoint;
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
    SmartDashboard.putData("Test Drive", new OpenLoopDrive(Robot.drive, 0.5));
    SmartDashboard.putData("Voltage Ramp", new DriveCharacterizationTest(Robot.drive, 0.25));
    // SmartDashboard.putData("Voltage ramp elevator", new MotorVoltageRamping(Robot.elevator, 0.25 / 12.0));
    // SmartDashboard.putData("Reset elevator encoder", new ResetSingleMotorEncoder(Robot.elevator));
    SmartDashboard.putData("Reset Encoders", new ResetDriveEncoders(Robot.drive));
    SmartDashboard.putData("Reset Gyro", new ResetGyro(Robot.drive));
    SmartDashboard.putData("Drive Motion Magic", new DriveDistanceMotionMagic(Robot.drive, 50000, 10000, 30000));
    SmartDashboard.putData("Velocity Test", new VelocityTest(Robot.drive, 15000, 5));

    TrajectoryGen gen = new TrajectoryGen();
    ArrayList<TrajectoryPoint> traj = gen.generateTrajectory(Arrays.asList(new Pose2D(0, 0, 0).pose, new Pose2D(5,5, 0).pose), 
    4.5, 0. , 0., 4., 4., false);
    SmartDashboard.putData("drive falcon traj", new DriveFalconTrajectory(Robot.drive, traj, 3, true, 0.3, 0));
  }
}
