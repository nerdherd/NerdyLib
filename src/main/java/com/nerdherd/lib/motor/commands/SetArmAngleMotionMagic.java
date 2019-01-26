/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.SingleMotorArm;

import edu.wpi.first.wpilibj.command.Command;

public class SetArmAngleMotionMagic extends Command {
  private SingleMotorArm m_motor;
  private double m_angle;

  public SetArmAngleMotionMagic(SingleMotorArm motor, double angle) {
    m_motor = motor;
    m_angle = angle;
    requires(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    m_motor.setAngleMotionMagic(m_angle);
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
