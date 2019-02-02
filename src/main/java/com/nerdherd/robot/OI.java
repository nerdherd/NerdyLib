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
import com.nerdherd.lib.drivetrain.auto.TurnToAngleMotionMagic;
import com.nerdherd.lib.drivetrain.characterization.DriveCharacterizationTest;
import com.nerdherd.lib.drivetrain.characterization.OpenLoopDrive;
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
    // trigger_1 = new JoystickButton(super.driveJoyLeft, 1);
    // trigger_1.whenPressed(new OpenLoopDrive(Robot.drive, 0.2));
    // button_2 = new JoystickButton(super.driveJoyLeft, 2);
    // button_2.whenPressed(new OpenLoopDrive(Robot.drive, 0));
    // SmartDashboard.putData("Set Climber Wheel Position", new SetMotorPositionPID(Robot.climberWheelLeft, 1024));
    // SmartDashboard.putData("Retract Climber Wheel", new SetMotorPower(Robot.climberWheelLeft, -0.1));
    // SmartDashboard.putData("YEET OFF STAIRS ", new OpenLoopDrive(Robot.drive, 0.75));
    // SmartDashboard.putData("Voltage Ramp", new DriveCharacterizationTest(Robot.drive, 0.25));
    // SmartDashboard.putData("Voltage ramp elevator", new MotorVoltageRamping(Robot.elevator, 0.25 / 12.0));
    // SmartDashboard.putData("Reset elevator encoder", new ResetSingleMotorEncoder(Robot.elevator));
    // SmartDashboard.putData("Reset Encoders", new ResetDriveEncoders(Robot.drive));
    // SmartDashboard.putData("Reset Gyro", new ResetGyro(Robot.drive));
    // SmartDashboard.putData("Drive Motion Magic", new DriveDistanceMotionMagic(Robot.drive, 50000, 20000, 15000));
    // SmartDashboard.putData("Velocity Test", new VelocityTest(Robot.drive, 15000, 5));
    // SmartDashboard.putData("Velocity Test FPS", new VelocityTestFPS(Robot.drive, 10, 5));
    // SmartDashboard.putData("Turn To Angle Motion Magic", new TurnToAngleMotionMagic(Robot.drive, 90., 50000*0.2, 20000 *0.2, 17688.26817));











    // TrajectoryGen gen = new TrajectoryGen();
    // // ArrayList<TrajectoryPoint> traj = gen.generateTrajectory(Arrays.asList(new Pose2D(5, 5, 0).pose, new Pose2D(10, 10, 0).pose), 
    // // 20, 0. , 0., 4., 7., false);
    // ArrayList<TrajectoryPoint> farRightRocket = gen.generateTrajectory(Arrays.asList(new Pose2D(5, 10, 0).pose, new Pose2D(21.5, 2, -150).pose), 
    // 10, 0. , 0., 10., 15., false);
    // ArrayList<TrajectoryPoint> farRightRocket2 = gen.generateTrajectory(Arrays.asList(new Pose2D(21.5, 2, 30).pose, new Pose2D(19, 6, 180).pose, new Pose2D(9, 2.5, 180).pose), 
    // 100, 0. , 0., 10., 15., false);
    // // ArrayList<TrajectoryPoint> straightLine = gen.generateTrajectory(Arrays.asList(new Pose2D(0, 0, 0).pose, new Pose2D(50, 0, 0).pose), 
    // // 1000, 0. , 0., 5., 15., false);
    // SmartDashboard.putData("far right rocketo autoo", new DriveFalconTrajectory(Robot.drive, farRightRocket, 3, true, 0.15, 0));
    // SmartDashboard.putData("far right rocketo autoo twooo", new DriveFalconTrajectory(Robot.drive, farRightRocket2, 3, false, 0.15, 0));
    // // SmartDashboard.putData("Straight Line", new DriveFalconTrajectory(Robot.drive, straightLine, 3, true, 0.15, 0));
  }
}