/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands.mechanisms;

import com.nerdherd.lib.motor.single.mechanisms.StaticFrictionMechanism;

import edu.wpi.first.wpilibj.command.Command;

public class ZeroMechanismManually extends Command {
  
  private StaticFrictionMechanism m_mechanism;
  private double m_rate;
  private boolean m_useFF;

  public ZeroMechanismManually(StaticFrictionMechanism mechanism, double descentRate) {
    this(mechanism, descentRate, false);
  }

  public ZeroMechanismManually(StaticFrictionMechanism mechanism, double descentRate, boolean useFF) {
      m_mechanism = mechanism;
      m_rate = descentRate;
      m_useFF = useFF;
      requires(m_mechanism);

  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    if (m_useFF) {
      m_mechanism.setVoltageWithFF(m_rate);
    } else {
      m_mechanism.setVoltage(m_rate);
    }
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  protected void end() {
    m_mechanism.resetEncoder();
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  @Override
  protected void interrupted() {
  }
}
