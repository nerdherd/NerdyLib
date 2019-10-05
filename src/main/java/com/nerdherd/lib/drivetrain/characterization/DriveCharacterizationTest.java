/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.characterization;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * Voltage ramp to characterize a drivetrain, analyze the data in logger pro
 */
public class DriveCharacterizationTest extends CommandBase {

  private double m_voltage, m_startTime, m_time;
  private double m_rampRate;
  private AbstractDrivetrain m_drive;
  
  public DriveCharacterizationTest(AbstractDrivetrain drive, double rampRate) {
    m_rampRate = rampRate;
    m_drive = drive;
    addRequirements(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
      m_startTime = Timer.getFPGATimestamp();
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {
    m_time = Timer.getFPGATimestamp() - m_startTime;
    m_voltage = (m_rampRate * m_time)/12;
    m_drive.setPower(m_voltage, m_voltage);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  public boolean isFinished() {
    return m_time > 12 / m_rampRate;
  }

  // Called once after isFinished returns true
  @Override
  public void end(boolean interrupted) {
    m_drive.setPowerZero();
  }

        
}
