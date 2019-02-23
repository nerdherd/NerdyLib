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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorArm extends GravityAffectedMechanism {

  private double m_angleRatio, m_angleOffset;

  public SingleMotorArm(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
    super.m_gravityFF = 0;
    super.m_staticFF = 0;
    m_angleRatio = 1;
    m_angleOffset = 0;
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

  public void setPowerWithFF(double power) {
    if (isNotMoving()) {
      motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(power));
    } else {
      motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }

  public void setVoltageWithFF(double voltage) {
    if (isNotMoving()) {
      motor.set(ControlMode.PercentOutput, voltage / 12., DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(voltage));
    } else {
      motor.set(ControlMode.PercentOutput, voltage / 12., DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }

  public double getFFIfMoving() {
    return m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle()));
  }

  public double getFFIfNotMoving(double error) {
    double sign = Math.signum(error);
    return m_gravityFF * Math.cos(NerdyMath.degreesToRadians(getAngle())) + 
      sign * m_staticFF;
  }
  
  @Override
  public void setPosition(double pos) {
    if (isNotMoving()) {
      motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(pos - getPosition()));
    } else {
      motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
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

  public void setAngleMotionMagic(double angle) {
    if (isNotMoving()) {
      motor.set(ControlMode.MotionMagic, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(angle - getAngle()));
    } else {
      motor.set(ControlMode.MotionMagic, angleToTicks(angle), DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }
  
  @Override
  public void setPositionMotionMagic(double pos) {
    if (isNotMoving()) {
      motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(pos - getPosition()));
    } else {
      motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, 
        getFFIfMoving());
    }
  }

  @Override
  public void setVelocity(double vel) {
    if (isNotMoving()) {
      motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(vel - getVelocity()));
    } else {
      motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, 
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

  @Override
  public void reportToSmartDashboard() {
    super.reportToSmartDashboard();
    SmartDashboard.putNumber(m_name + " Angle", getAngle());
  }

  @Override
  public void initLoggingData() {
    super.initLoggingData();
    NerdyBadlog.createTopic(m_name + "/Angle", () -> getAngle());
  }

}
