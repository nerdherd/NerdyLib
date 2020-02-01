/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.oi.DefaultOI;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI extends DefaultOI{
  
  
  
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
    // SmartDashboard.putData("Reset encoder", new ResetSingleMotorEncoder(Robot.testMotor));
    // SmartDashboard.putData("Track pi", new TrackReference(Robot.testMotor, new Matrix(new double[][] {
    //   {3.14},
    //   {0}
    // })));
    // SmartDashboard.putData("Track 10pi", new TrackReference(Robot.testMotor, new Matrix(new double[][] {
    //   {31.4},
    //   {0}
    // })));
    // SmartDashboard.putData("Track 0", new TrackReference(Robot.testMotor, new Matrix(new double[][] {
    //   {0},
    //   {0}
    // })));
    // SmartDashboard.putData("Set position 40000", new SetMotorPositionPID(Robot.testMotor, 40000));
    // SmartDashboard.putData("Set position 19000", new SetMotorPositionPID(Robot.testMotor, 19000));
    // SSMotionProfile motProf1 = new SSMotionProfile(145, 145*2, 0, 314, 0, 0);
    // SmartDashboard.putData("Follow dumbe motprof thing to 314", new FollowSSMotionProfile(Robot.testMotor, motProf1));
    // SSMotionProfile motProf2 = new SSMotionProfile(145, 145*2, 314, 0, 0, 0);
    // SmartDashboard.putData("Follow dumbe motprof thing to 0", new FollowSSMotionProfile(Robot.testMotor, motProf2));
    // SmartDashboard.putData("Set 12 V", new SetMotorPower(Robot.testMotor, 1));
    // SmartDashboard.putData("Voltage ramp", new MechanismVoltageRampingWithFF(Robot.testMotor, 0.25 / 12.));
    // SmartDashboard.putData("Turn To Angle Motion Magic", new TurnToAngleMotionMagic(Robot.drive, 90., 50000*0.2, 20000 *0.2, 17688.26817));

    // ArrayList<TrajectoryPoint> traj = gen.generateTrajectory(Arrays.asList(new Pose2D(5, 5, 0).pose, new Pose2D(10, 10, 0).pose), 
    // 20, 0. , 0., 4., 7., false);
    // ArrayList<TrajectoryPoint> farRightRocket = gen.generateTrajectory(Arrays.asList(new Pose2D(5, 10, 0).pose, new Pose2D(21.5, 2, -150).pose), 
    // 10, 0. , 0., 10., 15., false);
    // ArrayList<TrajectoryPoint> farRightRocket2 = gen.generateTrajectory(Arrays.asList(new Pose2D(21.5, 2, 30).pose, new Pose2D(19, 6, 180).pose, new Pose2D(9, 2.5, 180).pose), 
    // 100, 0. , 0., 10., 15., false);
    // ArrayList<TrajectoryPoint> straightLine = gen.generateTrajectory(Arrays.asList(new Pose2D(0, 0, 0).pose, new Pose2D(50, 0, 0).pose), 
    // 1000, 0. , 0., 5., 15., false);
    // SmartDashboard.putData("far right rocketo autoo", new DriveFalconTrajectory(Robot.drive, farRightRocket, 3, true, 0.15, 0));
    // SmartDashboard.putData("far right rocketo autoo twooo", new DriveFalconTrajectory(Robot.drive, farRightRocket2, 3, false, 0.15, 0));
    // SmartDashboard.putData("Straight Line", new DriveFalconTrajectory(Robot.drive, straightLine, 3, true, 0.15, 0));
    // SmartDashboard.putData("SetVel10000", new SetMotorVelocity(Robot.yeeterTalon, 10000, 0.5*1023./17500.));
    // SmartDashboard.putData("VoltageRamp", new MotorVoltageRamping(Robot.yeeterTalon, 0.25));
    
    // Robot.m_drive.configKinematics(6.22, new Rotation2d(0), new Pose2d(0, 0, new Rotation2d(0)));
    // var m_kinematics = new DifferentialDriveKinematics(6.22);
  //   TrajectoryConfig m_config = new TrajectoryConfig(1, 1);
  //   Trajectory m_traj = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)) , 
  //   List.of(
  //     new Translation2d(0, 2.5)
  // ), new Pose2d(0, 5, new Rotation2d(0)),
  //       m_config);
  //    RamseteCommand ramsete = new RamseteCommand(m_traj, Robot.m_drive::getPose2d, new RamseteController(2.0, 0.7), 
  //                                   new SimpleMotorFeedforward(1.2, 0.241, 0.065), 
  //                                   Robot.m_drive.m_kinematics, Robot.m_drive::getCurrentSpeeds, 
  //                                   new PIDController(3.1, 0, 0), new PIDController(3.1, 0, 0),
  //                                    Robot.m_drive::setVoltage, Robot.m_drive);
    // SmartDashboard.putData("Reset Gyro", new ResetGyro(Robot.m_drive));
    // SmartDashboard.putData("ResetXY", new InstantCommand(() -> Robot.m_drive.resetXY()));
    // SmartDashboard.putData("Reset Encoders", new ResetDriveEncoders(Robot.m_drive));  
    // SmartDashboard.putData("Ramsete3", new Ramsete3BallShoot(Robot.m_drive));
    // SmartDashboard.putData("Ramsete5", new Ramsete5Ball(Robot.m_drive));
    
  }
}