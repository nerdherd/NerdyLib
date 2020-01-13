/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.SmartMotorControllerSubsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;


public class SetMotorMotionMagic extends CommandBase {

  private SmartMotorControllerSubsystem m_motor;
  private double m_pos;

  public SetMotorMotionMagic(SmartMotorControllerSubsystem motor, double pos) {
    m_motor = motor;
    m_pos = pos;
    addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.setPositionMotionMagic(m_pos);
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
