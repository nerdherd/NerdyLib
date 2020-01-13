/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.SmartMotorControllerSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetMotorPositionPID extends CommandBase {

  private SmartMotorControllerSubsystem m_motor;
  private double m_pos;
  private boolean m_holdPosition;
  private double m_threshold;

  public SetMotorPositionPID(SmartMotorControllerSubsystem motor, double pos) {
    m_motor = motor;
    m_pos = pos;
    m_holdPosition = true;
    m_threshold = 0;
    addRequirements(m_motor);
  }

  public SetMotorPositionPID(SmartMotorControllerSubsystem motor, double pos, double threshold, boolean holdPosition) {
    m_motor = motor;
    m_pos = pos;
    m_threshold = threshold;
    m_holdPosition = holdPosition;
    addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.setPosition(m_pos);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    // return false;
    if (m_holdPosition) {
      return false;
    } else {
      return Math.abs(m_pos - m_motor.getPosition()) <= m_threshold;
    }
    
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    if (!m_holdPosition) {
      m_motor.setPower(0);
    }
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
