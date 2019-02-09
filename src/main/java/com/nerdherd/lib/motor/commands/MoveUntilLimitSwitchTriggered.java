/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.SingleMotorTalonSRXWithLimitSwitch;

import edu.wpi.first.wpilibj.command.Command;

public class MoveUntilLimitSwitchTriggered extends Command {

  private SingleMotorTalonSRXWithLimitSwitch m_motor;
  private double m_power;

  public MoveUntilLimitSwitchTriggered(SingleMotorTalonSRXWithLimitSwitch motor, double power) {
    m_power = power;
    requires(m_motor);  
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
    return m_motor.getLimitSwitch();
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
