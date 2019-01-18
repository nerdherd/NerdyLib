/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.motor;

import com.nerdherd.lib.misc.Loggable;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public abstract class AbstractSingleMotor extends Subsystem implements Loggable {
  
  public abstract void setPower(double power);

  public abstract void reportToSmartDashboard();

  public abstract void setInversion(boolean inversion);
  
  public abstract double getVoltage();
    
}
