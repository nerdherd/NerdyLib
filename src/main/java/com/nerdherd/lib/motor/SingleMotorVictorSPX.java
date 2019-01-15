/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorVictorSPX extends AbstractSingleMotor {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private VictorSPX m_motor;
  private String m_name;

  public SingleMotorVictorSPX(int victorID, String subsystemName) {
    m_name = subsystemName;
    m_motor = new VictorSPX(victorID); 
    m_motor.configFactoryDefault();
  }

  @Override
  public void setInversion(boolean inversion) {
    m_motor.setInverted(inversion);
  }

  @Override
  public void setPower(double power) {
    m_motor.set(ControlMode.PercentOutput, power);
  }

  @Override
  public double getVoltage() {
    return m_motor.getMotorOutputVoltage();
  }

  @Override
  public void reportToSmartDashboard() {
    SmartDashboard.putNumber(m_name + " Voltage", getVoltage());
  }


  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }
}
