/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.motorcontrollers.NerdyFalcon;
import com.nerdherd.lib.motor.single.SingleMotorMechanism;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetMotorVelocity extends CommandBase {
  private SingleMotorMechanism m_motor;
  private double m_velocity, m_arbFF;
    
    public SetMotorVelocity(SingleMotorMechanism motor, double velocity, double kF) {
      m_motor = motor;
      m_velocity = velocity;
      m_arbFF = m_velocity*kF/1023.;
      addRequirements(m_motor);
    }

    public SetMotorVelocity(SingleMotorMechanism motor, double velocity) {
      m_motor = motor;
      m_velocity = velocity;
      if (motor.motor instanceof NerdyFalcon) {
        m_arbFF = 0;
      } else {
        m_arbFF = m_velocity*m_motor.kF/1023.;
      }
      addRequirements(m_motor);
    }
  
    // Called just before this Command runs the first time
    @Override
    public void initialize() {
  }

  @Override
  public void execute() {
    m_motor.setVelocity(m_velocity, m_arbFF);
    SmartDashboard.putNumber("MotorVelocity", m_velocity);

  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
