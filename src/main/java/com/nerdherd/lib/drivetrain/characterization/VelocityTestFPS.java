/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.characterization;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Drive forwards at a specified velocity (velocity in feet per second) without and ending condition. Use with extreme caution and a finger on the disable key. 
 */
public class VelocityTestFPS extends CommandBase {

  private double m_desiredVel, m_time, m_startTime;
  private double m_desiredTime;
  private AbstractDrivetrain m_drive;

  public VelocityTestFPS(AbstractDrivetrain drive, double desired_vel, double desired_time) {
    m_desiredVel = desired_vel;
    m_desiredTime = desired_time;
    m_drive = drive;
    addRequirements(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_startTime = Timer.getFPGATimestamp();
    m_drive.addDesiredVelocities(m_desiredVel, m_desiredVel);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_drive.addDesiredVelocities(m_desiredVel, m_desiredVel);
    m_drive.setVelocityFPS(m_desiredVel, m_desiredVel);
    
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
      return m_time > m_desiredTime;
      // return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drive.setPowerZero();
  }

        
}
