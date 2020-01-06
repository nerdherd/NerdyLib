/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.nerdherd.lib.logging.NerdyBadlog;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Generic Single motor controlled by a VictorSPX
 */
public class SingleMotorVictorSPX extends AbstractSingleMotor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private VictorSPX m_motor;

   /**
   * 
   * @param victorID CAN ID of victor
   * @param subsystemName String name of subsystem to display on smart dashboard
   * @param inversion boolean inversion of the VictorSPX
   */
  public SingleMotorVictorSPX(int victorID, String subsystemName, boolean inversion) {
    name = subsystemName;
    m_motor = new VictorSPX(victorID); 
    m_motor.configFactoryDefault();
    setInversion(inversion);
  }

  @Override
  public void setInversion(boolean inversion) {
    m_motor.setInverted(inversion);
  }

  public void controlMotor(ControlMode controlMode, double setpoint, double arbFF) {
    m_motor.set(controlMode, setpoint, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public void setPower(double power) {
    m_motor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void setPower(double power, double arbFF) {
    m_motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public double getVoltage() {
    return m_motor.getMotorOutputVoltage();
  }

  public void setVoltage(double voltage) {
    m_motor.set(ControlMode.PercentOutput, voltage/12.0);
  }


  @Override
  public void reportToSmartDashboard() {
    SmartDashboard.putNumber(name + " Voltage", getVoltage());
  }


  

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(name + "/Voltage", () -> getVoltage());
  }
}
