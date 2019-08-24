/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;
import com.nerdherd.lib.oi.AbstractOI;

import edu.wpi.first.wpilibj.command.Command;

public class CurvatureDrive extends Command {
  private double m_leftPower, m_rightPower;
  private double m_xSpeed, m_zRot, m_turningDeadband;
  private boolean m_isQuickTurn;
  private AbstractDrivetrain m_drive;
  private AbstractOI m_oi;

  public CurvatureDrive(AbstractDrivetrain drive, AbstractOI oi, double xSpeed, double zRot, boolean isQuickTurn, double turningDeadband) {
    m_turningDeadband = turningDeadband;
    m_xSpeed = xSpeed;
    m_zRot = zRot;
    // m_isQuickTurn = isQuickTurn;

    m_drive = drive;
    m_oi = oi;
    requires(m_drive); 
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  
  
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if(Math.abs(m_xSpeed) > m_turningDeadband){
      m_leftPower = m_xSpeed + m_xSpeed * m_zRot;
      m_rightPower = m_xSpeed - m_xSpeed * m_zRot; 
    } 
    else{
      m_leftPower = m_xSpeed + m_zRot;
      m_rightPower = -m_xSpeed + m_zRot;
      m_drive.setPower(m_leftPower, m_rightPower);
  
    }
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
