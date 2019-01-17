/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.nerdherd.lib.drivers.NerdyTalon;
import com.nerdherd.lib.drivetrain.Drivetrain;
import com.nerdherd.lib.drivetrain.teleop.ArcadeDrive;
import com.nerdherd.lib.motor.SingleMotorTalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Scheduler;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  
  public static Drivetrain drive;
  // public static SingleMotorTalonSRX climberWheelLeft, climberWheelRight;
  public static OI oi;
  @Override
  public void robotInit() {
    drive = new Drivetrain(RobotMap.kLeftMasterTalonID, RobotMap.kRightMasterTalonID, 
    new NerdyTalon[]{new NerdyTalon(RobotMap.kLeftSlaveTalonID)}, 
    new NerdyTalon[]{new NerdyTalon(RobotMap.kRightSlaveTalonID)}, 
    false, true);
    drive.configMaxVelocity(3000);
    drive.configSensorPhase(true, true);
    drive.configStaticFeedforward(0.1, 0.1);
    drive.configTicksPerFoot(2600, 2600);
    drive.configDate("2019_1_15_");

    // climberWheelLeft = new SingleMotorTalonSRX(RobotMap.kClimberWheelLeftID, "Climber Wheel Left");
    // climberWheelLeft.configSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    // climberWheelLeft.configPIDF(0, 0, 0, 0);
    // climberWheelRight = new SingleMotorTalonSRX(RobotMap.kClimberWheelRightID, "Climber Wheel Right");
    // climberWheelRight.configSensor(FeedbackDevice.CTRE_MagEncoder_Absolute);
    // climberWheelRight.configPIDF(0, 0, 0, 0);
  
    oi = new OI();

    drive.configDefaultCommand(new ArcadeDrive(drive, oi));
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    drive.reportToSmartDashboard();
    // climberWheelLeft.reportToSmartDashboard();
    // climberWheelRight.reportToSmartDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
    drive.stopLog();
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)

  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
    drive.startLog();

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    drive.logToCSV();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
