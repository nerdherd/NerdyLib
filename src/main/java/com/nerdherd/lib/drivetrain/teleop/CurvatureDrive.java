/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.oi.AbstractOI;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class CurvatureDrive extends CommandBase {
  private double m_leftPower, m_rightPower;
  private double m_xSpeed, m_zRot, m_turningDeadband;

  private AbstractDrivetrain m_drive;
  private AbstractOI m_oi;

  public CurvatureDrive(AbstractDrivetrain drive, AbstractOI oi,double turningDeadband) {
    m_turningDeadband = turningDeadband;

    m_drive = drive;
    m_oi = oi;
    addRequirements(m_drive); 
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_xSpeed = m_oi.getDriveJoyLeftY();
    m_zRot = m_oi.getDriveJoyRightX();
    if(Math.abs(m_xSpeed) > m_turningDeadband){
      m_leftPower = m_xSpeed + m_xSpeed * m_zRot;
      m_rightPower = m_xSpeed - m_xSpeed * m_zRot; 
      m_drive.setPower(m_leftPower, m_rightPower);
    } 
    else{
      m_leftPower = m_zRot;
      m_rightPower = -m_zRot;
      m_drive.setPower(m_leftPower, m_rightPower);
  
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }

   
}
