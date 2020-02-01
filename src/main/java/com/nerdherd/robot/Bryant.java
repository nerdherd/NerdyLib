/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.robot;

import java.util.List;

import com.nerdherd.lib.drivetrain.auto.DriveStraightContinuous;
import com.nerdherd.lib.drivetrain.experimental.Drivetrain;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.geometry.Translation2d;
import edu.wpi.first.wpilibj.trajectory.Trajectory;
import edu.wpi.first.wpilibj.trajectory.TrajectoryConfig;
import edu.wpi.first.wpilibj.trajectory.TrajectoryGenerator;
import edu.wpi.first.wpilibj.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class Bryant extends SequentialCommandGroup {
  /**
   * Creates a new Ramsete5Ball.
   */
  private Drivetrain m_drive;
  public Bryant(Drivetrain drive) {
    m_drive = drive;
    var autoVoltageConstraint =
    new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(1.2, 0.241, 0.065),
        m_drive.m_kinematics,
        10);

    TrajectoryConfig m_config = new TrajectoryConfig(3.2, 2.0);
    m_config.addConstraint(autoVoltageConstraint);
    
    Trajectory m_traj = TrajectoryGenerator.generateTrajectory(new Pose2d(3.048, -2.404, new Rotation2d(Math.PI)),
    List.of(), new Pose2d(2.2, -0.705, new Rotation2d(0)),
        m_config);
     RamseteCommand ramsete = new RamseteCommand(m_traj, m_drive::getPose2d, new RamseteController(3.6, 0.7), 
                                    new SimpleMotorFeedforward(1.2, 0.241, 0.065), 
                                    m_drive.m_kinematics, m_drive::getCurrentSpeeds, 
                                    new PIDController(3.1, 0, 0), new PIDController(3.1, 0, 0),
                                     m_drive::setVoltage, m_drive); 
      

      
      Trajectory m_traj3 = TrajectoryGenerator.generateTrajectory(new Pose2d(2.2, -0.705, new Rotation2d(0)),
      List.of(new Translation2d(9.624, -0.705)), new Pose2d(6.401, -2.404, new Rotation2d(Math.PI)),
      m_config);
      RamseteCommand ramsete3 = new RamseteCommand(m_traj3, m_drive::getPose2d, new RamseteController(0.420, 0.7), 
      new SimpleMotorFeedforward(1.2, 0.241, 0.065), 
      m_drive.m_kinematics, m_drive::getCurrentSpeeds, 
      new PIDController(3.1, 0, 0), new PIDController(3.1, 0, 0),
      m_drive::setVoltage, m_drive);   
      
      addCommands(ramsete, ramsete3, new DriveStraightContinuous(m_drive, 0, 0));
 
  }
}
