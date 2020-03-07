/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.motorcontrollers;

import com.ctre.phoenix.motorcontrol.IMotorController;

/**
 * Add your docs here.
 */
public interface CANMotorController {

    /**
     * 
     * @param power duty cycle from -1 to 1, representing -12 to 12V
     */
    public abstract void setPower(double power);

    /**
     * 
     * @param power duty cycle from -1 to 1, representing -12 to 12V
     * @param arbitraryFF arbitrary feedforward, also in units of -1 to 1 
     */
    public abstract void setPower(double power, double arbitraryFF);

    /**
     * 
     * @param voltage voltage to set, should be from -12 to 12 V
     */
    public abstract void setVoltage(double voltage);

    /**
     * 
     * @param voltage voltage to set, should be from -12 to 12 V
     * @param arbitraryFF arbitrary feedforward, also in units of -1 to 1 
     */
    public abstract void setVoltage(double voltage, double arbitraryFF);

    /**
     * 
     * @return motor controller output voltage
     */
    public abstract double getVoltage();

    /**
     * Enable default settings (voltage compensate at 12 V)
     */
    public abstract void configDefaultSettings();

    /**
     * Set Inversion of motor controller, if the mechanism is moving in the wrong direction when given a voltage (for example moving down when given a positive voltage)
     * @param inversion 
     */
    public abstract void setInversion(boolean inversion);

    public abstract void setBrakeMode();

    public abstract void setCoastMode();

    /**
     * Set a voltage for 1 percent output to equal
     * For example setPower(1.0) will set 12 volts to the motor
     * @param voltage
     */
    public abstract void configVoltageCompensation(double voltage);

    /**
     * Set a CAN Motor Controller to follow. CTRE motor controllers cannot follow REV motor controllers but REV motor controllers can follow CTRW motor controllers
     * @param master
     */
    public abstract void followCANMotorController(CANMotorController master);

    public abstract int getID();

    public abstract boolean getInversion();

    public default void configFollowers(CANMotorController[] followers) {
        for (CANMotorController follower : followers) {
            follower.configDefaultSettings();
            follower.followCANMotorController(this);
            follower.setInversion(this.getInversion());
            follower.setBrakeMode();
        }
    }

    public abstract void configDeadband(double deadband);

    public abstract void configCurrentLimitPeak(double current);

    public abstract void configCurrentLimitContinuous(double current);    


}
