/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import java.util.Arrays;

import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.logging.SubscribedLoggable;
import com.nerdherd.lib.misc.AutoChooser;
import com.nerdherd.lib.motor.motorcontrollers.NerdySparkMax;
import com.nerdherd.lib.motor.motorcontrollers.NerdyTalon;
import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.RamseteCommand;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  public static AutoChooser chooser;
  public static SubscribedLoggable tester;
  // public static SingleMotorTalonSRX yeeterTalon;
  public static Drivetrain m_drive;
  public static Command m_autonomousCommand;

  public static OI oi;

  public Robot() {
    super(0.02);
  }

  @Override
  public void robotInit() {
    chooser = new AutoChooser();
    yeeterTalon = new SingleMotorTalonSRX(new NerdyTalon(5), "flywheel", false, false);
    yeeterTalon.configFollowersTalons(new NerdyTalon[] {new NerdyTalon(2)});
    yeeterTalon.configDeadband(0.004);
    //Tuning for the big heavy flywheel
    yeeterTalon.configPIDF(0, 0, 0, 0);
    //old P 0.175
    oi = new OI();
    NerdyBadlog.initAndLog("/media/sda1/logs/", "testing", 0.02, yeeterTalon);
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
    yeeterTalon.reportToSmartDashboard();
    SmartDashboard.putNumber("RPM", ((yeeterTalon.getVelocity()*60)/409.6));
  }

  /**
   * This function is called once each time the robot enters Disabled mode. You
   * can use it to reset any subsystem information you want to clear when the
   * robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    CommandScheduler.getInstance().run();
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
//     var autoVoltageConstraint =
//     new DifferentialDriveVoltageConstraint(
//         new SimpleMotorFeedforward(DriveConstants.ksVolts,
//                                    DriveConstants.kvVoltSecondsPerMeter,
//                                    DriveConstants.kaVoltSecondsSquaredPerMeter),
//         DriveConstants.kDriveKinematics,
//         10);
    
    // final TrajectoryConfig m_config = new TrajectoryConfig(3, 3)
    // .setKinematics(DriveConstants.kDriveKinematics)
//             // Apply the voltage constraint
//             .addConstraint(autoVoltageConstraint);
// ;


     TrajectoryConfig m_config = new TrajectoryConfig(3, 3);
     Trajectory m_traj = TrajectoryGenerator.generateTrajectory(new Pose2d(0, 0, new Rotation2d(0)) , 
    List.of(
      new Translation2d(0, 2.5)
  ), new Pose2d(0, 5, new Rotation2d(0)),
        m_config);
     RamseteCommand ramsete = new RamseteCommand(m_traj, () -> m_drive.getPose2d(), new RamseteController(3.0, 0.7), 
                                    new SimpleMotorFeedforward(1.2, 0.241, 0.065), 
                                    m_drive.m_kinematics, () -> m_drive.getCurrentSpeeds(), 
                                    new PIDController(3.1, 0, 0), new PIDController(3.1, 0, 0),
                                     m_drive::setVoltage, m_drive);
    m_autonomousCommand =  ramsete.andThen(() -> m_drive.setVoltage(0, 0));

    // m_autonomousCommand =  new DriveStraightContinuous(m_drive, 10000, 0.3);
    if (m_autonomousCommand != null) { 
      m_autonomousCommand.schedule();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    CommandScheduler.getInstance().run();
    if (m_autonomousCommand.isScheduled()) {
      SmartDashboard.putBoolean("Auto Command Running", true);
    }
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
