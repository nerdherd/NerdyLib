/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.motorcontrollers.NerdyTalon;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorTalonSRX extends SmartMotorControllerSubsystem {
 
  public NerdyTalon motor;

  /**
   * 
   * @param talonID CAN ID of talon
   * @param subsystemName String name of subsystem to display on smart dashboard
   */
  public SingleMotorTalonSRX(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    name = subsystemName;
    motor = new NerdyTalon(talonID); 
    motor.configDefaultSettings();
    setInversion(inversion);
    setSensorPhase(sensorPhase);
  }

  public void configFollowersTalons(NerdyTalon[] followers) {
    motor.configFollowerTalons(followers);
  }

  public void configFollowerVictors(VictorSPX[] followers) {
    motor.configFollowerVictors(followers);
  }

  public void configPIDF(double kP, double kI, double kD, double kF) {
    motor.configPIDF(kP, kI, kD, kF, 0);
  }

  public void configPIDF(double kP, double kI, double kD, double kF, int pidIndex) {
    motor.configPIDF(kP, kI, kD, kF, pidIndex);
  }

  public void configCurrentLimit(int peak, int continuous) {
    motor.configCurrentLimitContinuous(continuous);
    motor.configCurrentLimitPeak(peak);
  }

  public void configMotionMagic(int accel, int cruise_vel) {
    motor.configMotionMagic(accel, cruise_vel);
  }

  public void configSensor(FeedbackDevice device) {
    motor.configSelectedFeedbackSensor(device);
  }

  public void configTalonDeadband(double deadband) {
    motor.configNeutralDeadband(deadband);
  }

  @Override
  public void setInversion(boolean inversion) {
    motor.setInverted(inversion);
  }

  @Override
  public void resetEncoder() {
    motor.setSelectedSensorPosition(0);
  }
  @Override
  public void setSensorPhase(boolean phase) {
    motor.setSensorPhase(phase);
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
  public void setVoltage(double voltage) {
    motor.set(ControlMode.PercentOutput, voltage/12.0);
  }

  @Override
  public void setVoltage(double voltage, double arbFF) {
    motor.set(ControlMode.PercentOutput, voltage/12.0, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public void setPosition(double pos) {
    motor.set(ControlMode.Position, pos);
  }

  @Override
  public void setPosition(double pos, double arbFF) {
    motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, arbFF);
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    motor.set(ControlMode.MotionMagic, pos);
  }

  @Override
  public void setPositionMotionMagic(double pos, double arbFF) {
    motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public void setVelocity(double vel) {
    motor.set(ControlMode.Velocity, vel);
  }

  @Override
  public void setVelocity(double vel, double arbFF) {
    motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, arbFF);
  }

  @Override
  public double getCurrent() {
    return motor.getOutputCurrent();
  }

  @Override
  public double getPosition() {
    return motor.getSelectedSensorPosition();
  }

  @Override
  public double getVelocity() {
    return motor.getSelectedSensorVelocity();
  }

  @Override
  public double getVoltage() {
    return motor.getMotorOutputVoltage();
  }

  @Override
  public void reportToSmartDashboard() {
    SmartDashboard.putNumber(name + " Current", getCurrent());
    SmartDashboard.putNumber(name + " Voltage", getVoltage());
    SmartDashboard.putNumber(name + " Velocity", getVelocity());
    SmartDashboard.putNumber(name + " Position", getPosition());
  }

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(name + "/Position", () -> getPosition());
    NerdyBadlog.createTopic(name + "/Velocity", () -> getVelocity());
    NerdyBadlog.createTopic(name + "/Voltage", () -> getVoltage());
    NerdyBadlog.createTopic(name + "/Current", () -> getCurrent());
  }
  
}
