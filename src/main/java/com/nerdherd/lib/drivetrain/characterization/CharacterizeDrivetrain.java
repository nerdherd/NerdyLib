/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.characterization;

import java.util.function.Supplier;

import com.nerdherd.lib.drivetrain.singlespeed.AbstractDrivetrain;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;

public class CharacterizeDrivetrain extends Command {

  private AbstractDrivetrain m_drive;
  private Supplier<Double> m_batteryVoltage;
  NetworkTableEntry autoSpeedEntry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
	NetworkTableEntry telemetryEntry = NetworkTableInstance.getDefault().getEntry("/robot/telemetry");

	double priorAutospeed = 0;
  Number[] numberArray = new Number[9];
  
  public CharacterizeDrivetrain(AbstractDrivetrain drive, Supplier<Double> batteryVoltage) {
    m_drive = drive;
    m_batteryVoltage = batteryVoltage;
    requires(m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    double now = Timer.getFPGATimestamp();
    double leftPos = m_drive.getLeftMasterPosition();
    double rightPos = m_drive.getRightMasterPosition();
    double leftVel = m_drive.getLeftMasterVelocity();
    double rightVel = m_drive.getRightMasterVelocity();
    double battery = m_batteryVoltage.get();
    double leftVoltage = m_drive.getLeftOutputVoltage();
    double rightVoltage = m_drive.getRightOutputVoltage();
    double autospeed = autoSpeedEntry.getDouble(0);
    priorAutospeed = autospeed;
    m_drive.setPower(autospeed, autospeed);
    numberArray[0] = now;
    numberArray[1] = battery;
		numberArray[2] = autospeed;
		numberArray[3] = leftVoltage;
		numberArray[4] = rightVoltage;
		numberArray[5] = leftPos;
		numberArray[6] = rightPos;
		numberArray[7] = leftVel;
    numberArray[8] = rightVel;
    telemetryEntry.setNumberArray(numberArray);

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
