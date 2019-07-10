/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.motorcontrollers.CANMotorController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Generic Single motor controlled by a VictorSPX
 */
public class DumbMotorControllerSubsystem extends AbstractSingleMotor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  protected CANMotorController m_motor;

   /**
   * 
   * @param victorID CAN ID of victor
   * @param subsystemName String name of subsystem to display on smart dashboard
   * @param inversion boolean inversion of the VictorSPX
   */
  public DumbMotorControllerSubsystem(CANMotorController motor, String subsystemName, boolean inversion) {
    name = subsystemName;
    m_motor = motor;
    m_motor.configDefaultSettings();
    setInversion(inversion);
  }

  @Override
  public void setInversion(boolean inversion) {
    m_motor.setInversion(inversion);
  }

  @Override
  public void setPower(double power) {
    m_motor.setPower(power);
  }

  @Override
  public void setPower(double power, double arbFF) {
    m_motor.setPower(power, arbFF);
  }

  @Override
  public double getVoltage() {
    return m_motor.getVoltage();
  }

  @Override
  public void reportToSmartDashboard() {
    SmartDashboard.putNumber(name + " Voltage", getVoltage());
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(name + "/Voltage", () -> getVoltage());
  }
}
