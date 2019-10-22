/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.shifting;

import com.nerdherd.lib.drivetrain.experimental.TwoSpeedDrivetrain;

import edu.wpi.first.wpilibj.command.Command;

public class ShiftHigh extends Command {

  TwoSpeedDrivetrain m_drive;
  public ShiftHigh(TwoSpeedDrivetrain drive) {
    m_drive = drive;
    // requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_drive.shiftHigh();
  }


  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return true;
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
