/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import com.nerdherd.lib.sensor.RevColorSensorV3;

import edu.wpi.first.wpilibj.AddressableLED;
import edu.wpi.first.wpilibj.AddressableLEDBuffer;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */

public class Robot extends TimedRobot {


  // public static AddressableLED m_led;
  // public static AddressableLEDBuffer m_ledBuffer;
  public static RevColorSensorV3 colorSensor;

  
  public Robot() {
    super(0.02);
  }

  @Override
  public void robotInit() {
    colorSensor = new RevColorSensorV3(I2C.Port.kOnboard, "ColorSensor");
    colorSensor.addColor(0, 0, 0);

    // m_led = new AddressableLED(2);
    // m_ledBuffer = new AddressableLEDBuffer(10);
    // m_led.setLength(m_ledBuffer.getLength());
    // m_led.setData(m_ledBuffer);
    // m_led.start();
    
    
    m_drive.resetEncoders();
    m_drive.resetYaw();
        
    m_drive.configTicksPerFoot(25292.8, 25292.8);
    m_drive.configSensorPhase(false, false);
    m_drive.configAutoChooser(chooser);
    m_drive.configKinematics(0.63742712872013762571, new Rotation2d(0), new Pose2d(0,0, new Rotation2d(0)));
    oi = new OI();
    // NerdyBadlog.initAndLog("/media/sda1/logs/", "ramseteTuning", 0.02, m_drive);
    
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for
   * items like diagnostics that you want ran during disabled, autonomous,
   * teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    colorSensor.reportToSmartDashboard();
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  /*  for (var i = 0; i < 2; i++) {
      m_ledBuffer.setRGB(i, 101, 255, 41);
      SmartDashboard.putNumber("led", i);
  }
  m_led.setData(m_ledBuffer);*/
}

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
    // for (var i = 0; i < m_ledBuffer.getLength(); i++) {
      // Sets the specified LED to the RGB values for red
      // m_ledBuffer.setRGB(i, 255, 0, 0);
   
   
  //  m_led.setData(m_ledBuffer);
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable chooser
   * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
   * remove all of the chooser code and uncomment the getString code to get the
   * auto name from the text box below the Gyro
   *
   * <p>
   * You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons to
   * the switch structure below with additional strings & commands.
   */




  @Override
  public void autonomousInit() {
    
  //    TrajectoryConfig m_config = new TrajectoryConfig(3, 3);
  //    Trajectory m_traj = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)) , 
  //   List.of(
  //     new Translation2d(0, 2.5)
  // ), new Pose2d(0, 5, new Rotation2d(0)),
  //       m_config);
  //    RamseteCommand ramsete = new RamseteCommand(m_traj, m_drive::getPose2d, new RamseteController(3.0, 0.7), 
  //                                   new SimpleMotorFeedforward(1.2, 0.241, 0.065), 
  //                                   m_drive.m_kinematics, m_drive::getCurrentSpeeds, 
  //                                   new PIDController(3.1, 0, 0), new PIDController(3.1, 0, 0),
  //                                    m_drive::setVoltage, m_drive);
    // m_autonomousCommand =  new DriveStraightContinuous(m_drive, 10000, 0.8);
    // // m_autonomousCommand =  ramsete.andThen(() -> m_drive.setVoltage(0, 0));
    // // m_autonomousCommand =  null;
    // if (m_autonomousCommand != null) { 
    //   m_autonomousCommand.schedule();
    // }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {

  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
CommandScheduler.getInstance().run();
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
