/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.AbstractSingleMotor;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.experimental.command.SendableCommandBase;

public class MotorJoystickControl extends SendableCommandBase {

  private AbstractSingleMotor m_motor;
  private Joystick m_joystick;

  public MotorJoystickControl(Joystick joystick, AbstractSingleMotor motor) {
    m_motor = motor;
    m_joystick = joystick;
    addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.setPower(m_joystick.getY());
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return false;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_motor.setPower(0);
  }

  // Called when another command which requires one or more of the same
  // subsystems is scheduled to run
  
}
