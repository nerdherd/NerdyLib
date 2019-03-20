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

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class SingleMotorElevator extends GravityAffectedMechanism {

  protected double m_distanceRatio, m_distanceOffset;

  public SingleMotorElevator(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
    super.m_gravityFF = 0;
    super.m_staticFF = 0;
    m_distanceRatio = 1;
    m_distanceOffset = 0;
  }

  public void configDistanceRatio(double newDistanceRatio) {
    m_distanceRatio = newDistanceRatio;
  }

  public void configDistanceOffset(double newDistanceOffset) {
    m_distanceOffset = newDistanceOffset;
  }

  public void configHeightConversion(double newDistanceRatio, double newDistanceOffset) {
    m_distanceRatio = newDistanceRatio;
    m_distanceOffset = newDistanceOffset;
  }

  public double getFFIfMoving() {
    return m_gravityFF;
  }

  public double getFFIfNotMoving(double error) {
    double sign = Math.signum(error);
    return m_gravityFF + sign * m_staticFF;
  }

  public void setHeight(double height) {
    if (isNotMoving()) {
      motor.set(ControlMode.Position, heightToTicks(height), DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(height - getHeight()));
    } else {
      motor.set(ControlMode.Position, heightToTicks(height), DemandType.ArbitraryFeedForward, getFFIfMoving());
    }
  }

  public void setHeightMotionMagic(double height) {
    if (isNotMoving()) {
      motor.set(ControlMode.MotionMagic, heightToTicks(height), DemandType.ArbitraryFeedForward, 
        getFFIfNotMoving(height - getHeight()));
    } else {
      motor.set(ControlMode.MotionMagic, heightToTicks(height), DemandType.ArbitraryFeedForward, getFFIfMoving());
    }
  }

  public double heightToTicks(double height) {
    return (height - m_distanceOffset) / m_distanceRatio;
  }

  public double ticksToHeight(double ticks) {
    return (m_distanceRatio * ticks) + m_distanceOffset;
  }

  public double getHeight() {
    return ticksToHeight(this.getPosition());
  }

  @Override
  public void reportToSmartDashboard() {
    super.reportToSmartDashboard();
    SmartDashboard.putNumber(name + " Height", getHeight());
  }

  @Override
  public void initLoggingData() {
    super.initLoggingData();
    NerdyBadlog.createTopic(name + "/Height", () -> getHeight());
  }

}