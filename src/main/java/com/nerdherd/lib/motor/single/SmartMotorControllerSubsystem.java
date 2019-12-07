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
public abstract class SmartMotorControllerSubsystem extends AbstractSingleMotor {

    /**
     * set a position to the mechanism using onboard TalonSRX position PID control
     * @param pos position in ticks
     */
    public abstract void setPosition(double pos);
    public abstract void setPosition(double pos, double arbFF);

    /**
     * set a voltage to the motor
     * @param voltage voltage to apply, -12 to 12 Volts
     */
    public abstract void setVoltage(double voltage);
    public abstract void setVoltage(double voltage, double arbFF);

    /**
     * Set a velocity to the mechanims using onboard TalonSRX velocity PID control
     * @param vel velocity in talon velocity sensor units (ticks/decisecnond)
     */
    public abstract void setVelocity(double vel);
    public abstract void setVelocity(double vel, double arbFF);
 
    /**
     * Set a position using the TalonSRX's onboard MotionMagic control mode
     * @param pos position in ticks
     */
    public abstract void setPositionMotionMagic(double pos);
    public abstract void setPositionMotionMagic(double pos, double arbFF);
    
    public abstract void setPositionOblargian(double pos);
    public abstract void setPositionOblargian(double pos, double arbFF);
    /**
     * Configure TalonSRX onboard PIDF
     * @param kP
     * @param kI
     * @param kD
     * @param kF
     */
    public abstract void configPIDF(double kP, double kI, double kD, double kF);

    /**
     * Configure TalonSRX deadband
     */
    public abstract void configTalonDeadband(double deadband);
  
    /**
     * Get the mechanism's velocity
     */
    public abstract double getVelocity();
  
    /**
     * Get the mechanism's position
     */
    public abstract double getPosition();
  
    /**
     * Get the mechanims's current
     */
    public abstract double getCurrent();
    
    /**
     * Configure a current limit
     * @param peak peak current, in amps
     * @param continuous continuous current, in amps
     */
    public abstract void configCurrentLimit(int peak, int continuous);

    /**
	 * Configure acceleration and velocity for motion magic
	 * @param accel acceleration in ticks/decisecond/second
	 * @param cruise_vel cruise velocity in ticks/decisecond
	 */
    public abstract void configMotionMagic(int accel, int cruise_vel);

    /**
     * Configure a feedback device for the Talon
     * @param device
     */
    public abstract void configSensor(FeedbackDevice device);

    /**
     * Set the boolean sensor phase, switch if the mechanism's position is changing incorrectly,
     * such as if forward is negative
     * @param phase
     */
    public abstract void setSensorPhase(boolean phase);

    /**
     * Reset the talons encoder to 0
     */
    public abstract void resetEncoder();
}
