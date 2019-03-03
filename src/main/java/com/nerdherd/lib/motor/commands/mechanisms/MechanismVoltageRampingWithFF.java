/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.single.mechanisms.GravityAffectedMechanism;
import com.nerdherd.lib.motor.single.mechanisms.StaticFrictionMechanism;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class MechanismVoltageRampingWithFF extends Command {

  private StaticFrictionMechanism m_motor;
  private double m_rate, m_startTime;

  public MechanismVoltageRampingWithFF(StaticFrictionMechanism motor, double rate) {
    m_motor = motor;
    m_rate = rate;
    requires(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    m_startTime = Timer.getFPGATimestamp();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_motor.setPowerWithFF(m_rate * (Timer.getFPGATimestamp() - m_startTime));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return m_motor.getVoltage() > 12.0;
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
    end();
  }
}
