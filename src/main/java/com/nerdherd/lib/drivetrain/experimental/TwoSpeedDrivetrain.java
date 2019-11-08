/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package com.nerdherd.lib.drivetrain.experimental;

/**
 * Add your docs here.
 */
public interface TwoSpeedDrivetrain {

    
    /**
   * Shift into high gear
   */
  public abstract void shiftHigh();
  
  /**
   * Shift into low gear
   */
  public abstract void shiftLow();

  /**
   * Shift from high gear to low gear or vice versa
   */
  public abstract void switchShift();

}
