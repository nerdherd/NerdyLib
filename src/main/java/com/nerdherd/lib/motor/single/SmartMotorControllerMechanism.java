/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.motorcontrollers.SmartCANMotorController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SmartMotorControllerMechanism extends AbstractSmartMotorControllerSubsystem {
 
  public SmartCANMotorController motor;

  
  public SmartMotorControllerMechanism(SmartCANMotorController motorController, String subsystemName, boolean inversion, boolean sensorPhase) {
    name = subsystemName;
    motor = motorController;
    motor.configDefaultSettings();
    setInversion(inversion);
    setSensorPhase(sensorPhase);
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

  @Override
  public void setInversion(boolean inversion) {
    motor.setInversion(inversion);
  }

  @Override
  public void resetEncoder() {
    motor.resetEncoder();
  }

  @Override
  public void setSensorPhase(boolean phase) {
    motor.setSensorPhase(phase);
  }

  
  @Override
  public void setPower(double power) {
    motor.setPower(power);
  }

  @Override
  public void setPower(double power, double arbFF) {
    motor.setPower(power,arbFF);
  }

  @Override
  public void setVoltage(double voltage) {
    motor.setVoltage(voltage);
  }

  @Override
  public void setVoltage(double voltage, double arbFF) {
    motor.setVoltage(voltage, arbFF); 
  }

  @Override
  public void setPosition(double pos) {
    motor.setPositionPID(pos);
  }
  
  @Override
  public void setPosition(double pos, double arbFF) {
    motor.setPositionPID(pos, arbFF);
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    motor.setPositionMotionMagic(pos);
  }

  @Override
  public void setPositionMotionMagic(double pos, double arbFF) {
    motor.setPositionMotionMagic(pos, arbFF);
  }

  @Override
  public void setVelocity(double vel) {
    motor.setVelocity(vel);
  }

  @Override
  public void setVelocity(double vel, double arbFF) {
    motor.setVelocity(vel, arbFF);
  }

  @Override
  public double getCurrent() {
    return motor.getCurrent();
  }

  @Override
  public double getPosition() {
    return motor.getPosition();
  }

  @Override
  public double getVelocity() {
    return motor.getVelocity();
  }

  @Override
  public double getVoltage() {
    return motor.getVoltage();
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

  @Override
  public void configTalonDeadband(double deadband) {
    
  }

  @Override
  public void configSensor(FeedbackDevice device) {

  }
  
}
