/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.motorcontrollers;

/**
 * Add your docs here.
 */
public interface SmartCANMotorController extends CANMotorController{


    public abstract void setPositionMotionMagic(double pos);

    public abstract void setPositionMotionMagic(double pos, double arbitraryFF);

    public abstract void setPositionPID(double pos);

    public abstract void setPositionPID(double pos, double arbitraryFF);

    public abstract void setVelocity(double velocity);

    public abstract void setVelocity(double velocity, double arbitraryFF);

    public abstract double getCurrent();

    public abstract void configPIDF(double p, double i, double d, double f, int slot);

    public abstract void configMotionMagic(int accel, int vel);

    public abstract void configCurrentLimitPeak(double current);

    public abstract void configCurrentLimitContinuous(double current);


}
