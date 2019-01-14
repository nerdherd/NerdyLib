/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.teleop;

import com.nerdherd.lib.AbstractOI;
import com.nerdherd.lib.drivetrain.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {

  private AbstractDrivetrain m_drive;
  private AbstractOI m_oi;
  public TankDrive(AbstractDrivetrain drive, AbstractOI oi) {
    m_drive = drive;
    m_oi = oi;
    requires(drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_drive.setPower(m_oi.getDriveJoyLeftY(), m_oi.getDriveJoyRightY());
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
