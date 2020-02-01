/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single.mechanisms;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.nerdherd.lib.logging.NerdyBadlog;
import com.nerdherd.lib.misc.NerdyMath;

import edu.wpi.first.wpilibj.controller.ArmFeedforward;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorArm extends GravityAffectedMechanism {

  protected double m_angleRatio, m_angleOffset;
  protected ArmFeedforward m_armFeedForward;

  public SingleMotorArm(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
    super.m_gravityFF = 0;
    super.m_staticFF = 0;
    m_angleRatio = 1;
    m_angleOffset = 0;
  }

  public void configOblargConstants(double kS, double kV, double kA, double kCos){
    m_armFeedForward = new ArmFeedforward(kS, kCos, kV, kA);
  }

  public void configAngleRatio(double newAngleRatio) {
    m_angleRatio = newAngleRatio;
  }

  public void configAngleOffset(double newAngleOffset) {
    m_angleOffset = newAngleOffset;
  }

  public void configAngleConversion(double newAngleRatio, double newAngleOffset) {
    m_angleRatio = newAngleRatio;
    m_angleOffset = newAngleOffset;
  }

  public double getFFIfMoving() {
    return m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle()));
  }

  public double getFFIfNotMoving(double error) {
    double sign = Math.signum(error);
    return m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())) + 
      sign * m_staticFF;
  }

  public void setAngle(double angle) {
    if (isNotMoving()) {
      motor.set(ControlMode.Position, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(angle - getAngle()));
    } else {
      motor.set(ControlMode.Position, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }

  public void setAngleCalculated(double angle){
    super.setPositionCalculated(angleToTicks(angle), m_armFeedForward.calculate(NerdyMath.degreesToRadians(getAngle()),
       NerdyMath.degreesToRadians(getAngularVelocity())));
  }

  public void setAngleMotionMagic(double angle) {
    if (isNotMoving()) {
      motor.set(ControlMode.MotionMagic, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(angle - getAngle()));
    } else {
      motor.set(ControlMode.MotionMagic, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }

  public double angleToTicks(double angle) {
    return (angle - m_angleOffset) / m_angleRatio;
  }

  public double ticksToAngle(double ticks) {
    return (m_angleRatio * ticks) + m_angleOffset;
  }

  public double getAngle() {
    return ticksToAngle(this.getPosition());
  }

  public double getAngularVelocity() {
    return ((m_angleRatio * this.getVelocity()) * 10);
  }

  @Override
  public void reportToSmartDashboard() {
    super.reportToSmartDashboard();
    SmartDashboard.putNumber(name + " Angle", getAngle());
  }

  @Override
  public void initLoggingData() {
    super.initLoggingData();
    NerdyBadlog.createTopic(name + "/Angle", () -> getAngle());
  }

}
