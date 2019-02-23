/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.AbstractSingleMotorTalonSRX;

import edu.wpi.first.wpilibj.command.Command;

public class DumbSetPosition extends Command {

  private AbstractSingleMotorTalonSRX m_motor;
  private double m_position, m_tolerance, m_power;
  public DumbSetPosition(AbstractSingleMotorTalonSRX motor, double position, double power, double tolerance) {
    m_motor = motor;
    m_position = position;
    m_power = power;
    m_tolerance = tolerance;
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_motor.setPower(m_power);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return Math.abs(m_position - m_motor.getPosition()) <= m_tolerance; 
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_motor.setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
