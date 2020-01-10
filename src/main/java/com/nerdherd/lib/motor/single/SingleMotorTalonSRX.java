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
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.motor.motorcontrollers.NerdyTalon;
import com.nerdherd.lib.motor.motorcontrollers.NerdyVictorSPX;
import com.nerdherd.lib.motor.motorcontrollers.SmartCANMotorController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.trajectory.TrapezoidProfile.Constraints;

/**
 * Add your docs here.
 */
public class SingleMotorTalonSRX extends SmartMotorControllerSubsystem {

  public SmartCANMotorController motor;
  private TrapezoidProfile.Constraints m_constraints;

  /**
   * 
   * @param talonID       CAN ID of talon
   * @param subsystemName String name of subsystem to display on smart dashboard
   */
  public SingleMotorTalonSRX(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    name = subsystemName;
    motor = new NerdyTalon(talonID);
    motor.configDefaultSettings();
    setInversion(inversion);
    setSensorPhase(sensorPhase);
  }

  public SingleMotorTalonSRX(SmartCANMotorController motorController, String subsystemName, boolean inversion, boolean sensorPhase) {
    name = subsystemName;
    motor = motorController;
    motor.configDefaultSettings();
    setInversion(inversion);
    setSensorPhase(sensorPhase);
  }
  
  public void configFollowersTalons(NerdyTalon[] followers) {
    motor.configFollowers(followers);
  }

  public void configFollowerVictors(NerdyVictorSPX[] followers) {
    motor.configFollowers(followers);
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

  public void configTrapezoidalConstraints(Constraints constraints) {
    m_constraints = constraints;
  }

  public void configSensor(FeedbackDevice device) {
    motor.configSensor(device);
  }

  @Override
  public void configDeadband(double deadband) {
    motor.configDeadband(deadband);
    // motor.conf
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

  // public void controlMotor(ControlMode controlMode, double setpoint, double arbFF) {
  //   motor.set(controlMode, setpoint, DemandType.ArbitraryFeedForward, arbFF);
  // }
  
  @Override
  public void setPower(double power) {
    // motor.set(ControlMode.PercentOutput, power);
    motor.setPower(power);
  }

  @Override
  public void setPower(double power, double arbFF) {
    // motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, arbFF);
    motor.setPower(power, arbFF);
  }

  @Override
  public void setVoltage(double voltage) {
    // motor.set(ControlMode.PercentOutput, voltage/12.0);
    motor.setVoltage(voltage);
  }

  @Override
  public void setVoltage(double voltage, double arbFF) {
    // motor.set(ControlMode.PercentOutput, voltage/12.0, DemandType.ArbitraryFeedForward, arbFF);
    motor.setVoltage(voltage, arbFF);
  }

  @Override
  public void setPosition(double pos) {
    // motor.set(ControlMode.Position, pos);
    motor.setPositionPID(pos);
  }

  @Override
  public void setPosition(double pos, double arbFF) {
    // motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, arbFF);
    motor.setPositionPID(pos, arbFF);
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    // motor.set(ControlMode.MotionMagic, pos);
    motor.setPositionMotionMagic(pos);
  }

  @Override
  public void setPositionMotionMagic(double pos, double arbFF) {
    // motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, arbFF);
    motor.setPositionMotionMagic(pos, arbFF);
  }

  @Override
  public void setPositionOblargian(double pos){
    // motor.set(ControlMode.Position, pos);
    motor.setPositionPID(pos);
  }

  @Override
  public void setPositionOblargian(double pos, double arbFF){
    // motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, arbFF);
    motor.setPositionPID(pos, arbFF);
  }

  @Override
  public void setVelocity(double vel) {
    // motor.set(ControlMode.Velocity, vel);
    motor.setVelocity(vel);
  }

  @Override
  public void setVelocity(double vel, double arbFF) {
    // motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, arbFF);
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
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  @Override
  public void initLoggingData() {
    NerdyBadlog.createTopic(name + "/Position", () -> getPosition());
    NerdyBadlog.createTopic(name + "/Velocity", () -> getVelocity());
    NerdyBadlog.createTopic(name + "/Voltage", () -> getVoltage());
    NerdyBadlog.createTopic(name + "/Current", () -> getCurrent());
  }

  public double convertPosToRealUnits(double position) {
    return position;
  }

  public double converVelToRealUnits(double velocity){
    return velocity;
  }
  
}
