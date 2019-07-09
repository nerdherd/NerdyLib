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
public interface CANMotorController {

    public abstract void setPower(double power);

    public abstract void setPower(double power, double arbitraryFF);

    public abstract void setVoltage(double voltage);

    public abstract void setVoltage(double voltage, double arbitraryFF);

    public abstract double getVoltage();

    public abstract void configDefaultSettings();

    public abstract void setInversion(boolean inversion);

    public abstract void setBrakeMode();

    public abstract void setCoastMode();

    public abstract void configVoltageCompensation(double voltage);


}
