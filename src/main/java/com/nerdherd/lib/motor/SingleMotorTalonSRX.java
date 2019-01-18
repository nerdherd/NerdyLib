/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.nerdherd.lib.drivers.NerdyTalon;

import badlog.lib.BadLog;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorTalonSRX extends AbstractSingleMotorTalonSRX {
 
  private NerdyTalon m_motor;
  private String m_name;

  /**
   * 
   * @param talonID CAN ID of talon
   * @param subsystemName String name of subsystem to display on smart dashboard
   */
  public SingleMotorTalonSRX(int talonID, String subsystemName) {
    m_name = subsystemName;
    m_motor = new NerdyTalon(talonID); 
    m_motor.configDefaultSettings();
  }

  public void configPIDF(double kP, double kI, double kD, double kF) {
    m_motor.configPIDF(kP, kI, kD, kF, 0);
  }

  public void configCurrentLimit(int peak, int continuous) {
    m_motor.configCurrentLimitContinuous(continuous);
    m_motor.configCurrentLimitPeak(peak);
  }

  public void configMotionMagic(int accel, int cruise_vel) {
    m_motor.configMotionMagic(accel, cruise_vel);
  }

  public void configSensor(FeedbackDevice device) {
    m_motor.configSelectedFeedbackSensor(device);
  }

  @Override
  public void setInversion(boolean inversion) {
    m_motor.setInverted(inversion);
  }

  @Override
  public void resetEncoder() {
    m_motor.setSelectedSensorPosition(0);
  }
  @Override
  public void setSensorPhase(boolean phase) {
    m_motor.setSensorPhase(phase);
  }
  
  public void setPower(double power) {
    m_motor.set(ControlMode.PercentOutput, power);
  }

  public void setVoltage(double voltage) {
    m_motor.set(ControlMode.PercentOutput, voltage/12);
  }

  @Override
  public void setPosition(double pos) {
    m_motor.set(ControlMode.Position, pos);
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    m_motor.set(ControlMode.MotionMagic, pos);
  }

  @Override
  public void setVelocity(double vel) {
    m_motor.set(ControlMode.Velocity, vel);
  }

  @Override
  public double getCurrent() {
    return m_motor.getOutputCurrent();
  }

  @Override
  public double getPosition() {
    return m_motor.getSelectedSensorPosition();
  }

  @Override
  public double getVelocity() {
    return m_motor.getSelectedSensorVelocity();
  }

  @Override
  public double getVoltage() {
    return m_motor.getMotorOutputVoltage();
  }

  @Override
  public void reportToSmartDashboard() {
    SmartDashboard.putNumber(m_name + " Current", getCurrent());
    SmartDashboard.putNumber(m_name + " Voltage", getVoltage());
    SmartDashboard.putNumber(m_name + " Velocity", getVelocity());
    SmartDashboard.putNumber(m_name + " Position", getPosition());
  }
 
  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void initLoggingData() {
    BadLog.createTopic("Position", "ticks", () -> getPosition());
    BadLog.createTopic("Velocity", "STU", () -> getVelocity());
    BadLog.createTopic("Voltage", "V", () -> getVoltage());
    BadLog.createTopic("Current", "Amps", () -> getCurrent());
  }
}
