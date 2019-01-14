/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.AbstractDrivetrain;
import com.nerdherd.lib.drivetrain.TrajectoryFollower;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import jaci.pathfinder.Trajectory;

public class DriveTrajectory extends Command {
  
  private TrajectoryFollower m_controller;
  private double m_leftVelocity, m_rightVelocity, m_startTime, m_time, m_lastTime;
  private AbstractDrivetrain m_drive;

  public DriveTrajectory(AbstractDrivetrain drive, Trajectory traj, int lookahead, Boolean goingForwards, double kP, double kD) {
    m_drive = drive;
    m_controller = new TrajectoryFollower(traj, lookahead, goingForwards, kP, kD);
    requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_startTime = Timer.getFPGATimestamp();
    m_lastTime = Timer.getFPGATimestamp();
    m_time = Timer.getFPGATimestamp() - m_startTime;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_controller.calculate(m_drive.getXpos(), m_drive.getYpos(), m_drive.getRawYaw(), m_time - m_lastTime);
    m_drive.setVelocityFPS(m_controller.getLeftVelocity(), m_controller.getRightVelocity());
    m_lastTime = m_time;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_controller.isFinished();
  }

  

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_drive.setPowerZero();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
