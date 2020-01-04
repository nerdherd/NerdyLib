/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.experimental.Drivetrain;

// import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.controller.RamseteController;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.trajectory.Trajectory;

public class DriveRamsete extends Command {
  private RamseteController m_controller;
  private Trajectory m_trajectory;
  private Drivetrain m_drive;
  private double m_time; 
  public DriveRamsete(Drivetrain drive, Trajectory trajectory, double b, double zeta) {
    m_drive = drive;
    m_controller = new RamseteController(b, zeta);
    m_trajectory = trajectory;
   }  

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_time = 0;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_time += 0.02;
    m_drive.setChasisSpeeds(m_controller.calculate(m_drive.getPose2d(), m_trajectory.sample(m_time)), m_trajectory.sample(m_time).accelerationMetersPerSecondSq, m_trajectory.sample(m_time).accelerationMetersPerSecondSq);
    }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
