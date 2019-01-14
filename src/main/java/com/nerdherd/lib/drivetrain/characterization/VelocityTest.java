/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.characterization;


import com.nerdherd.lib.drivetrain.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class VelocityTest extends Command {

  private double m_desiredVel, m_time, m_startTime;
  private double m_desiredTime;
  private AbstractDrivetrain m_drive;

  public VelocityTest(AbstractDrivetrain drive, double desired_vel, double desired_time) {
    m_desiredVel = desired_vel;
    m_desiredTime = desired_time;
    m_drive = drive;
    requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_startTime = Timer.getFPGATimestamp();

    // m_drive.startVelocityController();
    // m_drive.setTargetVelocities(m_desiredVel, m_desiredVel);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_drive.addDesiredVelocities(m_desiredVel, m_desiredVel);
    m_drive.setVelocity(m_desiredVel, m_desiredVel);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
      return m_time > m_desiredTime;
      // return false;
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
    end();
  }
}
