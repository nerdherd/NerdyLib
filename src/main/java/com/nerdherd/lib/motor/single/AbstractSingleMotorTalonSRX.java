/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;

/**
 * Add your docs here.
 */
public abstract class AbstractSingleMotorTalonSRX extends AbstractSingleMotor {

    public abstract void setPosition(double pos);

    public abstract void setVoltage(double voltage);

    public abstract void setVelocity(double vel);
  
    public abstract void setPositionMotionMagic(double pos);
  
    public abstract void configPIDF(double kP, double kI, double kD, double kF);
  
    public abstract double getVelocity();
  
    public abstract double getPosition();
  
    public abstract double getCurrent();
    
    public abstract void configCurrentLimit(int peak, int continuous);

    public abstract void configMotionMagic(int accel, int cruise_vel);

    public abstract void configSensor(FeedbackDevice device);

    public abstract void setSensorPhase(boolean phase);

    public abstract void resetEncoder();
}
