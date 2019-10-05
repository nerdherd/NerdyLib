/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.dual.AbstractDualMotorIntake;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class SetDualMotorPower extends CommandBase {

  private AbstractDualMotorIntake m_intake;
  double m_leftPower, m_rightPower;
  
  public SetDualMotorPower(AbstractDualMotorIntake intake, double leftPower, double rightPower) {
    m_intake = intake;
    m_leftPower = leftPower;
    m_rightPower = rightPower;
    addRequirements(m_intake);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_intake.setPower(m_leftPower, m_rightPower);
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

   
}
