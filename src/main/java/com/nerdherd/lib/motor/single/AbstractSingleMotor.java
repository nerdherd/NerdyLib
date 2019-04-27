/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor.single;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.nerdherd.lib.logging.Loggable;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public abstract class AbstractSingleMotor extends Subsystem implements Loggable {

  public String name;
  
  /**
   * Set power in a percentage (-1 to 1) to the motor controller
   * @param power percentage -1 to 1, which is equivalent to -12 to 12 Volts
   */
  public abstract void setPower(double power);

  /**
   * Set power in a percentage (-1 to 1) to the motor controller, with an arbitrary feedforward power added
   * @param power percentage -1 to 1, which is equivalent to -12 to 12 Volts
   * @param arbFF percentage -1 to 1, added to power
   */
  public abstract void setPower(double power, double arbFF);

  /**
   * Report sensor data to smart dashboard
   */
  public abstract void reportToSmartDashboard();

  /**
   * Set the boolean inversion of the motor controller
   * @param inversion
   */
  public abstract void setInversion(boolean inversion);
  
  /**
   * get the output voltage of the motor
   */
  public abstract double getVoltage();
    
}
