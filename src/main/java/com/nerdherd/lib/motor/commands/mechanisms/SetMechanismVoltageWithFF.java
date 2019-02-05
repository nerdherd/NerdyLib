/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.single.mechanisms.GravityAffectedMechanism;

import edu.wpi.first.wpilibj.command.Command;

public class SetMechanismVoltageWithFF extends Command {

  private GravityAffectedMechanism m_motor;
  private double m_voltage;

  public SetMechanismVoltageWithFF(GravityAffectedMechanism motor, double voltage) {
    m_motor = motor;
    m_voltage = voltage;
    requires(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_motor.setPowerWithFF(m_voltage / 12.);
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
