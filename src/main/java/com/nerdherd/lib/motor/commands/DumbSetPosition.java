/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.AbstractSmartMotorControllerSubsystem;

import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class DumbSetPosition extends SendableCommandBase {

  private AbstractSmartMotorControllerSubsystem m_motor;
  private double m_position, m_tolerance, m_power;
  public DumbSetPosition(AbstractSmartMotorControllerSubsystem motor, double position, double power, double tolerance) {
    m_motor = motor;
    m_position = position;
    m_power = power;
    m_tolerance = tolerance;
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.setPower(m_power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return Math.abs(m_position - m_motor.getPosition()) <= m_tolerance; 
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_motor.setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
