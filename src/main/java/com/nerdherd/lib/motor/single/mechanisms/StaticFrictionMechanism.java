/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single.mechanisms;

import com.nerdherd.lib.motor.single.SingleMotorMechanism;

/**
 * Add your docs here.
 */
public abstract class StaticFrictionMechanism extends SingleMotorMechanism {

    // This is in STU (Stupid Talon Units, AKA ticks/decisecond)
    protected static final double kStaticFrictionDeadband = 5;

    public StaticFrictionMechanism(int talonID, String subsystemName, boolean inversion, boolean sensorPhase) {
        super(talonID, subsystemName, inversion, sensorPhase);
    }

    protected double m_staticFF;

    public void configStaticFF(double newStaticFF) {
      this.m_staticFF = newStaticFF / 12.0;
    }

    public boolean isNotMoving() {
      return Math.abs(getVelocity()) <= StaticFrictionMechanism.kStaticFrictionDeadband;
    }

    public abstract double getFFIfMoving();
    public abstract double getFFIfNotMoving(double error);

    public void setPowerWithFF(double power) {
        if (isNotMoving()) {
            motor.setPower(power, getFFIfNotMoving(power));
        } else {
            motor.setPower(power, getFFIfMoving());
        }
    }

    public void setVoltageWithFF(double voltage) {
        if (isNotMoving()) {
            motor.setPower(voltage, getFFIfNotMoving(voltage));
        } else {
            motor.setPower(voltage, getFFIfMoving());
        }
    }
  
    public void setPosition(double pos) {
        if (isNotMoving()) {
            motor.setPositionPID(pos, getFFIfNotMoving(pos - getPosition()));
        } else {
            motor.setPositionPID(pos, getFFIfMoving());
        }
    }
  
    public void setPositionMotionMagic(double pos) {
        if (isNotMoving()) {
            motor.setPositionMotionMagic(pos, getFFIfNotMoving(pos - getPosition()));
        } else {
            motor.setPositionPID(pos, getFFIfMoving());
        }
    }
  
    public void setVelocity(double vel) {
        if (isNotMoving()) {
            motor.setVelocity(vel, getFFIfNotMoving(vel - getVelocity()));
        } else {
            motor.setVelocity(vel, getFFIfMoving());
        }
    }

}
