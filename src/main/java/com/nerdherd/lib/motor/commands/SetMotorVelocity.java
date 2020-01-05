/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class SetMotorVelocity extends Command {
  private SingleMotorTalonSRX m_motor;
  private double m_velocity, m_arbFF;
    
    public SetMotorVelocity(SingleMotorTalonSRX motor, double velocity, double kF) {
      m_motor = motor;
      m_velocity = velocity;
      m_arbFF = m_velocity*kF/1023;
      requires(m_motor);
    }
  
    // Called just before this Command runs the first time
    @Override
    protected void initialize() {
  }

  @Override
  protected void execute() {
    m_motor.setVelocity(m_velocity, m_arbFF);
    SmartDashboard.putNumber("MotorVelocity", m_velocity);

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
