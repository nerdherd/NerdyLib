/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.commands;

import com.nerdherd.lib.motor.single.AbstractSingleMotor;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class MotorVoltageRamping extends CommandBase {

  private AbstractSingleMotor m_motor;
  private double m_rate, m_startTime;

  public MotorVoltageRamping(AbstractSingleMotor motor, double rate) {
    m_motor = motor;
    m_rate = rate;
    addRequirements(m_motor);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
    m_startTime = Timer.getFPGATimestamp();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_motor.setPower(m_rate * (Timer.getFPGATimestamp() - m_startTime));
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_motor.getVoltage() > 12.0;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_motor.setPower(0);
  }

        
}
