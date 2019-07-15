/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.oi.AbstractOI;
import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class TankDrive extends SendableCommandBase {

  private AbstractDrivetrain m_drive;
  private AbstractOI m_oi;
  public TankDrive(AbstractDrivetrain drive, AbstractOI oi) {
    m_drive = drive;
    m_oi = oi;
    addRequirements(drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_drive.setPower(m_oi.getDriveJoyLeftY(), m_oi.getDriveJoyRightY());
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

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
