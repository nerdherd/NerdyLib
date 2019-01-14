/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.characterization;


import com.nerdherd.lib.drivetrain.AbstractDrivetrain;

import edu.wpi.first.wpilibj.command.Command;


public class OpenLoopDrive extends Command {

  private double m_power;
  private AbstractDrivetrain m_drive;

  public OpenLoopDrive(AbstractDrivetrain drive, double power) {
    m_power = power;
    m_drive = drive;
    requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_drive.setPower(m_power, m_power);
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
