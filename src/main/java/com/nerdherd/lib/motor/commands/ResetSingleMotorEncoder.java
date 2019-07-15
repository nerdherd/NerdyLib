/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.AbstractSmartMotorControllerSubsystem;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;


public class ResetSingleMotorEncoder extends SendableCommandBase {

  private AbstractSmartMotorControllerSubsystem m_motor;
  public ResetSingleMotorEncoder(AbstractSmartMotorControllerSubsystem motor) {
    m_motor = motor;
    addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_motor.resetEncoder();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return true;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
