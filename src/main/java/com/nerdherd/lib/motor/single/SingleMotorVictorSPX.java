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
import com.nerdherd.lib.motor.motorcontrollers.NerdyVictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Generic Single motor controlled by a VictorSPX
 */
public class SingleMotorVictorSPX extends AbstractSingleMotor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  public NerdyVictorSPX motor;

   /**
   * 
   * @param victorID CAN ID of victor
   * @param subsystemName String name of subsystem to display on smart dashboard
   * @param inversion boolean inversion of the VictorSPX
   */
  public SingleMotorVictorSPX(int victorID, String subsystemName, boolean inversion) {
    name = subsystemName;
    motor = new NerdyVictorSPX(victorID); 
    motor.configFactoryDefault();
    setInversion(inversion);
  }

  @Override
  public void setInversion(boolean inversion) {
    motor.setInverted(inversion);
  }

  public void controlMotor(ControlMode controlMode, double setpoint, double arbFF) {
    motor.set(controlMode, setpoint, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public void setPower(double power) {
    motor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public void setPower(double power, double arbFF) {
    motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public double getVoltage() {
    return motor.getMotorOutputVoltage();
  }

  public void setVoltage(double voltage) {
    motor.set(ControlMode.PercentOutput, voltage/12.0);
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
