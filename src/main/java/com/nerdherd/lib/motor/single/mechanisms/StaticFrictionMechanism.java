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
            // motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, 
            //     getFFIfNotMoving(power));
        } else {
            // motor.set(ControlMode.PercentOutput, power, DemandType.ArbitraryFeedForward, 
            //     getFFIfMoving());
        }
    }

    public void setVoltageWithFF(double voltage) {
        if (isNotMoving()) {
            // motor.set(ControlMode.PercentOutput, voltage / 12., DemandType.ArbitraryFeedForward, 
            //     getFFIfNotMoving(voltage));
        } else {
            // motor.set(ControlMode.PercentOutput, voltage / 12., DemandType.ArbitraryFeedForward, 
            //     getFFIfMoving());
        }
    }
  
    public void setPosition(double pos) {
        if (isNotMoving()) {
            // motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, 
            //     getFFIfNotMoving(pos - getPosition()));
        } else {
            // motor.set(ControlMode.Position, pos, DemandType.ArbitraryFeedForward, 
            //     getFFIfMoving());
        }
    }
  
    public void setPositionMotionMagic(double pos) {
        if (isNotMoving()) {
            // motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, 
            //     getFFIfNotMoving(pos - getPosition()));
        } else {
            // motor.set(ControlMode.MotionMagic, pos, DemandType.ArbitraryFeedForward, 
            //     getFFIfMoving());
        }
    }
  
    public void setVelocity(double vel) {
        if (isNotMoving()) {
            // motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, 
            //     getFFIfNotMoving(vel - getVelocity()));
        } else {
            // motor.set(ControlMode.Velocity, vel, DemandType.ArbitraryFeedForward, 
            //     getFFIfMoving());
        }
    }

}
