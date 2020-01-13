/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.characterization;

import java.util.function.Supplier;

import com.nerdherd.lib.motor.single.mechanisms.SingleMotorArm;
import com.nerdherd.lib.motor.single.mechanisms.SingleMotorElevator;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class Arm extends CommandBase {

  private SingleMotorArm m_arm;
  private Supplier<Double> m_batteryVoltage;
  NetworkTableEntry autoSpeedEntry = NetworkTableInstance.getDefault().getEntry("/robot/autospeed");
      NetworkTableEntry telementryEntry = NetworkTableInstance.getDefault().getEntry("/robot/telementry");

            double priorAutospeed = 0;
  Number[] numberArray = new Number[9];

  public Arm(SingleMotorElevator arm, Supplier<Double> batteryVoltage) {
    // m_arm = arm;
    m_batteryVoltage = batteryVoltage;
    addRequirements(m_arm);
  }

  // Called just before this Command runs the first time
  @Override
  public void initialize() {
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  public void execute() {

    // double now = Timer.getFPGATimestamp();

    // double position = encoderPosition.get();
    // double rate = encoderRate.get();

    double bettery = RobotController.getBatteryVoltage();

    double motorVolts = RobotController.getBatteryVoltage();

    // Retrieve the commanded speed from NetworkTables
    double autospeed = autoSpeedEntry.getDouble(0);
    priorAutospeed = autospeed;

    // command motors to do things
    // arm.set(autospeed);

    // send telementry data array back to NT
    // numberArray[0] = now;
    // numberArray[1] = battery;
    numberArray[2] = autospeed;
    numberArray[3] = motorVolts;
    // numberArray[4] = position;
    // numberArray[5] = rate;
    // numberArray[6] = velocity;
    telementryEntry.setNumberArray(numberArray);

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
