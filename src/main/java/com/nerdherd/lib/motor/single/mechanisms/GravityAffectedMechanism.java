/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single.mechanisms;

import com.nerdherd.lib.motor.single.SingleMotorTalonSRX;

/**
 * Add your docs here.
 */
public abstract class GravityAffectedMechanism extends StaticFrictionMechanism {

  public GravityAffectedMechanism(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
    super(talonID, subsystemName, inversion, sensorPhase);
  }

  // These are initialized in volts using the config methods, but are stored as percent outputs
  protected double m_gravityFF;

  public void configGravityFF(double newGravityFF) {
    this.m_gravityFF = newGravityFF / 12.0;
  }

  public void configFFs(double newGravityFF, double newStaticFF) {
    this.configGravityFF(newGravityFF);
    this.configStaticFF(newStaticFF);
  }

}
