/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.auto;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.drivetrain.trajectory.pathfinder.TrajectoryFollower;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import jaci.pathfinder.Trajectory;

/**
 * Drive a Pathfinder trajectory using a proportional heading follower
 */
public class DriveTrajectory extends CommandBase {
  
  private TrajectoryFollower m_controller;
  private double  m_startTime, m_time, m_lastTime;
  private AbstractDrivetrain m_drive;

  public DriveTrajectory(AbstractDrivetrain drive, Trajectory traj, int lookahead, Boolean goingForwards, double kP, double kD) {
    m_drive = drive;
    m_controller = new TrajectoryFollower(traj, lookahead, goingForwards, kP, kD);
    addRequirements(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_startTime = Timer.getFPGATimestamp();
    m_lastTime = Timer.getFPGATimestamp();
    m_time = Timer.getFPGATimestamp() - m_startTime;
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_controller.calculate(m_drive.getXpos(), m_drive.getYpos(), m_drive.getRawYaw(), m_time - m_lastTime);
    m_drive.setVelocityFPS(m_controller.getLeftVelocity(), m_controller.getRightVelocity());
    m_lastTime = m_time;
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_controller.isFinished();
  }

  

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drive.setPowerZero();
  }

   
}
